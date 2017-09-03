package ke.co.stashare.syncsample.survey.helper;

import java.io.Serializable;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class Quiz implements Serializable {

    private String que;
    private String que_no;
    private String is_subque;
    private String que_type;
    private String single_response;
    private String selections;
    private String section;
    private String sub_section;
    private String subque;
    private String subque_singleResponse;
    private String subque_type;
    private  String subque_no;
    private String subque_selections;

    public Quiz(String que, String que_no, String is_subque, String que_type, String single_response, String selections, String section, String sub_section,
                String subque, String subque_singleResponse, String subque_type, String subque_no, String subque_selections) {
        this.que = que;
        this.que_no = que_no;
        this.is_subque = is_subque;
        this.que_type = que_type;
        this.single_response = single_response;
        this.selections = selections;
        this.section = section;
        this.sub_section = sub_section;
        this.subque = subque;
        this.subque_singleResponse = subque_singleResponse;
        this.subque_type = subque_type;
        this.subque_no = subque_no;
        this.subque_selections = subque_selections;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getQue_no() {
        return que_no;
    }

    public void setQue_no(String que_no) {
        this.que_no = que_no;
    }

    public String getIs_subque() {
        return is_subque;
    }

    public void setIs_subque(String is_subque) {
        this.is_subque = is_subque;
    }

    public String getQue_type() {
        return que_type;
    }

    public void setQue_type(String que_type) {
        this.que_type = que_type;
    }

    public String getSingle_response() {
        return single_response;
    }

    public void setSingle_response(String single_response) {
        this.single_response = single_response;
    }

    public String getSelections() {
        return selections;
    }

    public void setSelections(String selections) {
        this.selections = selections;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSub_section() {
        return sub_section;
    }

    public void setSub_section(String sub_section) {
        this.sub_section = sub_section;
    }

    public String getSubque() {
        return subque;
    }

    public void setSubque(String subque) {
        this.subque = subque;
    }

    public String getSubque_singleResponse() {
        return subque_singleResponse;
    }

    public void setSubque_singleResponse(String subque_singleResponse) {
        this.subque_singleResponse = subque_singleResponse;
    }

    public String getSubque_type() {
        return subque_type;
    }

    public void setSubque_type(String subque_type) {
        this.subque_type = subque_type;
    }

    public String getSubque_no() {
        return subque_no;
    }

    public void setSubque_no(String subque_no) {
        this.subque_no = subque_no;
    }

    public String getSubque_selections() {
        return subque_selections;
    }

    public void setSubque_selections(String subque_selections) {
        this.subque_selections = subque_selections;
    }
}
