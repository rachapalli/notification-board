package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
