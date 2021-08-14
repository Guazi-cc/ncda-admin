package com.ncda.dao.ext;

import com.ncda.entity.ext.ExtAccountBill;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AcBiMapper {

    List<ExtAccountBill> getAll();

    Integer saveUploadData(ExtAccountBill accountBill);
}
