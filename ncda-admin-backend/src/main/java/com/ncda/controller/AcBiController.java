package com.ncda.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncda.entity.ext.*;
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
        return ResultData.createSuccessResult("一级分类查询成功", accountBillTypes);
    }

    @PostMapping("/saveType")
    public ResultData saveType(@RequestBody ExtAccountBillType accountBillType) {
        if (acBiService.saveType(accountBillType) > 0) {
            return ResultData.createSuccessResult("分类保存成功", true);
        }
        return ResultData.createFailResultData("分类保存失败");
    }

    @PostMapping("/updateType")
    public ResultData updateType(@RequestBody ExtAccountBillType accountBillType) {
        if (acBiService.updateType(accountBillType) > 0) {
            return ResultData.createSuccessResult("分类更新成功", true);
        }
        return ResultData.createFailResultData("分类更新失败");
    }

    @GetMapping("/deleteType")
    public ResultData deleteType(String typeId) {
        Integer integer = acBiService.deleteType(typeId);
        if (integer > 0) {
            return ResultData.createSuccessResult("分类删除成功", true);
        }
        return ResultData.createFailResultData("分类删除失败");
    }

    // 暂未用到
    @GetMapping("/getAllYearAndMonth")
    public ResultData getAllYearAndMonth() {
        List<ExtAccountBillUploadRecord> allYearAndMonth = acBiService.getAllYearAndMonth();
        return ResultData.createSuccessResult("查询成功", allYearAndMonth);
    }

    @PostMapping("/selectBarChartData")
    public ResultData selectBarChartData(@RequestBody ExtAccountBill accountBill) {
        List<ChartEntiey> chartEntieyList = acBiService.selectBarChartData(accountBill);
        return ResultData.createSuccessResult("柱状图信息查询成功", chartEntieyList);
    }

    @PostMapping("/selectCalendarHeatmapChartData")
    public ResultData selectCalendarHeatmapChartData(@RequestBody ExtAccountBill accountBill) {
        List<ChartEntiey> chartEntieyList = acBiService.selectCalendarHeatmapChartData(accountBill);
        return ResultData.createSuccessResult("日历热力图信息查询成功", chartEntieyList);
    }

    @PostMapping("/saveAdvancedSetting")
    public ResultData saveAdvancedSetting(@RequestBody AdvancedSetting advancedSetting) {
        if (acBiService.saveAdvancedSetting(advancedSetting)) {
            return ResultData.createSuccessResult("高级设置保存成功", true);
        }
        return ResultData.createFailResultData("高级设置保存失败");
    }

    @GetMapping("/getAdvancedSetting")
    public ResultData getAdvancedSetting() {
        AdvancedSetting advancedSetting = acBiService.getAdvancedSetting();
        return ResultData.createSuccessResult("高级设置获取成功", advancedSetting);
    }

    @GetMapping("/getCurrentFileUploadTimeLine")
    public ResultData getCurrentFileUploadTimeLine() {
        List<ExtAccountBillUploadRecord> currentFileUploadTimeLine = acBiService.getCurrentFileUploadTimeLine();
        return ResultData.createSuccessResult("获取当前上传文件时间线成功", currentFileUploadTimeLine);
    }

    @PostMapping("/getHistoryFileUploadTimeLine")
    public ResultData getHistoryFileUploadTimeLine(@RequestBody ExtAccountBillUploadRecord accountBillUploadRecord) {
        List<ExtAccountBillUploadRecord> historyFileUploadTimeLine = acBiService.getHistoryFileUploadTimeLine(accountBillUploadRecord);
        return ResultData.createSuccessResult("历史上传文件时间线查询成功", historyFileUploadTimeLine);
    }

    @PostMapping("/deletePrimaryData")
    public ResultData deletePrimaryData(@RequestBody ExtAccountBillUploadRecord accountBillUploadRecord) {
        Integer integer = acBiService.deletePrimaryData(accountBillUploadRecord);
        if (integer > 0) {
            return ResultData.createSuccessResult("主要数据及其关联数据删除成功", true);
        }
        return ResultData.createFailResultData("主要数据及其关联数据删除失败");
    }

    @GetMapping("/deleteHistoryData")
    public ResultData deleteHistoryData(String id) {
        Integer integer = acBiService.deleteHistoryData(id);
        if (integer > 0) {
            return ResultData.createSuccessResult("历史上传数据删除成功", integer);
        }
        return ResultData.createFailResultData("历史上传数据删除失败");
    }

    @GetMapping("/getWordCloudData")
    public ResultData getWordCloudData() {
        List<ChartEntiey> wordCloudData = acBiService.getWordCloudData();
        return ResultData.createSuccessResult("WordClous数据查询成功", wordCloudData);
    }
}
