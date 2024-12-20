package in.codingstreams.etuserauthservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.codingstreams.etuserauthservice.constants.LoggingConstants;
import in.codingstreams.etuserauthservice.controller.dto.ChangePasswordRequest;
import in.codingstreams.etuserauthservice.controller.dto.UserDetails;
import in.codingstreams.etuserauthservice.controller.dto.UserInfo;
import in.codingstreams.etuserauthservice.controller.mapper.UserInfoMapper;
import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.service.auth.AuthService;
import in.codingstreams.etuserauthservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	//get user info
	
	private final UserService userService;
	@GetMapping("/{userId}")
	public ResponseEntity<UserInfo> userInfo(@PathVariable String userId) {
		
		
		String methodName = "user info :: get user info";
        
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        
        AppUser appUser=userService.getUserInfo(userId);
        
        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        
		return ResponseEntity.status(HttpStatus.OK).body(UserInfoMapper.INSTANCE.maptoUserInfo(appUser));
	}
	
	
	@PostMapping("/{userId}/changepassword")
	public ResponseEntity<String> changePassword(
			@PathVariable String userId,
			@RequestBody ChangePasswordRequest changePasswordRequest) {
		String methodName = "user info :: change password";
        
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        
        userService.changePassword(userId,changePasswordRequest.getOldPassword(),changePasswordRequest.getNewPassword());
        
        log.info(LoggingConstants.END_METHOD_LOG, methodName);

		
        return ResponseEntity.ok("Password updated successfully");
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<UserInfo> updateUserDetails(
			@PathVariable String userId,
			@RequestBody UserDetails userDetails) {
		String methodName = "user info :: update User Details";
        
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        
//        userService.changePassword(userId,changePasswordRequest.getOldPassword(),changePasswordRequest.getNewPassword());
        
        var appUser=switch(userDetails.getRequestType())
        {
        case UPDATE_EMAIL -> userService.updateEmail(userId,userDetails.getNewEmail());
        
        case UPDATE_NAME ->	userService.updateName(userId,userDetails.getNewName());
        
        };
        
        
        log.info(LoggingConstants.END_METHOD_LOG, methodName);

	        return ResponseEntity.status(HttpStatus.OK).body(UserInfoMapper.INSTANCE.maptoUserInfo(appUser));
		}
		

}
