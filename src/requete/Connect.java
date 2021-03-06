package requete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.*;

import clavardage.AlerteMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Connect {
	private static int idCourant=1;
	
	//get id
	public static int getId() {return idCourant; }
	//set id
	public static void setId(int id2) {idCourant=id2; }
	//update id
	public static void updateId() {
		int id2 = queryMaxId("database.db");
		idCourant=id2+1; 
	}
	
	//connect to the database, and create it if it doesn't exist
    public static void createNewDatabase(String fileName) {
    	 
        String url = "jdbc:sqlite:./database/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }
    
    
    /* ------------------------------------------CREATION DES TABLES--------------------------------------*/
    //creation de la table User
    public static void createNewTableUser(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS User(\n"
                + "    pseudo text,\n"
                + "    password text,\n"
                + "    id\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //System.out.println("[CONNECT] A new table User has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }
    
    //creation de la table ListUserConnected : id et adresse ip
    public static void createNewTableLUC(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS ListUserConnected(\n"
                + "    id INTEGER,\n"
                + "    pseudo text PRIMARY KEY NOT NULL,\n"
                + "    ip text,\n" // PRIMARY KEY NOT NULL
                + "    port INTEGER\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //System.out.println("A new table LUC has been created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }

    //creation de la table Conversation
    public static void createNewTableConv(String filename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Conversation(\n"
                + "    idUser1 INTEGER,\n"
                + "    idUser2 INTEGER,\n"
                + "    content text,\n"
                + "    date text PRIMARY KEY NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }
    
    /* ------------------------------------------SUPPRESSION DES TABLES--------------------------------------*/
  //creation de la table Conversation
    public static void deleteTable(String filename, String tablename) {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/"+filename;
        String sql = "DROP TABLE " + tablename + ";";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // delete the table
            stmt.execute(sql);
            //System.out.println("A table has been deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
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
            idCourant++;
            //System.out.println("[CONNECT] Insertion dans User de : " + pseudo + " de id : " + id);
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT] User " + e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }
    
  //Insertion d'un utilisateur dans la liste des utilisateurs connectes
    public static void insertUserLUC(String filename, int id, String ip) {
    	String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO ListUserConnected (id, ip) VALUES (?, ?);";
        
        //System.out.println("tentative de requete sql : " + sql);
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
                    pstmt.setString(2, ip);
            pstmt.executeUpdate();
            //System.out.println("A User has been created in UserLUC");
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT] userLUC " + e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }
    
    //Insertion d'un utilisateur dans la liste des utilisateurs connectes
    public static void insertUserLUCbyPseudo(String filename, String pseudo, String ip) {
    	String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO ListUserConnected (pseudo, ip) VALUES (?, ?);";

        //System.out.println("tentative de requete sql : " + sql);
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, pseudo);
                    pstmt.setString(2, ip);
            pstmt.executeUpdate();
            //System.out.println("A User has been created in UserLUC");
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT] userLUC " + e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }
    
  //Insertion d'un utilisateur dans la liste des utilisateurs connectes
    public static void insertUserLUCbyAll(String filename, String pseudo, String ip, int id) {
    	String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO ListUserConnected (pseudo, ip, id) VALUES (?, ?, ?);";

        //System.out.println("tentative de requete sql : " + sql);
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, pseudo);
                    pstmt.setString(2, ip);
                    pstmt.setInt(3, id);
            pstmt.executeUpdate();
            //System.out.println("A User has been created in UserLUC");
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT] userLUC " + e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        } 
    }
    
    //Insertion d'un utilisateur dans la liste des utilisateurs connectes
    public static void insertUserLUCbyAllPort(String filename, String pseudo, String ip, int id, int port) {
    	String url = "jdbc:sqlite:./database/"+filename;
        String sql = "INSERT INTO ListUserConnected (pseudo, ip, id, port) VALUES (?, ?, ?, ?);";

        //System.out.println("Tentative de requete sql : " + sql  + " pseudo : " + pseudo + "\n");
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, pseudo);
                    pstmt.setString(2, ip);
                    pstmt.setInt(3, id);
                    pstmt.setInt(4, port);
            pstmt.executeUpdate();
            //System.out.println("A User has been created in UserLUC");
        } catch (SQLException e) {
            System.out.println("[ERROR INSERT] userLUC " + e.getMessage());
			AlerteMessage error = new AlerteMessage("null", "null", 3);
        }
    }

    //Insertion d'un nouveau message dans la table Conversation
      public static void insertConversation(String filename, int id1, int id2, String content, String date) {
    	  String url = "jdbc:sqlite:./database/"+filename;
          String sql = "INSERT INTO Conversation (idUser1, idUser2, content, date) VALUES (?, ?, ?, ?);";
          
          //System.out.println("Tentative de requete sql : " + sql);
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id1);
                      pstmt.setInt(2, id2);
                      pstmt.setString(3, content);
                      pstmt.setString(4, date);
              pstmt.executeUpdate();
              //System.out.println("[CONNECT] On a add le message : " + content + " de date : " + date + " avec id1 = " + id1 + " et id2 = " + id2);
          } catch (SQLException e) {
              System.out.println("[ERROR INSERT] Conv " + e.getMessage());
				AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }

      /* ------------------------------------------SUPPRESSION--------------------------------------*/

      // Suppression d'un utilisateur dans la table User
      public static void deleteUser(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM User WHERE id = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
				AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
      
      // Suppression d'un utilisateur dans la table User
      public static void deleteUserbyPseudo(String filename, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM User WHERE pseudo = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setString(1, pseudo);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
				AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }

      // Suppression d'un utilisateur dans la table ListUserConnected
      public static void deleteUserLUC(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM ListUserConnected WHERE id = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
      
      // Suppression d'un utilisateur dans la table ListUserConnected
      public static void deleteUserLUC(String filename, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM ListUserConnected WHERE pseudo = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setString(1, pseudo);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
      
	   // Suppression d'un utilisateur dans la table ListUserConnected
	      public static void deleteAllUserLUC(String filename) {
	          String url = "jdbc:sqlite:./database/"+filename;
	          String sql = "DELETE FROM ListUserConnected;";
	          
	          try (Connection conn = DriverManager.getConnection(url);
	                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
	              pstmt.executeUpdate();
	          } catch (SQLException e) {
	              System.out.println(e.getMessage());
	              AlerteMessage error = new AlerteMessage("null", "null", 3);
	          }
	      }

      // Suppression d'un message dans la table Conversation
      public static void deleteConversation(String filename, int id1, int id2, String content, String date) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "DELETE FROM Conversation WHERE idUser1 = ?, idUser2 = ?, content = ?, date = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, id1);
                      pstmt.setInt(2, id2);
                      pstmt.setString(3, content);
                      pstmt.setString(4, date);
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
      /* ------------------------------------------UPDATE--------------------------------------*/
      
   // Update un utilisateur dans la table User
      public static void updateUser(String filename, String pseudo, String pass, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "UPDATE User SET pseudo = ?, password = ? WHERE id = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setString(1, pseudo);
                      pstmt.setString(2, pass);
                      pstmt.setInt(3, id);
              // update 
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
   // update un pseudo dans LUC
      public static void updatePseudoLUC(String filename, String pseudo, String ip) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "UPDATE ListUserConnected SET pseudo = ? WHERE ip = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setString(1, pseudo);
                      pstmt.setString(2, ip);
              // update
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
      
      // update une ip dans LUC
      public static void updateIpLUC(String filename, int id, String ip) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "UPDATE ListUserConnected SET ip = ? WHERE id = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setString(1, ip);
                      pstmt.setInt(2, id);
              // update 
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
      
   // update un port dans LUC
      public static void updatePortLUC(String filename, int id,int port) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "UPDATE ListUserConnected SET port = ? WHERE id = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, port);
                      pstmt.setInt(2, id);
              // update 
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }
   // update un port dans LUC
      public static void updatePortLUC(String filename, String pseudo, int port) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "UPDATE ListUserConnected SET port = ? WHERE ipseudo = ?;";
          
          try (Connection conn = DriverManager.getConnection(url);
                  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                      pstmt.setInt(1, port);
                      pstmt.setString(2, pseudo);
              // update 
              pstmt.executeUpdate();
          } catch (SQLException e) {
              System.out.println(e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
      }

      
      /* ------------------------------------------QUERY--------------------------------------*/
      
      // Récupération de l'id d'un utilisateur
      public static int queryUser(String filename, String pseudo, String pass) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT id FROM User WHERE pseudo = '" + pseudo + "' AND password = '" + pass + "';";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter ;

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = Integer.valueOf(rs.getInt(("id")));
                  resultat.add(resInter);
              }
              resultat.add(-1);
              return resultat.get(0).intValue();
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] user " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0).intValue();
      }
      
   // Recuperation d'un pseudo
      public static String queryUserPseudo(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT pseudo FROM User WHERE id = " + id + ";";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter ;

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getString(("pseudo"));
                  resultat.add(resInter);
              }
              resultat.add("end");
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] user " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0);
      }
   // Recuperation d'un pseudo si different de notre pseudo
      public static String queryUserPseudo(String filename, int id, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT pseudo FROM User WHERE id = " + id + ";";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter ;
          String res="";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getString(("pseudo"));
                  //System.out.println("[CONNECT] end queryUser");
                  resultat.add(resInter);
              }
              resultat.add("end");
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] user " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          
          //System.out.println("[CONNECT] end queryUser");
          for (String courant : resultat) {
        	  if (!courant.equals(pseudo) && !courant.equals("end")) {
        		  res= courant;
        	  }
          }
          if (res.equals("")) {
        	  res="end";
          }
          return res;
      }
      
   // Recuperation d'un mot de passe
      public static String queryUserPass(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT password FROM User WHERE id = " + id + ";";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter ;

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getString(("password"));
                  resultat.add(resInter);
              }
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] user " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          resultat.add("end");
          return resultat.get(0);
      }
      
      //verification pseudo
      public static ArrayList<String> queryUserPseudo(String filename, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT id FROM User WHERE pseudo = '" + pseudo + "';";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += Integer.valueOf(rs.getInt(("id")));
                  resultat.add(resInter);
              }
              resultat.add("end");
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] verif pseudo " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat;
      }
      
      
      
      //verification password
      public static ArrayList<String> queryUserPassword(String filename, String password) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT id FROM User WHERE password = '" + password + "';";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += Integer.valueOf(rs.getInt(("id")));
                  resultat.add(resInter);
              }
              resultat.add("end");
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] verif password " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat;
      }

      // Récupération de l'id d'un utilisateur
      public static int queryMaxId(String filename) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT id FROM User ORDER BY id DESC;";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter ;
          	
          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              // loop through the result set
              while (rs.next()) {
                  resInter = Integer.valueOf(rs.getInt(("id")));
                  resultat.add(resInter);
              }
              //System.out.println("add du 0" );
              resultat.add(0);
              return resultat.get(0).intValue();
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] Max Id " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0).intValue();
      }
      
      // Récupération d'une IP dans la LUC en ayant son id
      public static String queryUserLUC(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT ip FROM ListUserConnected WHERE id = " + id + ";";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getString(("ip"));
                  resultat.add(resInter);
              }
              resultat.add("end");
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] user LUC " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0);
      }
      
   // Récupération d'une IP dans la LUC en ayant son pseudo
      public static String queryUserLUCbyPseudo(String filename, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT ip FROM ListUserConnected WHERE pseudo = '" + pseudo + "';";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getString(("ip"));
                  resultat.add(resInter);
              }
              resultat.add("end");
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] user LUC " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0);
      }
      
   // Récupération de tous les pseudo correspondant aux ids
      public static ArrayList <String> queryAllUserLUC(String filename) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT user.pseudo FROM User, ListUserConnected WHERE ListUserConnected.pseudo = User.pseudo ;";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getString(("pseudo"));
                  if (!resultat.contains(resInter)) { // pour ne pas avoir de doublon
                	  resultat.add(resInter);
                  }
              }
              resultat.add("end");
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] All User " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat;
      }
      
   // Récupération de tous les pseudo
      public static ArrayList <String> queryAllUserLUCbyPseudo(String filename) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT pseudo FROM ListUserConnected;";
          ArrayList<String> resultat = new ArrayList<String>();
          String resInter = "";

          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getString(("pseudo"));
                  resultat.add(resInter);
              }
              resultat.add("end");
              //return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY] All User " + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          //Enlever les doublons
          Set<String> set = new HashSet<String>() ;
          set.addAll(resultat);
          ArrayList<String> res = new ArrayList<String>(set);
          return res;
      }
      
   // Récupération d'un id si le pseudo est correct
      public static int queryUserIdLUC(String filename, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT user.id FROM User, ListUserConnected WHERE ListUserConnected.id = User.id AND User.pseudo = '" + pseudo + "';";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter;

          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getInt(("id"));
                  resultat.add(resInter);
              }
              resultat.add(-1);
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY]" + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0).intValue();
      }
      
   // Récupération d'un port
      public static int queryPortLUC(String filename, int id) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT ListUserConnected.port FROM ListUserConnected WHERE ListUserConnected.id = " + id + ";";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter;

          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getInt(("port"));
                  resultat.add(resInter);
              }
              resultat.add(-1);
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY PORT]" + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0).intValue();
      }
   // Récupération d'un port A PARTIR D'UN PSEUDO
      public static int queryPortLUC(String filename, String pseudo) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT ListUserConnected.port FROM ListUserConnected WHERE ListUserConnected.pseudo = '" + pseudo + "';";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter;

          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getInt(("port"));
                  resultat.add(resInter);
              }
              resultat.add(-1);
              return resultat.get(0);
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY PORT]" + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat.get(0).intValue();
      }
      
      
      // all ports id
      public static ArrayList <Integer> queryAllPortLUC(String filename) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT id FROM ListUserConnected;";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter;

          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getInt(("id"));
                  resultat.add(resInter);
              }
              resultat.add(-1);
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY PORT]" + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat;
      }
   // all ports num
      public static ArrayList <Integer> queryAllPortLUC2(String filename) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT port FROM ListUserConnected;";
          ArrayList<Integer> resultat = new ArrayList<Integer>();
          Integer resInter;

          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter = rs.getInt(("port"));
                  resultat.add(resInter);
              }
              resultat.add(-1);
              return resultat;
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY PORT]" + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          return resultat;
      }
      

      // Récupération d'une conversation entre id1 et id2 dans la liste Conversation. retourne une liste de String de forme : content||date
      public static ArrayList<String> queryConversation(String filename, int id1, int id2) {
          String url = "jdbc:sqlite:./database/"+filename;
          String sql = "SELECT content, date FROM Conversation WHERE idUser1 = " + Integer.valueOf(id1) + " AND idUser2 = " + Integer.valueOf(id2) 
          + " OR idUser1 = " + Integer.valueOf(id2) + " AND idUser2 = " + Integer.valueOf(id1) + " ORDER BY date ;";
          ArrayList<String> resultat = new ArrayList<String>();
          resultat.add("debut -- end");
          String resInter = "";

          //System.out.println("Tentative de requete sql : " + sql );
          try (Connection conn = DriverManager.getConnection(url);
               Statement stmt  = conn.createStatement();
               ResultSet rs    = stmt.executeQuery(sql)){
              
              // loop through the result set
              while (rs.next()) {
                  resInter="";
                          resInter += rs.getString("date") + " ----- ";
                          resInter += rs.getString("content");
                          resultat.add(resInter);
                  }
          } catch (SQLException e) {
              System.out.println("[ERROR QUERY]" + e.getMessage());
              AlerteMessage error = new AlerteMessage("null", "null", 3);
          }
          resultat.add("!!end");
          return resultat;
      }
      
      //recuperation d'un historique dans le bon ordre
      public static ArrayList <String> queryHistorique(String filename, int id1, int id2) {
    	  ArrayList <String> id1id2 = queryConversation(filename, id1, id2);
    	  Integer iter = 0;
    	  for (String message : id1id2 ) {
    		  id1id2.set(iter, Integer.valueOf(id1) + message);
    	  }
    	  return id1id2;
      }
      
      
      
      /*------------------------------------------VERIFICATION USER EXISTANT ------------------------------------- */
      public static boolean checkIsUser(String filename, String pseudo, String pass) {
          int resultat = queryUser(filename, pseudo, pass);
          return (resultat != -1 );
      }
      
      /*------------------------------------------VERIFICATION VALIDITE PSEUDO ET PASSWORD (new user) ------------------------------------- */
      // test l'unicité du pseudo
      public static boolean checkPseudo(String filename, String pseudo) {
          ArrayList<String> resultat = queryUserPseudo(filename, pseudo);
          return !(resultat.get(0) != "end" );
      }
      // test de la validite du mot de passe (au minimum 10 caractères + ne contient pas le pseudo de l'utilisateur)
      public static boolean checkPassword(String filename, String pseudo, String pass) {
    	  boolean isValid = true;
    	  if (pass.length() <= 10) {
    		  isValid = false;
    	  }
    	  else if (pass.contains(pseudo)) {
    		  isValid = false;
    	  }
          return isValid;
      }
      
      /*------------------------------------------ RAFRAICHISSEMENT DE LA LUC ------------------------------------- */

      // pseudo dansles deux listes
      public static ArrayList <String> queryOldUser(String filename, String[] luc) {
    	  ArrayList <String> res = new ArrayList <String>();
    	  ArrayList <String> newLUC = queryAllUserLUC(filename);
    	  
    	  for (String courant : luc) {
    		  if (newLUC.contains(courant)) {
    			  res.add(courant);
    		  }
    	  }
    	  return res;
      }
      
      //pseudo seulement dans l'ancienne liste
      public static ArrayList <String> queryDisconnectedUser(String filename, String[] luc) {
    	  ArrayList <String> res = new ArrayList <String>();
    	  ArrayList <String> newLUC = queryAllUserLUC(filename);
    	  
    	  for (String courant : luc) {
    		  if (!newLUC.contains(courant)) {
    			  res.add(courant);
    		  }
    	  }
    	  return res;
      }
      
      //pseudo seulement dans a nouvelle liste
      public static ArrayList <String> queryNewUser(String filename, String[] luc) {
    	  ArrayList <String> res = new ArrayList <String>();
    	  ArrayList <String> newLUC = queryAllUserLUC(filename);
    	  
    	  ArrayList <String> oldLUC = new ArrayList<String>(Arrays.asList(luc));
    	  for (String courant : newLUC) {
    		  if (!oldLUC.contains(courant)) {
    			  res.add(courant);
    		  }
    	  }
    	  return res;
      }
      
      /*------------------------------------------METHODES GENERALISTES-------------------------------------
      
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
      */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	createNewDatabase("database.db");
        createNewTableUser("database.db");
        insertUser("database.db", "Toto", "titi123456789", 1);
        /*
        boolean query = checkIsUser("database.db", "Toto", "titi");
        System.out.println("Toto existe ? " + query);
        */
        boolean v1 = checkPseudo("database.db", "Toto");
        boolean v2 = checkPseudo("database.db", "toto");
        boolean v3 = checkPassword("database.db", "toto", "titi");
        boolean v4 = checkPassword("database.db", "toto", "xxtoto2356481545751867");
        boolean v5 = checkPassword("database.db", "toto", "xxtoto2");
        System.out.println("validite du pseudo Toto : " + v1);
        System.out.println("validite du pseudo toto : " + v2);
        System.out.println("validite du password titi : " + v3);
        System.out.println("validite du password xxtoto2356481545751867 : " + v4);
        System.out.println("validite du password xxtoto2 : " + v5);
        
    }
}