package it.gov.daf.nifi.processors.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Trasnformations implements Serializable {

    private String datasetPath;

    private List<TransformationStep> transformationList;


    public static List<TransformationStep> gensTransformations(List<String> list, Stream<FlatSchema> flatSchemaStream) {

        List<TransformationStep> transformationSteps = new ArrayList<>();

        for (String transformationName : list) {

            switch (transformationName) {
                case "text to utf-8":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "empty to null":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "date to ISO8601":
                    transformationSteps.add(new TransformationStep(transformationName));
                    break;

                case "url to standard":
                    final List<String> urls = flatSchemaStream
                            .filter(s -> s.getMetadata().getCat().equals("url"))
                            .map(FlatSchema::getName)
                            .collect(toList());

                    if (!urls.isEmpty())
                        transformationSteps.add(new TransformationStep(transformationName, urls));
                    break;

                case "vocabulary validate":
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
