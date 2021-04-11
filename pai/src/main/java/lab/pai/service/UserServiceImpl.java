package lab.pai.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lab.pai.model.Delegation;
import lab.pai.model.Role;
import lab.pai.model.User;
import lab.pai.repository.DelegationRepo;
import lab.pai.repository.RoleRepo;
import lab.pai.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	DelegationRepo delegationRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	public void changePassword(long userId, String newPassword) {
		// TODO Auto-generated method stub
		Optional<User> u = userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			user.setPassword(newPassword);
		}
		
	}

	public boolean deleteUserById(long userId) {
		// TODO Auto-generated method stub
		Optional<User> u = userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			for (Delegation delegation : user.getDelegation()) {				
				delegationRepo.delete(delegation);
			}
			userRepo.deleteById(userId);
			return true;
		}
		return false;
	}

	public List<User> getAllUsersByRoleName(String roleName) {
		// TODO Auto-generated method stub
//		List<Role> roles = roleRepo.findAll().
//				stream().
//				filter(x -> x.getRoleName().contains(roleName)).
//				collect(Collectors.toList());
//		List<User> users = null;
//		for(int i=0; i<roles.size(); i++) {
//			users = roles.get(i).getUser();
//		}
//		return users;
		List<User> users = userRepo.findAll();//.stream().
//				filter(x -> x.getRole().
//						forEach(y -> y.getRoleName().contains(roleName);)).
//				collect(Collectors.toList());
		List<User> usersWithRoleName = new ArrayList<>();
		int i=0;
		for(User user : users) {
			for(Role role : user.getRole()) {
				if(role.getRoleName().contains(roleName)) {
					usersWithRoleName.add(user);
				}
			}
			i++;
		}
		return usersWithRoleName;
	}
	
    public User findUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

	public List<Delegation> getAllDelByUserOrderByDateStartDesc(long userId) {
		// TODO Auto-generated method stub
		Optional<User> u= userRepo.findById(userId);
		if(u.isPresent()) {
			User user = u.get();
			List<Delegation> del = user.getDelegation();
			return del.stream().
					sorted(Comparator.comparing(Delegation::getDateTimeStart, Comparator.reverseOrder())).
					collect(Collectors.toList());
		}
		return null;
	}
	
	   public void makeAdmin(long userId){
//	        userRepository.findById((int)userId).map(user1 -> {
//	            user1.setRoles(new HashSet<>(Arrays.asList(new Role(RoleTypes.ADMIN ))));
//	            return userRepository.save(user1);
//	        });
		   Optional<User> u = userRepo.findById(userId);
		   if(u.isPresent()) {
			   User user = u.get();
			   List<Role> userRole = user.getRole();
				Long roleId = 2L;
				Optional<Role> r = roleRepo.findById(roleId);
				if(r.isPresent()) {
					Role role = r.get();
					userRole.add(role);
					user.setRole(userRole);
					//userRepo.findById(userId).map(User::setRole)
							//user -> {
						//user.setRole(userRole);
					//})
					//;
				}
		   }	
	    }

	    public void makeUser(long userId){
//	        userRepository.findById((int)userId).map(user1 -> {
//	            user1.setRoles(new HashSet<>(Arrays.asList(new Role(RoleTypes.USER))));
//	            return userRepository.save(user1);
//	        });
	    	Optional<User> u = userRepo.findById(userId);
	    	if(u.isPresent()) {
	    		User user = u.get();
				List<Role> userRole = user.getRole();
				Long roleId = 1L;
				Optional<Role> r = roleRepo.findById(roleId);
				if(r.isPresent()) {
					Role role = r.get();
					userRole.add(role);
					user.setRole(userRole);
				}
	    	}	
	    }	
}
