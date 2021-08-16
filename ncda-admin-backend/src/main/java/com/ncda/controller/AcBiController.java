package com.ncda.controller;

import com.ncda.entity.AccountBill;
import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.result.ResultData;
import com.ncda.service.AcBiService;
import com.ncda.util.AcBiReadUtil;
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
    public ResultData fileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if(".txt".equals(suffix)) {
            try {
                List<ExtAccountBill> bills = acBiService.fileUpload(file.getInputStream());
                return ResultData.createSuccessResultData("成功", bills, (long) bills.size());
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFailResultData(e.getMessage());
            }
        }
        return ResultData.createFailResultData("格式不正确，只接收txt格式文件");
    }

    @PostMapping("/textUpload")
    public ResultData textUpload(@RequestBody ExtAccountBill accountBill) {
        try {
            List<ExtAccountBill> bills = acBiService.textUpload(accountBill.getText());
            return ResultData.createSuccessResultData("成功", bills, (long) bills.size());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFailResultData(e.getMessage());
        }
    }

    @PostMapping("/saveUploadData")
    public ResultData saveData(@RequestBody List<ExtAccountBill> accountBills) {
        Integer num = acBiService.saveUploadData(accountBills);
        if (num == 1) {
            return ResultData.createSuccessResultData("", num, num.longValue());
        }
        return ResultData.createFailResultData("");
    }

    @GetMapping("/getAll")
    public List<ExtAccountBill> getAccountBill() {
        List<ExtAccountBill> bills = acBiService.getAll();
        return bills;
    }
}
