package org.tricode.hackathonserverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tricode.hackathonserverside.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(Role.RoleName roleName);
}
