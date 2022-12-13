package com.alphadev.webflux.handler;

import com.alphadev.webflux.dao.CustomerDao;
import com.alphadev.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        Flux<Customer> customerList = dao.getCustomerList();
        return ServerResponse.ok().body(customerList, Customer.class);
    }

    public Mono<ServerResponse> findCustomers(ServerRequest request) {
        int customerId = Integer.valueOf(request.pathVariable("input"));
        Mono<Customer> customerMono = dao.getCustomerList()
                .filter(c->c.getId()==customerId)
                .next();
        return ServerResponse.ok().body(customerMono,Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest) {
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId()+":"+dto.getName());
        return ServerResponse.ok().body(saveResponse, String.class);
    }
}
