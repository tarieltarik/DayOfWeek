package com.example.DayOfTheWeek.repo;

import com.example.DayOfTheWeek.model.Day;
import org.springframework.data.repository.CrudRepository;

public interface DayRepository extends CrudRepository<Day,Long> {
}
