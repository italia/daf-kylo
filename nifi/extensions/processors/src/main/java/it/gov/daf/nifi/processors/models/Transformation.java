package it.gov.daf.nifi.processors.models;

import java.io.Serializable;
import java.util.List;

/**
 *  This class holds the definition of all the possible transformations
 *  that can be done
 */
public class Transformation implements Serializable {

    private String name;

    private List<String> columns;

    private String vocabularyPath;

    public Transformation() {
    }

    public Transformation(String name) {
        this.name = name;
    }

    public Transformation(String name, List<String> columns) {
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

    @Override
    public String toString() {
        return "Transformation{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", vocabularyPath='" + vocabularyPath + '\'' +
                '}';
    }
}
