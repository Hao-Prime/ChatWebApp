package com.se.thymeleafdemo.service;

import java.util.List;

import com.se.thymeleafdemo.entity.TinNhan;

public interface TinNhanService {
    public List<TinNhan> findAll();
    public List<TinNhan> findFirt();
    public List<TinNhan> findNew();
    public List<TinNhan> findOld();
    public TinNhan findById(int theId);
    public void save(TinNhan theEmployee);
    public void deleteById(int theId); }
