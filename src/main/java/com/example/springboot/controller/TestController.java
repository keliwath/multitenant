package com.example.springboot.controller;

import com.example.springboot.jpa.entities.People;
import com.example.springboot.jpa.repos.PeopleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    private final PeopleRepo peopleRepo;

    public TestController(PeopleRepo peopleRepo) {
        this.peopleRepo = peopleRepo;
    }

    @GetMapping()
    public List<String> all() {
        return List.of("A", "B");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean isAdmin() {
        return true;
    }

    @GetMapping("/people")
    public Iterable<People> getPeople() {
        return this.peopleRepo.findAll();
    }


}


