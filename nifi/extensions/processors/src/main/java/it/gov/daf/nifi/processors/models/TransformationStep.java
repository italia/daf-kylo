package it.gov.daf.nifi.processors.models;

import java.io.Serializable;
import java.util.List;

/**
 *  This class holds the definition of all the possible transformations
 *  that can be done
 */
public class TransformationStep implements Serializable {

    private String name;

    private List<String> columns;

    //the following are optionals
    private String vocabularyPath;

    private String sourceDateFormat;

    public TransformationStep() {
    }

    public TransformationStep(String name) {
        this.name = name;
    }

    public TransformationStep(String name, List<String> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getVocabularyPath() {
        return vocabularyPath;
    }

    public void setVocabularyPath(String vocabularyPath) {
        this.vocabularyPath = vocabularyPath;
    }

    public void setSourceDateFormat(String sourceDateFormat) {
        this.sourceDateFormat = sourceDateFormat;
    }

    public String getSourceDateFormat() {
        return sourceDateFormat;
    }

    @Override
    public String toString() {
        return "TransformationStep{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", vocabularyPath='" + vocabularyPath + '\'' +
                ", sourceDateFormat='" + sourceDateFormat + '\'' +
                '}';
    }
}
