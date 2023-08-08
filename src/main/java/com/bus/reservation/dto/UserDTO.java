package com.bus.reservation.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

	private Integer userNo;
	
	@NotBlank(message = "이름을 입력하세요.")
	@Size(min = 1, max = 14, message = "이름(닉네임)은 1~14자로 이내로 입력하세요.")
    private String userName;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	@Size(min = 2, max = 20, message = "비밀번호는 8~20자리 이내로 입력하세요.")
    private String password;
    
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
	
    @NotBlank(message = "전화번호를 입력하세요.")
    private String phone;
    
    private String address;
    
    private LocalDateTime regDate;
	private LocalDateTime modDate;
    
}
