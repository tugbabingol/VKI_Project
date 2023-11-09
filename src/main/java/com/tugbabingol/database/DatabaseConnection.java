package com.tugbabingol.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection extends DatabaseInformation {

    // Variable
    private String url = super.url;
    private String user = super.user;
    private String password = super.password;
    private String forNameData = super.forNameData;

    // JDBC Connection
    private Connection connection;  //java.sql.Connection

    // Singleton Design Pattern (Variable)
    private static DatabaseConnection instance;

    // Singleton Design Pattern (Constructor)
    private DatabaseConnection() {
        try {
            Class.forName(this.forNameData);
            //System.out.println("Driver başarıyla Yüklendi");
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Database bağlantısı başarılı"); //Başarılı çıktısı alındı.
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    } //end constructor

    // Singleton Design Pattern (Method)
    public static DatabaseConnection getInstance() {
        try {
            // Eğer connection kapalı veya null ise oluştur
            // Eğer bağlantı varsa o bağlantıyı kullan.
            if (instance == null || instance.connection.isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return instance;
    } //end instance

    // GETTER AND SETTER
    public Connection getConnection () {
        return connection;
    }

    public void setConnection (Connection connection){
        this.connection = connection;
    }

    public static void main(String[] args) {
         DatabaseConnection databaseConnection=new DatabaseConnection();
    }


}
