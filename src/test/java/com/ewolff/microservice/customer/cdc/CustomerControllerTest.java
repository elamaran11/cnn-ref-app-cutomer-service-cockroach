package com.ewolff.microservice.customer.cdc;

import com.ewolff.microservice.customer.Customer;
import com.ewolff.microservice.customer.CustomerRepository;
import com.ewolff.microservice.customer.SpringRestDataConfig;
import com.ewolff.microservice.customer.web.CustomerController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Customer test class.
 *
 * @author chakrav
 * @since 2019-08-20
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringRestDataConfig.class, loader = AnnotationConfigContextLoader.class)
public class CustomerControllerTest {
    @Mock
    private CustomerRepository customerRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerRepository))
                .build();

        when(customerRepository.findById(anyString())).thenReturn(Optional.of(dummyCustomer(true)));
        when(customerRepository.findAll()).thenReturn(Arrays.asList(dummyCustomer(true)));
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get("/1.html")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("customer"));
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/list.html")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("customerlist"));
    }

    @Test
    public void testForm() throws Exception {
        mockMvc.perform(get("/form.html")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("customer"));
    }

    @Test
    public void testFormPost() throws Exception {
        mockMvc.perform(post("/form.html")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testFindByIdPut() throws Exception {
        mockMvc.perform(put("/1.html")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    @Test
    public void testFindByIdDelete() throws Exception {
        mockMvc.perform(delete("/1.html")
                .accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("success"));
    }

    private Customer dummyCustomer(final boolean flag) {
        Customer cust =  new Customer();
        cust.setId("1");
        cust.setCity("test city");
        cust.setEmail("testemail@keybank.com");
        cust.setFirstname("testfirstname");
        cust.setName("test name");
        cust.setStreet("test street");

        return cust;
    }

}
