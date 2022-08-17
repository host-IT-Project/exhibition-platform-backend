package com.hostit.exhibitionplatform.domain.user;

import com.hostit.exhibitionplatform.domain.user.entity.User;
import com.hostit.exhibitionplatform.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserTestController {
    private final UserRepository repository;

    @GetMapping("/user")
    public List<User> findAllUser() {
        return repository.findAll();
    }
}
