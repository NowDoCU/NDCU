package com.hotsix.semochang.service;

import com.hotsix.semochang.model.Bookmark;
import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.Founder;
import com.hotsix.semochang.repository.BookmarkRepository;
import com.hotsix.semochang.repository.CommercialRepository;
import com.hotsix.semochang.repository.FounderRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * author: pinest94
 * since: 2021-04-02
 */

@Service
@Slf4j
public class BookmarkServiceImp implements BookmarkService{

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    FounderRepository founderRepository;

    @Autowired
    CommercialRepository commercialRepository;

    @Override
    @Transactional
    public ResponseEntity<?> create(String commercialCode, Authentication authentication) {

        // 1. authentication으로 founder 정보 가져오기
        Claims claims = (Claims) authentication.getPrincipal();
        Long founderId = claims.get("id", Long.class);

        Founder founder = founderRepository.findById(founderId).orElse(null);
        Optional<Commercial> commercialOptional = commercialRepository.findByCommercialCode(commercialCode);

        return commercialOptional
                .map(commercial -> {
                    Bookmark newBookmark = Bookmark.builder()
                            .founder(founder)
                            .commercial(commercial)
                            .build();

                    bookmarkRepository.save(newBookmark);

                    return new ResponseEntity<>(HttpStatus.CREATED);
                })
                .orElseGet(() -> new ResponseEntity<>("Not Found Commercial", HttpStatus.NO_CONTENT));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Optional<Bookmark> optional = bookmarkRepository.findById(id);

        return optional
                .map(bookmark -> {
                    bookmarkRepository.delete(bookmark);
                    return new ResponseEntity<>(HttpStatus.OK);
                })
                .orElseGet(()-> new ResponseEntity<>("No Data", HttpStatus.NO_CONTENT));
    }
}
