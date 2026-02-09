package br.dev.hygino.schedule.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.hygino.schedule.dto.RequestScheduleDto;
import br.dev.hygino.schedule.dto.ResponseScheduleDto;
import br.dev.hygino.schedule.dto.UpdateRequestScheduleDto;
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

    @GetMapping("date/{date}")
    public ResponseEntity<List<ResponseScheduleDto>> getSchedulesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(scheduleService.findByScheduledDate(date));
    }

    @PatchMapping
    public ResponseEntity<ResponseScheduleDto> modifySchedule(
            @RequestBody UpdateRequestScheduleDto dto) {
        var result = scheduleService.modifySchedule(dto);

        return ResponseEntity.ok(result);
    }
}
