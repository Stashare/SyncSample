package ke.co.stashare.syncsample.ui.models;

import java.io.Serializable;

/**
 * Created by Ken Wainaina on 13/09/2017.
 */

public class Quiza implements Serializable {

    private String survey_id;
    private String question_id;
    private String question_name;
    private String description;
    private String  answer_type;
    private String sub_question_statuse;
    private String section_name;
    private String sub_section;
    private String column_name;
    private String parent_id;
    private String selections;

    private String assessment_id;
    private String assessment_name;
    private String country_status;
    private String display_status;
    private String submitted_date;
    private String project_id;

    public Quiza(String survey_id, String question_id, String question_name, String description, String answer_type, String sub_question_statuse, String section_name, String sub_section, String column_name, String parent_id, String selections, String assessment_id, String assessment_name,
                 String country_status, String display_status, String submitted_date, String project_id) {
        this.survey_id = survey_id;
        this.question_id = question_id;
        this.question_name = question_name;
        this.description = description;
        this.answer_type = answer_type;
        this.sub_question_statuse = sub_question_statuse;
        this.section_name = section_name;
        this.sub_section = sub_section;
        this.column_name = column_name;
        this.parent_id = parent_id;
        this.selections = selections;
        this.assessment_id = assessment_id;
        this.assessment_name = assessment_name;
        this.country_status = country_status;
        this.display_status = display_status;
        this.submitted_date = submitted_date;
        this.project_id = project_id;
    }

    public String getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(String survey_id) {
        this.survey_id = survey_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer_type() {
        return answer_type;
    }

    public void setAnswer_type(String answer_type) {
        this.answer_type = answer_type;
    }

    public String getSub_question_statuse() {
        return sub_question_statuse;
    }

    public void setSub_question_statuse(String sub_question_statuse) {
        this.sub_question_statuse = sub_question_statuse;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSub_section() {
        return sub_section;
    }

    public void setSub_section(String sub_section) {
        this.sub_section = sub_section;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSelections() {
        return selections;
    }

    public void setSelections(String selections) {
        this.selections = selections;
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

    public String getCountry_status() {
        return country_status;
    }

    public void setCountry_status(String country_status) {
        this.country_status = country_status;
    }

    public String getDisplay_status() {
        return display_status;
    }

    public void setDisplay_status(String display_status) {
        this.display_status = display_status;
    }

    public String getSubmitted_date() {
        return submitted_date;
    }

    public void setSubmitted_date(String submitted_date) {
        this.submitted_date = submitted_date;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}
