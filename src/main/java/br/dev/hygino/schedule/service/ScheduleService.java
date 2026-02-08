package br.dev.hygino.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.hygino.schedule.dto.ResponseScheduleDto;
import br.dev.hygino.schedule.infrastructure.repository.ScheduleRepository;
import br.dev.hygino.schedule.mappers.ScheduleMapper;
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
}
