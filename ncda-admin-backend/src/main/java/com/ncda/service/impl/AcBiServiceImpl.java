package com.ncda.service.impl;

import com.ncda.dao.ext.AcBiMapper;
import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.service.AcBiService;
import com.ncda.util.AcBiReadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Service
@Transactional
public class AcBiServiceImpl implements AcBiService {

    private final AcBiMapper acBiMapper;

    @Autowired
    public AcBiServiceImpl(AcBiMapper acBiMapper) {
        this.acBiMapper = acBiMapper;
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

    @Override
    public Integer saveUploadData(List<ExtAccountBill> accountBillList) {
//        Integer count = 0;
//        for (ExtAccountBill extAccountBill : accountBillList) {
//            count += acBiMapper.saveUploadData(extAccountBill);
//        }
//        return accountBillList.size() == count ? 1 : 0;
        Integer integer = acBiMapper.batchSaveUploadData(accountBillList);
        return 1;
    }
}
