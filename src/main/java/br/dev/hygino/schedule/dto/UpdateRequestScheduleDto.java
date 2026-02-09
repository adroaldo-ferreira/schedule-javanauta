package br.dev.hygino.schedule.dto;

import java.time.LocalDateTime;

public record UpdateRequestScheduleDto(
                LocalDateTime oldDate,
                LocalDateTime newDate,
                String client) {
}
