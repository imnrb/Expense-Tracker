package in.codingstreams.etuserauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.codingstreams.etuserauthservice.constants.LoggingConstants;
import in.codingstreams.etuserauthservice.controller.dto.AuthRequest;
import in.codingstreams.etuserauthservice.controller.dto.AuthResponse;
import in.codingstreams.etuserauthservice.controller.dto.VerifyTokenRequest;
import in.codingstreams.etuserauthservice.controller.dto.VerifyTokenResponse;
import in.codingstreams.etuserauthservice.controller.mapper.AuthRequestMapper;
import in.codingstreams.etuserauthservice.service.auth.AuthService;
import in.codingstreams.etuserauthservice.utils.JwtUtils;
//import in.codingstreams.etuserauthservice.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
    

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest authRequest) {
        String methodName = "signup";
        
        log.info(LoggingConstants.START_METHOD_LOG, methodName, authRequest);
        
        var accessToken=authService.signUp(
        		AuthRequestMapper.INSTANCE.maptoSignUpRequest(authRequest)
        		);
        
        log.info(LoggingConstants.END_METHOD_LOG, methodName);
        return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body(
        		AuthResponse.builder()
        		.accessToken(accessToken)
        		.build()
        		);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        //TODO: process POST request
        String methodName = "login";
        
        log.info(LoggingConstants.START_METHOD_LOG, methodName, authRequest);
        
        var accessToken=authService.login(
        		AuthRequestMapper.INSTANCE.maptoLoginRequest(authRequest)
        		);
        
		log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body(
        		AuthResponse.builder()
        		.accessToken(accessToken)
        		.build()
        		);
      }
    @PostMapping("/verifytoken")
    public ResponseEntity<VerifyTokenResponse> postMethodName(@RequestBody VerifyTokenRequest verifyTokenRequest) {
        //TODO: process POST request
    	String methodName = "verify-token";
        
        log.info(LoggingConstants.START_METHOD_LOG, methodName, verifyTokenRequest);
        
        var userId=authService.verifytoken(verifyTokenRequest.getAccessToken());
        
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(
        		 VerifyTokenResponse.builder()
        		.userId(userId)
        		.build()
        		);
    }
    
    
}