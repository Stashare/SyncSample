package ke.co.stashare.syncsample.survey.helper;

import android.net.wifi.ScanResult;

import java.util.List;

/**
 * Created by Ken Wainaina on 19/03/2017.
 */

public class DbList {

    private List<Answers> results;

    public DbList(List<Answers> results) {
        this.results = results;
    }

    public List<Answers> getResults() {
        return results;
    }

    public void setResults(List<Answers> results) {
        this.results = results;
    }
}
