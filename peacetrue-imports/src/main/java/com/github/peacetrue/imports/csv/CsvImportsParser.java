package com.github.peacetrue.imports.csv;//package com.github.peacetrue.imports.csv;
//
//import com.github.peacetrue.imports.*;
//import com.github.peacetrue.imports.supports.CellErrorMessage;
//import com.github.peacetrue.imports.supports.ImportsResultImpl;
//import com.github.peacetrue.imports.supports.RowIndexWrapperImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * @author xiayx
// */
//public class CsvImportsParser<T> implements ImportsRowProcessor<T> {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public ImportsResult parse(List<T> records, ImportsContext importsContext) {
//        logger.info("解析数据共[{}]条", records.size());
//
//        ImportsResultImpl result = new ImportsResultImpl();
//        result.setRecords(new LinkedList<>());
//        result.setErrorMessages(new LinkedList<>());
//
//        for (RowIndexWrapper<String[]> wrapper : records) {
//            Object[] parsedCells = new Object[importsSetting.getHeader().length];
//            for (int i = 0; i < wrapper.getRow().length; i++) {
//                String cell = wrapper.getRow()[i];
//                logger.debug("读取单元格[{},{}]的内容[{}]", wrapper.getRowNumber(), i, cell);
//                ColumnSetting columnSetting = importsSetting.getColumnSettingSupplier().getColumnSetting(i);
//                parsedCells[i] = columnSetting.getParser().apply(cell);
//            }
//            try {
//                Object record = importsSetting.getRowConverter().convert(parsedCells, importsSetting);
//                result.getCheckedRecords().add(new RowIndexWrapperImpl<>(wrapper.getRowNumber(), record));
//            } catch (CellConvertException e) {
//                result.getErrorMessages().add(new CellErrorMessage(wrapper.getRowNumber(), e.getIndex(), e.getMessage()));
//            }
//        }
//
//        return result;
//
//    }
//}
