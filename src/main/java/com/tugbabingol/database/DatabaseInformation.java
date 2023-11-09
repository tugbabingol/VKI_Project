package com.tugbabingol.database;

abstract public class DatabaseInformation {
    protected String url;
    protected String user;
    protected String password;
    protected String forNameData;

    // Constructor (Parametresiz)
    public DatabaseInformation() {
        //Default Mysql
        user="root";        // yanlıs verirsem: Caused by: java.sql.SQLException: Access denied for user 'root44'@'localhost' (using password: YES)
        password="root_9654";  // yanlıs verirsem: Caused by: java.sql.SQLException: Access denied for user 'root'@'localhost'   (using password: YES)
        url="jdbc:mysql://localhost:3306/vki";
        forNameData="com.mysql.cj.jdbc.Driver";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForNameData() {
        return forNameData;
    }

    public void setForNameData(String forNameData) {
        this.forNameData = forNameData;
    }
}
