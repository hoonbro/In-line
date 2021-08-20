package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.CommuteDto;
import com.inline.sub2.api.service.CommuteService;
import com.inline.sub2.db.entity.CommuteEntity;
import com.querydsl.core.types.dsl.Param;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/commute")
public class CommuteController {

    @Autowired
    CommuteService commuteService;

    @PutMapping("/{commuteId}/in")
    @ApiOperation(value = "출근 버튼 클릭 시 출근 처리한다", response = CommuteEntity.class)
    public ResponseEntity<String> in(@PathVariable("commuteId") Long commuteId) {
        HttpStatus httpStatus = HttpStatus.OK;
        CommuteEntity commuteEntity = null;
        String comeIn = "";
        try{
            commuteEntity = commuteService.commuteIn(commuteId);

            Date In = commuteEntity.getComeIn();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             comeIn = transFormat.format(In).split(" ")[1];
            httpStatus = HttpStatus.CREATED;
        }
        catch(Exception e) {
           httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(comeIn,httpStatus);
    }


    @PutMapping("/{commuteId}/out")
    @ApiOperation(value = "퇴근 버튼 클릭 시 퇴근 처리한다", response = CommuteEntity.class)
    public ResponseEntity<String> out(@PathVariable("commuteId") Long commuteId) {
        HttpStatus httpStatus = HttpStatus.OK;
        CommuteEntity commuteEntity = null;
        String comeOut ="";
        try {
            commuteEntity = commuteService.commuteOut(commuteId);

            Date Out = commuteEntity.getComeOut();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comeOut = transFormat.format(Out).split(" ")[1];

            httpStatus = HttpStatus.CREATED;
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(comeOut,httpStatus);
    }



}
