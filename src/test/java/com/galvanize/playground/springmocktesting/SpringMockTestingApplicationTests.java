package com.galvanize.playground.springmocktesting;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wiremock.net.minidev.json.JSONObject;


import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringMockTestingApplicationTests {

		@Mock
		private LessonRepository mockRepo;

	@ClassRule
	public static WireMockClassRule wireMockClassRule = new WireMockClassRule(1234);
	@Rule
	public WireMockClassRule wireMockRule = wireMockClassRule;

		@Test
		public void testGetRequest(){
			//assertTrue(true);

			//setup
			ArrayList expectedResult = new ArrayList();

			LessonController lessonController = new LessonController(mockRepo);
			when(mockRepo.findAll()).thenReturn(expectedResult);

			//Execute
			ArrayList actualResult = (ArrayList) lessonController.all();

			//Assert
			assertEquals(expectedResult,actualResult);

		}
		/*@Test
		public void testPostRequest(){
			//assertTrue(true);
			//setup
			Lesson expectedResult = new Lesson();

			LessonController lessonController = new LessonController(mockRepo);
			when(mockRepo.save(expectedResult)).thenReturn(expectedResult);

			//Execute
			Lesson actualResult = lessonController.create(expectedResult);

			//Assert
			assertEquals(expectedResult,actualResult);
		}*/
		@Test
		public void testDeleteRequest(){
			//assertTrue(true);
			//setup
			Lesson lesson = new Lesson();
			lesson.setId((long) 4);

			LessonController lessonController = new LessonController(mockRepo);
			lessonController.delete(lesson);


			then(mockRepo).should(times(1)).delete(lesson);

			//Execute

			//Assert

		}

		@Test
		public void testUpdateRequest(){

			//setup
			Lesson expectedResult = new Lesson();
			expectedResult.setId((long)4);


			//Execute
			LessonController lessonController = new LessonController(mockRepo);

			when(mockRepo.save(expectedResult)).thenReturn(expectedResult);

			Lesson actualResult = lessonController.update(expectedResult);

			//Assert
			assertEquals(expectedResult,actualResult);

		}

		@Test
		public void testDummyService(){

			wireMockRule.stubFor(get(urlEqualTo("/get-lessons"))
					.withHeader("Accept", equalTo("application/json"))
					.willReturn(aResponse()
							.withStatus(200)
							.withHeader("Content-Type","application/json")
							.withBody("{" +
									"'id':1," +
									"'title':'First Book'," +
									"'deliveredOn':2012-10-10" +
									"}</response>")));

			LessonController lessonController = new LessonController();
			try {
				lessonController.getLessons();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}


			Result result = null;



		}

}
