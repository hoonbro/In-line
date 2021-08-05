package com.inline.sub2.api.controller;

import com.inline.sub2.api.service.ChatService;
import com.inline.sub2.db.entity.ChatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping("/{officeId}")
    public ResponseEntity<List<ChatEntity>> chatList(@PathVariable("officeId") Long officeId){
        HttpStatus httpStatus = HttpStatus.OK;
        List<ChatEntity> list = chatService.chatList(officeId);

        return new ResponseEntity<List<ChatEntity>>(list,httpStatus);
    }



}
