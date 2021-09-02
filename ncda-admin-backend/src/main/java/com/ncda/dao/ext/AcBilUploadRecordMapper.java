package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtAccountBillUploadRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AcBilUploadRecordMapper {

    /**
     * 保存上传记录
     * @param accountBillUploadRecord
     * @return
     */
    Integer saveUploadRecordData(ExtAccountBillUploadRecord accountBillUploadRecord);

    /**
     * 根据年月查询数量
     * @param year
     * @param month
     * @return
     */
    Integer selectCountByYearMonth(@Param("year") int year, @Param("month") int month);

    /**
     * 根据年月查询数据
     * @param year
     * @param month
     * @return
     */
    ExtAccountBillUploadRecord selectDataByYearMonth(@Param("year") int year, @Param("month") int month);

    /**
     * 根据年月删除旧数据（逻辑删除）
     * @param year
     * @param month
     * @return
     */
    Integer deleteOldDataByYearMonth(@Param("year") int year, @Param("month") int month);

    /**
     * 获取所有的年月
     * @return
     */
    List<ExtAccountBillUploadRecord> getAllYearAndMonth();

    List<ExtAccountBillUploadRecord> getHistoryFileUploadTimeLine(ExtAccountBillUploadRecord accountBillUploadRecord);

    List<ExtAccountBillUploadRecord> getCurrentFileUploadTimeLine();

    Integer deletePrimaryData(ExtAccountBillUploadRecord accountBillUploadRecord);

    Integer deleteHistoryData(String id);
}