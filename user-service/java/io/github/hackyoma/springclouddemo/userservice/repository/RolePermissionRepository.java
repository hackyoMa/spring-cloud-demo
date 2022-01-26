package io.github.hackyoma.springclouddemo.userservice.repository;

import io.github.hackyoma.springclouddemo.userservice.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RolePermissionRepository
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    /**
     * findAllByRoleIdIn
     *
     * @param roleIds role ids
     * @return role permission
     */
    List<RolePermission> findAllByRoleIdIn(List<String> roleIds);

}
