package maria.persistence;

import org.springframework.data.repository.CrudRepository;

import maria.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
