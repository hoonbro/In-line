package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.DeptUserDto;
import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.JobEntity;
import com.inline.sub2.db.entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/office")
@CrossOrigin("*")
public class OfficeController {

    @Autowired
    UserService userService;

    @Autowired
    OfficeService officeService;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @PostMapping
    @ApiOperation(value = "회사 정보와 관리자 정보를 DB에 저장한다.")
    public ResponseEntity<Void> registAdmin(@RequestBody UserRegistDto admin) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        UserEntity userEntity = null;
        try{
            userEntity = userService.registAdmin(admin);
            if (userEntity != null) {
                httpStatus = HttpStatus.CREATED;
            }
        }catch(Exception e){
            httpStatus = HttpStatus.CONFLICT;
            return new ResponseEntity<Void>(httpStatus);
        }
        return new ResponseEntity<Void>(httpStatus);
    }

    @GetMapping("/duplicate/{officeName}")
    @ApiOperation(value = "회사 이름의 중복을 확인한다.", response = Boolean.class)
    public ResponseEntity<Boolean> duplicateOfficeName(@PathVariable("officeName") String officeName){
        HttpStatus httpStatus = HttpStatus.OK;
        boolean isDuplicate = officeService.duplicateOfficeName(officeName);
        if(isDuplicate)
            httpStatus = HttpStatus.CONFLICT;

        return new ResponseEntity<Boolean>(isDuplicate, httpStatus);
    }

    @GetMapping("/depts")
    @ApiOperation(value = "부서 리스트를 반환한다.", response = List.class)
    public ResponseEntity<List<DeptEntity>> getDeptList(){
        HttpStatus status = HttpStatus.OK;
        List<DeptEntity> list = new ArrayList<>();
        try{
            list = deptService.getDeptList(1l);
            log.info("부서 리스트 반환 성공");
        }catch (Exception e){
            log.error("부서 리스트 반환 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<List<DeptEntity>>(list, status);
    }

    @GetMapping("/jobs")
    @ApiOperation(value = "직책 리스트를 반환한다.", response = List.class)
    public ResponseEntity<List<JobEntity>> getJobList(){
        HttpStatus status = HttpStatus.OK;
        List<JobEntity> list = new ArrayList<>();
        try{
            list = jobService.getJobList(1l);
            log.info("직책 리스트 반환 성공");
        }catch (Exception e){
            log.error("직책 리스트 반환 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<List<JobEntity>>(list, status);
    }

    @GetMapping("/dashboard/{officeId}")
    @ApiOperation(value = "office별 인원 관리 정보(구성원 수, 부서별 사원 수)를 반환한다.", response = Map.class)
    public ResponseEntity<Map<String, Collection>> getCounts(@PathVariable("officeId") Long officeId) {
        Set<Integer> officeSet = new HashSet<>();
        Map<String, Collection> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            //전체 구성원 수
            int officeUsers = officeService.getOfficeUserCount(officeId);
            officeSet.add(officeUsers);

            //부서별 구성원 수
            List<DeptUserDto> dept = officeService.getDeptUserCount(officeId);

            map.put("officeUserCount", officeSet);
            map.put("deptUserCount", dept);
            log.info("인원 관리 정보 반환 성공");
        } catch (Exception e) {
            log.error("인원 관리 정보 반환 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Collection>>(map, status);
    }
}
