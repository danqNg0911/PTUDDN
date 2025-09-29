package com.example.users_blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.users_blogs.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}