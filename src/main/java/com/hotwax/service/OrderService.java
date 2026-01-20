package com.hotwax.service;


import com.hotwax.entity.ContactMech;
import com.hotwax.entity.Customer;
import com.hotwax.entity.OrderHeader;
import com.hotwax.entity.OrderItem;
import com.hotwax.entity.Product;
import com.hotwax.repository.ContactMechRepository;
import com.hotwax.repository.CustomerRepository;
import com.hotwax.repository.OrderRepository;
import com.hotwax.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ContactMechRepository contactMechRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ContactMechRepository contactMechRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.contactMechRepository = contactMechRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderHeader createOrder(OrderHeader order) {

        Customer customer = customerRepository.findById(order.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ContactMech shipping = contactMechRepository.findById(
                order.getShippingContactMech().getContactMechId())
                .orElseThrow(() -> new RuntimeException("Shipping contact not found"));

        ContactMech billing = contactMechRepository.findById(
                order.getBillingContactMech().getContactMechId())
                .orElseThrow(() -> new RuntimeException("Billing contact not found"));

        order.setCustomer(customer);
        order.setShippingContactMech(shipping);
        order.setBillingContactMech(billing);

        // Link each item to this order and resolve product
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                if (item.getProduct() == null || item.getProduct().getProductId() == null) {
                    throw new RuntimeException("Product ID is required for each order item");
                }
                Product product = productRepository.findById(item.getProduct().getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                item.setProduct(product);
                item.setOrderHeader(order); // critical: sets order_id
            }
        }

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<OrderHeader> getOrderById(Integer orderId) {
        Optional<OrderHeader> orderOpt = orderRepository.findById(orderId);
        orderOpt.ifPresent(order -> {
            order.getCustomer().getFirstName();
            order.getShippingContactMech().getStreetAddress();
            order.getBillingContactMech().getStreetAddress();
            order.getOrderItems().forEach(item -> item.getProduct().getProductName());
        });
        return orderOpt;
    }

    @Transactional
    public OrderHeader updateOrder(Integer orderId, OrderHeader updatedOrder) {
        OrderHeader existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (updatedOrder.getOrderDate() != null) {
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
        }
        if (updatedOrder.getShippingContactMech() != null) {
            ContactMech shipping = contactMechRepository.findById(
                    updatedOrder.getShippingContactMech().getContactMechId())
                    .orElseThrow(() -> new RuntimeException("Shipping contact not found"));
            existingOrder.setShippingContactMech(shipping);
        }
        if (updatedOrder.getBillingContactMech() != null) {
            ContactMech billing = contactMechRepository.findById(
                    updatedOrder.getBillingContactMech().getContactMechId())
                    .orElseThrow(() -> new RuntimeException("Billing contact not found"));
            existingOrder.setBillingContactMech(billing);
        }
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrder(Integer orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }
}