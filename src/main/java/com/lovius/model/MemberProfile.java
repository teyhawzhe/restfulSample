package com.lovius.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "會員資料")
public class MemberProfile implements Serializable {

	private static final long serialVersionUID = 1924112708509814902L;

	@Id
	@ApiModelProperty(value = "用戶ID", required = true , example = "peter" )
	@Column(unique = true, length = 20, nullable = false)
	@NotBlank(message = "請填寫用戶ID")
	@Size(min = 1, max = 20, message = "用戶ID長度介於1到20之間")
	private String id;

	@ApiModelProperty(value = "姓名", required = true  , example = "彼得" )
	@Column(length = 20, nullable = false)
	@NotBlank(message = "請填寫姓名")
	@Size(min = 1, max = 20, message = "姓名長度介於1到20之間")
	private String name;

	@ApiModelProperty(value = "小名", required = true  , example = "小得" )
	@Column(length = 20, nullable = false)
	@NotBlank(message = "請填寫小名")
	@Size(min = 1, max = 20, message = "小名長度介於1到20之間")
	private String nickName;

	@ApiModelProperty(value = "性別", required = true  , example = "1" )
	@Column(length = 1, nullable = false)
	@NotBlank(message = "請填寫性別")
	@Size(min = 1, max = 1, message = "性別長度只有1")
	private String sex;

	@ApiModelProperty(value = "年齡", required = true  , example = "21" )
	@Column(length = 10, nullable = false)
	@Max(value = 100, message = "年齡不能大於100")
	@Min(value = 0, message = "年齡不能為負數")
	private int age;

	@ApiModelProperty(value = "電話", required = true , example = "0900-000000")
	@Column(length = 20, nullable = false)
	@NotBlank(message = "請填寫電話")
	@Size(min = 1, max = 20, message = "用戶ID長度介於1到20之間")
	@Pattern(regexp = "[0-9]{4}-[0-9]{6}",message = "電話格式因該是0901-234567")
	private String tel;

	@ApiModelProperty(value = "地址", required = true  , example = "台灣" )
	@Column(length = 50, nullable = false)
	@NotBlank(message = "請填寫地址")
	@Size(min = 1, max = 50, message = "地址長度介於1到20之間")
	private String address;

}
