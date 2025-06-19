package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.ICustomerService;
import dev.patika.veterinary.dao.CustomerRepo;
import dev.patika.veterinary.entities.Customer;
import org.springframework.stereotype.Service;

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
}
