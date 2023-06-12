package co.com.api.pagos.client.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarjeta {
    private String numero;
    private Date fecha_caducidad;
    private String cvv;
    private String propietario;
}
