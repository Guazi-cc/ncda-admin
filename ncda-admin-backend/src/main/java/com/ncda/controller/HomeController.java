package com.ncda.controller;

import com.ncda.entity.ext.ExtTodoList;
import com.ncda.entity.result.ResultData;
import com.ncda.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/getTodoList")
    public ResultData getTodoList() {
        List<ExtTodoList> todoList = homeService.getTodoList();
        return ResultData.createSuccessResult("success", todoList);
    }

    @PostMapping("/addTodoList")
    public ResultData addTodoList(@RequestBody ExtTodoList todoList) {
        Integer integer = homeService.addTodoList(todoList);
        return ResultData.createSuccessResult("success", integer);
    }

    @PostMapping("/updateTodoList")
    public ResultData updateTodoList(@RequestBody ExtTodoList todoList) {
        Integer integer = homeService.updateTodoList(todoList);
        return ResultData.createSuccessResult("success", integer);
    }

    @PostMapping("/removeTodoList")
    public ResultData removeTodoList(@RequestBody ExtTodoList todoList) {
        Integer integer = homeService.removeTodoList(todoList);
        return ResultData.createSuccessResult("success", integer);
    }

    @PostMapping("/recoverTodoList")
    public ResultData recoverTodoList(@RequestBody ExtTodoList todoList) {
        Integer integer = homeService.recoverTodoList(todoList);
        return ResultData.createSuccessResult("success", integer);
    }

    @PostMapping("/deleteTodoList")
    public ResultData deleteTodoList(@RequestBody ExtTodoList todoList) {
        Integer integer = homeService.deleteTodoList(todoList);
        return ResultData.createSuccessResult("success", integer);
    }
}
