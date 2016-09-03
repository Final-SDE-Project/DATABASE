package ehealth.model.da;

import ehealth.model.to.MeasureDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Navid on 11/17/2015.
 */
public class MeasureDefinitionDA {
    private Connection connection;
    private PreparedStatement statement;

    public MeasureDefinitionDA()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:ehealth.sqlite");
            //THIS HAS CHANGED TO TRUE BECAUSE I GOT RID OF CLOSE()
            connection.setAutoCommit(true);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<String> selectAll() throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM MeasureDefinition");
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<String> measureNames = new ArrayList<String>();
        while (resultSet.next())
        {
            measureNames.add(resultSet.getString("measureName"));
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectAll");

        return measureNames;
    }

    public MeasureDefinition selectById (int idMeasureDef) throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM MeasureDefinition WHERE idMeasureDef = ?");
        statement.setInt(1, idMeasureDef);
        ResultSet resultSet =  statement.executeQuery();
        MeasureDefinition measureDefinition = new MeasureDefinition();
        while (resultSet.next())
        {
            measureDefinition.setIdMeasureDef(resultSet.getInt("idMeasureDef"));
            measureDefinition.setMeasureType(resultSet.getString("measureName"));
            measureDefinition.setMeasureType_Type(resultSet.getString("measureType"));
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectById");

        return measureDefinition;
    }

    public MeasureDefinition selectByMeasure(String measureName) throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM MeasureDefinition WHERE measureName = ?");
        statement.setString(1, measureName);
        ResultSet resultSet =  statement.executeQuery();
        MeasureDefinition measureDefinition = new MeasureDefinition();
        while (resultSet.next())
        {
            measureDefinition.setIdMeasureDef(resultSet.getInt("idMeasureDef"));
            measureDefinition.setMeasureType(resultSet.getString("measureName"));
            measureDefinition.setMeasureType_Type(resultSet.getString("measureType"));
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB in "+getClass().getSimpleName() + " selectByMeasure");

        return measureDefinition;
    }

}
