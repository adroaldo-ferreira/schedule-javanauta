package br.dev.hygino.schedule.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.hygino.schedule.dto.RequestScheduleDto;
import br.dev.hygino.schedule.dto.ResponseScheduleDto;
import br.dev.hygino.schedule.service.ScheduleService;
import br.dev.hygino.schedule.service.exceptions.ScheduleAlreadyExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Page<ResponseScheduleDto>> getAllSchedules(Pageable pageable) {
        return ResponseEntity.ok().body(scheduleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseScheduleDto> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(scheduleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody RequestScheduleDto requestScheduleDto) {
        try {
            ResponseScheduleDto createdSchedule = scheduleService.save(requestScheduleDto);
            return ResponseEntity.status(201).body(createdSchedule);
        } catch (ScheduleAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
