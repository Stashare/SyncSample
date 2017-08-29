package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 28/08/2017.
 */

public class GenQuiz {
    String que;
    String que_title;

    public GenQuiz(String que, String que_title) {
        this.que = que;
        this.que_title = que_title;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getQue_title() {
        return que_title;
    }

    public void setQue_title(String que_title) {
        this.que_title = que_title;
    }
}
