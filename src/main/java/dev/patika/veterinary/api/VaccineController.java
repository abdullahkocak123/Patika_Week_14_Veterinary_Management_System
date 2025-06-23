package dev.patika.veterinary.api;

import dev.patika.veterinary.business.abstracts.IVaccineService;
import dev.patika.veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;
import dev.patika.veterinary.core.utils.ResultHelper;
import dev.patika.veterinary.dao.AnimalRepo;
import dev.patika.veterinary.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.veterinary.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.veterinary.dto.response.animal.AnimalResponse;
import dev.patika.veterinary.dto.response.vaccine.VaccineResponse;
import dev.patika.veterinary.dto.response.vaccine.VaccineWithAnimalResponse;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

        import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {

    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;
    private final AnimalRepo animalRepo;

    public VaccineController(IVaccineService vaccineService, IModelMapperService modelMapper, AnimalRepo animalRepo) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
        this.animalRepo = animalRepo;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {

        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);

        List<Animal> animalList = vaccineSaveRequest.getAnimalIdList().stream()
                .map(animalId -> animalRepo.findById(animalId)
                        .orElseThrow(() -> new NotFoundException(animalId)))
                .toList();
        saveVaccine.setAnimalList(animalList);

        this.vaccineService.save(saveVaccine);

        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class);
        return ResultHelper.createdData(vaccineResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") long id) {
        Vaccine vaccine = this.vaccineService.get(id);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.successData(vaccineResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getAll() {
        List<Vaccine> vaccineList = this.vaccineService.getAll();
        List<VaccineResponse> vaccineResponseList = vaccineList.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .toList();

        return ResultHelper.successData(vaccineResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        this.vaccineService.update(updateVaccine);

        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class);
        return ResultHelper.successData(vaccineResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete (@PathVariable("id") long id){
        this.vaccineService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/expiring-between/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineWithAnimalResponse>> getVaccinesExpiringBetween(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Vaccine> vaccines = this.vaccineService.getVaccinesByProtectionFinishDateBetween(startDate, endDate);

        List<VaccineWithAnimalResponse> responseList = vaccines.stream()
                .map(vaccine -> {
                    VaccineWithAnimalResponse response = this.modelMapper.forResponse().map(vaccine, VaccineWithAnimalResponse.class);
                    List<AnimalResponse> animals = vaccine.getAnimalList().stream()
                            .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                            .toList();
                    response.setAnimalList(animals);
                    return response;
                })
                .toList();

        return ResultHelper.successData(responseList);
    }

    @GetMapping("/by-animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable Long animalId) {
        List<Vaccine> vaccines = this.vaccineService.getVaccinesByAnimalId(animalId);

        List<VaccineResponse> responseList = vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                        .toList();

        return ResultHelper.successData(responseList);
    }


}
