package com.inline.sub2.db.repository;


import com.inline.sub2.db.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String> {
    public List<TodoEntity> findByUserId(Long userId);
    public TodoEntity findUserIdByTodoId(Long todoId);
    public TodoEntity findByTodoId(Long todoId);
}
