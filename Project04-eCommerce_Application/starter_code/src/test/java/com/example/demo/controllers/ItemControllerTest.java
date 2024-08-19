package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTest {
    @Autowired
    private ItemController itemController;


    @Test
    public void find_item_by_id_happy_path(){

        final ResponseEntity<Item> response = itemController.getItemById(Long.valueOf(1));
        final ResponseEntity<Item> response2 = itemController.getItemById(Long.valueOf(2));

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        assertNotNull(response2);
        assertEquals(200, response2.getStatusCodeValue());



        Item i1 = response.getBody();
        assertNotNull(i1);
        assertEquals("Round Widget", i1.getName());
        assertEquals(java.math.BigDecimal.valueOf(2.99), i1.getPrice());


        Item i2 = response2.getBody();
        assertNotNull(i2);
        assertEquals("Square Widget", i2.getName());
        assertEquals(java.math.BigDecimal.valueOf(1.99), i2.getPrice());


    }

    @Test
    public void find_all_items_happy_path(){


        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> items = response.getBody();

        assertEquals(items.get(0).getName(),"Round Widget");
        assertEquals(items.get(1).getName(),"Square Widget");
        assertEquals(items.size(),2);

    }


    @Test
    public void find_items_by_name(){


        final ResponseEntity<List<Item>> response = itemController.getItemsByName("not found");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        final ResponseEntity<List<Item>> response2 = itemController.getItemsByName("Round Widget");
        assertNotNull(response2);
        assertEquals(200, response2.getStatusCodeValue());
        assertEquals(response2.getBody().get(0).getName(), "Round Widget");
        assertEquals(response2.getBody().size(), 1);


        final ResponseEntity<List<Item>> response3 = itemController.getItemsByName("Square Widget");
        assertNotNull(response3);
        assertEquals(200, response3.getStatusCodeValue());
        assertEquals(response3.getBody().get(0).getName(), "Square Widget");
        assertEquals(response2.getBody().size(), 1);









    }






}
