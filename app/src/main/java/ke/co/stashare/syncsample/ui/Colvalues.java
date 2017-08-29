package ke.co.stashare.syncsample.ui;

import java.io.Serializable;

/**
 * Created by Ken Wainaina on 29/08/2017.
 */

public class Colvalues implements Serializable {
    private String col;
    private String values;

    public Colvalues(String col, String values) {
        this.col = col;
        this.values = values;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
