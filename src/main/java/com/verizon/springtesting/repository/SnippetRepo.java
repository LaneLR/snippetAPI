package com.verizon.springtesting.repository;

import com.verizon.springtesting.models.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetRepo extends JpaRepository<Snippet, Integer> {
    List<Snippet> findByLanguageIgnoreCase(String language);
}
