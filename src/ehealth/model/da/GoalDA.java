package ehealth.model.da;

import ehealth.model.to.Goal;
import ehealth.model.to.MeasureDefinition;
import ehealth.model.to.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Navid on 08/17/2016.
 */
public class GoalDA {
    private Connection connection;
    private PreparedStatement statement;

    public GoalDA()
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

    public ArrayList<Goal> selectAllByPerson_GroupByLatestMeasures(Long idPerson)throws Exception
    {
        statement = connection.prepareStatement("SELECT hmh.idGoal, hmh.idPerson, hmh.idMeasureDefinition, " +
                "hmh.value, hmh.created FROM (SELECT idPerson, idMeasureDefinition, MAX(created) AS MaxDate " +
                "FROM Goal GROUP BY idPerson, idMeasureDefinition) temp " +
                "INNER JOIN Goal hmh ON temp.idPerson = hmh.idPerson " +
                "AND temp.idMeasureDefinition = hmh.idMeasureDefinition AND temp.MaxDate = hmh.created AND " +
                "hmh.idPerson = ?");
        statement.setLong(1, idPerson);
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<Goal> goals = new ArrayList<Goal>();
        while (resultSet.next())
        {
            Goal goal = new Goal();
            goal.setGid(resultSet.getLong("idGoal"));
            goal.setValue(resultSet.getString("value"));
            goal.setCreated(resultSet.getDate("created"));
            goal.setPerson(new PersonDA().selectById(resultSet.getLong("idPerson")));
            goal.setMeasureDefinition(new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition")));

            goals.add(goal);
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " + getClass().getSimpleName() + " selectAllByPerson_GroupByLatestMeasures");

        return goals;
    }

    public ArrayList<Goal> selectAllByPerson(Long idPerson)throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM Goal WHERE idPerson = ?");
        statement.setLong(1, idPerson);
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<Goal> goals = new ArrayList<Goal>();
        while (resultSet.next())
        {
            Goal goal = new Goal();
            goal.setGid(resultSet.getLong("idGoal"));
            goal.setValue(resultSet.getString("value"));
            goal.setCreated(resultSet.getDate("created"));
            goal.setPerson(new PersonDA().selectById(resultSet.getLong("idPerson")));
            goal.setMeasureDefinition(new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition")));

            goals.add(goal);
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " + getClass().getSimpleName() + " selectAllByPerson");

        return goals;
    }

    //I do not need this
    public Goal select(Long idGoal) throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM Goal WHERE idGoal = ?");
        statement.setLong(1, idGoal);
        ResultSet resultSet =  statement.executeQuery();
        Goal goal = new Goal();
        while (resultSet.next())
        {
            goal.setGid(resultSet.getLong("idGoal"));
            goal.setValue(resultSet.getString("value"));
            goal.setCreated(resultSet.getDate("created"));
            goal.setPerson(new PersonDA().selectById(resultSet.getLong("idPerson")));
            goal.setMeasureDefinition(new MeasureDefinitionDA().selectById(resultSet.getInt("idMeasureDefinition")));
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " +getClass().getSimpleName() + " select");

        return goal;
    }

    public Goal insert(Goal goal, boolean systemTime) throws Exception
    {
        statement = connection.prepareStatement("INSERT INTO Goal (idPerson,idMeasureDefinition,value,created) VALUES (?,?,?,?)");

        statement.setLong(1, goal.getPerson().getIdPerson());
        MeasureDefinition measureDefinition = new MeasureDefinitionDA().selectByMeasure(goal.getMeasureDefinition().getMeasureType());

        statement.setInt(2, measureDefinition.getIdMeasureDef());
        statement.setString(3, goal.getValue());
        //It should be done by System current time. Otherwise it is a Post in Person's measureType and the value will
        //be given by the user in XML or JSON format
        if(systemTime)
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        else
            statement.setDate(4, new java.sql.Date(goal.getCreated().getTime()));
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        goal.setGid(rs.getLong(statement.RETURN_GENERATED_KEYS));

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " +getClass().getSimpleName() + " insert");

        return goal;
    }


    public Goal update(Goal goal) throws Exception
    {
        statement = connection.prepareStatement("UPDATE Goal SET value=?, created=? WHERE idGoal = ?");
        statement.setString(1, goal.getValue());
        statement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
        statement.setLong(3, goal.getGid());
        statement.executeUpdate();

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " + getClass().getSimpleName() + " update");
        return goal;
    }

    public void deleteByGoalId(Goal goal) throws Exception
    {
        statement = connection.prepareStatement("DELETE FROM Goal WHERE idGoal = ?");
        statement.setLong(1, goal.getGid());
        statement.executeUpdate();

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " deleteByGoalId");
    }

    public void deleteByPerson(Person person) throws Exception
    {
        statement = connection.prepareStatement("DELETE FROM Goal WHERE idPerson = ?");
        statement.setLong(1, person.getIdPerson());
        statement.executeUpdate();

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " deleteByPerson");
    }
}
