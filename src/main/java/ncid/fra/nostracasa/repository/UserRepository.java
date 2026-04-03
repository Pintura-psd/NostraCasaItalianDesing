package ncid.fra.nostracasa.repository;

import ncid.fra.nostracasa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
