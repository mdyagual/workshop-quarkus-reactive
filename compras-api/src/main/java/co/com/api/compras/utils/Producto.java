package co.com.api.compras.utils;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Producto {

    @NotNull(message = "Nombre del producto es obligatorio")
    @Size(max=15, min=5, message = "Nombre del producto muy extenso")
    private String nombre;

    @NotNull(message = "Cantidad del producto es obligatorio")
    @Positive(message = "La cantidad no puede ser negativa o cero")
    private Integer cantidad;

    @NotNull(message = "Precio del producto es obligatorio")
    @Positive(message = "El precio no puede ser negativo o cero")
    private Double precio;


}
