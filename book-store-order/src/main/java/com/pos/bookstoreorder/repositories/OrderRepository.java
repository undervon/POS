package com.pos.bookstoreorder.repositories;

import com.pos.bookstoreorder.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCustom {

}
