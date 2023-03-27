package org.sfedueye.crudwebappsfedueye.web.data.repository;

import org.sfedueye.crudwebappsfedueye.web.data.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    Page<UserInfo> findAllByAcceptedIsFalseOrderBySurname(Pageable pageable);
    Page<UserInfo> findAllByAcceptedIsTrueOrderBySurname(Pageable pageable);
    int countAllByAcceptedIsFalse();
    int countAllByAcceptedIsTrue();
    boolean existsByEmail(String email);
    UserInfo findByEmail(String email);
}
