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
    }
    
    /* ------------------------------------------CREATION DES TABLES--------------------------------------*/
    //creation de la table User
    public static void createNewTableUser(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS User(\n"
                + "    pseudo text NOT NULL,\n"
                + "    pass text NOT NULL,\n"
                + "    id PRIMARY KEY NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("A new table User has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //creation de la table ListUserConnected
    public static void createNewTableLUC(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS ListUserConnected(\n"
                + "    id PRIMARY KEY NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("A new table LUC has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //creation de la table Conversation
    public static void createNewTableConv(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Conversation (\n"
                + "    idUser1 PRIMARY KEY NOT NULL\n"
                + "    idUser2 NOT NULL,\n"
                + "    content text NOT NULL,\n"
                + "    date text NOT NULL,\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("A new table Conversation has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /* ------------------------------------------INSERTION--------------------------------------*/
    
    //Insertion d'un utilisateur dans la listes des utilisateurs
    public static void insertUser (String filename, String pseudo, String pass, int id) {
    	String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO User (pseudo, password, id) VALUES (?,?,?);";
        
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, pseudo);
                    pstmt.setString(2, pass);
                    pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT]" + e.getMessage());
        }
    }
    
  //Insertion d'un utilisateur dans la liste des utilisateurs connectes
    public static void insertUserLUC(String filename, int id) {
    	String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO ListUserConnected (id) VALUES (?);";
        
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT]" + e.getMessage());
        }
    }

    //Insertion d'un nouveau message dans la table Conversation
      public static void insertUserLUC(String filename, int id1, int id2, String content, String date) {
      	String url = "jdbc:sqlite:./database/"+filename;
          String sql = "INSERT INTO Conversation (idUser1, idUser2, content, date) VALUES (?, ?, ?, ?);";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id1);
                      pstmt.setInt(2, id2);
                      pstmt.setString(3, content);
                      pstmt.setString(4, date);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println("[ERROR INSERT]" + e.getMessage());
          }
      }

      /* ------------------------------------------SUPPRESSION--------------------------------------*/

      // Suppression d'un utilisateur dans la table User
      public static void deleteUser(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM User WHERE id = ? ;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }
      }

      // Suppression d'un utilisateur dans la table ListUserConnected
      public static void deleteUserLUC(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM ListUserConnected WHERE id = ? ;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }
      }

      // Suppression d'un message dans la table Conversation
      public static void deleteUserLUC(String filename, int id1, int id2, String content, String date) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM Conversation WHERE idUser1 = ?, idUser2 = ?, content = ?, date = ? ;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id1);
                      pstmt.setInt(2, id2);
                      pstmt.setString(3, content);
                      pstmt.setString(4, date);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }
      }
      /* ------------------------------------------UPDATE--------------------------------------*/
      
   // Update un utilisateur dans la table User
      public static void update(String filename, String pseudo, String pass, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "UPDATE User SET pseudo = ?, pass = ? WHERE id = ? ;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setString(1, pseudo);
                      pstmt.setString(2, pass);
                      pstmt.setInt(3, id);
              // update 
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }
      }

      
      /* ------------------------------------------QUERY--------------------------------------*/
      
      // Récupération d'un utilisateur, s'il existe [retourne l'id sous forme de string s'il existe, "" sinon]
      public static ArrayList<String> queryUser(String filename, String pseudo, String pass) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT id FROM User WHERE pseudo = " + pseudo + " , pass = " + pass + " ;";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getInt(Integer.valueOf("id"));
                  resultat.add(resInter);
              }
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY]" + e.getMessage());
          }
          return resultat;
      }

      // Récupération d'une conversation entre id1 et id2 dans la liste Conversation. retourne une liste de String de forme : content||date
      public static ArrayList<String> queryConversation(String filename, int id1, int id2) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT content, date FROM Conversation WHERE idUser1 = " + Integer.valueOf(id1) + " , idUser2 = " + Integer.valueOf(id2) + " ;";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getString("content") + "||"; 
                          resInter += rs.getString("date");
                  }
                  resultat.add(resInter);
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY]" + e.getMessage());
          }
          return resultat;
      }

      
      /*------------------------------------------METHODES GENERALISTES-------------------------------------*/

      //INSERTIOn de données dans la bd
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
          String sql = "UPTextDATE " + filename + " SET ";
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
    	createNewDatabase("database.db");
        createNewTableUser("database.db");
        insertUser("database.db", "Toto", "titi", 1);
        ArrayList<String> query = queryUser("database.db", "Toto", "titi");
        System.out.println(query.get(0));
        System.out.println(query.get(1));
    }
}