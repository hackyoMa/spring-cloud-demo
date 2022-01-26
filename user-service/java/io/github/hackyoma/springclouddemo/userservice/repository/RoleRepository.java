package io.github.hackyoma.springclouddemo.userservice.repository;

import io.github.hackyoma.springclouddemo.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoleRepository
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * findAllByIdIn
     *
     * @param ids ids
     * @return roles
     */
    List<Role> findAllByIdIn(List<String> ids);

}
