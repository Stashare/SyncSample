package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 26/07/2017.
 */


public class SubQuestions {
    private String sub_que;
    private String unique_subqueid;
    private String que_no;
    private String selections;
    private String type;
    private String single_response;

    public SubQuestions(String sub_que, String unique_subqueid,
                        String que_no, String selections, String type, String single_response) {
        this.sub_que = sub_que;
        this.unique_subqueid = unique_subqueid;
        this.que_no = que_no;
        this.selections = selections;
        this.type = type;
        this.single_response = single_response;
    }

    public String getSub_que() {
        return sub_que;
    }

    public String getUnique_subqueid() {
        return unique_subqueid;
    }

    public String getQue_no() {
        return que_no;
    }

    public String getSelections() {
        return selections;
    }

    public String getType() {
        return type;
    }

    public String getSingle_response() {
        return single_response;
    }

    public void setSub_que(String sub_que) {
        this.sub_que = sub_que;
    }

    public void setUnique_subqueid(String unique_subqueid) {
        this.unique_subqueid = unique_subqueid;
    }

    public void setQue_no(String que_no) {
        this.que_no = que_no;
    }

    public void setSelections(String selections) {
        this.selections = selections;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSingle_response(String single_response) {
        this.single_response = single_response;
    }
}
