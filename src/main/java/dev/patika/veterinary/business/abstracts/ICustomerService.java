package dev.patika.veterinary.business.abstracts;

import dev.patika.veterinary.entities.Customer;

import java.util.List;

public interface ICustomerService {

    Customer save (Customer customer);
    Customer get(long id);

    List<Customer> getAll();

    Customer update (Customer customer);

    boolean delete (long id);

}
