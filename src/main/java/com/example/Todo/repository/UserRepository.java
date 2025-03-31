package com.example.Todo.repository;

import com.example.Todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자 이름(아이디)로 사용자 찾기
    Optional<User> findByUsername(String username);

    // 사용자 이름과 비밀번호로 사용자 찾기 (로그인 검증용)
    Optional<User> findByUsernameAndPassword(String username, String password);

    // 중복 아이디 체크용 (existsBy~ 는 true/false 반환)
    boolean existsByUsername(String username);

}