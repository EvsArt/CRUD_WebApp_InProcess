package org.sfedueye.crudwebappsfedueye.web.data.repository;

import org.sfedueye.crudwebappsfedueye.web.data.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByName(String name);

}
