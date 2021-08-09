package com.inline.sub2.api.controller;

import com.inline.sub2.api.service.AdminService;
import com.inline.sub2.db.entity.CommuteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/members/commute")
    public ResponseEntity<Map<String, List<CommuteEntity>>> getCommutes(@RequestParam("officeId") Long officeId){
        HttpStatus status = HttpStatus.OK;
        Map<String, List<CommuteEntity>> map = null;
        try{
            map = adminService.getCommutesByDate(officeId);
        }catch (Exception e){
            log.info("error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, List<CommuteEntity>>>(map, status);
    }

}
