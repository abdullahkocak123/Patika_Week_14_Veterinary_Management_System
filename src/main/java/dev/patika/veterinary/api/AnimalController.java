package dev.patika.veterinary.api;

import dev.patika.veterinary.business.abstracts.IAnimalService;
import dev.patika.veterinary.business.abstracts.ICustomerService;
import dev.patika.veterinary.business.abstracts.IVaccineService;
import dev.patika.veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;
import dev.patika.veterinary.core.utils.ResultHelper;
import dev.patika.veterinary.dto.request.animal.AnimalSaveRequest;
import dev.patika.veterinary.dto.request.animal.AnimalUpdateRequest;
import dev.patika.veterinary.dto.response.animal.AnimalResponse;
import dev.patika.veterinary.dto.response.customer.CustomerResponse;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Customer;
import dev.patika.veterinary.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {

    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;
    private final IVaccineService vaccineService;

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService, IVaccineService vaccineService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);

        if (animalSaveRequest.getVaccineIdList() != null && !animalSaveRequest.getVaccineIdList().isEmpty()) {
            List<Vaccine> vaccineList = animalSaveRequest.getVaccineIdList().stream()
                    .map(this.vaccineService::get)
                    .toList();
            saveAnimal.setVaccineList(vaccineList);
        }
            this.animalService.save(saveAnimal);

            AnimalResponse animalResponse = this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class);
            return ResultHelper.createdData(animalResponse);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") long id) {
        Animal animal = this.animalService.get(id);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal, AnimalResponse.class);
        return ResultHelper.successData(animalResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAll() {
        List<Animal> animalList = this.animalService.getAll();
        List<AnimalResponse> animalResponseList = animalList.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .toList();

        return ResultHelper.successData(animalResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest, Animal.class);

        Customer customer = this.customerService.get(animalUpdateRequest.getCustomerId());
        updateAnimal.setCustomer(customer);

        if (animalUpdateRequest.getVaccineIdList() != null && !animalUpdateRequest.getVaccineIdList().isEmpty()) {
            List<Vaccine> vaccineList = animalUpdateRequest.getVaccineIdList().stream()
                    .map(this.vaccineService::get)
                    .toList();
            updateAnimal.setVaccineList(vaccineList);
        } else {
            updateAnimal.setVaccineList(null); // liste boşsa ilişkileri sıfırla
        }

        this.animalService.update(updateAnimal);

        AnimalResponse animalResponse = this.modelMapper.forResponse().map(updateAnimal, AnimalResponse.class);
        return ResultHelper.successData(animalResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete (@PathVariable("id") long id){
        this.animalService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/filter-by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> filterByName(@PathVariable("name") String name) {
        List<Animal> filteredAnimals = this.animalService.getByName(name);
        List<AnimalResponse> responseList = filteredAnimals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .toList();
        return ResultHelper.successData(responseList);
    }


    @GetMapping("/by-customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getByCustomer(@PathVariable("customerId") Long customerId) {
        List<Animal> filteredAnimals = this.animalService.getByCustomerId(customerId);
        List<AnimalResponse> responseList = filteredAnimals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .toList();
        return ResultHelper.successData(responseList);
    }



}

