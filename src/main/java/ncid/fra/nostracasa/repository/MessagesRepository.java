package ncid.fra.nostracasa.repository;

import ncid.fra.nostracasa.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository <Messages, Long> {
}
