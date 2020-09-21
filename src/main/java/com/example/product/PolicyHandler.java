package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler {

//    @StreamListener(Processor.INPUT)
//    public void onEventByString(@Payload String productChanged){
//        System.out.println(productChanged);
//    }
    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByObject(@Payload OrderPlaced orderPlaced){
//        System.out.println(productChanged.getEventType());
        if ("OrderPlaced".equals(orderPlaced.getEventType())) {
            Optional<Product> productById = productRepository.findById(orderPlaced.getProductId());
            Product p = productById.get();

            p.setStock(p.getStock() - orderPlaced.getQty());

            productRepository.save(p);
        }

    }
}
