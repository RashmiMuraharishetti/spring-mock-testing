package com.galvanize.playground.springmocktesting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface LessonRepository extends CrudRepository<Lesson, Date> {

}
