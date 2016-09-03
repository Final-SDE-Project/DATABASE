package ehealth.model.da;

import ehealth.model.to.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Navid on 11/17/2015.
 */
public class PersonDA {
    private Connection connection;
    private PreparedStatement statement;

    public PersonDA()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:ehealth.sqlite");
            //THIS HAS CHANGED TO TRUE BECAUSE I GOT RID OF CLOSE()
            connection.setAutoCommit(true);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Person> selectAll()throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM Person");
        ResultSet resultSet =  statement.executeQuery();
        ArrayList<Person> persons = new ArrayList<Person>();
        while (resultSet.next())
        {
            Person person = new Person();
            person.setIdPerson(resultSet.getLong("idPerson"));
            person.setFirstname(resultSet.getString("firstname"));
            person.setLastname(resultSet.getString("lastname"));
            person.setBirthdate(resultSet.getDate("birthdate"));
            person.setMeasure(new HealthProfileDA().selectByPersonId(person.getIdPerson()));
            persons.add(person);
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB " + getClass().getSimpleName() + " selectAll");

        return persons;
    }

    public Person selectById (Long idPerson) throws Exception
    {
        statement = connection.prepareStatement("SELECT * FROM Person WHERE idPerson = ?");
        statement.setLong(1, idPerson);
        ResultSet resultSet =  statement.executeQuery();
        Person person = new Person();
        while (resultSet.next())
        {
            person.setIdPerson(resultSet.getLong("idPerson"));
            person.setFirstname(resultSet.getString("firstname"));
            person.setLastname(resultSet.getString("lastname"));
            person.setBirthdate(resultSet.getDate("birthdate"));
            person.setMeasure(new HealthProfileDA().selectByPersonId(person.getIdPerson()));
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectById");

        return new Person(person);
    }

    public Person insert(Person person) throws Exception
    {
        statement = connection.prepareStatement("INSERT INTO Person (firstname,lastname,birthdate) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, person.getFirstname());
        statement.setString(2, person.getLastname());
        statement.setDate(3, new java.sql.Date(person.getBirthdate().getTime()));

        statement.executeUpdate();
        //This part is working well for getting ID of inserted row
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        person.setIdPerson(rs.getLong(statement.RETURN_GENERATED_KEYS));

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " insert");

        return person;

    }

    public void updateById(Person person) throws Exception
    {
        statement = connection.prepareStatement("UPDATE Person SET firstname= ?,lastname = ?,birthdate = ? WHERE idPerson = ?");
        statement.setString(1, person.getFirstname());
        statement.setString(2, person.getLastname());
        statement.setDate(3, new java.sql.Date(person.getBirthdate().getTime()));
        statement.setLong(4, person.getIdPerson());
        statement.executeUpdate();
        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " updateById");
    }

    public void deleteById(Person person) throws Exception
    {
        //person.print();
        statement = connection.prepareStatement("DELETE FROM Person WHERE idPerson = ?");
        statement.setLong(1, person.getIdPerson());
        statement.executeUpdate();

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " deleteById");
    }

    public long selectCount() throws Exception
    {
        statement = connection.prepareStatement("SELECT COUNT(*) FROM Person");
        ResultSet resultSet = statement.executeQuery();
        long count = 0;
        while(resultSet.next())
        {
            count = resultSet.getLong("COUNT(*)");
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectCount");

        return  count;
    }

    public Long selectFirstPerson() throws Exception
    {
        statement = connection.prepareStatement("SELECT idPerson FROM Person ORDER BY idPerson ASC LIMIT 1");
        ResultSet resultSet = statement.executeQuery();
        Long person_id = Long.valueOf(0);
        while(resultSet.next())
        {
            person_id = resultSet.getLong("idPerson");
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectFirstPerson");

        return  person_id;
    }

    public Long selectLastPerson() throws Exception
    {
        statement = connection.prepareStatement("SELECT idPerson FROM Person ORDER BY idPerson DESC LIMIT 1");
        ResultSet resultSet = statement.executeQuery();
        Long person_id = Long.valueOf(0);
        while(resultSet.next())
        {
            person_id = resultSet.getLong("idPerson");
        }

        //THIS MAKES ATOMICITY NOT WORKING
        this.statement.close();
        this.connection.close();
        System.out.println("CLOSED THE DB "+getClass().getSimpleName() + " selectLastPerson");

        return  person_id;
    }
}
