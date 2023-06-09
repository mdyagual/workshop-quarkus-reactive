package co.com.api.compras.entity;

import co.com.api.compras.utils.Usuario;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "solicitudes")
public class Solicitud {
    private String solicitudId = UUID.randomUUID().toString().substring(0, 10);
    private Usuario usuario;
    private Double total;
    private Date timestamp = new Date();
}
