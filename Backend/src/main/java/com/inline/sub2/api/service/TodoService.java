package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.TodoEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoService {
    @Transactional(rollbackFor = Exception.class)
    List<TodoEntity> UserTodoList(Long userId);

    TodoEntity registTodo(TodoEntity todoEntity);

    TodoEntity updateTodo(TodoEntity todoEntity);
    TodoEntity findUserIdByTodoId(Long todoId);

    void deleteTodo(Long todoId);
    void taskTodo(Long todoId);
}
