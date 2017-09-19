package ke.co.stashare.syncsample.ui.models;

import java.util.List;

import ke.co.stashare.syncsample.ui.Colvalues;

/**
 * Created by Ken Wainaina on 18/09/2017.
 */

public class AddedSavedList {
    private List<String> results;

    public AddedSavedList (List<String> results) {
        this.results = results;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }


}
