package org.sfedueye.crudwebappsfedueye.web.data.repository;

import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User save(User user);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findUserById(long id);
    List<User> findAllByUserInfo_AcceptedIsTrueOrderByEmail(Pageable email);
    int countAllByUserInfo_AcceptedIsTrue();

}
