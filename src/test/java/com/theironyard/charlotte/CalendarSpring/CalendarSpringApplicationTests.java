package com.theironyard.charlotte.CalendarSpring;

import com.theironyard.charlotte.CalendarSpring.services.EventRepo;
import com.theironyard.charlotte.CalendarSpring.services.UserRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalendarSpringApplicationTests {

    @Autowired
    UserRepo users;

    @Autowired
    EventRepo events;

    @Autowired
	WebApplicationContext wap; //build this upon boot

	MockMvc mockMvc; //used to test requests against framework. Mock class pretends to be the thing you need.

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .param("name", "TestUser")
        );

        Assert.assertTrue(users.count() == 1); //tests that after we preform the moc test, we have one users in the DB.
    }
    @Test
    public void testAddEvent() throws Exception {
        testLogin();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/create-event")
                        .param("description", "Test event")
                        .param("dateTime", LocalDateTime.now().toString())
                        .sessionAttr("userName", "TestUser") //pretend we have a session of "TestUser"
        );

        Assert.assertTrue(events.count() == 1);
    }


}
