package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private CartController cartController;
    @Autowired
    private UserController userController;

    @Test
    public void cart_controller_happy_path(){

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        userController.createUser(r);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(Long.valueOf(1));
        modifyCartRequest.setQuantity(5);

        final ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart cart = response.getBody();
        assertNotNull(cart);

        assertEquals(5, cart.getItems().size());
        assertEquals("test", cart.getUser().getUsername());
        assertEquals(1, cart.getId());
        assertEquals("Round Widget", cart.getItems().get(0).getName());
        assertEquals(java.math.BigDecimal.valueOf(14.95), cart.getTotal());
    }

    @Test
    public void remove_from_cart(){

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        userController.createUser(r);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(Long.valueOf(1));
        modifyCartRequest.setQuantity(5);

        cartController.addTocart(modifyCartRequest);
        modifyCartRequest.setQuantity(2);
        final ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart cart = response.getBody();
        assertNotNull(cart);

        assertEquals(3, cart.getItems().size());
        assertEquals("test", cart.getUser().getUsername());
        assertEquals(1, cart.getId());
        assertEquals("Round Widget", cart.getItems().get(0).getName());
        assertEquals(java.math.BigDecimal.valueOf(8.97), cart.getTotal());

        //removing more items than available
        modifyCartRequest.setQuantity(20);
        final ResponseEntity<Cart> response2 = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response2);
        assertEquals(200, response2.getStatusCodeValue());

        cart = response2.getBody();
        assertNotNull(cart);

        assertEquals(0, cart.getItems().size());
        assertEquals("test", cart.getUser().getUsername());

        //assertEquals(BigDecimal.ZERO, cart.getTotal());
        assertThat(BigDecimal.ZERO,  Matchers.comparesEqualTo(cart.getTotal()));


    }

    @Test
    public void invalid_remove_from_cart(){

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        userController.createUser(r);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(Long.valueOf(1));
        modifyCartRequest.setQuantity(5);

        cartController.addTocart(modifyCartRequest);
        modifyCartRequest.setUsername("test2");
         ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());


        modifyCartRequest.setUsername("test");
        //invalid id of item
        modifyCartRequest.setItemId(Long.valueOf(5));

        response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());



    }






}
