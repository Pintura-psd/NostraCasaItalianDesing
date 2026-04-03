package ncid.fra.nostracasa.repository;

import ncid.fra.nostracasa.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {
}
