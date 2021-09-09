package com.ncda.service.impl;

import com.ncda.dao.ext.ExtTodoListMapper;
import com.ncda.entity.ext.ExtTodoList;
import com.ncda.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private final ExtTodoListMapper todoListMapper;

    @Autowired
    public HomeServiceImpl(ExtTodoListMapper todoListMapper) {
        this.todoListMapper = todoListMapper;
    }

    @Override
    public List<ExtTodoList> getTodoList() {
        return todoListMapper.getTodoList();
    }

    @Override
    public Integer addTodoList(ExtTodoList todoList) {
        return todoListMapper.addTodoList(todoList);
    }

    @Override
    public Integer updateTodoList(ExtTodoList todoList) {
        todoList.setCompleteTime(todoList.getStatus() ? new Date() : null);
        return todoListMapper.updateTodoList(todoList);
    }

    @Override
    public Integer removeTodoList(ExtTodoList todoList) {
        return todoListMapper.removeTodoList(todoList);
    }

    @Override
    public Integer recoverTodoList(ExtTodoList todoList) {
        return todoListMapper.recoverTodoList(todoList);
    }

    @Override
    public Integer deleteTodoList(ExtTodoList todoList) {
        return todoListMapper.deleteTodoList(todoList);
    }
}
