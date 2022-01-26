package io.github.hackyoma.springclouddemo.userservice.repository;

import io.github.hackyoma.springclouddemo.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * findUserByUsernameAndPassword
     *
     * @param username username
     * @param password password
     * @return user
     */
    User findUserByUsernameAndPassword(String username, String password);

    /**
     * findUserById
     *
     * @param id id
     * @return user
     */
    User findUserById(String id);

}
