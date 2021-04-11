package lab.pai.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lab.pai.model.Delegation;
import lab.pai.model.User;

public interface UserService {
	User registerUser(User user);
	List<User> getAllUsers();
	void changePassword(long userId, String newPassword);
	boolean deleteUserById(long userId);	
	List<User> getAllUsersByRoleName(String roleName);
	User findUserByEmail(String email);
	List<Delegation> getAllDelByUserOrderByDateStartDesc(long userId);
	void makeAdmin(long userId);
	void makeUser(long userId);
}
