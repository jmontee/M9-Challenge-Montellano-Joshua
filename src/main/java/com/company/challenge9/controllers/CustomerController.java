package com.company.challenge9.controllers;

import com.company.challenge9.model.Customer;
import com.company.challenge9.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository repo;

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody Customer customer){
        return repo.save(customer);
    }

    @PutMapping("/customer/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable Integer id){
        Optional<Customer> oldCustomer = repo.findById(id);

        //checks to make sure the oldCustomer is present
        //and updates it with the new data from the customer in
        //the request body
        if(oldCustomer.isPresent()){
            oldCustomer.get()
                    .setFirstName(customer.getFirstName())
                    .setLastName(customer.getLastName())
                    .setEmail(customer.getEmail())
                    .setCompany(customer.getCompany())
                    .setPhone(customer.getPhone())
                    .setAddress1(customer.getAddress1())
                    .setAddress2(customer.getAddress2())
                    .setCity(customer.getCity())
                    .setState(customer.getState())
                    .setPostalCode(customer.getPostalCode())
                    .setCountry(customer.getCountry());
            repo.save(oldCustomer.get());
        }
    }

    @GetMapping("/customer/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable Integer id){
        Optional<Customer> customer = repo.findById(id);

        if(customer.isPresent()){
            return customer.get();
        }
        return null;
    }

    @GetMapping("/customer/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getCustomerByState(@PathVariable String state){
        return repo.findByState(state);
    }

    @DeleteMapping("/customer/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerById(@PathVariable Integer id){
        repo.deleteById(id);
    }

}
