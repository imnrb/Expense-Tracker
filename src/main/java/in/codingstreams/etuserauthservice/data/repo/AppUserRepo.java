package in.codingstreams.etuserauthservice.data.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.codingstreams.etuserauthservice.data.model.AppUser;

public interface AppUserRepo extends MongoRepository<AppUser, String> {
	
	Optional<AppUser>  findByEmail(String email);
	
	boolean existsByEmail(String email);


}
