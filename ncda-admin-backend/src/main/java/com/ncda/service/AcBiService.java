package com.ncda.service;

import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.result.ResultData;

import java.io.InputStream;
import java.util.List;

public interface AcBiService {

    List<ExtAccountBill> getAccountBill(ExtAccountBill accountBill);

    List<ExtAccountBill> fileUpload(InputStream inputStream) throws Exception;

    List<ExtAccountBill> textUpload(String text) throws Exception;

    /**
     * 文件上传完成后保存数据，因此该方法必须在上传方法调用后使用
     * @param accountBillList 数据集合
     * @return result
     */
    ResultData saveUploadData(List<ExtAccountBill> accountBillList);

    /**
     * 保存新的数据，会覆盖旧的数据
     * @param accountBillList 数据集合
     * @return result
     */
    ResultData saveNewData(List<ExtAccountBill> accountBillList);
}
