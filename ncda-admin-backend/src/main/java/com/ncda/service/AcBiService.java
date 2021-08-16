package com.ncda.service;

import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.result.ResultData;

import java.io.InputStream;
import java.util.List;

public interface AcBiService {

    List<ExtAccountBill> getAll();

    List<ExtAccountBill> fileUpload(InputStream inputStream) throws Exception;

    List<ExtAccountBill> textUpload(String text) throws Exception;

    ResultData saveUploadData(List<ExtAccountBill> accountBillList);
}
