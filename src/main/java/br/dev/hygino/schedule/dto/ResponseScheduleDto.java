package br.dev.hygino.schedule.dto;

import java.time.LocalDateTime;

import br.dev.hygino.schedule.infrastructure.entity.Schedule;

public record ResponseScheduleDto(
        Long id,
        String service,
        String professional,
        LocalDateTime scheduledDateTime,
        String client,
        String contact,
        LocalDateTime createdAt) {

    public ResponseScheduleDto(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getService(),
                schedule.getProfessional(),
                schedule.getScheduledDateTime(),
                schedule.getClient(),
                schedule.getContact(),
                schedule.getCreatedAt());
    }
}
