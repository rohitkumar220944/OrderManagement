package com.hotwax.controller;

import com.hotwax.entity.OrderItem;
import com.hotwax.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{order_id}/items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(
            @PathVariable("order_id") Integer orderId,
            @Valid @RequestBody OrderItem orderItem) {
        OrderItem createdItem = orderItemService.addOrderItem(orderId, orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{order_item_seq_id}")
    public ResponseEntity<OrderItem> updateOrderItem(
            @PathVariable("order_id") Integer orderId,
            @PathVariable("order_item_seq_id") Integer orderItemSeqId,
            @Valid @RequestBody OrderItem orderItem) {
        OrderItem updatedItem = orderItemService.updateOrderItem(orderId, orderItemSeqId, orderItem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{order_item_seq_id}")
    public ResponseEntity<Void> deleteOrderItem(
            @PathVariable("order_id") Integer orderId,
            @PathVariable("order_item_seq_id") Integer orderItemSeqId) {
        orderItemService.deleteOrderItem(orderId, orderItemSeqId);
        return ResponseEntity.noContent().build();
    }
}