package dev.patika.veterinary.business.abstracts;

import dev.patika.veterinary.entities.AvailableDate;
import dev.patika.veterinary.entities.Customer;

import java.util.List;

public interface IAvailableDateService {

    AvailableDate save (AvailableDate availableDate);

    AvailableDate get(long id);

    List<AvailableDate> getAll();

    AvailableDate update (AvailableDate availableDate);

    boolean delete (long id);
}
