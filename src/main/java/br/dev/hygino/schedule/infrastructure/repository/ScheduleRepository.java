package br.dev.hygino.schedule.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.hygino.schedule.infrastructure.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

        Optional<Schedule> findByServiceAndScheduledDateTimeBetween(
                        String service,
                        LocalDateTime startDateTime,
                        LocalDateTime endDateTime);

        @Transactional
        void deleteByClientAndScheduledDateTime(String client, LocalDateTime scheduledDateTime);

        List<Schedule> findByScheduledDateTimeBetween(
                        LocalDateTime startDateTime,
                        LocalDateTime endDateTime);

        @Transactional(readOnly = true)
        Optional<Schedule> findByClientAndScheduledDateTime(String client, LocalDateTime scheduledDateTime);
}
