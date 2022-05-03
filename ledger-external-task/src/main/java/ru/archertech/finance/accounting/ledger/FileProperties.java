package ru.archertech.finance.accounting.ledger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@Component
public class FileProperties {
    private String folder;
    private String name;

    public FileProperties() {
    }

    public FileProperties(String folder, String name) {
        this.folder = folder;
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
