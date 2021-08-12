package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.service.OnBoardService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.OnBoardEntity;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/on-board")
@CrossOrigin("*")
public class OnBoardController {

    @Autowired
    OnBoardService onBoardService;

    @Autowired
    UserService userService;

    @PostMapping("/user")
    @ApiOperation(value = "관리자가 구성원을 추가했을 때 onBoard 테이블에 추가하고 이메일을 보낸다.", response = OnBoardEntity.class)
    public ResponseEntity<OnBoardEntity> registUserOnboard(@RequestBody UserRegistDto user) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        OnBoardEntity onBoardEntity = null;
        boolean isDuplicate = userService.duplicateEmail(user.getEmail());
        if (isDuplicate) {
            httpStatus = HttpStatus.CONFLICT;
            log.error("이미 등록된 구성원 이메일");
        }
        else{
            try {
                onBoardEntity = onBoardService.registUserOnboard(user);
            }catch (Exception e){
                log.error("가입 처리중인 이메일");
                httpStatus = HttpStatus.BAD_REQUEST;
            }

        }
        return new ResponseEntity<OnBoardEntity>(onBoardEntity, httpStatus);
    }

    @GetMapping("/user/{email}")
    @ApiOperation(value = "구성원이 초대 메일을 클릭했을 시 Data를 넘겨준다.", response = UserRegistDto.class)
    public ResponseEntity<UserRegistDto> clickEmail(@PathVariable("email") String email) {
        UserRegistDto userRegistDto = new UserRegistDto();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            userRegistDto = onBoardService.clickEmail(email);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<UserRegistDto>(userRegistDto, httpStatus);
        }
        return new ResponseEntity<UserRegistDto>(userRegistDto, httpStatus);
    }

    @DeleteMapping("/user/{email}")
    @ApiOperation(value = "관리자가 onBoard 테이블에서 구성원을 삭제한다.")
    public ResponseEntity<Void> deleteUserOnboard(@PathVariable("email") String email) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            onBoardService.deleteUserOnboard(email);
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Void>(httpStatus);
    }

    @GetMapping("/{officeId}")
    @ApiOperation(value = "onboard테이블의 있는 구성원들을 반환해준다..", response = List.class)
    public ResponseEntity<List<OnBoardEntity>> getOnboardUsers(@PathVariable("officeId") Long officeId){
        HttpStatus httpStatus = HttpStatus.OK;
        List<OnBoardEntity> list = null;

        try{
            list = onBoardService.getOnboardUsers(officeId);
            log.info("onboard list 반환 성공");
        }catch (Exception e){
            log.error("서버 에러");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<List<OnBoardEntity>>(list, httpStatus);
    }
}
