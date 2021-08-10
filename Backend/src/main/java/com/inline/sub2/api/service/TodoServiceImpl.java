package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.TodoEntity;
import com.inline.sub2.db.repository.TodoRepository;
import com.inline.sub2.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<TodoEntity> UserTodoList(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
    public TodoEntity registTodo(TodoEntity todoEntity) {
//        todoEntity.setDay(new Date());
        Date now = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = transFormat.format(now).split(" ")[0];
        todoEntity.setDay(nowDate);
        return todoRepository.save(todoEntity);
    }

    @Override
    public TodoEntity updateTodo(TodoEntity todoEntity) {
//        todoEntity.setTodoDate(new Date());
        return todoRepository.save(todoEntity);


    }

    @Override
    public TodoEntity findUserIdByTodoId(Long todoId) {
        return todoRepository.findUserIdByTodoId(todoId);
    }

    @Override
    public void deleteTodo(Long todoId) {
        TodoEntity todoEntity = todoRepository.findByTodoId(todoId);
        todoRepository.delete(todoEntity);

    }

    @Override
    public TodoEntity taskTodo(Long todoId) {
        TodoEntity todoEntity = todoRepository.findByTodoId(todoId);
        if (todoEntity.isDone() == false) {
            todoEntity.setDone(true);
        } else {
            todoEntity.setDone(false);
        }

        return todoRepository.save(todoEntity);

    }
}
