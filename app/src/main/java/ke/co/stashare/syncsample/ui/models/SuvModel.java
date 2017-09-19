package ke.co.stashare.syncsample.ui.models;

/**
 * Created by Ken Wainaina on 12/09/2017.
 */

public class SuvModel {
    private String id;
    private String survey_name;

    public SuvModel(String id, String survey_name) {
        this.id = id;
        this.survey_name = survey_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurvey_name() {
        return survey_name;
    }

    public void setSurvey_name(String survey_name) {
        this.survey_name = survey_name;
    }
}
