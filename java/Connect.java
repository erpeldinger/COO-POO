import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
	
    //connect to the database, and create it if it doesn't exist
    public static void createNewDatabase(String fileName) {
    	 
        String url = "jdbc:sqlite:./database/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //create a new table in the database
    public static void createNewTable(String filename, String tableName, ArrayList <TableSQL> data) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                + "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    capacity real\n"
                + ");";
        // EXPLOITER ARRAY LIST TABLSQL POUR REMPLIR LA REQUETE SQL
        for (TableSQL courant : data) {
        	sql += courant.getName() + " " + courant.getType() + " " + courant.getOption(); // Y A T IL TJRS OPTION ?
        }
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("A new table has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//INSERTIOn de données dans la bd
    public static void insert(String tableName, String filename, ArrayList<TableSQL> data) {
        String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO " + tableName + " (";
        int iter = 0;
        int iter2 = 1;
        int i;
         // EXPLOITER ARRAY LIST TABLSQL POUR REMPLIR LA REQUETE SQL
        for (Iterator <TableSQL> courant=data.iterator() ; courant.hasNext();) {
            if (courant.hasNext())
        	    sql += courant.next().getName() + ", ";
            else 
                sql += courant.next().getName();
            iter++;
        }
        sql += "); VALUES(";
        for(i=0;i<iter;i++) {
            if (i+1<iter)
                sql+="?, ";
            else
                sql+="?);";
        }
        // exemple : sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (TableSQL courant : data) {
                if (courant.getType()=="string")
                    pstmt.setString(iter2, courant.getValue());
                else //c'est un int
                    pstmt.setInt(iter2, Integer.valueOf(courant.getValue()));
                iter2++;
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT]" + e.getMessage());
        }
    }

    //QUESTIONNER la base de données
    public static ArrayList<String> query(String tableName, String filename, ArrayList<TableSQL> data) {
        String url = "jdbc:sqlite:./database/"+filename;
        String sql = "SELECT ";
        ArrayList<String> resultat = new ArrayList<String>();
        String resInter = "";
        // Exploitation de l'array list pour remplir la variable sql
        for (Iterator <TableSQL> courant=data.iterator() ; courant.hasNext();) {
            if (courant.hasNext())
        	    sql += courant.next().getName() + ", ";
            else 
                sql += courant.next().getName() + " ";
        }
        sql += "FROM " + tableName + ";";
        // exemple : sql = "SELECT id, name, capacity FROM warehouses";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                resInter="";
                for (TableSQL courant : data) {
                    if (courant.getType()=="string")
                        resInter += rs.getString(courant.getValue()) + "\t";
                    else //c'est un int
                        resInter += rs.getInt(Integer.valueOf(courant.getValue())) + "\t";
                }
                resultat.add(resInter);
            }
            return resultat;
        } catch (SQLException e) {
            System.out.println("[ERROR QUERY]" + e.getMessage());
        }
        return resultat;
    }

    // UPDATE data in the database
    public static void update(String tableName, String filename, ArrayList<TableSQL> data1, ArrayList<TableSQL> data2) {
        String url = "jdbc:sqlite:./database/"+filename;
        String sql = "UPDATE " + filename + " SET ";
        int iter = 1;
        // Exploitation de l'array list pour remplir la variable sql
        for (Iterator <TableSQL> courant=data1.iterator() ; courant.hasNext();) {
            if (courant.hasNext())
        	    sql += courant.next().getName() + " = ? , ";
            else 
                sql += courant.next().getName() + " ";
        }
        sql += "WHERE ";
        for (Iterator <TableSQL> courant=data2.iterator() ; courant.hasNext();) {
            if (courant.hasNext())
        	    sql += courant.next().getName() + " = ? , ";
            else 
                sql += courant.next().getName() + " = ? ;";
        }
        // exemple :  sql = "UPDATE warehouses SET name = ? , capacity = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            for (TableSQL courant : data1) {
                if (courant.getType()=="string")
                    pstmt.setString(iter, courant.getValue());
                else //c'est un int
                    pstmt.setInt(iter, Integer.valueOf(courant.getValue()));
                iter++;
            }
            for (TableSQL courant : data2) {
                if (courant.getType()=="string")
                    pstmt.setString(iter, courant.getValue());
                else //c'est un int
                    pstmt.setInt(iter, Integer.valueOf(courant.getValue()));
                iter++;
            }
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //SUPPRIMER des données dans la base de données
    public static void delete(String tableName, String filename, ArrayList<TableSQL> data1, ArrayList<TableSQL> data2) {
        String url = "jdbc:sqlite:./database/"+filename;
        String sql = "DELETE " + filename + " WHERE ";
        int iter = 1;
        // Exploitation de l'array list pour remplir la variable sql
        for (Iterator <TableSQL> courant=data1.iterator() ; courant.hasNext();) {
            if (courant.hasNext())
        	    sql += courant.next().getName() + " = ? , ";
            else 
                sql += courant.next().getName() + " = ? ;";
        }
        // exemple :  sql = "DELETE FROM warehouses WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            for (TableSQL courant : data2) {
                if (courant.getType()=="string")
                    pstmt.setString(iter, courant.getValue());
                else //c'est un int
                    pstmt.setInt(iter, Integer.valueOf(courant.getValue()));
                iter++;
            }
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewTable("database.db", "testTable", new ArrayList <TableSQL>());
    }
}