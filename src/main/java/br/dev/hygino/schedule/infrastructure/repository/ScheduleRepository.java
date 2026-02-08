package br.dev.hygino.schedule.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.hygino.schedule.infrastructure.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByServiceAndScheduledDateTimeBetween(
            String service,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime);
}
