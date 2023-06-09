package co.com.api.compras.entity;

import co.com.api.compras.utils.Producto;
import co.com.api.compras.utils.Usuario;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "solicitudes")
public class Solicitud {
    private String solicitudId = UUID.randomUUID().toString().substring(0, 10);

    private Usuario usuario;
    @NotNull(message = "Total es requerido")
    @Positive(message = "Total no puede ser negativo o cero")
    private Double total;
    private Date timestamp = new Date();

    private Double calculate(List<Producto> productos){
        return productos.stream().map(producto -> {
            var precio = producto.getPrecio();
            var cantidad = producto.getCantidad();
            return precio * cantidad;
        }).reduce(Double::sum).orElse(0.0);
    }

    public void setCalculate(){
        this.total = calculate(usuario.getProductos());
    }
}
