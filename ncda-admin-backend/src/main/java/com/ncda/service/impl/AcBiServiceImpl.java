package com.ncda.service.impl;

import com.ncda.dao.ext.AcBiMapper;
import com.ncda.dao.ext.AcBiTypeMapper;
import com.ncda.dao.ext.AcBilUploadRecordMapper;
import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.ext.ExtAccountBillType;
import com.ncda.entity.ext.ExtAccountBillUploadRecord;
import com.ncda.entity.result.ResultData;
import com.ncda.service.AcBiService;
import com.ncda.util.AcBiReadUtil;
import com.ncda.util.AcBiUtil;
import com.ncda.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Service
@Transactional
public class AcBiServiceImpl implements AcBiService {

    private final AcBiMapper acBiMapper;

    private final AcBilUploadRecordMapper acBilUploadRecordMapper;

    private final AcBiTypeMapper acBiTypeMapper;

    private final RedisUtil redisUtil;

    @Autowired
    public AcBiServiceImpl(AcBiMapper acBiMapper, AcBilUploadRecordMapper acBilUploadRecordMapper, AcBiTypeMapper acBiTypeMapper, RedisUtil redisUtil) {
        this.acBiMapper = acBiMapper;
        this.acBilUploadRecordMapper = acBilUploadRecordMapper;
        this.acBiTypeMapper = acBiTypeMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public List<ExtAccountBill> getAccountBill(ExtAccountBill accountBill) {
//        accountBill.setFilterKeyword(AcBiUtil.strReplace(accountBill.getFilterKeyword(), ' ', '|'));
        return acBiMapper.getAccountBill(accountBill);
    }

    @Override
    public Integer updateAcBiData(ExtAccountBill accountBill) {
        return acBiMapper.updateAcBiData(accountBill);
    }

    @Override
    public List<ExtAccountBill> fileUpload(InputStream inputStream) throws Exception {
        return AcBiReadUtil.analysisAcBiFile(inputStream);
    }

    @Override
    public List<ExtAccountBill> textUpload(String text) throws Exception {
        return AcBiReadUtil.analysisAcBiText(text);
    }

    @Override
    public ResultData saveUploadData(List<ExtAccountBill> accountBillList) {
        Date date = accountBillList.get(0).getDate();
        Calendar calendar = AcBiUtil.dateToCalendar(date);   // 获取数据的时间，主要是年月，所以可以选择集合中的任意数据
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;       // 获取的月份要 +1 才是真实的月份
        Integer count = acBilUploadRecordMapper.selectCountByYearMonth(year, month);
        if (count > 0) {
            // 该月份数据已经存在
            ExtAccountBillUploadRecord uploadRecord = acBilUploadRecordMapper.selectDataByYearMonth(year, month);
            // 将冲突月份数据返回，让用户比较，决定谁去谁留
            HashMap<Object, Object> map = new HashMap<>();
            map.put("oldContent", uploadRecord.getFileContent());  // 旧数据
            map.put("newContent", AcBiReadUtil.getContent());      // 新数据
            map.put("newData", accountBillList);
            return ResultData.createFailResult("该月份数据已经存在，请对比文件差异", map);
        }
        // 没有数据才可以保存
        Integer num = saveData(accountBillList);
        if (num == 1) {
            return ResultData.createSuccessResult("保存成功", num);
        }
        return ResultData.createFailResultData("保存失败");
    }

    @Override
    public ResultData saveNewData(List<ExtAccountBill> accountBillList) {
        Calendar calendar = AcBiUtil.dateToCalendar(accountBillList.get(0).getDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        Integer count = acBiMapper.deleteDataByYearMonth(year, month);  // 删除旧数据（真删除）
        if (count > 0) {
            acBilUploadRecordMapper.deleteOldDataByYearMonth(year, month);  // 删除上传记录（逻辑删除）
            Integer num = saveData(accountBillList);
            if (num == 1) {
                return ResultData.createSuccessResult("数据更新成功", num);
            }
            return ResultData.createFailResultData("数据更新失败");
        }
        return ResultData.createFailResultData("数据更新失败");
    }

    /**
     * 执行保存操作，主表插入数据，记录表插入记录
     * @param accountBillList list
     * @return 1 成功，0 失败
     */
    public Integer saveData(List<ExtAccountBill> accountBillList) {
        Integer state = acBiMapper.batchSaveUploadData(accountBillList);    // 没有数据才可以保存
        if (state == accountBillList.size()) {
            ExtAccountBillUploadRecord uploadRecord = new ExtAccountBillUploadRecord();
            uploadRecord.setDate(accountBillList.get(0).getDate());
            uploadRecord.setFileContent(AcBiReadUtil.getContent());
            uploadRecord.setUploadTime(new Date());
            // 每一次保存成功后都会在记录表中加入一条信息
            acBilUploadRecordMapper.saveUploadRecordData(uploadRecord);
            return 1;
        }
        return 0;
    }

    @Override
    public List<ExtAccountBillType> selectLevelOneType() {
        return acBiTypeMapper.selectLevelOneType();
    }


    @Override
    public List<ExtAccountBillUploadRecord> getAllYearAndMonth() {
        return acBilUploadRecordMapper.getAllYearAndMonth();
    }


    @Override
    public List<ExtAccountBill> selectBarChartData(ExtAccountBill accountBill) {
        return acBiMapper.selectBarChartData(accountBill);
    }

    @Override
    public List<ExtAccountBill> selectCalendarHeatmapChartData(ExtAccountBill accountBill) {
//        accountBill.setFilterKeyword(AcBiUtil.strReplace(accountBill.getFilterKeyword(), ' ', '|'));
        return acBiMapper.selectCalendarHeatmapChartData(accountBill);
    }

    @Override
    public Boolean saveAdvancedSetting(ExtAccountBill accountBill) {
        return (redisUtil.set("heatmapMax", accountBill.getHeatmapMax()) &&
                redisUtil.set("moneyMax", accountBill.getMoneyMax()) &&
                redisUtil.set("moneyMin", accountBill.getMoneyMin()) &&
                redisUtil.set("filterKeyword", accountBill.getFilterKeyword()));
    }

    @Override
    public ExtAccountBill getAdvancedSetting() {
        ExtAccountBill accountBill = new ExtAccountBill();
        accountBill.setHeatmapMax((Double) redisUtil.get("heatmapMax"));
        accountBill.setMoneyMax((Double) redisUtil.get("moneyMax"));
        accountBill.setMoneyMin((Double) redisUtil.get("moneyMin"));
        accountBill.setFilterKeyword((String) redisUtil.get("filterKeyword"));
        return accountBill;
    }

    @Override
    public Integer saveType(ExtAccountBillType accountBillType) {
        return acBiTypeMapper.saveType(accountBillType);
    }

    @Override
    public Integer deleteType(String typeId) {
        Integer delState = acBiTypeMapper.deleteType(typeId);
        if (delState > 0) {
            acBiMapper.deleteAcBiTypeByTypeId(typeId);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updateType(ExtAccountBillType accountBillType) {
        return acBiTypeMapper.updateType(accountBillType);
    }
}
