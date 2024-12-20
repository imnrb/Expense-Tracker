package in.codingstreams.etuserauthservice.service.auth;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.codingstreams.etuserauthservice.constants.ErrorMessages;
import in.codingstreams.etuserauthservice.constants.LoggingConstants;
import in.codingstreams.etuserauthservice.controller.AuthController;
import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.data.repo.AppUserRepo;
import in.codingstreams.etuserauthservice.exception.InvalidCreds;
import in.codingstreams.etuserauthservice.exception.InvalidTokenException;
import in.codingstreams.etuserauthservice.exception.UserAlreadyExistsException;
import in.codingstreams.etuserauthservice.exception.UserNotFoundException;
import in.codingstreams.etuserauthservice.service.auth.model.LoginRequest;
import in.codingstreams.etuserauthservice.service.auth.model.SignUpRequest;
import in.codingstreams.etuserauthservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final AppUserRepo appUserRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String signUp(SignUpRequest signUpRequest) {
		String methodName = "AuthServiceImpl:SignUp";

		log.info(LoggingConstants.START_METHOD_LOG, methodName, signUpRequest);

		if (appUserRepo.existsByEmail(signUpRequest.getEmail())) {
			log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, signUpRequest.getEmail() + "already exists");

			throw new UserAlreadyExistsException(ErrorMessages.USER_AlREADY_EXISTS.getErrorMessage(),
					ErrorMessages.USER_AlREADY_EXISTS.getErrorCode());
		}

		// create App User Model

		var appUser = AppUser.builder().name(signUpRequest.getName()).email(signUpRequest.getEmail())
				.password(passwordEncoder.encode(signUpRequest.getPassword())).build();

		// save user
		appUserRepo.save(appUser);

		// generate access token

		var accessToken = JwtUtils.generateAccessToken(signUpRequest.getEmail());

		log.info(LoggingConstants.END_METHOD_LOG, methodName);

		return accessToken;
	}

	@Override
	public String login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		String methodName = "AuthServiceImpl:loginRequest";

		log.info(LoggingConstants.START_METHOD_LOG, methodName, loginRequest);
		
		var appUser=appUserRepo.findByEmail(loginRequest.getEmail())
		.orElseThrow(()-> {
			log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, loginRequest.getEmail() + "does not exist");

			return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getErrorMessage(), ErrorMessages.USER_NOT_FOUND.getErrorCode());
			});
		
		if(!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
			log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, loginRequest.getEmail() + "password incorrect");

			throw new InvalidCreds(ErrorMessages.INVALID_CREDS.getErrorMessage(), ErrorMessages.INVALID_CREDS.getErrorCode());
			};
		
		
		
			var accessToken = JwtUtils.generateAccessToken(loginRequest.getEmail());

			log.info(LoggingConstants.END_METHOD_LOG, methodName);

			return accessToken;
	}

	@Override
	public String verifytoken(String accessToken) {
		// TODO Auto-generated method stub
		String methodName = "AuthServiceImpl:verifytoken";

		log.info(LoggingConstants.START_METHOD_LOG, methodName, accessToken);
		
		//extract usernaame(email) from token
		var username=JwtUtils.getUsernameFromToken(accessToken)
				.orElseThrow(()-> {
					log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, accessToken+ " token is invalid");

					return new InvalidTokenException(ErrorMessages.INVALID_TOKEN.getErrorMessage(), ErrorMessages.INVALID_TOKEN.getErrorCode());
					});
		
		//find userid by email
		
		var appuser=appUserRepo.findByEmail(username)
				.orElseThrow(()->{
					log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, username+ " user not found");
					return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getErrorMessage(), ErrorMessages.USER_NOT_FOUND.getErrorCode());
				}
				);
		var userid=appuser.getUserId();
		return userid;
	}

}
