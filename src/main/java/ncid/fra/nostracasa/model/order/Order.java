package ncid.fra.nostracasa.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ncid.fra.nostracasa.model.DetailOrder;
import ncid.fra.nostracasa.model.user.User;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_order", nullable = false, length = 255)
    private LocalDate dateOrder;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_order")
    private StateOrder stateOrder;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    //Relaciones
    @OneToMany(mappedBy = "order")
    private List<DetailOrder> detailOrders;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
