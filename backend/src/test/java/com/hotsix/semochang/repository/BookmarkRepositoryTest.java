package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Bookmark;
import com.hotsix.semochang.model.Commercial;
import com.hotsix.semochang.model.Founder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BookmarkRepositoryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    CommercialRepository commercialRepository;

    @Autowired
    FounderRepository founderRepository;

    private Founder founder;

    @BeforeEach
    public void init() {
        founder = founderRepository.findById(1L).orElse(null);
    }

    @Test
    public void createTest() {

        Founder founder = founderRepository.findById(1L).orElse(null);
        Commercial commercial = commercialRepository.findByCommercialCode("1000001").orElse(null);

        Bookmark bookmark = Bookmark.builder().founder(founder).commercial(commercial).build();
        bookmarkRepository.save(bookmark);
    }

    @Test
    @Transactional
    public void readTest() {
        Founder founder = founderRepository.findById(1L).orElse(null);
        founder.getBookmarkList()
                .stream()
                .forEach(bookmark ->
                        System.out.println(bookmark.getCommercial().getCommercialName()));
    }

    @Test
    @Transactional
    public void deleteTest() {

    }
}