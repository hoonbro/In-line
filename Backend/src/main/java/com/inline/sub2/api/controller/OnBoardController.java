package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.service.OnBoardService;
import com.inline.sub2.db.entity.OnBoardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/on-board")
public class OnBoardController {

    @Autowired
    OnBoardService onBoardService;

    @PostMapping("/user")
    public ResponseEntity<Void> registUserOnboard(@RequestBody UserRegistDto user) {
        OnBoardEntity onBoardEntity = onBoardService.registUserOnboard(user);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
