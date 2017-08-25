package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 19/08/2017.
 */

public class Que {
    private int id;
    private String que;
    private String que_type;

    public Que(int id, String que, String que_type) {
        this.id = id;
        this.que = que;
        this.que_type = que_type;
    }

    public int getId() {
        return id;
    }

    public String getQue() {
        return que;
    }

    public String getQue_type() {
        return que_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public void setQue_type(String que_type) {
        this.que_type = que_type;
    }
}
