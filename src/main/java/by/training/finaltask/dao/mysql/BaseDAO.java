package by.training.finaltask.dao.mysql;

import java.sql.Connection;
import java.util.ResourceBundle;

abstract public class BaseDAO {

    protected Connection connection;
    protected ResourceBundle resourceBundle;
    protected final String PROPERTY_PATH = "daomysqlqueries";

    protected BaseDAO(Connection connection)
    {
        this.connection = connection;
    }
    protected Connection getConnection() {
        return this.connection;
    }
    protected void setConnection(Connection connection)
    {
        this.connection = connection;
    }

}
