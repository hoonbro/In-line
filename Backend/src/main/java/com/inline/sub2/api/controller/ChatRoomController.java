//package com.inline.sub2.api.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.SQLException;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/chat")
//public class ChatRoomController {
//    private final com.hoon.chat.repo.ChatRoomRepoImpl chatRoomRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final ChatRoomService chatRoomService;
//
//    //채팅 리스트 화면
//    @GetMapping("/room")
//    public String rooms(Model model) {
//        return "/chat/room";
//    }
//
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
//
//    //채팅방 생성
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
//
//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId",roomId);
//        return "/chat/roomdetail";
//    }
//
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
//
//    @GetMapping("/user")
//    @ResponseBody
//    public UserDto getUserInfo(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        return UserDto.builder().name(name).token(jwtTokenProvider.createToken(name)).build();
//    }
//}
