package com.lovius.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovius.common.RMessage;
import com.lovius.common.ResponseMessage;
import com.lovius.model.MemberProfile;
import com.lovius.serviceInterface.MemberProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/member/profile")
@Api(tags = "會員資料", description = "尋找、新增、.更新、.刪除會員資料")
@Slf4j
public class MemberProfileRestController {

	@Autowired
	private MemberProfileService memberProfileService;

	@ApiOperation(value = "尋找所有會員資料", notes = "依據ID排序")
	@GetMapping("/findAllOrderById")
	public RMessage findAllOrderById() {

		List<MemberProfile> memberProfileLists = memberProfileService.findAllOrderById();

		return new RMessage(HttpStatus.OK.value(), ResponseMessage.findSuccess, memberProfileLists);
	}
	
	@ApiOperation(value = "尋找所有會員資料", notes = "依據ID排序")
	@GetMapping("/{id}")
	public RMessage findById(@PathVariable("id") String id) {
		MemberProfile memberProfile = memberProfileService.findById(id);
		//log.info("memberProfile = " +memberProfile.toString());
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.findSuccess, memberProfile);
	}

	@ApiOperation(value = "更新會員資料", notes = "依據ID作更新")
	@PutMapping
	public RMessage updateById(@Valid MemberProfile memberProfile, BindingResult br) {

		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "\n");
			}
			return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateFailed, sb.toString());
		}

		memberProfileService.update(memberProfile);
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateSuccess);
	}
	
	@ApiOperation(value = "動態更新會員資料", notes = "依據ID作更新")
	@PutMapping("/dy")
	public RMessage updateDynamicById(@Valid MemberProfile memberProfile, BindingResult br) {

		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "\n");
			}
			return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateFailed, sb.toString());
		}
		memberProfileService.updateDynamic(memberProfile);
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateSuccess);
	}

	@ApiOperation(value = "新增會員資料", notes = "新增會員資料，ID為PK")
	@PostMapping
	public RMessage insert(@Valid MemberProfile memberProfile, BindingResult br) {
		
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "\n");
			}
			return new RMessage(HttpStatus.OK.value(), ResponseMessage.updateFailed, sb.toString());
		}
		
		memberProfileService.update(memberProfile);
		return new RMessage(HttpStatus.OK.value(), ResponseMessage.insertSuccess);
	}

	@ApiOperation(value = "刪除會員資料", notes = "以ID刪除會員資料")
	@DeleteMapping
	public RMessage delete(String memberId) {

		return new RMessage(HttpStatus.OK.value(), ResponseMessage.deleteSuccess);
	}

}
