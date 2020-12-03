package com.github.peacetrue.imports.csv;

import com.github.peacetrue.imports.ImportsContext;
import com.github.peacetrue.imports.ImportsException;
import com.github.peacetrue.imports.ImportsInputStreamProcessor;
import com.github.peacetrue.imports.ImportsRowProcessor;
import com.github.peacetrue.imports.supports.RowNumberWrapperImpl;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author xiayx
 */
@Setter
@Getter
public class CsvImportsInputStreamProcessor implements ImportsInputStreamProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ImportsRowProcessor<String> importsRowProcessor;

    public CsvImportsInputStreamProcessor(ImportsRowProcessor<String> importsRowProcessor) {
        this.importsRowProcessor = importsRowProcessor;
    }

    @Override
    public void processInputStream(InputStream inputStream, ImportsContext importsContext) throws IOException {
        logger.info("解析数据[{}]", inputStream);

        CsvImportsSetting setting = (CsvImportsSetting) importsContext.getImportsSetting();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, setting.getCharset()));
        String line;
        int lineIndex = -1;
        while ((line = reader.readLine()) != null) {
            lineIndex++;

            if (lineIndex == setting.getMaxRowCount()) {
                logger.debug("到达允许的最大行数[{}]，处理完成", setting.getMaxRowCount());
                return;
            }

            logger.debug("读取第[{}]行的信息[{}]", lineIndex + 1, line);

            if (lineIndex == 0) {
                String[] header = line.split(setting.getSeparator());
                if (!Arrays.equals(header, setting.getHeader())) {
                    throw new ImportsException(String.format("表头%s错误，期待%s", Arrays.toString(header), Arrays.toString(setting.getHeader())));
                }
                logger.debug("忽略第一行（标题行）");
                continue;
            }

            if (line.trim().equals("")) {
                logger.debug("忽略空行");
                continue;
            }

            String[] cells = line.split(setting.getSeparator(), setting.getHeader().length);
            logger.debug("转换成单元格数组[{}]", Arrays.toString(cells));

            importsRowProcessor.processRow(new RowNumberWrapperImpl<>(lineIndex + 1, cells), importsContext);
        }

    }
}
