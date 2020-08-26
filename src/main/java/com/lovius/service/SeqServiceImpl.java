package com.lovius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovius.repository.SeqRepository;
import com.lovius.service.interfaces.SeqService;

@Service
public class SeqServiceImpl implements SeqService {

	@Autowired
	private SeqRepository seqRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Long getLogSeq() {
		return seqRepository.getLogSeq();
	}

}
