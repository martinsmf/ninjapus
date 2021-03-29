package libs;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.nio.file.Files.readAllBytes;

public class DataBase {
    private final String url = "jdbc:postgresql://pgdb:5432/ninjaplus";
    private final String user = "postgres";
    private final String pass = "qaninja";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public void restartMovies() {
        String executionPah = System.getProperty("user.dir");
        String os = System.getProperty("os.name");

        String target;
        String moviesSql;

        if (os.contains("Windows")) {
            target = executionPah + "\\src\\main\\resources\\sql\\movies.sql";
        } else {
            target = executionPah + "/src/main/resources/sql/movies.sql";
        }
        try {
             moviesSql = new String(readAllBytes(Paths.get(target)));
             PreparedStatement query = this.connect().prepareStatement(moviesSql);
            query.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteMovie(String title) {
        String SQL = "DELETE FROM PUBLIC.MOVIES WHERE TITLE = ?;";

        try {
            PreparedStatement query = this.connect().prepareStatement(SQL);
            query.setString(1, title);
            query.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        ;

    }
}
