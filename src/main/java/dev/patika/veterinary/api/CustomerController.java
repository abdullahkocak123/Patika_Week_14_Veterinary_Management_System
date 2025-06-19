package dev.patika.veterinary.api;

import dev.patika.veterinary.business.abstracts.ICustomerService;
import dev.patika.veterinary.entities.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody Customer customer){
        return this.customerService.save(customer);
    }
}
