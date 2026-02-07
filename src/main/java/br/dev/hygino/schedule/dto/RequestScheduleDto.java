package br.dev.hygino.schedule.dto;

import java.time.LocalDateTime;

public record RequestScheduleDto(
        String service,
        String professional,
        LocalDateTime scheduledDateTime,
        String client,
        String contact) {
}
