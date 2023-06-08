package co.com.api.compras.utils;


import lombok.Data;

import java.util.List;



@Data
public class Usuario {
    private Tarjeta tarjeta;
    private List<Producto> productos;
}
