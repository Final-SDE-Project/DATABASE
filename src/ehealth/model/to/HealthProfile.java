package ehealth.model.to;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Created by Navid on 11/17/2015.
 */
//@XmlRootElement(name="healthProfile")
@XmlType(propOrder = { "mid","created","measure","value","measureValueType"})
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthProfile {
    //@XmlTransient
    private Long mid;
    @XmlElement(name="measureType")
    private String measure;
    private String measureValueType;
    @XmlElement(name="measureValue")
    private String value;
    @XmlElement(name="dateRegistered")
    private Date created;

    public HealthProfile() {}

    //FOR COPYING DATA in Person
    public HealthProfile(HealthProfile healthProfile)
    {
        this.mid=healthProfile.mid;
        this.measure = healthProfile.measure;
        this.value = healthProfile.value;
        this.measureValueType = healthProfile.measureValueType;
        this.created = healthProfile.created;
    }

    //getters
    public Long getMid() {return this.mid;}
    public String getValue() {return this.value;}
    public String getMeasure() {return measure;}
    public String getMeasureValueType() {return measureValueType;}
    public Date getCreated() {return this.created;}

    //setters
    public void setMid(Long idMeasureHistory) {this.mid = idMeasureHistory;}
    public void setValue(String value) {this.value = value;}
    public void setMeasure(String measure) {this.measure = measure;}
    public void setMeasureValueType(String measureValueType) {this.measureValueType = measureValueType;}
    public void setCreated(Date created) {this.created = created;}
}
