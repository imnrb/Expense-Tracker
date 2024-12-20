package in.codingstreams.etuserauthservice.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.codingstreams.etuserauthservice.constants.ErrorMessages;
import in.codingstreams.etuserauthservice.constants.LoggingConstants;
import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.data.repo.AppUserRepo;
import in.codingstreams.etuserauthservice.exception.InvalidCreds;
import in.codingstreams.etuserauthservice.exception.UserNotFoundException;
import in.codingstreams.etuserauthservice.service.auth.AuthServiceImpl;
import in.codingstreams.etuserauthservice.service.auth.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
	
	private final AppUserRepo appUserRepo;
	private final PasswordEncoder passwordEncoder;
	
    @Override
    public AppUser getUserInfo(String userId) {
    	
    	String methodName = "UserServiceImpl:getUserInfo";

		log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
		
		
		//find user by userid
		
		var appUser=appUserRepo.findById(userId).orElseThrow(()->{
			log.error(LoggingConstants.ERROR_METHOD_LOG,methodName,userId+"not found.");
			return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getErrorMessage(), ErrorMessages.USER_NOT_FOUND.getErrorCode());
		});
		
		
		log.info(LoggingConstants.END_METHOD_LOG, methodName);
		return appUser;
    }

	@Override
	public void changePassword(String userId, String oldPassword, String newPassword) {
	    String methodName = getClass().getSimpleName() + ":changePassword";
	    
	    log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
	    
	    var appUser = getUserInfo(userId);
	    
	    // Correct comparison: check if the provided oldPassword matches the stored password
	    if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
	        log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, "Old password is incorrect for user: " + appUser.getEmail());
	        throw new InvalidCreds(ErrorMessages.INVALID_CREDS.getErrorMessage(), ErrorMessages.INVALID_CREDS.getErrorCode());
	    }
	    
	    // Encode and set the new password
	    appUser.setPassword(passwordEncoder.encode(newPassword));
	    appUserRepo.save(appUser);
	    
	    log.info(LoggingConstants.END_METHOD_LOG, methodName);
	}

	@Override
	public AppUser updateEmail(String userId, String newEmail) {
		
	    String methodName = getClass().getSimpleName() + ":updateEmail";
	    
	    log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
		
		var appUser=getUserInfo(userId);
		appUser.setEmail(newEmail);
		
		var savedUser=appUserRepo.save(appUser);
	    log.info(LoggingConstants.END_METHOD_LOG, methodName);

		
		return savedUser;
	}

	@Override
	public AppUser updateName(String userId, String newName) {
		
	    String methodName = getClass().getSimpleName() + ":updateName";
	    
	    log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

		var appUser=getUserInfo(userId);
		appUser.setName(newName);
		
		var savedUser=appUserRepo.save(appUser);
	    log.info(LoggingConstants.END_METHOD_LOG, methodName);

		return savedUser;
	}





    
}