package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.ChatRoom;
import com.inline.sub2.api.dto.ChatUserDto;
import com.inline.sub2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
//    private final com.hoon.chat.repo.ChatRoomRepoImpl chatRoomRepository;
    private final JwtUtil jwtUtil;
//    private final ChatRoomService chatRoomService;

    //채팅 리스트 화면
    @GetMapping("/office")
    public String rooms(Model model) {
        System.out.println("가오 : 개같은거");
        return "chat/office";
    }

//    //모든 채팅방 목록 반환
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        try {
//            return chatRoomService.findAllRoom();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return null;
//        }
//    }

    //채팅방 생성
//    @PostMapping("/room")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestParam String name) {
//        try {
//            return chatRoomService.createChatRoom(name);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return null;
//        }
//    }

//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId",roomId);
//        return "/chat/roomdetail";
//    }

//    //특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        try {
//            return chatRoomService.findRoomById(roomId);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return null;
//        }
//    }

    @GetMapping("/user")
    @ResponseBody
    public ChatUserDto getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        System.out.println("이름을 찾자.."+name);
        return ChatUserDto.builder().name(name).token(jwtUtil.createToken(name)).build();
    }
}