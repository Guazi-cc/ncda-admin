package com.ncda.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.ext.ExtAccountBillType;
import com.ncda.entity.ext.ExtAccountBillUploadRecord;
import com.ncda.entity.result.ResultData;
import com.ncda.service.AcBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/acbi")
public class AcBiController {

    private final AcBiService acBiService;

    @Autowired
    public AcBiController(AcBiService acBiService) {
        this.acBiService = acBiService;
    }

    @PostMapping("/getAccountBill")
    public ResultData getAccountBill(@RequestBody ExtAccountBill accountBill) {
        PageHelper.startPage(accountBill.getCurrentPage(), accountBill.getPageSize());
        List<ExtAccountBill> bills = acBiService.getAccountBill(accountBill);
        PageInfo<ExtAccountBill> pageInfo = new PageInfo<>(bills);
        return ResultData.createSuccessResultData("查询成功", pageInfo, pageInfo.getTotal());
    }

    @PostMapping("/updateAcBiData")
    public ResultData updateAcBiData(@RequestBody ExtAccountBill accountBill) {
        Integer num = acBiService.updateAcBiData(accountBill);
        if (num == 1) {
            return ResultData.createSuccessResult("更新成功", num);
        }
        return ResultData.createFailResultData("更新失败");
    }

    @PostMapping("fileUpload")
    public ResultData fileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(".txt".equals(suffix)) {
            try {
                List<ExtAccountBill> bills = acBiService.fileUpload(file.getInputStream());
                return ResultData.createSuccessResultData("文件解析成功", bills, (long) bills.size());
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
            return ResultData.createSuccessResultData("文本解析成功", bills, (long) bills.size());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.createFailResultData(e.getMessage());
        }
    }

    @PostMapping("/saveUploadData")
    public ResultData saveUploadData(@RequestBody List<ExtAccountBill> accountBills) {
        ResultData resultData = acBiService.saveUploadData(accountBills);
        if (resultData.isSuccess()) {
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/saveNewData")
    public ResultData saveNewData(@RequestBody List<ExtAccountBill> accountBills) {
        return acBiService.saveNewData(accountBills);
    }

    @GetMapping("/getOneType")
    public ResultData getOneType() {
        List<ExtAccountBillType> accountBillTypes = acBiService.selectLevelOneType();
        return ResultData.createSuccessResultData("一级分类查询成功", accountBillTypes, (long) accountBillTypes.size());
    }

    @GetMapping("/getTwoType")
    public ResultData getTwoType(String oneTypeID) {
        List<ExtAccountBillType> accountBillTypes = acBiService.selectLevelTwoType(Integer.parseInt(oneTypeID));
        return ResultData.createSuccessResultData("二级分类查询成功", accountBillTypes, (long) accountBillTypes.size());
    }

    @GetMapping("/getAllYearAndMonth")
    public ResultData getAllYearAndMonth() {
        List<ExtAccountBillUploadRecord> allYearAndMonth = acBiService.getAllYearAndMonth();
        return ResultData.createSuccessResult("查询成功", allYearAndMonth);
    }
}
