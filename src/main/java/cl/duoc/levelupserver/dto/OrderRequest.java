package cl.duoc.levelupserver.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemDto> items; // Lista de productos a comprar
}