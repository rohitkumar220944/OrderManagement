package com.hotwax.service;

import com.hotwax.entity.OrderHeader;
import com.hotwax.entity.OrderItem;
import com.hotwax.entity.Product;
import com.hotwax.repository.OrderItemRepository;
import com.hotwax.repository.OrderRepository;
import com.hotwax.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemService(OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderItem addOrderItem(Integer orderId, OrderItem orderItem) {
        if (orderItem.getProduct() == null || orderItem.getProduct().getProductId() == null) {
            throw new RuntimeException("Product ID is required");
        }

        OrderHeader order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        Product product = productRepository.findById(orderItem.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orderItem.setOrderHeader(order);
        orderItem.setProduct(product);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public OrderItem updateOrderItem(Integer orderId, Integer orderItemSeqId, OrderItem updatedItem) {
        OrderItem existingItem = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        if (!existingItem.getOrderHeader().getOrderId().equals(orderId)) {
            throw new RuntimeException("Order item does not belong to this order");
        }

        if (updatedItem.getProduct() != null && updatedItem.getProduct().getProductId() != null) {
            Product product = productRepository.findById(updatedItem.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existingItem.setProduct(product);
        }

        if (updatedItem.getQuantity() != null) {
            existingItem.setQuantity(updatedItem.getQuantity());
        }

        if (updatedItem.getStatus() != null) {
            existingItem.setStatus(updatedItem.getStatus());
        }

        return orderItemRepository.save(existingItem);
    }

    @Transactional
    public void deleteOrderItem(Integer orderId, Integer orderItemSeqId) {
        OrderItem item = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        if (!item.getOrderHeader().getOrderId().equals(orderId)) {
            throw new RuntimeException("Order item does not belong to this order");
        }

        orderItemRepository.delete(item);
    }
}