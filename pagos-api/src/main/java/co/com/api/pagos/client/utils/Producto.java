package co.com.api.pagos.client.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private String nombre;
    private Integer cantidad;
    private Double precio;


}
