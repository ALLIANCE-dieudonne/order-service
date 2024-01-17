package com.alliance.orderservice.service;

import com.alliance.orderservice.dto.OrderLineItemsDto;
import com.alliance.orderservice.dto.OrderRequest;
import com.alliance.orderservice.model.Order;
import com.alliance.orderservice.model.OrderLineItems;
import com.alliance.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  @Autowired
  private final OrderRepository orderRepository;

  @Override
  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList()
      .stream().map(this::mapToOrderRequestDto).toList();

    order.setOrderLineItemsList(orderLineItems);
    orderRepository.save(order);
  }

  private OrderLineItems mapToOrderRequestDto(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems lineItems = new OrderLineItems();
    lineItems.setQuantity(orderLineItemsDto.getQuantity());
    lineItems.setPrice(orderLineItemsDto.getPrice());
    lineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return lineItems;
  }
}
