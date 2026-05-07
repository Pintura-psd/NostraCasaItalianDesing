package ncid.fra.nostracasa.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @Size(max = 100)
    private String material;

    @NotBlank
    @Size(max = 80)
    private String type;

    @Size(max = 50)
    private String size;

    @Positive
    private Double thickness;

    @Size(max = 200)
    private String finishes;

    @NotNull
    @Min(value = 1, message = "Debe haber al menos 1 pieza por caja")
    private Integer piecesPerBox;

    @Min(value = 0)
    private Integer boxesPerPallet;

    @PositiveOrZero
    private Double m2PerBox;

    @PositiveOrZero
    private Double m2PerPallet;

    @PositiveOrZero
    private Double weightPerBox;

    @PositiveOrZero
    private Double weightPerPallet;

    @NotNull
    private Long categoryId;

    // Traducciones
    @NotBlank
    @Size(max = 255)
    private String nameEs;

    @NotBlank
    @Size(max = 255)
    private String nameEn;

    @Size(max = 2000)
    private String descriptionEs;

    @Size(max = 2000)
    private String descriptionEn;

//Imagenes
    private MultipartFile thumbnail;
    private MultipartFile detail;
    private List<MultipartFile> compositions;

}
