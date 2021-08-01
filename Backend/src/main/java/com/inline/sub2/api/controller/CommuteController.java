package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.CommuteDto;
import com.inline.sub2.api.service.CommuteService;
import com.inline.sub2.db.entity.CommuteEntity;
import com.querydsl.core.types.dsl.Param;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/commute")
public class CommuteController {

    @Autowired
    CommuteService commuteService;

    @PostMapping("/in")
    @ApiOperation(value = "출근 버튼 클릭 시 출근 처리한다", response = CommuteEntity.class)
    public ResponseEntity<CommuteEntity> in(@RequestBody CommuteDto commuteDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        CommuteEntity commuteEntity = null;
        try{
            commuteEntity = commuteService.commuteIn(commuteDto);
            httpStatus = HttpStatus.CREATED;
        }
        catch(Exception e) {
           httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<CommuteEntity>(commuteEntity,httpStatus);
    }


    @PatchMapping("/out/{commuteId}")
    @ApiOperation(value = "퇴근 버튼 클릭 시 퇴근 처리한다", response = CommuteEntity.class)
    public ResponseEntity<CommuteEntity> out(@PathVariable("commuteId") Long commuteId) {
        HttpStatus httpStatus = HttpStatus.OK;
        CommuteEntity commuteEntity = null;
        try {
            commuteEntity = commuteService.commuteOut(commuteId);
            httpStatus = HttpStatus.CREATED;
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<CommuteEntity>(commuteEntity,httpStatus);
    }



}
