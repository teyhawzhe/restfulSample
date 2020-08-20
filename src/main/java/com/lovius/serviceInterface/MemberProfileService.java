package com.lovius.serviceInterface;

import java.util.List;

import com.lovius.model.MemberProfile;

public interface MemberProfileService {

	public List<MemberProfile> findAllOrderById() throws Exception;

	public MemberProfile findById(String id) throws Exception;
	
	public MemberProfile findByIdDymanicIn() throws Exception;

	public void update(MemberProfile memberProfile) throws Exception;

	public void updateDynamic(MemberProfile memberProfile) throws Exception;

}
