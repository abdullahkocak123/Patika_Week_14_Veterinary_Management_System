package dev.patika.veterinary.api;

import dev.patika.veterinary.business.abstracts.ICustomerService;
import dev.patika.veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;
import dev.patika.veterinary.core.utils.ResultHelper;
import dev.patika.veterinary.dto.request.customer.CustomerSaveRequest;
import dev.patika.veterinary.dto.request.customer.CustomerUpdateRequest;
import dev.patika.veterinary.dto.response.customer.CustomerResponse;
import dev.patika.veterinary.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    public CustomerController(ICustomerService customerService, IModelMapperService modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        this.customerService.save(saveCustomer);

        CustomerResponse customerResponse = this.modelMapper.forResponse().map(saveCustomer, CustomerResponse.class);
        return ResultHelper.createdData(customerResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") long id) {
        Customer customer = this.customerService.get(id);
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(customer, CustomerResponse.class);
        return ResultHelper.successData(customerResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getAll() {
        List<Customer> customerList = this.customerService.getAll();
        List<CustomerResponse> customerResponseList = customerList.stream()
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                .toList();

        return ResultHelper.successData(customerResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        Customer updateCustomer = this.modelMapper.forRequest().map(customerUpdateRequest, Customer.class);
        this.customerService.update(updateCustomer);

        CustomerResponse customerResponse = this.modelMapper.forResponse().map(updateCustomer, CustomerResponse.class);
        return ResultHelper.successData(customerResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete (@PathVariable("id") long id){
        this.customerService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/filter-by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> filterByName(@PathVariable("name") String name) {
        List<Customer> filteredCustomers = this.customerService.getByName(name);
        List<CustomerResponse> responseList = filteredCustomers.stream()
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                .toList();
        return ResultHelper.successData(responseList);
    }

}
