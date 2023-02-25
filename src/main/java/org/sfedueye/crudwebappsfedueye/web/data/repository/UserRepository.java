package org.sfedueye.crudwebappsfedueye.web.data.repository;

import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
    User findUserById(long id);

}
