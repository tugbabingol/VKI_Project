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
        return vkiDao.allDelete();
    }

    @Override
    public VKIDto create(VKIDto vkiDto) {
        return vkiDao.create(vkiDto);
    }

    @Override
    public VKIDto findById(Long id) {
        return vkiDao.findById(id);
    }

    @Override
    public VKIDto findByEmail(String email) {
        return null;
    }

    @Override
    public VKIDto findByName(String name) {
        return vkiDao.findByName(name);
    }

    @Override
    public ArrayList<VKIDto> list() {
        return vkiDao.list();
    }

    @Override
    public VKIDto update(Long id, VKIDto vkiDto) {
        return vkiDao.update(id,vkiDto);
    }

    @Override
    public RegisterDto updateRemaing(Long id, VKIDto vkiDto) {
        return null;
    }

    @Override
    public VKIDto deleteById(VKIDto vkiDto) {
        return vkiDao.deleteById(vkiDto);
    }
}
