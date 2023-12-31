package com.bus.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bus.reservation.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from tbl_user u where u.userId = :userId and u.password = :password")
	User findByIdPwd(@Param("userId") String userId, @Param("password") String password);
	
	@Query("select u from tbl_user u where u.userNo = :userNo")
	public List<User> getUserListByNo(@Param("userNo") Integer userNo);
}
