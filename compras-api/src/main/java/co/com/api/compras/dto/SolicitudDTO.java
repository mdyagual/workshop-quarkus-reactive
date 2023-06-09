package co.com.api.compras.dto;

import co.com.api.compras.utils.Usuario;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDTO {
    private String solicitudId;
    @Valid
    private Usuario usuario;
    private Double total;
    private Date timestamp;
}
