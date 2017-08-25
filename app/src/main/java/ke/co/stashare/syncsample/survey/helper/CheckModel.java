package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 11/08/2017.
 */

public class CheckModel {
   private String selection;
    private boolean isSelected;

    public CheckModel(String selection, boolean isSelected) {
        this.selection = selection;
        this.isSelected = isSelected;
    }

    public String getSelection() {
        return selection;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
