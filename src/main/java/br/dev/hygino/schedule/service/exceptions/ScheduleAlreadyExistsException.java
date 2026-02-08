package br.dev.hygino.schedule.service.exceptions;

public class ScheduleAlreadyExistsException extends RuntimeException {
    public ScheduleAlreadyExistsException(String message) {
        super(message);
    }
}
