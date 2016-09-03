package ehealth.model.to;

import javax.xml.bind.annotation.*;

/**
 * Created by Navid on 11/17/2015.
 */
//@XmlRootElement
@XmlType(propOrder = { "measureType", "measureType_Type"})
// XmlAccessorType indicates what to use to create the mapping: either FIELDS, PROPERTIES (i.e., getters/setters), PUBLIC_MEMBER or NONE (which means, you should indicate manually)
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureDefinition
{
    public MeasureDefinition() {}
    @XmlTransient
    private int idMeasureDef;
    private String measureType;
    @XmlElement(name="measureValueType")
    private String measureType_Type;

    public MeasureDefinition(MeasureDefinition measureDefinition)
    {
        this.idMeasureDef = measureDefinition.idMeasureDef;
        this.measureType = measureDefinition.measureType;
        this.measureType_Type = measureDefinition.measureType_Type;
    }

    //getters
    public int getIdMeasureDef() {return this.idMeasureDef;}
    public String getMeasureType() {return this.measureType;}
    public String getMeasureType_Type() {return this.measureType_Type;}

    //setters
    public void setIdMeasureDef(int idMeasureDef) {this.idMeasureDef = idMeasureDef;}
    public void setMeasureType(String measureType) {this.measureType = measureType;}
    public void setMeasureType_Type(String measureType_type) {this.measureType_Type = measureType_type;}
}
