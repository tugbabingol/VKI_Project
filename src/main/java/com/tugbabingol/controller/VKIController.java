package com.tugbabingol.controller;

import com.tugbabingol.dao.IDaoGenerics;
import com.tugbabingol.dao.VKIDao;
import com.tugbabingol.dto.RegisterDto;
import com.tugbabingol.dto.VKIDto;

import java.util.ArrayList;

public class VKIController implements IDaoGenerics<VKIDto> {
    //Injection
    private VKIDao vkiDao= new VKIDao();

    @Override
    public String speedData(Long id) {
        return null;
    }

    @Override
    public String allDelete() {
        return null;
    }

    @Override
    public VKIDto create(VKIDto vkiDto) {
        return null;
    }

    @Override
    public VKIDto findById(Long id) {
        return null;
    }

    @Override
    public VKIDto findByEmail(String email) {
        return null;
    }

    @Override
    public VKIDto findByName(String name) {
        return null;
    }

    @Override
    public ArrayList<VKIDto> list() {
        return null;
    }

    @Override
    public VKIDto update(Long id, VKIDto vkiDto) {
        return null;
    }

    @Override
    public RegisterDto updateRemaing(Long id, VKIDto vkiDto) {
        return null;
    }

    @Override
    public VKIDto deleteById(VKIDto vkiDto) {
        return null;
    }
}
