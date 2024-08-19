package com.example.demo.controllers;

import com.example.demo.SareetaApplication;
import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;


    /*
    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }
*/

    @Test
    public void create_user_happy_path(){

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(1, u.getId());
        assertEquals("test", u.getUsername());
    }

    @Test
    public void passwordNotMeetingCriteria(){
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("test");
        r.setConfirmPassword("test");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

    }


    @Test
    public void returnUserById(){
        CreateUserRequest u1 = new CreateUserRequest();
        u1.setUsername("test1");
        u1.setPassword("testtest");
        u1.setConfirmPassword("testtest");

        CreateUserRequest u2 = new CreateUserRequest();
        u2.setUsername("test2");
        u2.setPassword("testtest");
        u2.setConfirmPassword("testtest");

        CreateUserRequest u3 = new CreateUserRequest();
        u3.setUsername("test3");
        u3.setPassword("testtest");
        u3.setConfirmPassword("testtest");


        userController.createUser(u1);
        userController.createUser(u2);
        userController.createUser(u3);

        assertEquals("test1", userController.findById(Long.valueOf(1)).getBody().getUsername());
        assertEquals("test2", userController.findById(Long.valueOf(2)).getBody().getUsername());
        assertEquals("test3", userController.findById(Long.valueOf(3)).getBody().getUsername());
    }


    @Test
    public void returnUserByName(){

        CreateUserRequest u1 = new CreateUserRequest();
        u1.setUsername("test1");
        u1.setPassword("testtest");
        u1.setConfirmPassword("testtest");

        CreateUserRequest u2 = new CreateUserRequest();
        u2.setUsername("test2");
        u2.setPassword("testtest");
        u2.setConfirmPassword("testtest");

        CreateUserRequest u3 = new CreateUserRequest();
        u3.setUsername("test3");
        u3.setPassword("testtest");
        u3.setConfirmPassword("testtest");


        userController.createUser(u1);
        userController.createUser(u2);
        userController.createUser(u3);

        assertEquals("test1", userController.findByUserName("test1").getBody().getUsername());
        assertEquals("test2", userController.findByUserName("test2").getBody().getUsername());
        assertEquals("test3", userController.findByUserName("test3").getBody().getUsername());

    }






}

