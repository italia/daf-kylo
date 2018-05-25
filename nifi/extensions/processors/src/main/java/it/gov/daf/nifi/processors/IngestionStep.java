package it.gov.daf.nifi.processors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class holds the definition of all the possible transformations
 *  that can be done
 */
public class IngestionStep implements Serializable {

    private int priority;

    private String name;

    private List<StepDetail> stepDetails = new ArrayList<>();

    public IngestionStep() {
    }

    public IngestionStep(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public IngestionStep(int priority, String name, List<StepDetail> stepDetails) {
        this.priority = priority;
        this.name = name;
        this.stepDetails = stepDetails;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public List<StepDetail> getStepDetails() {
        return stepDetails;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStepDetails(List<StepDetail> stepDetails) {
        this.stepDetails = stepDetails;
    }

    @Override
    public String toString() {
        return "IngestionStep{" +
                "priority='" + priority + '\'' +
                ", name='" + name + '\'' +
                ", stepDetails=" + stepDetails +
                '}';
    }
}
