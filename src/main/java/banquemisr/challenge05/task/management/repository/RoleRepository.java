package banquemisr.challenge05.task.management.repository;

import banquemisr.challenge05.task.management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
