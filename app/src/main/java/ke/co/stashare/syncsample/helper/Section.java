package ke.co.stashare.syncsample.helper;

/**
 * Created by Ken Wainaina on 01/04/2017.
 */


public class Section {

    public String title;
    private String url;

    public Section(String title, String url) {
        this.title = title;
        this.url= url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}
