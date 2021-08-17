package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtAccountBillUploadRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AcBilUploadRecordMapper {

    Integer saveUploadRecordData(ExtAccountBillUploadRecord accountBillUploadRecord);

    Integer selectCountByYearMonth(@Param("year") int year, @Param("month") int month);

    ExtAccountBillUploadRecord selectDataByYearMonth(@Param("year") int year, @Param("month") int month);

    Integer deleteOldDataByYearMonth(@Param("year") int year, @Param("month") int month);
}