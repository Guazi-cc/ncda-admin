package com.ncda.service.impl;

import com.ncda.dao.ext.AcBiMapper;
import com.ncda.dao.ext.AcBilUploadRecordMapper;
import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.ext.ExtAccountBillUploadRecord;
import com.ncda.entity.result.ResultData;
import com.ncda.service.AcBiService;
import com.ncda.util.AcBiReadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AcBiServiceImpl implements AcBiService {

    private final AcBiMapper acBiMapper;

    private final AcBilUploadRecordMapper acBilUploadRecordMapper;

    @Autowired
    public AcBiServiceImpl(AcBiMapper acBiMapper, AcBilUploadRecordMapper acBilUploadRecordMapper) {
        this.acBiMapper = acBiMapper;
        this.acBilUploadRecordMapper = acBilUploadRecordMapper;
    }

    @Override
    public List<ExtAccountBill> getAll() {
        return acBiMapper.getAll();
    }

    @Override
    public List<ExtAccountBill> fileUpload(InputStream inputStream) throws Exception {
        return AcBiReadUtil.analysisAcBiFile(inputStream);
    }

    @Override
    public List<ExtAccountBill> textUpload(String text) throws Exception {
        return AcBiReadUtil.analysisAcBiText(text);
    }

    /**
     * 文件上传完成后保存数据，因此该方法必须在上传方法调用后使用
     * @param accountBillList
     * @return
     */
    @Override
    public ResultData saveUploadData(List<ExtAccountBill> accountBillList) {
        Date date = accountBillList.get(0).getDate();       // 获取数据的时间，主要是年月，所以可以选择集合中的任意数据
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;       // 获取的月份要 +1 才是真实的月份
        Integer count = acBilUploadRecordMapper.selectCountByYearMonth(year, month);
        if (count > 0) {
            // 该月份数据已经存在
            ExtAccountBillUploadRecord uploadRecord = acBilUploadRecordMapper.selectDataByYearMonth(year, month);
            // 将冲突月份数据返回，让用户比较，谁去谁留
            HashMap<Object, String> map = new HashMap<>();
            map.put("oldData", uploadRecord.getFileContent());
            map.put("newData", AcBiReadUtil.getContent());
            return ResultData.createFailResult("该月份数据已经存在，请对照文件差异", map);
        }
        Integer state = acBiMapper.batchSaveUploadData(accountBillList);    // 没有数据才可以保存
        if (state == accountBillList.size()) {
            ExtAccountBillUploadRecord uploadRecord = new ExtAccountBillUploadRecord();
            uploadRecord.setDate(date);
            uploadRecord.setFileContent(AcBiReadUtil.getContent());
            Integer integer = acBilUploadRecordMapper.saveUploadRecordData(uploadRecord);
            return ResultData.createSuccessResult("保存成功", integer);
        }
        return ResultData.createFailResultData("保存失败");
    }
}
