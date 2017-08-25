package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 22/08/2017.
 */

public class Answers {
    String que;
    String ans;

    public Answers(String que, String ans) {
        this.que = que;
        this.ans = ans;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
