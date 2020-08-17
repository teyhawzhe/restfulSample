package com.lovius.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovius.common.exception.UpdateRollBackException;
import com.lovius.model.MemberProfile;
import com.lovius.repository.MemberProfileRepository;
import com.lovius.serviceInterface.MemberProfileService;

@Service
public class MemberProfileServiceImpl implements MemberProfileService {

	@Autowired
	private MemberProfileRepository memberProfileRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<MemberProfile> findAllOrderById() {
		return memberProfileRepository.findAllOrderById();
	}

	@Transactional(rollbackFor = UpdateRollBackException.class)
	@Override
	public void update(MemberProfile memberProfile) {
		memberProfileRepository.update(memberProfile);
	}

	@Transactional(rollbackFor = UpdateRollBackException.class)
	@Override
	public void updateDynamic(MemberProfile memberProfile) {
		memberProfileRepository.updateDynamic(memberProfile);
	}

	
}
