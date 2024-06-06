package me.calebe_oliveira.stock_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEvent {
    private String status; // pending, progress, completed
    private String message;
    private Order order;

    @Override
    public String toString() {
        return "OrderEvent{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", order=" + order +
                '}';
    }
}
