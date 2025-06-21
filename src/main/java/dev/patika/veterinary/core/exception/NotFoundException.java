package dev.patika.veterinary.core.exception;

public class NotFoundException extends RuntimeException{

    private final Long id;

    public NotFoundException(Long id) {
        super(id+" id'li kayıt sistemde bulunamadı");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
