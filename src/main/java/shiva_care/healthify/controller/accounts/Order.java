package shiva_care.healthify.controller.accounts;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shiva_care.healthify.kafkaProducer.OrderProducer;

@RestController
@RequestMapping("/order")
public class Order {
   private final OrderProducer orderProducer;

    public Order(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam String message){
        orderProducer.sendOrder(message);
        return "order sent successfully";
    }
}
