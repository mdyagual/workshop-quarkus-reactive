package co.com.api.pagos.entity;

import co.com.api.pagos.client.SolicitudDTO;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "transacciones")
public class Transaccion {
    private String transaccionId= UUID.randomUUID().toString().substring(0, 10);

    private SolicitudDTO solicitudDTO;
    private Boolean estado;
    private Date timestamp = new Date();


}
