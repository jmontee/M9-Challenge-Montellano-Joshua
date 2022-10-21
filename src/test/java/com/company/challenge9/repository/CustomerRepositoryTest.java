package com.company.challenge9.repository;

import com.company.challenge9.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repo;

    @Before
    public void setUp() throws Exception{
        repo.deleteAll();
    }


    @Test
    public void getCustomerByState(){
        Customer customer = new Customer();
        customer.setFirstName("James");
        customer.setLastName("Smith");
        customer.setState("Arizona");

        repo.save(customer);

        Customer customer2 = new Customer();
        customer2.setState("Arizona");
        customer2.setFirstName("Luke");
        customer2.setLastName("Jones");

        repo.save(customer2);
        Customer customer3 = new Customer();
        customer3.setState("California");
        customer3.setFirstName("Loid");
        customer3.setLastName("Forger");
        repo.save(customer3);

        List<Customer> customers = repo.findByState("Arizona");
        assertEquals(2, customers.size());


    }

    @Test
    public void updateCustomer(){
        Customer customer = new Customer().setFirstName("Josh");
        customer = repo.save(customer);
        Integer id = customer.getId();
        Optional<Customer> foundCustomer =  repo.findById(id);
        if (foundCustomer.isPresent()){
            foundCustomer.get().setFirstName("Anakin");
            repo.save(foundCustomer.get());
        }

        Optional<Customer> foundCustomer2 =  repo.findById(id);
        if(foundCustomer.isPresent()){
            assertEquals("Anakin" ,foundCustomer2.get().getFirstName());
        }else{
            assertEquals("Anakin" ,null);
        }

    }

    @Test
    public void deleteCustomer(){
        Customer customer = new Customer();
        customer = repo.save(customer);
        Integer id = customer.getId();
        Optional<Customer> savedCustomer = repo.findById(id);
        assertEquals(customer, savedCustomer.get());

        repo.deleteById(id);
        Optional<Customer> deletedCustomer = repo.findById(id);

        assertFalse(deletedCustomer.isPresent());

    }

    @Test
    public void getCustomerByIdAndAddCustomer(){
        Customer customer = new Customer().setFirstName("Gandalf");

        Customer customer2 = repo.save(customer);
        Integer id = customer2.getId();
        Optional<Customer> savedCustomer = repo.findById(id);
        assertEquals(customer, savedCustomer.get());

    }

}
