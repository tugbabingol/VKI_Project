package com.tugbabingol.dao;


import com.tugbabingol.database.DatabaseConnection;
import com.tugbabingol.dto.RegisterDto;
import java.sql.Connection;
import java.util.ArrayList;

// INTERFACE
public interface IDaoGenerics <T> {

    public String speedData(Long id);
    public String allDelete();

    ////////////////////////////////////////////////////

    // C R U D
    // CREATE
    public T create(T t);

    // FIND BY ID
    public T findById(Long id);
    public T findByEmail(String email);
    public T findByName(String name);

    // LIST
    public ArrayList<T> list();

    // UPDATE
    public T update(Long id, T t);
    public RegisterDto updateRemaing(Long id, T t);

    // DELETE
    public T deleteById(T t);

    ///////////////////////////////////////////////////////
    default Connection getInterfaceConnection(){
        return DatabaseConnection.getInstance().getConnection();
    } //end body interface
} //end interface IDaoGenerics