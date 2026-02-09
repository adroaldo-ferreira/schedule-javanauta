package br.dev.hygino.schedule.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.dev.hygino.schedule.dto.RequestScheduleDto;
import br.dev.hygino.schedule.dto.ResponseScheduleDto;
import br.dev.hygino.schedule.infrastructure.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ResponseScheduleDto toResponseScheduleDto(Schedule schedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Schedule toScheduleEntity(RequestScheduleDto requestScheduleDto);
}