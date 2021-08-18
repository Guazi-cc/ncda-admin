package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtAccountBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface AcBiMapper {

    List<ExtAccountBill> getAccountBill(ExtAccountBill accountBill);

    Integer updateAcBiData(ExtAccountBill accountBill);

    Integer batchSaveUploadData(@Param("acBiList") List<ExtAccountBill> acBiList);

    Integer deleteDataByYearMonth(@Param("year") int year, @Param("month") int month);

}
