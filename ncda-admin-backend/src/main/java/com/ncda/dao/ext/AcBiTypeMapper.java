package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtAccountBillType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AcBiTypeMapper {

    List<ExtAccountBillType> selectLevelOneType();

    Integer saveType(ExtAccountBillType accountBillType);

    Integer updateType(ExtAccountBillType accountBillType);

    Integer deleteType(String typeId);
}