package com.example.users_blogs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.users_blogs.JwtUtil;
import com.example.users_blogs.model.Blog;
import com.example.users_blogs.model.Role;
import com.example.users_blogs.model.User;
import com.example.users_blogs.repository.BlogRepository;
import com.example.users_blogs.repository.UserRepository;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public List<Blog> getAll() {
        return blogRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Blog blog,
                                    @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User currentUser = userRepo.findByUsername(username);

        blog.setUser(currentUser);
        return ResponseEntity.ok(blogRepo.save(blog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Blog blog,
                                    @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        User currentUser = userRepo.findByUsername(username);

        Blog existingBlog = blogRepo.findById(id).orElseThrow();

        // USER chỉ được sửa blog của chính mình
        if (!existingBlog.getUser().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền sửa blog này");
        }

        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        return ResponseEntity.ok(blogRepo.save(existingBlog));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        blogRepo.deleteById(id);
    }
}
