package com.bus.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bus.reservation.dto.UserDTO;
import com.bus.reservation.entity.User;
import com.bus.reservation.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
	
	// 의존성 주입 (생성자 or @Autowired 등)
	private final UserRepository userRepository;
    
	@PersistenceContext
	private EntityManager em;

	/*
	 * 사용자 생성
	 */
    @Override
    public User createUser(UserDTO userDTO) {
        // 데이터베이스에 사용자 생성 로직 수행
    	User user = dtoToEntity(userDTO);
        return userRepository.save(user);
    }//end createUser
    
    /*
     * 사용자 리스트 가져오는 함수
     */
    @Override
    public List<UserDTO> getUserList(){
    	// 사용자 모든 정보 조회 로직 수행
    	List<User> userList = userRepository.findAll();
    	List<UserDTO> userDTOList = userList.stream()
    										.map(entity -> entityToDto(entity)).collect(Collectors.toList());
    	return userDTOList;
    }//end getUserList
    
    /*
     * 사용자 한 명 정보 가져오는 함수(상세보기)
     */
    @Override
    public UserDTO getUserByNo(Integer userNo) {
        // 데이터베이스로부터 사용자 정보 조회 로직 수행
    	Optional<User> user = userRepository.findById(userNo);
    	UserDTO userDTO = null;
    	if(user.isPresent()) {
    		userDTO = entityToDto(user.get());
    	}
        return userDTO;
    }//end getUserByNo
    
    /*
     * 사용자 수정
     * 수정 가능한 속성 ( 이름, 비밀번호, 휴대폰번호, 주소)
     */
    @Override
    public User updateUser(UserDTO userDTO) {
        // UserDao를 사용하여 데이터베이스에서 사용자 업데이트 로직 수행
    	Optional<User> data = userRepository.findById(userDTO.getUserNo());
    	if(data.isPresent()) {
    		User targetEntity = data.get();
    		targetEntity.setPassword(userDTO.getPassword());
    		targetEntity.setUserName(userDTO.getUserName());
    		targetEntity.setPhone(userDTO.getPhone());
    		targetEntity.setAddress(userDTO.getAddress());
    		targetEntity.setEmail(userDTO.getEmail());
    		
    		return userRepository.save(targetEntity);
    	}
        return null;
    }//end updateUser
    
    /*
     * 사용자 삭제
     */
    @Override
    public Boolean deleteUser(Integer userNo) {
        // 데이터베이스에서 사용자 삭제 로직 수행
    	Optional<User> data = userRepository.findById(userNo);
    	if(data.isPresent()) {
    		userRepository.delete(data.get());
    		return true;
    	}
        return false;
    }//end deleteUser
   
    /*
     * 회원 한 명 가져오는 메소드 (id, password - 로그인)
     */
    @Override
    public User login(UserDTO userDTO) {
    	User user = dtoToEntity(userDTO);
    	User result = userRepository.findByIdPwd(user.getUserId(), user.getPassword());
    	
    	return result;
    }//end login
   
    
    /*
     * 회원 한 명 가져오는 메소드 (id - 중복 검사)
     */
    @Override
    public Boolean idExist(String userId) {
    	log.info("서비스에서 아디 중복체크 시작");
    	System.out.println("userId : " + userId);
    	Long count = 0L;
    	System.out.println("count 수량 : " + count);
    	count = em.createQuery("SELECT COUNT(*) FROM tbl_user u WHERE u.userId = :userId", Long.class)
    			  .setParameter("userId", userId)
    			  .getSingleResult();
    	
    	System.out.println("count 수량 : " + count);
    	
    	if (count > 0) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    /*
     *  Mypage userListByNo 함수
     */
	@Override
	public List<UserDTO> getUserListByNo(Integer userNo) {
		List<User> userByNo = userRepository.getUserListByNo(userNo);
		List<UserDTO> userDTOList = userByNo.stream()
											.map(entity -> entityToDto(entity)).collect(Collectors.toList());
		return userDTOList;
	}//end idExist
    
    
}//end Class UserServiceImpl 
