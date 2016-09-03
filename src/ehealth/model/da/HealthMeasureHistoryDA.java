package ehealth.model.da;

import ehealth.model.to.HealthMeasureHistory;
import ehealth.model.to.MeasureDefinition;
import ehealth.model.to.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Navid on 11/17/2015.
 */
public class HealthMeasureHistoryDA {
    private Connection connection;
    private PreparedStatement statement;

    public HealthMeasureHistoryDA()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:ehealth.sqlite");
            connection.setAutoCommit(true);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<HealthMeasureHistory> selectByPerson_MeasureDef(Long personId, int measureDefinitionId) throws Exception
    {
        statement = connection.prepareStatement(
                "SELECT hmh.idMeasureHistory, hmh.idPerson, hmh.idMeasureDefinition, hmh.value, hmh.created " +
                        "FROM (SELECT idPerson, idMeasureDefinition, MAX(created) AS MaxDate " +
                        "FROM HealthMeasureHistory GROUP BY idPerson, idMeasureDefinition) temp " +
                        "INNER JOIN HealthMeasureHistory hmh ON temp.idPerson = hmh.idPerson " +
                        "AND temp.idMeasureDefinition = hmh.idMeasureDefinition " +
                        "AND temp.MaxDate > hmh.created AND hmh.idPerson = ? AND hmh.idMeasureDefinition = ?");
        statement.setLong(1, personId);
        statement.setInt(2, measureDefinitionId);
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<HealthMeasureHistory> healthMeasureHistories = new ArrayList<HealthMeasureHistory>();
        while (resultSet.next())
        {
            HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
            healthMeasureHistory.setMid(resultSet.getLong("idMeasureHistory"));
            healthMeasureHistory.setValue(resultSet.getString("value"));
            healthMeasureHistory.setCreated(resultSet.getDate("created"));
            healthMeasureHistory.setPerson(new PersonDA().selectById(resultSet.getLong("idPerson")));

            MeasureDefinition measureDefinition = new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition"));

            healthMeasureHistory.setMeasureDefinition(measureDefinition);
            healthMeasureHistory.setMeasureType(measureDefinition.getMeasureType());
            healthMeasureHistory.setMeasureValueType(measureDefinition.getMeasureType_Type());

            healthMeasureHistories.add(healthMeasureHistory);
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " + getClass().getSimpleName() + " selectByPerson_MeasureDef");

        return healthMeasureHistories;
    }


    public ArrayList<HealthMeasureHistory> selectByPerson_MeasureDef_Date(Long personId, int measureDefinitionId, Date before, Date after) throws Exception
    {
        statement = connection.prepareStatement(
                "SELECT hmh.idMeasureHistory, hmh.idPerson, hmh.idMeasureDefinition, hmh.value, hmh.created " +
                        "FROM (SELECT idPerson, idMeasureDefinition, MAX(created) AS MaxDate " +
                        "FROM HealthMeasureHistory GROUP BY idPerson, idMeasureDefinition) temp " +
                        "INNER JOIN HealthMeasureHistory hmh ON temp.idPerson = hmh.idPerson " +
                        "AND temp.idMeasureDefinition = hmh.idMeasureDefinition " +
                        "AND temp.MaxDate > hmh.created AND hmh.idPerson = ? AND hmh.idMeasureDefinition = ? " +
                        "AND hmh.created <= ? AND hmh.created >= ?");
        statement.setLong(1, personId);
        statement.setInt(2, measureDefinitionId);
        statement.setDate(3, new java.sql.Date(before.getTime()));
        statement.setDate(4, new java.sql.Date(after.getTime()));
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<HealthMeasureHistory> healthMeasureHistories = new ArrayList<HealthMeasureHistory>();
        while (resultSet.next())
        {
            HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
            healthMeasureHistory.setMid(resultSet.getLong("idMeasureHistory"));
            healthMeasureHistory.setValue(resultSet.getString("value"));
            healthMeasureHistory.setCreated(resultSet.getDate("created"));
            healthMeasureHistory.setPerson(new PersonDA().selectById(resultSet.getLong("idPerson")));

            MeasureDefinition measureDefinition = new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition"));

            healthMeasureHistory.setMeasureDefinition(measureDefinition);
            healthMeasureHistory.setMeasureType(measureDefinition.getMeasureType());
            healthMeasureHistory.setMeasureValueType(measureDefinition.getMeasureType_Type());

            healthMeasureHistories.add(healthMeasureHistory);
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " +getClass().getSimpleName() + " selectByPerson_MeasureDef_Date");

        return healthMeasureHistories;
    }

    public HealthMeasureHistory selectByPerson_MeasureDef_mId(Long personId, int measureDefinitionId, Long mId) throws Exception
    {
        statement = connection.prepareStatement(
                "SELECT hmh.idMeasureHistory, hmh.idPerson, hmh.idMeasureDefinition, hmh.value, hmh.created " +
                        "FROM (SELECT idPerson, idMeasureDefinition, MAX(created) AS MaxDate " +
                        "FROM HealthMeasureHistory GROUP BY idPerson, idMeasureDefinition) temp " +
                        "INNER JOIN HealthMeasureHistory hmh ON temp.idPerson = hmh.idPerson " +
                        "AND temp.idMeasureDefinition = hmh.idMeasureDefinition " +
                        "AND temp.MaxDate > hmh.created AND hmh.idPerson = ? AND hmh.idMeasureDefinition = ? AND " +
                        "hmh.idMeasureHistory = ?");
        statement.setLong(1, personId);
        statement.setInt(2, measureDefinitionId);
        statement.setLong(3, mId);
        ResultSet resultSet =  statement.executeQuery();
        HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
        while (resultSet.next())
        {
            healthMeasureHistory.setMid(resultSet.getLong("idMeasureHistory"));
            healthMeasureHistory.setValue(resultSet.getString("value"));
            healthMeasureHistory.setCreated(resultSet.getDate("created"));
            healthMeasureHistory.setPerson(new PersonDA().selectById(resultSet.getLong("idPerson")));

            MeasureDefinition measureDefinition = new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition"));

            healthMeasureHistory.setMeasureDefinition(measureDefinition);
            healthMeasureHistory.setMeasureType(measureDefinition.getMeasureType());
            healthMeasureHistory.setMeasureValueType(measureDefinition.getMeasureType_Type());
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " +getClass().getSimpleName() + " selectByPerson_MeasureDef_mId");

        return healthMeasureHistory;
    }

    public HealthMeasureHistory insert(HealthMeasureHistory healthMeasureHistory, boolean systemTime) throws Exception
    {
        statement = connection.prepareStatement("INSERT INTO HealthMeasureHistory (idPerson,idMeasureDefinition,value,created) VALUES (?,?,?,?)");

        statement.setLong(1, healthMeasureHistory.getPerson().getIdPerson());
        statement.setInt(2, healthMeasureHistory.getMeasureDefinition().getIdMeasureDef());
        statement.setString(3, healthMeasureHistory.getValue());
        //When we are posting a Person with healthprofile it will create a person without a created date, it should
        //be done by System current time. Otherwise it is a Post in Person's measureType and the value will be given
        //by the user in XML or JSON format
        if(systemTime)
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        else
            statement.setDate(4, new java.sql.Date(healthMeasureHistory.getCreated().getTime()));
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        healthMeasureHistory.setMid(rs.getLong(statement.RETURN_GENERATED_KEYS));

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " +getClass().getSimpleName() + " insert");

        return healthMeasureHistory;
    }

    public HealthMeasureHistory update(HealthMeasureHistory healthMeasureHistory) throws Exception
    {
        statement = connection.prepareStatement("UPDATE HealthMeasureHistory SET value=?, created=? WHERE idPerson = ? AND idMeasureHistory=?");
        statement.setString(1, healthMeasureHistory.getValue());
        statement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
        statement.setLong(3, healthMeasureHistory.getPerson().getIdPerson());
        statement.setLong(4, healthMeasureHistory.getMid());
        statement.executeUpdate();

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " + getClass().getSimpleName() + " update");
        return healthMeasureHistory;
    }

    public void deleteByPerson(Person person) throws Exception
    {
        statement = connection.prepareStatement("DELETE FROM HealthMeasureHistory WHERE idPerson = ?");
        statement.setLong(1, person.getIdPerson());
        statement.executeUpdate();

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " deleteByPerson");
    }

}
