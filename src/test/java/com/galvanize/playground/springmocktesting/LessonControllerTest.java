package com.galvanize.playground.springmocktesting;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class LessonControllerTest {

    @Mock
    LessonRepository repository;

    @Test
    public void testCreate(){
        //Setup


        Lesson lessonExpected = new Lesson();
        lessonExpected.setTitle("FirstBook");
        lessonExpected.setDeliveredOn(new Date());
        LessonController testController = new LessonController(repository);


        //Execute

        Lesson actual = testController.save(lessonExpected);

        //Assert
        Assert.assertEquals(lessonExpected, actual);


        //Teardown
    }
}
