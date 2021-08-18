package com.ncda.service;

import com.ncda.entity.ext.ExtAccountBill;
import com.ncda.entity.ext.ExtAccountBillType;
import com.ncda.entity.result.ResultData;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface AcBiService {

    List<ExtAccountBill> getAccountBill(ExtAccountBill accountBill);

    /**
     * 更新
     * @param accountBill
     * @return
     */
    Integer updateAcBiData(ExtAccountBill accountBill);

    /**
     * 上传文件处理
     * @param inputStream 文件流
     * @return
     * @throws Exception
     */
    List<ExtAccountBill> fileUpload(InputStream inputStream) throws Exception;

    /**
     * 上传文本处理
     * @param text 文本
     * @return
     * @throws Exception
     */
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

    /**
     * 获取一级分类
     * @return
     */
    List<ExtAccountBillType> selectLevelOneType();

    /**
     * 获取二级分类
     * @return
     */
    List<ExtAccountBillType> selectLevelTwoType(Integer oneTypeId);

}
