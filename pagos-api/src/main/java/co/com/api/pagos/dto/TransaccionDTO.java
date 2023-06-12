package co.com.api.pagos.dto;

import co.com.api.pagos.client.SolicitudDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDTO {
    private String transaccionId;
    @Valid
    private SolicitudDTO solicitudDTO;
    private Boolean estado;
    private Date timestamp;
}
