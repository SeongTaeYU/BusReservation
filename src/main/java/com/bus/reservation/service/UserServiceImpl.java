package com.bus.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.javalab.reservation.dao.UserDao;
import com.javalab.reservation.vo.User;

public class UserServiceImpl implements UserService{
	
	// UserDao의 의존성 주입 (생성자 or @Autowired 등)
	@Autowired
	private  UserDao userDao;
    

	/*
	 * 사용자 생성
	 */
    @Override
    public User createUser(User user) {
        
        // UserDao를 사용하여 데이터베이스에 사용자 생성 로직 수행
        return userDao.createUser(user);
    }
    
    /*
     * 사용자 리스트 가져오는 함수
     */
    @Override
    public List<User> getUserList(){
    	// UserDao를 사용하여 데이터베이스로부터 사용자 모든 정보 조회 로직 수행
    	return userDao.getUserList();
    }
    
    /*
     * 사용자 한 명 정보 가져오는 함수(상세보기)
     */
    @Override
    public User getUserById(Integer userId) {
        // UserDao를 사용하여 데이터베이스로부터 사용자 정보 조회 로직 수행
        return userDao.getUserById(userId);
    }
    
    /*
     * 사용자 수정
     */
    @Override
    public User updateUser(User user) {
        
        // UserDao를 사용하여 데이터베이스에서 사용자 업데이트 로직 수행
        return userDao.updateUser(user);
    }
    
    /*
     * 사용자 삭제
     */
    @Override
    public Boolean deleteUser(Integer userId) {
        // UserDao를 사용하여 데이터베이스에서 사용자 삭제 로직 수행
        return userDao.deleteUser(userId);
    }
   
    /*
     * 사용자 로그인
     */
    @Override
    public User login(User user) {
    	return userDao.login(user);
    } 
   
    
    /*
     * 사용자 로그인
     */
    @Override
    public Boolean idExist(String userName) {
    	
    	return userDao.idExist(userName);
    }
}
