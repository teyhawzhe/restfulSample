package com.lovius.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovius.common.RMessage;
import com.lovius.common.ResponseMessage;
import com.lovius.model.MemberProfile;
import com.lovius.serviceInterface.MemberProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/member/profile")
@Api(tags = "會員資料" , description = "1.尋找會員資料  2.新增會員資料  3.更新會員資料  4.刪除會員資料")
public class MemberProfileRestController {

	@Autowired
	private MemberProfileService memberProfileService;

	@ApiOperation(value = "尋找所有會員資料", notes = "依據ID排序" , position = 0)
	@GetMapping("/findAllOrderById")
	public RMessage findAllOrderById() {

		List<MemberProfile> memberProfileLists = memberProfileService.findAllOrderById();

		return new RMessage(HttpStatus.OK.value(), ResponseMessage.findSuccess, memberProfileLists);
	}

	@GetMapping("/update")
	public RMessage updateById() {

		MemberProfile memberProfile = new MemberProfile();
		memberProfile.setId("lovius");
		memberProfile.setAge(19);
		
		memberProfileService.update(memberProfile);
		
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateSuccess);
	}

	
}
