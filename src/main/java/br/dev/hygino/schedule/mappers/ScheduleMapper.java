package br.dev.hygino.schedule.mappers;

import br.dev.hygino.schedule.dto.ResponseScheduleDto;
import br.dev.hygino.schedule.infrastructure.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ResponseScheduleDto toResponseScheduleDto(Schedule schedule);
}
