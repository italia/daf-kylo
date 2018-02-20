package it.gov.daf.nifi.processors.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Transformations implements Serializable {

    private String datasetPath;

    private List<TransformationStep> transformationSteps = new ArrayList<>();

    public Transformations() {
    }

    public Transformations(String datasetPath, List<TransformationStep> transformationSteps) {
        this.datasetPath = datasetPath;
        this.transformationSteps = transformationSteps;
    }

    public void setDatasetPath(String datasetPath) {
        this.datasetPath = datasetPath;
    }

    public String getDatasetPath() {
        return datasetPath;
    }

    public void setTransformationSteps(List<TransformationStep> transformationSteps) {
        this.transformationSteps = transformationSteps;
    }

    public List<TransformationStep> getTransformationSteps() {
        return transformationSteps;
    }

    public static List<TransformationStep> gensTransformations(List<String> list, List<FlatSchema> flatSchemaStream) {

        List<TransformationStep> transformationSteps = new ArrayList<>();

        for (String transformationName : list) {

            switch (transformationName) {
                case "text to utf-8":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "empty values to null":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "date to ISO8601":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "url to standard":
                    final List<String> urls = flatSchemaStream.stream()
                            .filter(s -> s.getMetadata().getCat().equals("url"))
                            .map(FlatSchema::getName)
                            .collect(toList());

                    if (!urls.isEmpty())
                        transformationSteps.add(new TransformationStep(transformationName, urls));
                    break;

                case "vocabulary validation":
                    //TODO for each column that has a vocabulary associated add the transformation
                    break;

                case "enrich address":
                    //TODO for each column that his tagged as address
                    break;

                case "add row id":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "add ingestion date":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "add update date":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                default:

            }


        }

        return transformationSteps;
    }
}
