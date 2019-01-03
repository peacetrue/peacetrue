package com.github.peacetrue.export;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author xiayx
 */
@EnableConfigurationProperties(ExportProperties.class)
public class ExportAutoConfiguration {

    private ExportProperties exportProperties;

    public ExportAutoConfiguration(ExportProperties exportProperties) {
        this.exportProperties = exportProperties;
    }

    @Bean
    @ConditionalOnMissingBean(ExportService.class)
    public ExportService exportService() {
        return new CSVExportService(exportProperties.getDefaultContent());
    }
}
