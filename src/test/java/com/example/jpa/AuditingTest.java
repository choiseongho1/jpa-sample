package com.example.jpa;

import com.example.jpa.entity.Article;
import com.example.jpa.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class AuditingTest {

    @Autowired ArticleRepository articleRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void auditing_시간차이_확인() throws InterruptedException {
        // ✅ 최초 저장
        Article article = new Article();
        article.setTitle("처음 글");
        article.setContent("내용입니다");
        articleRepository.save(article);

        em.flush();
        em.clear();

        System.out.println("🕓 최초 저장: " + article.getCreatedAt() + " / " + article.getUpdatedAt());

        // ✅ 1초 대기 후 수정
        Thread.sleep(1000);

        Article found = articleRepository.findById(article.getId()).orElseThrow();
        found.setContent("수정된 내용입니다");
        articleRepository.save(found);

        em.flush();
        em.clear();

        Article updated = articleRepository.findById(article.getId()).orElseThrow();
        System.out.println("🕘 수정 후: " + updated.getCreatedAt() + " / " + updated.getUpdatedAt());
    }
}