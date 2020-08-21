package com.lovius.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovius.common.exception.DeleteRollBackException;
import com.lovius.common.exception.InsertRollBackException;
import com.lovius.common.exception.UpdateRollBackException;
import com.lovius.model.MemberProfile;
import com.lovius.repository.MemberProfileRepository;
import com.lovius.service.interfaces.MemberProfileService;
import com.lovius.utils.ClazzToMap;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberProfileServiceImpl implements MemberProfileService {

	@Autowired
	private MemberProfileRepository memberProfileRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<MemberProfile> findAllOrderById() throws Exception {
		return memberProfileRepository.findAllOrderById();
	}

	@Transactional(rollbackFor = UpdateRollBackException.class)
	@Override
	public void update(MemberProfile memberProfile) throws Exception {
		Map<String, Object> param = ClazzToMap.handle(memberProfile);
		memberProfileRepository.update(param);
	}

	@Transactional(rollbackFor = UpdateRollBackException.class)
	@Override
	public void updateDynamic(MemberProfile memberProfile) throws Exception {
		Map<String, Object> param = ClazzToMap.handle(memberProfile);
		memberProfileRepository.updateDynamic(param);
	}

	@Transactional(readOnly = true)
	@Override
	public MemberProfile findById(String id) throws Exception {
		return memberProfileRepository.findById(id,null);
	}

	@Transactional(readOnly = true)
	@Override
	public MemberProfile findByIdDymanicIn() throws Exception {
		List<String> ids=List.of("lovius", "1", "2", "3");
		Map<String, Object> param = new HashMap<>();
		param.put("id", ids);
		return memberProfileRepository.findByIdDymaicIn(param);
	}

	@Transactional(rollbackFor = InsertRollBackException.class)
	@Override
	public void insert(MemberProfile memberProfile)  throws Exception {
		Map<String, Object> param = ClazzToMap.handle(memberProfile);
		memberProfileRepository.insert(param);
	}

	@Transactional(rollbackFor = DeleteRollBackException.class)
	@Override
	public void delete(String id) throws Exception {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("ID", id);
		memberProfileRepository.delete(param);
	}

	
	
}
