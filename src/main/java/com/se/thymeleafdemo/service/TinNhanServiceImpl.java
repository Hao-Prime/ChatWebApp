package com.se.thymeleafdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.thymeleafdemo.dao.TinNhanRepository;
import com.se.thymeleafdemo.entity.TinNhan;

@Service
public class TinNhanServiceImpl implements TinNhanService {
    
	private TinNhanRepository TinNhanRepository;
    @Autowired
    public TinNhanServiceImpl(TinNhanRepository theTinNhanRepository) {
    	TinNhanRepository = theTinNhanRepository;    }
    @Override
    public List<TinNhan> findAll() {
        return TinNhanRepository.findAll();    }
    @Override
    public TinNhan findById(int theId) {
        Optional<TinNhan> result = TinNhanRepository.findById(theId);
        TinNhan theTinNhan = null;
        if (result.isPresent()) {
           theTinNhan = result.get(); }
        else {
           throw new RuntimeException("Did not find TinNhan id - " + theId);
        }
        return theTinNhan; }
    @Override
    public void save(TinNhan theTinNhan) {
            TinNhanRepository.save(theTinNhan);
    }
    @Override
    public void deleteById(int theId) {
            TinNhanRepository.deleteById(theId);
    }
	@Override
	public List<TinNhan> findFirt() {
		return TinNhanRepository.findFirt();
	}
	@Override
	public List<TinNhan> findNew() {
		
		return TinNhanRepository.findNew();
	}
}






