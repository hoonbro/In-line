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
    @ApiOperation(value = "출근 버튼 클릭 시 출근 처리한다")
    public ResponseEntity<Map<String,Object>> in(@RequestBody CommuteDto commuteDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        CommuteEntity commuteEntity = null;
        Map<String,Object> map = new HashMap<String,Object>();

        try{
             commuteEntity = commuteService.commuteIn(commuteDto);
            System.out.println(commuteEntity.getCommuteId()+"@#@@@@@@@@@@@@@@");
            httpStatus = HttpStatus.CREATED;
            map.put("commuteId",commuteEntity.getCommuteId());
            map.put("comeIn",commuteEntity.getComeIn());
        }
        catch(Exception e) {
           httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
    }


    @PatchMapping("/out/{commuteId}")
    @ApiOperation(value = "퇴근 버튼 클릭 시 퇴근 처리한다")
    public ResponseEntity<Map<String,Object>> out(@PathVariable("commuteId") Long commuteId) {
        System.out.println(commuteId);

        HttpStatus httpStatus = HttpStatus.OK;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            CommuteEntity commuteEntity = commuteService.commuteOut(commuteId);
            map.put("commuteId",commuteEntity.getCommuteId());
            map.put("comeOut",commuteEntity.getComeOut());
            httpStatus = HttpStatus.CREATED;
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
    }



}
