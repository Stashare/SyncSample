package ke.co.stashare.syncsample.ui.models;

/**
 * Created by Ken Wainaina on 15/09/2017.
 */

public class AssessoModel {

    private String survey_id;
    private String assessment_id;
    private String assessment_name;

    public AssessoModel(String survey_id, String assessment_id, String assessment_name) {
        this.survey_id = survey_id;
        this.assessment_id = assessment_id;
        this.assessment_name = assessment_name;
    }

    public String getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(String survey_id) {
        this.survey_id = survey_id;
    }

    public String getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(String assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }
}
