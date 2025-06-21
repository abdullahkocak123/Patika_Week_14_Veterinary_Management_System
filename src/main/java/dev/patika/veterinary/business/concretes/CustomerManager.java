package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.ICustomerService;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.utils.Msg;
import dev.patika.veterinary.dao.CustomerRepo;
import dev.patika.veterinary.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;


    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(long id) {
        return this.customerRepo.findById(id).orElseThrow(() ->  new NotFoundException(id));
    }

    @Override
    public List<Customer> getAll() {
        return this.customerRepo.findAll();
    }

    @Override
    public Customer update(Customer customer) {
        //check if there is a customer, if not throw message
        this.get(customer.getId());
        return this.customerRepo.save(customer);
    }

    @Override
    public boolean delete(long id) {
        //check if there is a customer, if not throw message
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

}
