package br.dev.hygino.schedule.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.hygino.schedule.dto.RequestScheduleDto;
import br.dev.hygino.schedule.dto.ResponseScheduleDto;
import br.dev.hygino.schedule.infrastructure.entity.Schedule;
import br.dev.hygino.schedule.infrastructure.repository.ScheduleRepository;
import br.dev.hygino.schedule.mappers.ScheduleMapper;
import br.dev.hygino.schedule.service.exceptions.ScheduleAlreadyExistsException;
import br.dev.hygino.schedule.service.exceptions.ScheduleNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Transactional(readOnly = true)
    public Page<ResponseScheduleDto> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable)
                .map(scheduleMapper::toResponseScheduleDto);
    }

    @Transactional(readOnly = true)
    public ResponseScheduleDto findById(Long id) {
        return scheduleRepository.findById(id)
                .map(ResponseScheduleDto::new)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with id: " + id));
    }

    @Transactional
    public ResponseScheduleDto save(RequestScheduleDto requestScheduleDto) {
        LocalDateTime startDateTime = requestScheduleDto.scheduledDateTime();
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        Optional<Schedule> findSchedulesByProfessional = scheduleRepository
                .findByServiceAndScheduledDateTimeBetween(
                        requestScheduleDto.service(),
                        startDateTime,
                        endDateTime);

        if (findSchedulesByProfessional.isPresent()) {
            throw new ScheduleAlreadyExistsException("Schedule already exists for the given service and time range.");
        }

        Schedule schedule = scheduleMapper.toScheduleEntity(requestScheduleDto);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toResponseScheduleDto(schedule);
    }

    public void removeSchedule(String client, LocalDateTime scheduledDateTime) {
        scheduleRepository.deleteByClientAndScheduledDateTime(client, scheduledDateTime);
    }

    @Transactional(readOnly = true)
    public List<ResponseScheduleDto> findByScheduledDate(LocalDate date) {
        final LocalDateTime start = date.atStartOfDay();
        final LocalDateTime end = date.atTime(23, 59, 59);

        return scheduleRepository.findByScheduledDateTimeBetween(start, end)
                .stream()
                .map(scheduleMapper::toResponseScheduleDto)
                .toList();
    }
}
