package com.example.Todo.service;

import com.example.Todo.entity.Todo;
import java.time.LocalDate;
import java.util.List;

public interface TodoService {
    List<Todo> getTodosByDate(LocalDate date);

    Todo createTodo(Todo todo);

    Todo updateTodo(Long id, Todo updatedTodo);

    void deleteTodo(Long id);

    List<Todo> getAllTodos();

}