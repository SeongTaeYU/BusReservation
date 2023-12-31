package com.bus.reservation.service;

import java.util.List;

import com.bus.reservation.dto.UserDTO;
import com.bus.reservation.entity.User;

public interface UserService {
    User createUser(UserDTO userDTO);				// 사용자 생성
    List<UserDTO> getUserList();					// 사용자 리스트
    UserDTO getUserByNo(Integer userNo);			// 사용자 한 명 가져오는 함수(상세보기)
    User updateUser(UserDTO userDTO);				// 사용자 수정하는 함수
    Boolean deleteUser(Integer userNo);				// 사용자 삭제하는 함수
    
    User login(UserDTO userDTO);					// 사용자 로그인
    Boolean idExist(String userId);					// 사용자 로그인
    
    List<UserDTO> getUserListByNo(Integer userNo);	// Mypage userList 함수
    
    /*
     * DTO --> Entity 전환을 위한 default 메소드
     */
    default User dtoToEntity(UserDTO userDto) {
    	
    	User userEntity = User.builder()
    						  .userNo(userDto.getUserNo())
    						  .userId(userDto.getUserId())
    						  .userName(userDto.getUserName())
    						  .password(userDto.getPassword())
    						  .email(userDto.getEmail())
    						  .phone(userDto.getPhone())
    						  .address(userDto.getAddress())
    						  .build();
    	
    	return userEntity;
    }
    
    /*
     * Entity --> DTO 전환을 위한 default 메소드
     */
    default UserDTO entityToDto(User userEntity) {
    	
    	UserDTO userDto = UserDTO.builder()
    							 .userNo(userEntity.getUserNo())
    							 .userId(userEntity.getUserId())
    							 .userName(userEntity.getUserName())
    							 .password(userEntity.getPassword())
    							 .email(userEntity.getEmail())
    							 .phone(userEntity.getPhone())
    							 .address(userEntity.getAddress())
    							 .regDate(userEntity.getRegDate())
    							 .modDate(userEntity.getModDate())
    							 .build();
    	
    	return userDto;
    }
}
