package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.DeptUserDto;
import com.inline.sub2.api.service.AdminService;
import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/members/commute")
    @ApiOperation(value = "Office별 전체 구성원의 출퇴근 기록을 반환한다.", response = Map.class)
    public ResponseEntity<Map<String, List<CommuteEntity>>> getCommutes(@RequestParam("officeId") Long officeId){
        HttpStatus status = HttpStatus.OK;
        Map<String, List<CommuteEntity>> map = null;
        try{
            map = adminService.getCommutes(officeId);
            log.info("전체 구성원 출퇴근 기록 반환 성공");
        }catch (Exception e){
            log.error("전체구성원 출퇴근 기록 반환 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, List<CommuteEntity>>>(map, status);
    }

    @GetMapping("/dashboard/{officeId}")
    @ApiOperation(value = "office별 인원 관리 정보(구성원 수, 부서별 사원 수)를 반환한다.", response =  Map.class)
    public ResponseEntity<Map<String, Collection>> getCounts(@PathVariable("officeId") Long officeId){
        Set<Integer> officeSet = new HashSet<>();
        Map<String, Collection> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            //전체 구성원 수
            int officeUsers = adminService.getOfficeUserCount(officeId);
            officeSet.add(officeUsers);

            //부서별 구성원 수
            List<DeptUserDto> dept = adminService.getDeptUserCount(officeId);

            map.put("officeUserCount", officeSet);
            map.put("deptUserCount", dept);
            log.info("인원 관리 정보 반환 성공");
        }catch (Exception e){
            log.error("인원 관리 정보 반환 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Collection>>(map, status);
    }

    @PutMapping("/user/{userId}")
    @ApiOperation(value = "구성원을 퇴사 처리하고 변경된 구성원의 데이터를 반환한다.", response = UserEntity.class)
    public ResponseEntity<UserEntity> retireUser(@PathVariable Long userId){
        HttpStatus status = HttpStatus.CREATED;
        UserEntity userEntity = null;
        try{
            userEntity = adminService.retireUser(userId);
            log.info("구성원 퇴사처리 성공");
        }catch (Exception e){
            log.error("구성원 퇴사처리 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<UserEntity>(userEntity, status);
    }

    @GetMapping("/retireRate/{officeId}")
    @ApiOperation(value = "요청한 회사의 퇴사율을 반환한다.", response = Double.class)
    public ResponseEntity<Double> getRetireRate(@PathVariable Long officeId){
        HttpStatus status = HttpStatus.OK;
        double retireRate = 0;
        try{
            retireRate = adminService.getRetireRate(officeId);
            log.info("퇴사율 반환 성공");
        }catch (Exception e){
            log.error("퇴사율 반환 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Double>(retireRate, status);
    }
}
