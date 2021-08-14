package com.ncda.service.impl;

import com.ncda.dao.ext.AcBiMapper;
import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.service.AcBiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcBiServiceImpl implements AcBiService {

    private final AcBiMapper acBiMapper;

    @Autowired
    public AcBiServiceImpl(AcBiMapper acBiMapper) {
        this.acBiMapper = acBiMapper;
    }

    @Override
    public List<ExtAccountBill> getAll() {
        List<ExtAccountBill> billList = acBiMapper.getAll();
        return billList;
    }

    @Override
    @Transactional
    public Integer saveUploadData(List<ExtAccountBill> accountBillList) {
        Integer count = 0;
        for (ExtAccountBill extAccountBill : accountBillList) {
            count += acBiMapper.saveUploadData(extAccountBill);
        }
        return accountBillList.size() == count ? 1 : 0;
    }
}
