package io.github.hackyoma.springclouddemo.userservice.repository;

import io.github.hackyoma.springclouddemo.userservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRoleRepository
 *
 * @author 13712
 * @version 2018/8/22
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    /**
     * findAllByUserId
     *
     * @param userId user id
     * @return user role
     */
    List<UserRole> findAllByUserId(String userId);

}
