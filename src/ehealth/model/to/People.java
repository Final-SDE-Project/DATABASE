package ehealth.model.to;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navid on 11/17/2015.
 */

//@XmlRootElement(name="people")
@XmlAccessorType(XmlAccessType.FIELD)
public class People {

    @XmlElement
    private List<Person> person = new ArrayList<Person>();

    public People () {
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }
}
