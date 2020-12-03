package com.github.peacetrue.imports.csv;

import com.github.peacetrue.imports.*;
import com.github.peacetrue.imports.supports.ImportsContextImpl;
import com.github.peacetrue.imports.supports.ImportsResultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiayx
 */
public class CsvImportsService implements ImportsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ImportsInputStreamProcessor inputStreamProcessor;
    private ImportsChecker importsChecker;
    private ImportsSaver importsSaver;

    public CsvImportsService() {
    }

    public CsvImportsService(ImportsInputStreamProcessor inputStreamProcessor, ImportsChecker importsChecker, ImportsSaver importsSaver) {
        this.inputStreamProcessor = inputStreamProcessor;
        this.importsChecker = importsChecker;
        this.importsSaver = importsSaver;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ImportsResult imports(InputStream inputStream, ImportsSetting importsSetting) throws IOException {
        logger.info("从输入流[{}]中导入数据", inputStream);

        ImportsContextImpl importsContext = new ImportsContextImpl(importsSetting, new ImportsResultImpl<>());
        inputStreamProcessor.processInputStream(inputStream, importsContext);
        importsChecker.check(importsContext.getImportsResult().getParsedRecords(), importsContext);
        importsSaver.save(importsContext.getImportsResult().getCheckedRecords(), importsContext);
        return importsContext.getImportsResult();
    }

    public ImportsInputStreamProcessor getInputStreamProcessor() {
        return inputStreamProcessor;
    }

    public void setInputStreamProcessor(ImportsInputStreamProcessor inputStreamProcessor) {
        this.inputStreamProcessor = inputStreamProcessor;
    }

    public ImportsChecker getImportsChecker() {
        return importsChecker;
    }

    public void setImportsChecker(ImportsChecker<?> importsChecker) {
        this.importsChecker = importsChecker;
    }

    public ImportsSaver getImportsSaver() {
        return importsSaver;
    }

    public void setImportsSaver(ImportsSaver importsSaver) {
        this.importsSaver = importsSaver;
    }
}
