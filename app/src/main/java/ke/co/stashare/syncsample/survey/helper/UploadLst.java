package ke.co.stashare.syncsample.survey.helper;

import java.util.List;

import ke.co.stashare.syncsample.ui.Colvalues;

/**
 * Created by Ken Wainaina on 29/08/2017.
 */

public class UploadLst {
    private List<Colvalues> results;

    public UploadLst(List<Colvalues> results) {
        this.results = results;
    }

    public List<Colvalues> getResults() {
        return results;
    }

    public void setResults(List<Colvalues> results) {
        this.results = results;
    }
}
