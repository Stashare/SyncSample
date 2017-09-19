package ke.co.stashare.syncsample.ui.models;

/**
 * Created by Ken Wainaina on 12/09/2017.
 */

public class SelectedSuvies {
    private int pos;
    private String name;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelectedSuvies(int pos, String name) {

        this.pos = pos;
        this.name = name;
    }
}
