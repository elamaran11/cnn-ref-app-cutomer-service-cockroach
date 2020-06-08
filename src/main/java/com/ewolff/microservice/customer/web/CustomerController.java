package com.ewolff.microservice.customer.web;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.CaptureTransaction;
import com.ewolff.microservice.customer.Customer;
import com.ewolff.microservice.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class CustomerController {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	@CaptureSpan("GetOperation")
	public ModelAndView customer(@PathVariable("id") String id) {
		return new ModelAndView("customer", "customer",
				customerRepository.findById(id).get());
	}

	@RequestMapping("/list.html")
	@CaptureTransaction(type = "Task", value = "CustomerList")
	public ModelAndView customerList() {
		return new ModelAndView("customerlist", "customers",
				customerRepository.findAll());
	}

	@RequestMapping(value = "/form.html", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("customer", "customer", new Customer());
	}

	@RequestMapping(value = "/form.html", method = RequestMethod.POST)
	@CaptureTransaction(type = "Task", value = "CustomerAdd")
	public ModelAndView post(Customer customer, HttpServletRequest httpRequest) {
		customer.setId(UUID.randomUUID().toString());
		customer = customerRepository.save(customer);
		return new ModelAndView("success");
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.PUT)
	@CaptureSpan("UpdateOperation")
	public ModelAndView put(@PathVariable("id") String id, Customer customer,
			HttpServletRequest httpRequest) {
		if(id == null || id.equalsIgnoreCase("null")) {
			customer.setId(UUID.randomUUID().toString());
		}
		customerRepository.save(customer);
		return new ModelAndView("success");
	}

	@RequestMapping(value = "/{id}.html", method = RequestMethod.DELETE)
	@CaptureTransaction(type = "Task", value = "CustomerDelete")
	public ModelAndView delete(@PathVariable("id") String id) {
		customerRepository.deleteById(id);
		return new ModelAndView("success");
	}

}
