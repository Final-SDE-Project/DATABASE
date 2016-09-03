package ehealth.controller_db.ws;

import ehealth.model.to.Goal;
import ehealth.model.to.HealthMeasureHistory;
import ehealth.model.to.MeasureDefinition;
import ehealth.model.to.Person;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import java.util.Date;
import java.util.List;

/**
 * Created by Navid on 12/3/2015.
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface ServiceDB {

    @WebMethod(operationName="readPersonList")
    @WebResult(name="people")
    public List<Person> readPersonList();

    @WebMethod(operationName="readPerson")
    @WebResult(name="person")
    public Person readPerson(@WebParam(name="idPerson") Long id);

    @WebMethod(operationName="updatePerson")
    public void updatePerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="createPerson")
    @WebResult
    public Person createPerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="deletePerson")
    public void deletePerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readPersonHistory(@WebParam(name="idPerson") Long idPerson,@WebParam(name="measureType") String measureType);

    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measureTypes")
    public List<String> readMeasureTypes();

    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measure")
    public HealthMeasureHistory readPersonMeasure(@WebParam(name="idPerson") Long idPerson,@WebParam(name="measureType") String measureType,@WebParam(name="mid") Long mid);

    @WebMethod(operationName="savePersonMeasure")
    @WebResult
    public HealthMeasureHistory savePersonMeasure(@WebParam(name="idPerson") Long id,@WebParam(name="measure") HealthMeasureHistory healthMeasureHistory);

    @WebMethod(operationName="updatePersonMeasure")
    @WebResult
    public HealthMeasureHistory updatePersonMeasure(@WebParam(name="idPerson") Long id,@WebParam(name="measure") HealthMeasureHistory healthMeasureHistory);

    @WebMethod(operationName="readMeasureType")
    @WebResult(name="measureType")
    public MeasureDefinition readMeasureType(@WebParam(name="measureType") String measureType);

    @WebMethod(operationName="readMeasureHistoriesByDate")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readMeasureHistoriesByDate(@WebParam(name="idPerson") Long idPerson, @WebParam(name="measureType") String measureType, @WebParam(name="before") Date before, @WebParam(name="after") Date after);

    //GOALS PART
    @WebMethod(operationName="updateGoal")
    @WebResult
    public Goal updateGoal(@WebParam(name="goal") Goal goal);

    @WebMethod(operationName="readGoalList")
    @WebResult(name="goals")
    public List<Goal> readGoalList(@WebParam(name="idPerson") Long id);

    @WebMethod(operationName="readLatestGoalList")
    @WebResult(name="goals")
    public List<Goal> readLatestGoalList(@WebParam(name="idPerson") Long id);

    @WebMethod(operationName="saveGoal")
    @WebResult
    public Goal saveGoal(@WebParam(name="goal") Goal goal);


}
