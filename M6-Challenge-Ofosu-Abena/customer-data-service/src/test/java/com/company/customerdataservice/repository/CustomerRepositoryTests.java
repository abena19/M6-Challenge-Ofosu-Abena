package com.company.customerdataservice.repository;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    CustomerRepository customerRepo;

    @BeforeEach
    public void setUp() throws Exception {
        customerRepo.deleteAll();
    }

    @Test
    public void addCustomer() {
        //Arrange...
        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Smith");
        customer.setPhone("111-222-3456");
        customer.setCompany("BigCo");
        customer.setState("New York");

        //Act...
        customer = customerRepo.save(customer);

        //Assert...
        Optional<Customer> customer1 = customerRepo.findById(customer.getId());

        assertEquals(customer1.get(), customer);
    }

    @Test
    public void getAllCustomers() {
        //Arrange...

        //Act...
        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Smith");
        customer.setPhone("111-222-3456");
        customer.setCompany("BigCo");
        customer.setState("New York");

        customerRepo.save(customer);

        Customer customer1 = new Customer();
        customer1.setFirstName("Bob");
        customer1.setLastName("Marley");
        customer1.setPhone("222-333-4567");
        customer1.setCompany("Independent");
        customer1.setState("California");

        customerRepo.save(customer1);

        List<Customer> customerList = customerRepo.findAll();

        //Assert...
        assertEquals(2, customerList.size());
    }

    @Test
    public void updateCustomer() {
        //Arrange...
        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Smith");
        customer.setPhone("111-222-3456");
        customer.setCompany("BigCo");
        customer.setState("New York");

        customerRepo.save(customer);

        //Act...
        customer.setFirstName("UPDATED");

        customerRepo.save(customer);

        //Assert...
        Optional<Customer> customer1 = customerRepo.findById(customer.getId());

        assertEquals(customer1.get(), customer);
    }

    @Test
    public void deleteCustomer() {
        //Arrange...
        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Smith");
        customer.setPhone("111-222-3456");
        customer.setCompany("BigCo");
        customer.setState("New York");

        customerRepo.save(customer);

        //Act...
        customerRepo.deleteById(customer.getId());

        //Assert...
        Optional<Customer> customer1 = customerRepo.findById(customer.getId());
        assertFalse(customer1.isPresent());
    }

    @Test
    public void findCustomersByState() {
        //Arrange...

        //Act...
        Customer customer1 = new Customer();
        customer1.setFirstName("Joe");
        customer1.setLastName("Smith");
        customer1.setPhone("111-222-3456");
        customer1.setCompany("BigCo");
        customer1.setState("New York");
        customerRepo.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Bob");
        customer2.setLastName("Marley");
        customer2.setPhone("222-333-4567");
        customer2.setCompany("Independent");
        customer2.setState("California");
        customerRepo.save(customer2);

        Customer customer3 = new Customer();
        customer3.setFirstName("Alice");
        customer3.setLastName("Williams");
        customer3.setPhone("333-444-5678");
        customer3.setCompany("SmallCo");
        customer3.setState("New York");
        customerRepo.save(customer3);

        List<Customer> customersInNewYork = customerRepo.findByState("New York");

        //Assert...
        assertEquals(2, customersInNewYork.size());
    }
}