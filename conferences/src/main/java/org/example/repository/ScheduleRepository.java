package org.example.repository;

import org.example.entity.Timetable;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Timetable, Long> {
}
