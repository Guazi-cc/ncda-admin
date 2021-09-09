package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtTodoList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExtTodoListMapper {

    List<ExtTodoList> getTodoList();

    Integer addTodoList(ExtTodoList todoList);

    Integer updateTodoList(ExtTodoList todoList);

    Integer removeTodoList(ExtTodoList todoList);

    Integer recoverTodoList(ExtTodoList todoList);

    Integer deleteTodoList(ExtTodoList todoList);
}