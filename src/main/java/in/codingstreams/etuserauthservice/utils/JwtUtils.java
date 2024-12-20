package in.codingstreams.etuserauthservice.utils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.time.DateUtils;

import in.codingstreams.etuserauthservice.constants.LoggingConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
	public static final String ISSUER = "ET_USER_AUTH_SERVICE";

	public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

	private JwtUtils() {

	}

	public static String generateAccessToken(String email) {

		String methodName = "JwtUtils: generateAccessToken";

		log.info(LoggingConstants.START_METHOD_LOG, methodName, email);

		var issuedAt = new Date();
		var expiration = DateUtils.addMinutes(issuedAt, 15);
		// TODO Auto-generated method stub
		var accessToken = Jwts.builder().issuer(ISSUER).subject(email).id(UUID.randomUUID().toString())
				.issuedAt(issuedAt).expiration(expiration).signWith(SECRET_KEY).compact();
		log.info(LoggingConstants.END_METHOD_LOG, methodName);

		return accessToken;

	}

	public static Optional<String> getUsernameFromToken(String accessToken) {
		// TODO Auto-generated method stub

		String methodName = "JwtUtils: getUsernameFromToken";

		log.info(LoggingConstants.START_METHOD_LOG, methodName,accessToken );
		log.info(LoggingConstants.END_METHOD_LOG, methodName );
		return parseToken(accessToken).map(claims -> claims.getSubject());

	}

	private static Optional<Claims> parseToken(String accessToken) {
		// TODO Auto-generated method stub
		String methodName = "JwtUtils: generateAccessToken";

		log.info(LoggingConstants.START_METHOD_LOG, methodName,accessToken );

		var jwtParser=Jwts.parser()
		.verifyWith(SECRET_KEY)
		.build();
		
		 
		try 
		{
		return Optional.of(jwtParser.parseSignedClaims(accessToken).getPayload());	
		}catch(JwtException | IllegalArgumentException e)
		{
			log.info(LoggingConstants.ERROR_METHOD_LOG, methodName,"Error while parsing the token" );

			return Optional.empty();		
		}
	}
}
