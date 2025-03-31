package com.example.Todo.controller;

import com.example.Todo.entity.Todo;
import com.example.Todo.entity.User;
import com.example.Todo.repository.UserRepository;
import com.example.Todo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;

    @Autowired
    public TodoController(TodoService todoService, UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    // ✅ 특정 날짜 + username으로 조회
    @GetMapping("/{date}")
    public List<Todo> getTodosByDate(
            @PathVariable String date,
            @RequestParam("username") String username) {

        LocalDate todoDate = LocalDate.parse(date);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return todoService.getTodosByDate(todoDate).stream()
                .filter(todo -> todo.getUser() != null && todo.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    // ✅ 모든 할 일 (관리자 용도)
    @GetMapping
    public List<Todo> getTodosByUser(@RequestParam("username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return todoService.getAllTodos().stream()
                .filter(todo -> todo.getUser() != null && todo.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    // ✅ 등록 (username으로 유저 연결)
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        String todoDateStr = (String) request.get("todoDate");
        boolean completed = (boolean) request.get("completed");
        String username = (String) request.get("username");

        LocalDate todoDate = LocalDate.parse(todoDateStr);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Todo todo = Todo.builder()
                .title(title)
                .todoDate(todoDate)
                .completed(completed)
                .user(user)
                .build();

        Todo saved = todoService.createTodo(todo);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
        Todo updatedTodo = todoService.updateTodo(id, todo);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @GetMapping("/dates")
    public List<String> getTodoDates() {
        return todoService.getAllTodos().stream()
                .filter(todo -> todo.getTodoDate() != null)
                .map(todo -> todo.getTodoDate().toString())
                .distinct()
                .collect(Collectors.toList());
    }
}