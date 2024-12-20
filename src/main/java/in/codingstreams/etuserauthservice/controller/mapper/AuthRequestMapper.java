package in.codingstreams.etuserauthservice.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import in.codingstreams.etuserauthservice.controller.dto.AuthRequest;
import in.codingstreams.etuserauthservice.service.auth.model.LoginRequest;
import in.codingstreams.etuserauthservice.service.auth.model.SignUpRequest;

@Mapper
public interface AuthRequestMapper {

	AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);
	
	SignUpRequest maptoSignUpRequest(AuthRequest authRequest);
	LoginRequest maptoLoginRequest(AuthRequest authRequest);

}
