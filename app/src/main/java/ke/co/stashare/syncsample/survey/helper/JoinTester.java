package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 27/07/2017.
 */

public class JoinTester {
    private String que_no;
    private String sub_queNo;
    private String que;
    private String sub_que;
    private String que_type;
    private String subque_type;
    private String is_subquestion;

    public JoinTester(String que_no, String sub_queNo, String que,
                      String sub_que, String que_type, String subque_type, String is_subquestion) {
        this.que_no = que_no;
        this.sub_queNo = sub_queNo;
        this.que = que;
        this.sub_que = sub_que;
        this.que_type = que_type;
        this.subque_type = subque_type;
        this.is_subquestion = is_subquestion;
    }

    public String getQue_no() {
        return que_no;
    }

    public String getSub_queNo() {
        return sub_queNo;
    }

    public String getQue() {
        return que;
    }

    public String getSub_que() {
        return sub_que;
    }

    public String getQue_type() {
        return que_type;
    }

    public String getSubque_type() {
        return subque_type;
    }

    public String getIs_subquestion() {
        return is_subquestion;
    }

    public void setQue_no(String que_no) {
        this.que_no = que_no;
    }

    public void setSub_queNo(String sub_queNo) {
        this.sub_queNo = sub_queNo;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public void setSub_que(String sub_que) {
        this.sub_que = sub_que;
    }

    public void setQue_type(String que_type) {
        this.que_type = que_type;
    }

    public void setSubque_type(String subque_type) {
        this.subque_type = subque_type;
    }

    public void setIs_subquestion(String is_subquestion) {
        this.is_subquestion = is_subquestion;
    }
}
