package ke.co.stashare.syncsample.navigator;

import java.io.Serializable;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class Contact implements Serializable {

    private String name;

    public Contact(String name, String phone) {
        super();
        this.name = name;
        this.phone = phone;
    }

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
