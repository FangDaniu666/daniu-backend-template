package com.daniu.model.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.daniu.model.enums.StateEnum;

/**
 * 状态枚举转化器
 *
 * @author FangDaniu
 * @since 2024/05/13
 */
public class StateConverter implements Converter<Integer> {

    /**
     * 实体类中对象的属性类型
     *
     * @return {@link Class }<{@link ? }>
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    /**
     * excel 中对应的单元格数据属性类型
     *
     * @return {@link CellDataTypeEnum }
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 从CellData中读取数据，判断Excel中的值，将其转换为预期的数值
     *
     * @param context 上下文
     * @return {@link Integer }
     */
    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        return StateEnum.convert(context.getReadCellData().getStringValue()).getValue();
    }

    /**
     * 从实体类中读取数据，判断实体中的值，将其转换为Excel中的值
     *
     * @param context 上下文
     * @return {@link WriteCellData }<{@link ? }>
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        return new WriteCellData<>(StateEnum.convert(context.getValue()).getName());
    }
}
