package dev.patika.veterinary.api;

import dev.patika.veterinary.business.abstracts.IAvailableDateService;
import dev.patika.veterinary.business.abstracts.IDoctorService;
import dev.patika.veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;
import dev.patika.veterinary.core.utils.ResultHelper;
import dev.patika.veterinary.dto.request.available_date.AvailableDateSaveRequest;
import dev.patika.veterinary.dto.request.available_date.AvailableDateUpdateRequest;
import dev.patika.veterinary.dto.response.available_date.AvailableDateResponse;
import dev.patika.veterinary.entities.AvailableDate;
import dev.patika.veterinary.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/available-dates")
public class AvailableDateController {

    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper, IDoctorService doctorService) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);

        if (availableDateSaveRequest.getDoctorIdList() != null && !availableDateSaveRequest.getDoctorIdList().isEmpty()) {
            List<Doctor> doctorList = availableDateSaveRequest.getDoctorIdList().stream()
                    .map(this.doctorService::get)
                    .toList();
            saveAvailableDate.setDoctorList(doctorList);
        }

        this.availableDateService.save(saveAvailableDate);

        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(saveAvailableDate, AvailableDateResponse.class);
        return ResultHelper.createdData(availableDateResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class);
        return ResultHelper.successData(availableDateResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDateResponse>> getAll() {
        List<AvailableDate> availableDateList = this.availableDateService.getAll();
        List<AvailableDateResponse> availableDateResponseList = availableDateList.stream()
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class))
                .toList();

        return ResultHelper.successData(availableDateResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest, AvailableDate.class);

        if (availableDateUpdateRequest.getDoctorIdList() != null && !availableDateUpdateRequest.getDoctorIdList().isEmpty()) {
            List<Doctor> doctorList = availableDateUpdateRequest.getDoctorIdList().stream()
                    .map(this.doctorService::get)
                    .toList();
            updateAvailableDate.setDoctorList(doctorList);
        }

        this.availableDateService.update(updateAvailableDate);

        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(updateAvailableDate, AvailableDateResponse.class);
        return ResultHelper.successData(availableDateResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete (@PathVariable("id") long id){
        this.availableDateService.delete(id);
        return ResultHelper.success();
    }

}
