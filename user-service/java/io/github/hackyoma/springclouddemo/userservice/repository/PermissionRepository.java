package io.github.hackyoma.springclouddemo.userservice.repository;

import io.github.hackyoma.springclouddemo.userservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PermissionRepository
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    /**
     * findAllByIdInOrBasics
     *
     * @param ids    ids
     * @param basics basics
     * @return permissions
     */
    List<Permission> findAllByIdInOrBasics(List<String> ids, boolean basics);

}
