package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtAccountBillType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AcBiTypeMapper {

    List<ExtAccountBillType> selectLevelOneType();

    List<ExtAccountBillType> selectLevelTwoType(Integer oneTypeId);
}