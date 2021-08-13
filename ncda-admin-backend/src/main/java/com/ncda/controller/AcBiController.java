package com.ncda.controller;

import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.service.AcBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class AcBiController {

    private final AcBiService acBiService;

    @Autowired
    public AcBiController(AcBiService acBiService) {
        this.acBiService = acBiService;
    }

    @GetMapping("/getAll")
    public List<ExtAccountBill> getAccountBill() {
        List<ExtAccountBill> bills = acBiService.getAll();
        return bills;
    }
}
