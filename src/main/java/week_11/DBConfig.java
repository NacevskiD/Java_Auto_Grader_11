package week_11;


import java.sql.*;
import java.util.ArrayList;

public class DBConfig {
    
    static String db_url = "jdbc:sqlite:rubik.db";
    private static final String ID_COLUMN = "id";
    private static final String SOLVER_NAME = "name";
    private static final String SOLVE_TIME = "time";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS cube_records  (id integer PRIMARY KEY, solver_name text, time_seconds number)";
    private static final String ADD_DATA = "INSERT INTO cube_records (solver_name, time_seconds) VALUES (?,?)";
    private static final String DROP = "DROP TABLE cube_records";

    DBConfig(){
        createTable();
    }

    private void createTable(){

        try(Connection connection = DriverManager.getConnection(db_url)){
            Statement statement = connection.createStatement();

            statement.executeUpdate(CREATE_TABLE);

        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
        }

    }

    void addData(String name, Double time){
        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(ADD_DATA);

            ps.setString(1,name);
            ps.setDouble(2,time);
            ps.execute();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
        }
    }

    ArrayList<RubrikObject> getAll(){
        final String getAllSQL = "SELECT * FROM cube_records ORDER BY time_seconds";
        ArrayList<RubrikObject> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(getAllSQL);

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()){
                System.out.println("Empty");
                return null;
            }else {
                while (rs.next()){
                    RubrikObject rubrikObject = new RubrikObject(rs.getInt("id"),rs.getString("solver_name"),rs.getDouble("time_seconds"));
                    list.add(rubrikObject);
                }

                return list;
            }



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
            return null;
        }
    }
    void updateData(String name, Double time){
        String updateTime = "UPDATE cube_records SET time_seconds = ? WHERE solver_name = ?";

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(updateTime);

            ps.setDouble(1,time);
            ps.setString(2,name);

            ps.executeUpdate();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");

        }

    }
    void deleteItem(String name){
        String updateTime = "DELETE FROM cube_records WHERE solver_name = ?";

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(updateTime);


            ps.setString(1,name);

            ps.executeUpdate();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");

        }
    }

    
}


