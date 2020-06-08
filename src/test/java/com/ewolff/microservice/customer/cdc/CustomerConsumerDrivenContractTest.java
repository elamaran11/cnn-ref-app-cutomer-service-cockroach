package com.ewolff.microservice.customer.cdc;

import com.ewolff.microservice.customer.Customer;
import com.ewolff.microservice.customer.CustomerApp;
import com.ewolff.microservice.customer.CustomerRepository;
import com.ewolff.microservice.customer.SpringRestDataConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CustomerApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = SpringRestDataConfig.class, loader = AnnotationConfigContextLoader.class)
//@WebIntegrationTest
public class CustomerConsumerDrivenContractTest {

	@InjectMocks
	private CustomerApp customerApp;

	@Mock
	private CustomerRepository customerRepository;

	private List<Customer> custList;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		custList = new ArrayList<>();

		doAnswer(invocation -> {
			Customer cust = (Customer)invocation.getArguments()[0];
			custList.add(cust);
			return cust;
		}).when(customerRepository).save(any(Customer.class));


	}

	@Test
	public void testConstructor() {
		CustomerApp custApp = new CustomerApp(customerRepository);

		assertNotNull(custApp);
	}

	@Test
	public void testGenerateTestData() {
		CustomerApp custApp = new CustomerApp(customerRepository);
		custApp.generateTestData();

    	verify(customerRepository, times(5)).save(any(Customer.class));
    	assertEquals(5, custList.size());
	}



}
