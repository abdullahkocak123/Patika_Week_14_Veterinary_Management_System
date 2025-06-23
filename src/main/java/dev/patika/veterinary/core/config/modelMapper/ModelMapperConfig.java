package dev.patika.veterinary.core.config.modelMapper;

import dev.patika.veterinary.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.veterinary.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.veterinary.dto.request.available_date.AvailableDateSaveRequest;
import dev.patika.veterinary.dto.request.available_date.AvailableDateUpdateRequest;
import dev.patika.veterinary.dto.request.doctor.DoctorSaveRequest;
import dev.patika.veterinary.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.veterinary.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.AvailableDate;
import dev.patika.veterinary.entities.Doctor;
import dev.patika.veterinary.entities.Vaccine;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        // to skip null's so that modelmapper should not set other id's when saving
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // En doğru eşleşmeyi yapması için strict mod
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // VaccineSaveRequest -> Vaccine mapping should not include animalList setting!
        modelMapper.typeMap(VaccineSaveRequest.class, Vaccine.class)
                .addMappings(mapper -> mapper.skip(Vaccine::setAnimalList));

        modelMapper.typeMap(DoctorSaveRequest.class, Doctor.class)
                .addMappings(mapper -> mapper.skip(Doctor::setAppointmentList))
                .addMappings(mapper -> mapper.skip(Doctor::setAvailableDateList));

        modelMapper.typeMap(DoctorUpdateRequest.class, Doctor.class)
                .addMappings(mapper -> mapper.skip(Doctor::setAppointmentList))
                .addMappings(mapper -> mapper.skip(Doctor::setAvailableDateList));


        modelMapper.typeMap(AvailableDateSaveRequest.class, AvailableDate.class)
                .addMappings(mapper -> mapper.skip(AvailableDate::setDoctorList));

        modelMapper.typeMap(AvailableDateUpdateRequest.class, AvailableDate.class)
                .addMappings(mapper -> mapper.skip(AvailableDate::setDoctorList));

        modelMapper.typeMap(AppointmentSaveRequest.class, Appointment.class)
                .addMappings(mapper -> {
                    mapper.skip(Appointment::setAnimal);
                    mapper.skip(Appointment::setDoctor);
                });

        modelMapper.typeMap(AppointmentUpdateRequest.class, Appointment.class)
                .addMappings(mapper -> {
                    mapper.skip(Appointment::setAnimal);
                    mapper.skip(Appointment::setDoctor);
                });


        return modelMapper;
    }
}
