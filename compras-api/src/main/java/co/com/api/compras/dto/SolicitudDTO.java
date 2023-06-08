package co.com.api.compras.dto;

import co.com.api.compras.utils.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDTO {
    private String solicitudId;
    private Usuario usuario;
    private Double total;
    private Date timestamp;
}
