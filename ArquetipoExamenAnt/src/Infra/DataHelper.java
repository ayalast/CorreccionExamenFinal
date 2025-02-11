package Infra;

import java.sql.*;

public class DataHelper {
    private static final String DATABASE_URL = "jdbc:sqlite:ArquetipoExamenAnt/database/Hormiguero.sqlite";
    private static final String DRIVER = "org.sqlite.JDBC";

    public DataHelper() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        connection.setAutoCommit(false);
        return connection;
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
} 