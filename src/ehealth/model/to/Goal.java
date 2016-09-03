package ehealth.model.to;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * Created by Navid on 08/17/2016.
 */
//@XmlRootElement(name="measure")
@XmlType(propOrder = { "gid","created","value","measureDefinition","person"})
// XmlAccessorType indicates what to use to create the mapping: either FIELDS, PROPERTIES (i.e., getters/setters), PUBLIC_MEMBER or NONE (which means, you should indicate manually)
@XmlAccessorType(XmlAccessType.FIELD)
public class Goal {
    private Long gid;
    @XmlElement(name="dateRegistered")
    private Date created;
    @XmlElement(name="goalValue")
    private String value;

    private MeasureDefinition measureDefinition;

    private Person person;

    public Goal() {}

    public Goal(Goal goal)
    {
        this.gid = goal.gid;
        this.created = goal.created;
        this.value = goal.value;
        this.measureDefinition = new MeasureDefinition(goal.getMeasureDefinition());
        this.person = new Person(goal.getPerson());
    }

    //getters
    public Long getGid() {return this.gid;}
    public Date getCreated() {return this.created;}
    public String getValue() {return this.value;}
    public MeasureDefinition getMeasureDefinition() {return measureDefinition;}
    public Person getPerson() {return person;}


    //setters
    public void setGid(Long idMeasureHistory) {this.gid = idMeasureHistory;}
    public void setCreated(Date created) {this.created = created;}
    public void setValue(String value) {this.value = value;}
    public void setMeasureDefinition(MeasureDefinition measureDefinition) {this.measureDefinition = measureDefinition;}
    public void setPerson(Person person) {this.person = person;}
}
