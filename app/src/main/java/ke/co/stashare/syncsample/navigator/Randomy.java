package ke.co.stashare.syncsample.navigator;

import java.io.Serializable;

/**
 * Created by Ken Wainaina on 27/08/2017.
 */

public class Randomy implements Serializable {

    private String que;

    public Randomy(String que) {
        this.que = que;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }
}
