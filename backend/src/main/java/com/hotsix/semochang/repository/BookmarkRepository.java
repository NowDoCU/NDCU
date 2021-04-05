package com.hotsix.semochang.repository;

import com.hotsix.semochang.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    /***
     * founderId로 북마크 리스트 조회
     * @param founderId
     * @return
     */
    List<Bookmark> findAllByFounderId(Long founderId);
}
