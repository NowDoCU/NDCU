package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Bookmark;
import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.model.network.request.FounderApiRequest;
import com.hotsix.semochang.model.network.request.LoginApiRequest;
import com.hotsix.semochang.model.network.response.FounderApiResponse;
import com.hotsix.semochang.model.network.response.LoginApiResponse;
import com.hotsix.semochang.repository.FounderRepository;
import com.hotsix.semochang.utils.JWTUtil;
import com.hotsix.semochang.utils.TempKeyUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<?> create(FounderApiRequest request) {

        // 1. 요청이 잘못들어온 경우
        if(request == null) {
            return new ResponseEntity<>("요청이 잘못되었습니다.", HttpStatus.NO_CONTENT);
        }

        // 2. 해당 이메일이 존재하는 지 확인
        Optional<Founder> optional = founderRepository.findByEmail(request.getEmail());
        if(optional.isPresent()) { return new ResponseEntity<>(HttpStatus.CONFLICT); }

        Founder newFounder = Founder.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 패스워드 해싱 암호화
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .verificationKey(new TempKeyUtil().getKey(50, false))
                .status("ACTIVATED")
                .build();

        // 4. founder 저장
        founderRepository.save(newFounder);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<FounderApiResponse> read(Authentication authentication) {
        
        // authentication으로 id값 가져오기
        Claims claims = (Claims) authentication.getPrincipal();
        Long founderId = claims.get("id", Long.class);

        Optional<Founder> optional = founderRepository.findById(founderId);
        
        return optional
                .map(founder -> {
                    for(Bookmark bookmark : founder.getBookmarkList()) {
                        bookmark.getCommercial().setEstimatedSalesList(null);
                        bookmark.getCommercial().setEstimatedPopulationList(null);
                        bookmark.getCommercial().setStoreRentalPrice(null);
                    }

                    FounderApiResponse response = FounderApiResponse.builder()
                            .id(founder.getId())
                            .email(founder.getEmail())
                            .name(founder.getName())
                            .status(founder.getStatus())
                            .phoneNumber(founder.getPhoneNumber())
                            .bookmarkList(founder.getBookmarkList())
                            .build();


                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity("데이터 없음", HttpStatus.OK));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(FounderApiRequest request,
                                    Authentication authentication) {
        
        // 1. 요청이 잘못들어온 경우
        if(request == null) {
            return new ResponseEntity<>("요청이 잘못되었습니다.", HttpStatus.NO_CONTENT);
        }

        // authentication으로 id값 가져오기
        Claims claims = (Claims) authentication.getPrincipal();
        Long founderId = claims.get("id", Long.class);

        Optional<Founder> optional = founderRepository.findById(founderId);

        return optional
                .map(updateFounder -> {
                    updateFounder
                            .setId(updateFounder.getId())
                            .setName(request.getName())
                            .setEmail(request.getEmail())
                            .setPassword(passwordEncoder.encode(request.getPassword()))
                            .setStatus(updateFounder.getStatus())
                            .setPhoneNumber(request.getPhoneNumber());

                    return updateFounder;
                })
                .map(updatedFounder -> founderRepository.save(updatedFounder))
                .map(updatedFounder -> new ResponseEntity<>(updatedFounder, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("데이터 없음", HttpStatus.NO_CONTENT));
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
    public ResponseEntity<?> authenticate(LoginApiRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();
        
        // 1. 해당 founder가 있는지 확인 -> 인증 X(unauthorized) 리턴
        Optional<Founder> optional = founderRepository.findByEmail(email);

        return optional
                .map(founder -> {
                    // 2. 해당 founder의 패스워드 일치여부 확인 -> 일치하지 않다면 인증 X(unauthorized) 리턴
                    if(!passwordEncoder.matches(password, founder.getPassword())) {
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                    }

                    String accessToken
                            = jwtUtil.createToken(founder.getId(),
                            founder.getEmail(),
                            founder.getName(),
                            founder.getStatus());

                    LoginApiResponse response = LoginApiResponse.builder().accessToken(accessToken).build();

                    // 3. accessToken 생성해서 리턴 -> 200 OK
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
