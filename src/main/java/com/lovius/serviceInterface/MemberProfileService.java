package com.lovius.serviceInterface;

import java.util.List;

import com.lovius.model.MemberProfile;

public interface MemberProfileService {
	
	public List<MemberProfile> findAllOrderById();
	
	public MemberProfile findById(String id);
	
	public void update(MemberProfile memberProfile);
	
	public void updateDynamic(MemberProfile memberProfile);
	
}
