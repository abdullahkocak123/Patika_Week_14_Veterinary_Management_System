package dev.patika.veterinary.api;

import dev.patika.veterinary.business.abstracts.IAnimalService;
import dev.patika.veterinary.business.abstracts.IAppointmentService;
import dev.patika.veterinary.business.abstracts.IDoctorService;
import dev.patika.veterinary.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinary.core.result.Result;
import dev.patika.veterinary.core.result.ResultData;
import dev.patika.veterinary.core.utils.ResultHelper;
import dev.patika.veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.veterinary.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.veterinary.dto.response.appointment.AppointmentResponse;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;
    private final IDoctorService doctorService;

    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper, IAnimalService animalService, IDoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        Appointment saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);

        Animal animal = this.animalService.get(appointmentSaveRequest.getAnimalId());
        saveAppointment.setAnimal(animal);

        Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctorId());

        saveAppointment.setDoctor(doctor);

        this.appointmentService.save(saveAppointment);

        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class);
        return ResultHelper.createdData(appointmentResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") long id) {
        Appointment appointment = this.appointmentService.get(id);
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment, AppointmentResponse.class);
        return ResultHelper.successData(appointmentResponse);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAll() {
        List<Appointment> appointmentList = this.appointmentService.getAll();
        List<AppointmentResponse> appointmentResponseList = appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .toList();

        return ResultHelper.successData(appointmentResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class);

        Animal animal = this.animalService.get(appointmentUpdateRequest.getAnimalId());
        updateAppointment.setAnimal(animal);

        Doctor doctor = this.doctorService.get(appointmentUpdateRequest.getDoctorId());
        updateAppointment.setDoctor(doctor);

        this.appointmentService.update(updateAppointment);

        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(updateAppointment, AppointmentResponse.class);
        return ResultHelper.successData(appointmentResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete (@PathVariable("id") long id){
        this.appointmentService.delete(id);
        return ResultHelper.success();
    }

    @GetMapping("/filter-by-date-range-and-doctor/{doctorId}/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRangeAndDoctor(
            @PathVariable("doctorId") Long doctorId,
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<Appointment> appointments = this.appointmentService.getAppointmentsByDateRangeAndDoctor(startDate, endDate, doctorId);

        List<AppointmentResponse> responseList = appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .toList();

        return ResultHelper.successData(responseList);
    }

    @GetMapping("/filter-by-date-range-and-animal/{animalId}/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateRangeAndAnimal(
            @PathVariable("animalId") Long animalId,
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<Appointment> appointments = this.appointmentService.getAppointmentsByDateRangeAndAnimal(startDate, endDate, animalId);

        List<AppointmentResponse> responseList = appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .toList();

        return ResultHelper.successData(responseList);
    }


}
