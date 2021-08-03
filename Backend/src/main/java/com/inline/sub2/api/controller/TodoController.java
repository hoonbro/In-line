package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.service.TodoService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.api.service.UserServiceImpl;
import com.inline.sub2.db.entity.TodoEntity;
import com.inline.sub2.db.repository.TodoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoService;

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "사용자의 Todo List를 반환한다.", response = List.class)
    public ResponseEntity<List<TodoEntity>> UserTodoList(@PathVariable Long userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        List<TodoEntity> list = new ArrayList<TodoEntity>();

        try{
            list = todoService.UserTodoList(userId);
            System.out.println(list);
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<List<TodoEntity>>(list,httpStatus);
    }

    @PostMapping
    @ApiOperation(value = "사용자의 할일(Todo)를 등록하고 해당 Data를 반환한다.", response = TodoEntity.class)
    public ResponseEntity<TodoEntity> registTodo(@RequestBody TodoEntity todoEntity) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            todoEntity = todoService.registTodo(todoEntity);
        }
        catch(Exception e) {
            todoEntity = null;
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<TodoEntity>(todoEntity,httpStatus);
    }

    @PutMapping
    @ApiOperation(value = "사용자의 할일(Todo)를 수정한다.", response = TodoEntity.class)
    public ResponseEntity<TodoEntity> updateTodo(@RequestBody TodoEntity todoEntity) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try{
            todoEntity = todoService.updateTodo(todoEntity);
        }
        catch(Exception e) {
            todoEntity = null;
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<TodoEntity>(todoEntity,httpStatus);
    }

    @DeleteMapping("/{userId}/{todoId}")
    @ApiOperation(value = "사용자의 할일(Todo)를 삭제한다.")
    public ResponseEntity<Void> deleteTodo(@PathVariable(value = "userId") Long userId,
                                           @PathVariable(value = "todoId") Long todoId) {

        HttpStatus httpStatus = HttpStatus.NO_CONTENT;

        try{
            TodoEntity todoEntity = todoService.findUserIdByTodoId(todoId);

            if(userId == todoEntity.getUserId()) {
                //삭제 처리
                todoService.deleteTodo(todoId);
            }
            else {
                httpStatus = HttpStatus.UNAUTHORIZED;
            }

        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Void>(httpStatus);
    }

    @PutMapping("/done")
    @ApiOperation(value = "todo가 진행중이라면 종료, 종료라면 진행중으로 상태를 바꾼다.")
    public ResponseEntity<Void> taskTodo(@RequestBody TodoEntity todoEntity) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try{
             TodoEntity todoEntity2 = todoService.findUserIdByTodoId(todoEntity.getTodoId());

             if(todoEntity.getUserId() == todoEntity2.getUserId()) {
                 todoService.taskTodo(todoEntity.getTodoId());
             }
             else {
                 httpStatus = HttpStatus.UNAUTHORIZED;
             }
        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Void>(httpStatus);
    }
}

