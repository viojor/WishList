package MySQL;

import Model.Game;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private static final String userDB = "root";
    private static final String passwordDB = "c0ntr4s3ñ4_p4r4_MySQL";
    private static final String urlDB = "jdbc:mysql://localhost/games_db";

    private Connection connection;

    public DBConnector() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            System.out.println("Error al registrar el driver de MySQL: " + e);
        }

        try {

            connection = DriverManager.getConnection(urlDB, userDB, passwordDB);
            if (!existTable("GAMES")) {

                createGamesTable();
            }
        } catch (SQLException e) {

            System.out.println("Error al realizar la conexion con la BD: " + e);
        }
    }

    private boolean existTable(String tableName) {

        boolean isTableOnSchema = false;
        try {

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

            isTableOnSchema = resultSet.next();
        } catch (SQLException e) {

            System.out.println("Error al comprobar si existe la tabla en la BD: " + e);
        }
        return isTableOnSchema;
    }

    private void createGamesTable() {

        try {

            Statement st = connection.createStatement();
            st.executeUpdate("CREATE TABLE GAMES (id INT PRIMARY KEY auto_increment,  caratula VARCHAR(255)," +
                    " nombre VARCHAR(255), precio VARCHAR(255), genero VARCHAR(255), fecha_salida VARCHAR(255)," +
                    " horas_juego VARCHAR(255), horas_completo VARCHAR(255), estado VARCHAR(255))");
        } catch (SQLException e) {

            System.out.println("Error al crear la tabla: " + e);
        }
    }

    public void insertGame(Game elementToInsert) {

        try {

            String cover = elementToInsert.get_cover().toString();
            String name = elementToInsert.get_name();
            String price = elementToInsert.get_price();
            String gender = elementToInsert.get_gender();
            String releaseDate = elementToInsert.get_releaseDate();
            String estimatedHours = elementToInsert.get_estimatedHours();
            String totalHours = elementToInsert.get_totalsHours();
            String state = elementToInsert.get_state().name();

            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO GAMES (caratula, nombre, precio, genero, fecha_salida, horas_juego, " +
                    "horas_completo, estado) " +
                    "VALUES ('" + cover + "', '" + name + "', '" + price + "', '" + gender + "', '" + releaseDate +
                    "', '" + estimatedHours + "', '" + totalHours + "', '" + state + "')");
        } catch (SQLException e) {

            System.out.println("Error al insertar el elemento: " + e);
        }
    }

    public void updateGame(Game upgradedElement) {

        try {

            int id = upgradedElement.get_id();
            String cover = upgradedElement.get_cover().toString();
            String name = upgradedElement.get_name();
            String price = upgradedElement.get_price();
            String gender = upgradedElement.get_gender();
            String releaseDate = upgradedElement.get_releaseDate();
            String estimatedHours = upgradedElement.get_estimatedHours();
            String totalHours = upgradedElement.get_totalsHours();
            String state = upgradedElement.get_state().name();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE GAMES " +
                    "SET caratula = '" + cover + "', nombre = '" + name + "', precio = '" + price +
                    "', genero = '" + gender + "', fecha_salida = '" + releaseDate + "', horas_juego = '" +
                    estimatedHours + "', horas_completo = '" + totalHours + "', estado = '" + state + "'" +
                    " WHERE id = " + id);
        } catch (SQLException e) {

            System.out.println("Error al actualizar el elemento: " + e);
        }
    }

    public void disconnectDB() {

        try {
            if (!connection.isClosed()) {

                connection.close();
            }
        } catch (SQLException e) {

            System.out.println("Error al cerrar la conexion con la BD: " + e);
        }
    }
}
