package in.codingstreams.etuserauthservice.controller.dto;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
	
	private String newName;
	private String newEmail;
	private UpdateRequestType requestType;
	

}
