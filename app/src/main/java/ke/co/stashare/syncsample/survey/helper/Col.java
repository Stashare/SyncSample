package ke.co.stashare.syncsample.survey.helper;

import android.net.wifi.ScanResult;

import java.util.List;

/**
 * Created by Ken Wainaina on 19/03/2017.
 */

public class Col {

    private List<String> results;

    public Col(List<String> results) {
        this.results = results;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
