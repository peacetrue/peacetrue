package com.github.peacetrue.export;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author xiayx
 */
public class CSVExportService implements ExportService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String defaultContent;

    public CSVExportService() {
        this("--");
    }

    public CSVExportService(String defaultContent) {
        this.defaultContent = defaultContent;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void export(Writer out, Header header, Iterable<?> records) throws IOException {
        logger.debug("导出记录");

        String[] headerContents = IntStream.range(0, header.getCount()).mapToObj(value -> header.getCell(value).getContent()).toArray(String[]::new);
        logger.debug("表头: {}", Arrays.toString(headerContents));

        final CSVPrinter printer = CSVFormat.DEFAULT.withHeader(headerContents).print(out);
        int index = 1;
        for (Object record : records) {
            logger.trace("导出第{}行记录: {}", index, record);
            for (int i = 0; i < header.getCount(); i++) {
                Header.Cell cell = header.getCell(i);
                Object content = cell.getFormatter().apply(record);
                printer.print(Objects.toString(content, defaultContent));
                logger.trace("导出第{}行第{}个单元格: {}", index, i, content);
            }
            index++;
            printer.println();
        }
        printer.flush();
        printer.close();
    }
}
