package in.codingstreams.etuserauthservice.controller.dto;

import java.time.LocalDateTime;

import in.codingstreams.etuserauthservice.data.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String errorcode;
	private String errormessage;
}
