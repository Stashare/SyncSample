package ke.co.stashare.syncsample.survey.helper;

import java.util.List;

import ke.co.stashare.syncsample.ui.models.SuvModel;

/**
 * Created by Ken Wainaina on 12/09/2017.
 */

public class Selected_SUVLIST {
    private List<SuvModel> results;

    public Selected_SUVLIST(List<SuvModel> results) {
        this.results = results;
    }

    public List<SuvModel> getResults() {
        return results;
    }

    public void setResults(List<SuvModel> results) {
        this.results = results;
    }
}
