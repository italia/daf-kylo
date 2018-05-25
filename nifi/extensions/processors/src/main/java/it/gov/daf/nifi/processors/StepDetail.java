package it.gov.daf.nifi.processors;

import java.io.Serializable;

public class StepDetail implements Serializable {

    private String column;

    private String vocabularyPath;

    private String sourceDateFormat;

    private String sourceEncoding;

    public StepDetail() {
    }

    public StepDetail(String column) {
        this.column = column;
    }

    public void setVocabularyPath(String vocabularyPath) {
        this.vocabularyPath = vocabularyPath;
    }

    public void setSourceDateFormat(String sourceDateFormat) {
        this.sourceDateFormat = sourceDateFormat;
    }

    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public String getColumn() {
        return column;
    }

    public String getVocabularyPath() {
        return vocabularyPath;
    }

    public String getSourceDateFormat() {
        return sourceDateFormat;
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    @Override
    public String toString() {
        return "StepDetail{" +
                "name='" + column + '\'' +
                ", vocabularyPath='" + vocabularyPath + '\'' +
                ", sourceDateFormat='" + sourceDateFormat + '\'' +
                ", sourceEncoding='" + sourceEncoding + '\'' +
                '}';
    }
}
