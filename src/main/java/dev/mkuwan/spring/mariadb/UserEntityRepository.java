package dev.mkuwan.spring.mariadb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity getUserEntityByEmail(String email);

}
