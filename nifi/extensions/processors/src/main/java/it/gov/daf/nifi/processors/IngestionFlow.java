package it.gov.daf.nifi.processors;

import it.gov.daf.nifi.processors.models.FlatSchema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class IngestionFlow implements Serializable {

    private String datasetName;
    private String datasetUri;

    private List<IngestionStep> steps = new ArrayList<>();

    public IngestionFlow() {
    }

    public IngestionFlow(String datasetName, String datasetUri, List<IngestionStep> steps) {
        this.datasetName = datasetName;
        this.datasetUri = datasetUri;
        this.steps = steps;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getDatasetUri() {
        return datasetUri;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetUri(String datasetUri) {
        this.datasetUri = datasetUri;
    }

    public void setSteps(List<IngestionStep> steps) {
        this.steps = steps;
    }

    public List<IngestionStep> getSteps() {
        return steps;
    }

    public static final String DTYPE_STRING = "string";

    public static final String DTYPE_DATE = "date";

    /**
     *  Given the flat schema generates the Transformation Steps
     * @param list
     * @param flatSchemaStream
     * @return
     */
    public static List<IngestionStep> gensTransformations(List<String> list, List<FlatSchema> flatSchemaStream) {

        final String dummyCharset = "UTF-8";
        final String dummyDateFormat = "dd/mm/yyyy";

        List<IngestionStep> transformationSteps = new ArrayList<>();
        List<StepDetail> columns;

        for (String transformationName : list) {

            switch (transformationName) {
                case "text to utf-8":

                    //extract the string columns
                    columns = flatSchemaStream.stream()
                            .filter(x -> x.getType().equals(DTYPE_STRING))
                            .map(FlatSchema::getName)
                            .map(name -> {
                                final StepDetail format = new StepDetail(name);
                                format.setSourceEncoding(dummyCharset);
                                return format;
                            })
                            .collect(toList());
                    if (!columns.isEmpty())
                        transformationSteps.add(new IngestionStep(1, transformationName, columns));
                    break;

                case "empty values to null":
                    columns = flatSchemaStream.stream()
                            .filter(x -> x.getType().equals(DTYPE_STRING))
                            .map(FlatSchema::getName)
                            .map(StepDetail::new)
                            .collect(toList());
                    if (!columns.isEmpty())
                        transformationSteps.add(new IngestionStep(2, transformationName, columns));
                    break;

                case "url to standard":
                    columns = flatSchemaStream.stream()
                            .filter(s -> s.getType().equals("url"))
                            .map(FlatSchema::getName)
                            .map(StepDetail::new)
                            .collect(toList());

                    if (!columns.isEmpty())
                        transformationSteps.add(new IngestionStep(3, transformationName, columns));
                    break;

                case "date to ISO8601":
                    columns = flatSchemaStream.stream()
                            .filter(x -> x.getType().equals(DTYPE_DATE))
                            .map(FlatSchema::getName)
                            .map(name -> {
                                final StepDetail format = new StepDetail(name);
                                format.setSourceDateFormat(dummyDateFormat);
                                return format;
                            })
                            .collect(toList());
                    if (!columns.isEmpty())
                        transformationSteps.add(new IngestionStep(4, transformationName, columns));
                    break;


                case "vocabulary validation":
                    //TODO for each column that has a vocabulary associated add the transformation
                    //int priority = 5;
                    break;

                case "enrich address":
                    //TODO for each column that his tagged as address
                    //int priority = 6;
                    break;

                case "add row id":
                    transformationSteps.add(new IngestionStep(7, transformationName));
                    break;

                case "add ingestion date":
                    transformationSteps.add(new IngestionStep(8, transformationName));
                    break;

                case "add update date":
                    transformationSteps.add(new IngestionStep(9, transformationName));
                    break;

                default:
                    break;
            }
        }

        return transformationSteps;
    }
}
