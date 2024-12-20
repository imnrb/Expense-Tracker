package in.codingstreams.etuserauthservice.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import in.codingstreams.etuserauthservice.controller.dto.UserInfo;
import in.codingstreams.etuserauthservice.data.model.AppUser;

@Mapper
public interface UserInfoMapper {

	UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);
	
	UserInfo maptoUserInfo(AppUser appUser);

}
