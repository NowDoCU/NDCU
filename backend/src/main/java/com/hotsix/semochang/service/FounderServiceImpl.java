package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.model.LoginRequestDTO;
import com.hotsix.semochang.model.LoginResponseDTO;
import com.hotsix.semochang.repository.FounderRepository;
import com.hotsix.semochang.utils.JWTUtil;
import com.hotsix.semochang.utils.TempKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * author: pinest94
 * since: 2021-04-01
 */

@Service
@Slf4j
public class FounderServiceImpl implements FounderService{

    @Autowired
    private FounderRepository founderRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<?> create(Founder founder) {

        // 1. 요청이 잘못들어온 경우
        if(founder == null) {
            return new ResponseEntity<>("요청이 잘못되었습니다.", HttpStatus.NO_CONTENT);
        }

        // 2. 해당 이메일이 존재하는 지 확인
        Optional<Founder> optional = founderRepository.findByEmail(founder.getEmail());
        if(optional.isPresent()) { return new ResponseEntity<>(HttpStatus.CONFLICT); }

        // 2. 패스워드 해싱 암호화
        log.info("{}", founder.getPassword());
        String encodedPassword = passwordEncoder.encode(founder.getPassword());
        log.info("{}", encodedPassword);
        founder.setPassword(encodedPassword);

        // 3. autoKey 생성
        founder.setVerificationKey(new TempKeyUtil().getKey(50, false));
        founder.setStatus("ACTIVATED");

        // 4. founder 저장
        founderRepository.save(founder);

        return new ResponseEntity<>(founder, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> read(Long id) {

        Optional<Founder> optional = founderRepository.findById(id);
        
        return optional
                .map(founder -> new ResponseEntity<>(founder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("No Data", HttpStatus.OK));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Founder founder) {
        
        // 1. 요청이 잘못들어온 경우
        if(founder == null) {
            return new ResponseEntity<>("요청이 잘못되었습니다.", HttpStatus.NO_CONTENT);
        }

        // 2. 수정할 founder가 있는 지 확인
        Optional<Founder> optional = founderRepository.findById(founder.getId());
        
        return optional
                .map(updateFounder -> {
                    updateFounder
                            .setName(founder.getName())
                            .setEmail(founder.getEmail())
                            .setPassword(passwordEncoder.encode(founder.getPassword()))
                            .setPhoneNumber(founder.getPhoneNumber());

                    return updateFounder;
                })
                .map(updatedFounder -> founderRepository.save(updatedFounder))
                .map(updatedFounder -> new ResponseEntity<>(updatedFounder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("No Data", HttpStatus.NO_CONTENT));
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Founder> optional = founderRepository.findById(id);

        return optional
                .map(founder -> {
                    founderRepository.delete(founder);
                    return new ResponseEntity<>(HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>("No Data", HttpStatus.NO_CONTENT));
    }

    @Override
    @Transactional
    public ResponseEntity<?> authenticate(LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        
        // 1. 해당 founder가 있는지 확인 -> 인증 X(unauthorized) 리턴
        Optional<Founder> optional = founderRepository.findByEmail(email);

        return optional
                .map(founder -> {
                    // 2. 해당 founder의 패스워드 일치여부 확인 -> 일치하지 않다면 인증 X(unauthorized) 리턴
                    if(!passwordEncoder.matches(password, founder.getPassword())) {
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                    }

                    String accessToken
                            = jwtUtil.createToken(founder.getId(), founder.getEmail(), founder.getName(), founder.getStatus());
                            LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder().accessToken(accessToken).build();

                    // 3. accessToken 생성해서 리턴 -> 200 OK
                    return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
