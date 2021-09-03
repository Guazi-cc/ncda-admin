package com.ncda.dao.ext;

import com.ncda.entity.ext.AdvancedSetting;
import com.ncda.entity.ext.ChartEntiey;
import com.ncda.entity.ext.ExtAccountBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AcBiMapper {

    /**
     * 获取账单信息
     * @param accountBill 搜索条件
     * @return 账单信息
     */
    List<ExtAccountBill> getAccountBill(ExtAccountBill accountBill);

    /**
     * 更新账单信息
     * @param accountBill 账单数据
     * @return Integer
     */
    Integer updateAcBiData(ExtAccountBill accountBill);

    /**
     * 批量保存数据
     * @param acBiList 账单数据集合
     * @return Integer
     */
    Integer batchSaveUploadData(@Param("acBiList") List<ExtAccountBill> acBiList);

    /**
     * 根据年月删除账单数据
     * @param year 年
     * @param month 月
     * @return Integer
     */
    Integer deleteDataByYearMonth(@Param("year") int year, @Param("month") int month);

    /**
     * 获取图表数据
     * @param accountBill 查询条件
     * @return 图表数据
     */
    List<ChartEntiey> selectBarChartData(ExtAccountBill accountBill);

    /**
     * 查询日历日历热力图的数据
     * @param accountBill
     * @return
     */
    List<ChartEntiey> selectCalendarHeatmapChartData(ExtAccountBill accountBill);

    /**
     * 根据类型id 删除账单的类型
     * @param typeId
     * @return
     */
    Integer deleteAcBiTypeByTypeId(String typeId);

    /**
     * 根据高级设置将数据过滤（逻辑删除）
     * @param advancedSetting
     * @return
     */
    Integer filterDataByAdvanceSetting(AdvancedSetting advancedSetting);

    /**
     * 恢复所有被删除的数据
     */
    void recoverData();

    List<ChartEntiey> getWordCloudData();

    List<ChartEntiey> getPieChartDataByYear(String year);
}
