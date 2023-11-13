package com.tugbabingol.controller;

import com.tugbabingol.dao.RegisterDao;
import com.tugbabingol.dao.IDaoGenerics;
import com.tugbabingol.dto.RegisterDto;
import java.util.ArrayList;

public class RegisterController implements IDaoGenerics<RegisterDto> {

    // Injection
    private RegisterDao registerDao = new RegisterDao();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // SPEED DATA
    @Override
    public String speedData(Long id) {
        return registerDao.speedData(id);
    }

    // ALL DELETE
    @Override
    public String allDelete() {
        return registerDao.allDelete();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // C R U D
    // CREATE
    @Override
    public RegisterDto create(RegisterDto registerDto) {
        return registerDao.create(registerDto);
    }

    // FIND BY ID
    @Override
    public RegisterDto findById(Long id) {
        return registerDao.findById(id);
    }

    // FIND BY EMAIL
    @Override
    public RegisterDto findByEmail(String email) {
        return registerDao.findByEmail(email);
    }

    @Override
    public RegisterDto findByName(String name) {
        return null;
    }

    // LIST
    @Override
    public ArrayList<RegisterDto> list() {
        return registerDao.list();
    }

    // UPDATE Object
    @Override
    public RegisterDto update(Long id, RegisterDto registerDto) {
        return registerDao.update(id, registerDto);
    }

    // UPDATE Remaing
    @Override
    public RegisterDto updateRemaing(Long id, RegisterDto registerDto) {
        return registerDao.updateRemaing(id,registerDto);
    }

    // DELETE BYID
    @Override
    public RegisterDto deleteById(RegisterDto registerDto) {
        return registerDao.deleteById(registerDto);
    }
} //end class RegisterController