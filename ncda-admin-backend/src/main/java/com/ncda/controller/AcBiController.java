package com.ncda.controller;

import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.service.AcBiService;
import com.ncda.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/acbi")
public class AcBiController {

    private final AcBiService acBiService;

    @Autowired
    public AcBiController(AcBiService acBiService) {
        this.acBiService = acBiService;
    }

    @PostMapping("fileUpload")
    public List<ExtAccountBill> fileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if(".txt".equals(suffix)) {
            try {
                List<ExtAccountBill> bills = Utils.analysisAcBiFile(file.getInputStream());
                System.out.println(bills);
                return bills;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @GetMapping("/getAll")
    public List<ExtAccountBill> getAccountBill() {
        List<ExtAccountBill> bills = acBiService.getAll();
        return bills;
    }
}
