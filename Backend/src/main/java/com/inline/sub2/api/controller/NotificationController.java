package com.inline.sub2.api.controller;

import com.inline.sub2.api.service.NotificationService;
import com.inline.sub2.db.entity.NotificationEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping
    @ApiOperation(value = "userId별 전체 알림 기록을 반환한다.", response = List.class)
    public ResponseEntity<List<NotificationEntity>> getList(@RequestParam("userId") Long userId){
        HttpStatus httpStatus = HttpStatus.OK;
        List<NotificationEntity> list = null;
        try{
            list = notificationService.notificationList(userId);
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<List<NotificationEntity>>(list,httpStatus);
        }
        return new ResponseEntity<List<NotificationEntity>>(list,httpStatus);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("notificationId") Long notificationId){
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        try{
           notificationService.deleteNotification(notificationId);
        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<Void>(httpStatus);
        }
        return new ResponseEntity<Void>(httpStatus);
    }






}
