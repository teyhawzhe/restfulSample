package com.lovius.serviceInterface;

import java.util.List;

import com.lovius.model.MemberProfile;

public interface MemberProfileService {
	
	public List<MemberProfile> findAllOrderById();
	
	public void update(MemberProfile memberProfile);
	
}
