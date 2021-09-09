package com.ncda.service;

import com.ncda.entity.ext.ExtTodoList;

import java.util.List;

public interface HomeService {

    List<ExtTodoList> getTodoList();

    Integer addTodoList(ExtTodoList todoList);

    Integer updateTodoList(ExtTodoList todoList);

    Integer removeTodoList(ExtTodoList todoList);

    Integer recoverTodoList(ExtTodoList todoList);

    Integer deleteTodoList(ExtTodoList todoList);
}
