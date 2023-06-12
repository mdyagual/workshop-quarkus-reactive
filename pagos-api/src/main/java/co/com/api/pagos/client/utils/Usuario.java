package co.com.api.pagos.client.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Tarjeta tarjeta;
    private List<Producto> productos;
}
