package ehealth.model.to;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navid on 11/17/2015.
 */
//@XmlRootElement(name="measureTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureTypes {

    @XmlElement
    private List<String> measureType = new ArrayList<String>();

    public MeasureTypes() {
    }

    public List<String> getMeasureType() {
        return measureType;
    }

    public void setMeasureType(List<String> measureType) {
        this.measureType = measureType;
    }
}
