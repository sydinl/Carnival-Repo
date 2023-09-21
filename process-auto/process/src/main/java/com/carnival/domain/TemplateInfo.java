package com.carnival.domain;

import lombok.Data;

@Data
public class TemplateInfo {

    private Long myRowId;
    private String sourceEnd;
    private String source;
    private String destination;
    private String destinationEnd;
    private String templateName;

}
