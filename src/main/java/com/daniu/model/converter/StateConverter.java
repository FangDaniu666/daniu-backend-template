package com.daniu.model.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.daniu.model.enums.StateEnum;

/**
 * 状态枚举转化器
 */
public class StateConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        // 实体类中对象的属性类型
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // excel 中对应的单元格数据属性类型
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        // 从CellData中读取数据，判断Excel中的值，将其转换为预期的数值
        return StateEnum.convert(context.getReadCellData().getStringValue()).getValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        // 从实体类中读取数据，判断实体中的值，将其转换为Excel中的值
        return new WriteCellData<>(StateEnum.convert(context.getValue()).getName());
    }
}
