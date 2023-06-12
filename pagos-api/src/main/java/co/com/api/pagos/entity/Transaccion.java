package co.com.api.pagos.entity;

import co.com.api.pagos.client.SolicitudDTO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "transacciones")
public class Transaccion {
    private String transaccionId= UUID.randomUUID().toString().substring(0, 10);
    @NotNull(message = "Solicitud es requerida")
    private SolicitudDTO solicitudDTO;
    private Boolean estado;
    private Date timestamp = new Date();

    //TO DO
    private Boolean transaccionEstado(SolicitudDTO solicitudDTO){
        Random status = new Random();
        return status.nextBoolean();
        //Ver si la tarjeta tiene cupo
        //Ver que la fecha de caducidad no sea memor a la fecha actual
        //Ver si el CVV corresponde
    }
}
