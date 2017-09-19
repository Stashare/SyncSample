package ke.co.stashare.syncsample.ui.models;

import ke.co.stashare.syncsample.survey.helper.SampleRadioAdapter;

/**
 * Created by Ken Wainaina on 11/09/2017.
 */

public class Surveys {

    private String suv_id;
    private String suv_name;

    public Surveys(String suv_id, String suv_name) {
        this.suv_id = suv_id;
        this.suv_name = suv_name;
    }

    public String getSuv_id() {
        return suv_id;
    }

    public void setSuv_id(String suv_id) {
        this.suv_id = suv_id;
    }

    public String getSuv_name() {
        return suv_name;
    }

    public void setSuv_name(String suv_name) {
        this.suv_name = suv_name;
    }
}
