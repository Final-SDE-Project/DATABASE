package ehealth.model.to;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navid on 11/17/2015.
 */

//@XmlRootElement(name="healthProfile-history")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureHistory {

    @XmlElement
    private List<HealthMeasureHistory> measure = new ArrayList<HealthMeasureHistory>();

    public MeasureHistory() {
    }

    public List<HealthMeasureHistory> getHealthMeasureHistories() {
        return measure;
    }

    public void setHealthMeasureHistories(List<HealthMeasureHistory> healthMeasureHistories) {
        this.measure = healthMeasureHistories;
    }
}
