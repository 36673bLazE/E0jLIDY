// 代码生成时间: 2025-09-16 19:33:45
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}

@RestController
@RequestMapping("/api")
class AccessControlledRestController {

    @GetMapping("/secure/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getSecureItem(@PathVariable("itemId") String itemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Access to secure item with ID: " + itemId + " granted to: " + authentication.getName());
    }

    @GetMapping("/public/{itemId}")
    public ResponseEntity<String> getPublicItem(@PathVariable("itemId") String itemId) {
        return ResponseEntity.ok("Access to public item with ID: " + itemId);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error occurred: " + e.getMessage());
    }
}
