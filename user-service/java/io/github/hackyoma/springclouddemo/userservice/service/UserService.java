package io.github.hackyoma.springclouddemo.userservice.service;

import com.alibaba.fastjson.JSONObject;
import io.github.hackyoma.springclouddemo.common.EncryptUtil;
import io.github.hackyoma.springclouddemo.common.JwtTokenUtil;
import io.github.hackyoma.springclouddemo.servicecommon.util.I18n;
import io.github.hackyoma.springclouddemo.userservice.entity.*;
import io.github.hackyoma.springclouddemo.userservice.repository.*;
import io.github.hackyoma.springclouddemo.userservice.entity.*;
import io.github.hackyoma.springclouddemo.userservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * @author hackyo
 * @version 2018/8/22
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserRoleRepository userRoleRepository,
                       RolePermissionRepository rolePermissionRepository,
                       PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    public String login(JSONObject loginInfo) {
        String username = loginInfo.getString("username");
        String password = loginInfo.getString("password");
        User user = userRepository.findUserByUsernameAndPassword(username, EncryptUtil.hmacSha256(password));
        if (user == null) {
            throw new RuntimeException(I18n.get("usernameOrPasswordError"));
        }
        return JwtTokenUtil.generateToken(user.getId());
    }

    public JSONObject getUserInfo(String userId) {
        User user = userRepository.findUserById(userId);
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleIdIn(roleIds);
        List<String> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        List<Permission> permissions = permissionRepository.findAllByIdInOrBasics(permissionIds, true);
        return new JSONObject().fluentPut("user", user)
                .fluentPut("roles", roles)
                .fluentPut("permissions", permissions);
    }

}
