package com.pos.bookstoreorder.services;

import com.google.gson.Gson;
import com.pos.bookstoreorder.entities.Order;
import com.pos.bookstoreorder.exceptions.CollectionNotFoundException;
import com.pos.bookstoreorder.exceptions.InsufficientStockException;
import com.pos.bookstoreorder.models.Book;
import com.pos.bookstoreorder.models.Status;
import com.pos.bookstoreorder.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public List<Order> getAllOrdersByClientId(Long id) {
        log.info("[{}] -> getAllOrdersByClientId, client id: {}", this.getClass().getSimpleName(), id);

        String collectionName = String.format("client.%s", id.toString());

        orderRepository.setCollectionName(collectionName);

        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            log.error("{} does not exist", orderRepository.getCollectionName());
            throw new CollectionNotFoundException(id.toString());
        }

        return orderRepository.findAll();
    }

    public Order createOrder(Long id, List<Book> books) {
        log.info("[{}] -> createOrder, client id: {}, book list: {}",
                this.getClass().getSimpleName(), id, books);

        RestTemplate restTemplate = new RestTemplate();

        String collectionName = String.format("client.%s", id.toString());

        if (!mongoTemplate.collectionExists(orderRepository.getCollectionName())) {
            log.error("{} does not exist", orderRepository.getCollectionName());
            throw new CollectionNotFoundException(id.toString());
        }

        orderRepository.setCollectionName(collectionName);

        for (Book book : books) {
            URI uri;
            try {
                uri = new URI(String.format("http://localhost:8089/api/1.0/bookcollection/books/%s", book.getIsbn()));
            } catch (URISyntaxException uriSyntaxException) {
                log.error(uriSyntaxException.getMessage());
                throw new RuntimeException(uriSyntaxException.getMessage());
            }

            Object bookObject = restTemplate.getForObject(uri, Object.class);

            JSONObject bookJsonObject = new JSONObject(new Gson().toJson(bookObject));

            if (bookJsonObject.getInt("stock") >= book.getQuantity()) {
                JSONObject newBookJsonObject = new JSONObject();

                newBookJsonObject.put("title", bookJsonObject.getString("title"));
                newBookJsonObject.put("publishing", bookJsonObject.getString("publishing"));
                newBookJsonObject.put("publicationYear", bookJsonObject.getInt("publicationYear"));
                newBookJsonObject.put("genre", bookJsonObject.getString("genre"));
                newBookJsonObject.put("stock", bookJsonObject.getInt("stock") - book.getQuantity());
                newBookJsonObject.put("price", bookJsonObject.getDouble("price"));

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> httpEntity = new HttpEntity<>(newBookJsonObject.toString(), httpHeaders);

                restTemplate.put(uri, httpEntity);
            } else {
                throw new InsufficientStockException(book.getIsbn());
            }
        }

        Order order = Order.builder()
                .date(LocalDateTime.now())
                .items(books)
                .status(Status.INITIALIZED)
                .build();

        orderRepository.save(order);

        return order;
    }
}
