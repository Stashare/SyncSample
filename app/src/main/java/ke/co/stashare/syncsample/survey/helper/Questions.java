package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 25/07/2017.
 */

  /* db.addQuestions(que_no, single_response,que,section,sub_sections,type,is_subquestion,selections);
*/
  public class Questions {


    private String que_no;
    private String que;
    private String selections;
    private String single_response;
    private String section;
    private String sub_sections;
    private String type;
    private String is_subquestion;

    public Questions(String que_no, String que, String selections,
                     String single_response, String section, String sub_sections, String type, String is_subquestion) {
        this.que_no = que_no;
        this.que = que;
        this.selections = selections;
        this.single_response = single_response;
        this.section = section;
        this.sub_sections = sub_sections;
        this.type = type;
        this.is_subquestion = is_subquestion;
    }

    public String getQue_no() {
        return que_no;
    }

    public String getQue() {
        return que;
    }

    public String getSelections() {
        return selections;
    }

    public String getSingle_response() {
        return single_response;
    }

    public String getSection() {
        return section;
    }

    public String getSub_sections() {
        return sub_sections;
    }

    public String getType() {
        return type;
    }

    public String getIs_subquestion() {
        return is_subquestion;
    }

    public void setQue_no(String que_no) {
        this.que_no = que_no;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public void setSelections(String selections) {
        this.selections = selections;
    }

    public void setSingle_response(String single_response) {
        this.single_response = single_response;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setSub_sections(String sub_sections) {
        this.sub_sections = sub_sections;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIs_subquestion(String is_subquestion) {
        this.is_subquestion = is_subquestion;
    }
}
