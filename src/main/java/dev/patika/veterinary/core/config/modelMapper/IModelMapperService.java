package dev.patika.veterinary.core.config.modelMapper;

import ch.qos.logback.core.model.Model;
import org.modelmapper.ModelMapper;

public interface IModelMapperService {
    ModelMapper forRequest();
    ModelMapper forResponse();
}
