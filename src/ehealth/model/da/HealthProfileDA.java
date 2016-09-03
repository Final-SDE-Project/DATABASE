package ehealth.model.da;

import ehealth.model.to.HealthProfile;
import ehealth.model.to.MeasureDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Navid on 11/17/2015.
 */
public class HealthProfileDA {
    private Connection connection;
    private PreparedStatement statement;

    public HealthProfileDA()throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:ehealth.sqlite");
        //THIS HAS CHANGED TO TRUE BECAUSE I GOT RID OF CLOSE()
        connection.setAutoCommit(true);
    }

    public ArrayList<HealthProfile> selectByPersonId(Long personId) throws Exception
    {
        statement = connection.prepareStatement("SELECT hmh.idMeasureHistory, hmh.idPerson, hmh.idMeasureDefinition," +
                " hmh.value, hmh.created FROM (SELECT idPerson, idMeasureDefinition, MAX(created) AS MaxDate " +
                "FROM HealthMeasureHistory GROUP BY idPerson, idMeasureDefinition) temp " +
                "INNER JOIN HealthMeasureHistory hmh ON temp.idPerson = hmh.idPerson " +
                "AND temp.idMeasureDefinition = hmh.idMeasureDefinition AND temp.MaxDate = hmh.created AND " +
                "hmh.idPerson = ?");
        statement.setLong(1, personId);
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<HealthProfile> healthProfiles = new ArrayList<HealthProfile>();
        while (resultSet.next())
        {
            HealthProfile healthProfile = new HealthProfile();
            healthProfile.setMid(resultSet.getLong("idMeasureHistory"));
            healthProfile.setValue(resultSet.getString("value"));
            healthProfile.setCreated(resultSet.getDate("created"));

            MeasureDefinition measureDefinition = new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition"));

            healthProfile.setMeasure(measureDefinition.getMeasureType());
            healthProfile.setMeasureValueType(measureDefinition.getMeasureType_Type());

            healthProfiles.add(healthProfile);
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectByPersonId");

        return healthProfiles;
    }


}
