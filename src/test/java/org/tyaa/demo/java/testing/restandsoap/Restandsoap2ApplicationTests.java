package org.tyaa.demo.java.testing.restandsoap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.tyaa.demo.java.testing.restandsoap.models.author.AuthorDto;
import org.tyaa.demo.java.testing.restandsoap.models.author.nested.BirthDto;
import org.tyaa.demo.java.testing.restandsoap.models.author.nested.NameDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

// режим создания одиночного экземпляра класса тестов для всех кейсов
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// активация контейнера выполнения кейсов согласно пользовательской нумерации
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Restandsoap2ApplicationTests {

	private static final String BASE_URL = "http://localhost:8080/api/library";
	private static final String CORRECT_AUTHOR_NAME = "Alexander";
	private static final String CORRECT_AUTHOR_LAST_NAME = "Pushkin";

	// GET

	@Test
	@Order(3)
	void givenGetAllAuthorsRequest_whenResponse_thenAuthorListIsNotEmpty() {
		Response response =
			given().param("pagination", "false").when().get(BASE_URL + "/authors");
		assertEquals(200, response.getStatusCode());
		List<AuthorDto> authors = response.jsonPath().getList("$", AuthorDto.class);
		assertNotEquals(0, authors.size());
		authors.forEach(
			a -> System.out.printf(
				"%d %s %s %s\n",
				a.getAuthorId(),
				a.getAuthorName().getFirst(),
				a.getAuthorName().getSecond(),
				a.getAuthorDescription()
			)
		);
	}

	@Test
	@Order(4)
	void givenGetAllAuthorsRequest_whenRequestedPageTwoAndSizeFiveOrderTypeDescAndSortByWrongParamValueAndResponse_thenRequestFailed() {
		final String WRONG_PARAM_VALUE = "authorName.secondd";
			given()
				.param("pagination", "true")
				.param("orderType", "desc")
				.param("sortBy", WRONG_PARAM_VALUE)
				.param("page", "2")
				.param("size", "5")
				.when().get(BASE_URL + "/authors")
			.then().assertThat().statusCode(400);
	}

	// POST

	@Test
	@Order(1)
	void givenNewAuthor_whenCreateRequest_thenCreated() throws ParseException, JsonProcessingException {
		LocalDate birthDate = getCorrectAuthorBirthDate();
		AuthorDto newAuthorDto = AuthorDto.builder()
			.authorId(getNextAuthorId())
			.authorName(NameDto.builder().first(CORRECT_AUTHOR_NAME).second(CORRECT_AUTHOR_LAST_NAME).build())
			.birth(BirthDto.builder().date(birthDate).country("Russia").city("Moscow").build())
			.authorDescription("-")
			.nationality("Russian")
				.build();
		given()
			.header("Content-Type", "application/json")
			.body(new ObjectMapper().writeValueAsString(newAuthorDto))
		.when()
			.post(BASE_URL + "/author")
		.then()
			.assertThat().statusCode(201);
	}

	@Test
	@Order(2)
	void givenNewAuthorWithoutId_whenCreateRequest_thenRequestFailed() throws ParseException, JsonProcessingException {
		LocalDate birthDate = getCorrectAuthorBirthDate();
		AuthorDto newAuthorDto = AuthorDto.builder()
			.authorName(NameDto.builder().first(CORRECT_AUTHOR_NAME).second(CORRECT_AUTHOR_LAST_NAME).build())
			.birth(BirthDto.builder().date(birthDate).country("Russia").city("Moscow").build())
			.authorDescription("-")
			.nationality("Russian")
			.build();
		given()
			.header("Content-Type", "application/json")
			.body(new ObjectMapper().writeValueAsString(newAuthorDto))
			.when()
			.post(BASE_URL + "/author")
			.then()
			.assertThat().statusCode(400);
	}

	// PUT

	@Test
	@Order(5)
	void givenAuthor_whenUpdateRequest_thenUpdated() throws JsonProcessingException {
		AuthorDto author = getAuthorByName(CORRECT_AUTHOR_NAME + " " + CORRECT_AUTHOR_LAST_NAME);
		author.setAuthorDescription("A Russian poet, playwright, and novelist of the Romantic era. He is considered by many to be the greatest Russian poet, and the founder of modern Russian literature.");
			given()
				.header("Content-Type", "application/json")
				.body(new ObjectMapper().writeValueAsString(author))
			.when()
				.put(BASE_URL + "/author")
			.then()
				.assertThat().statusCode(200);
	}

	@Test
	@Order(6)
	void givenNewAuthor_whenUpdateRequest_thenNotFoundFail() throws JsonProcessingException, ParseException {
		LocalDate birthDate = getCorrectAuthorBirthDate();
		AuthorDto newAuthor = AuthorDto.builder()
			.authorId(getNextAuthorId())
			.authorName(NameDto.builder().first(CORRECT_AUTHOR_NAME).second(CORRECT_AUTHOR_LAST_NAME).build())
			.birth(BirthDto.builder().date(birthDate).country("Russia").city("Moscow").build())
			.authorDescription("-")
			.nationality("Russian")
			.build();
		given()
			.header("Content-Type", "application/json")
			.body(new ObjectMapper().writeValueAsString(newAuthor))
			.when()
			.put(BASE_URL + "/author")
			.then()
			.assertThat().statusCode(404);
	}

	// DELETE

	@Test
	@Order(7)
	void givenExistingAuthorId_whenRequestForDelete_thenDeleted() {
		AuthorDto author = getAuthorByName(CORRECT_AUTHOR_NAME + " " + CORRECT_AUTHOR_LAST_NAME);
		given()
			.when().delete(BASE_URL + "/author/" + author.getAuthorId())
			.then().assertThat().statusCode(204);
	}

	@Test
	@Order(8)
	void givenNotExistingAuthorId_whenRequestForDelete_thenNotFoundFail() {
		given()
			.when().delete(BASE_URL + "/author/" + getNextAuthorId())
			.then().assertThat().statusCode(404);
	}

	private static Long getNextAuthorId() {
		return given()
					.param("pagination", "false")
					.param("orderType", "desc")
				.when()
					.get(BASE_URL + "/authors")
					.jsonPath()
					.getList("$", AuthorDto.class)
					.stream()
			.findFirst().get().getAuthorId() + 1L;
	}

	private static AuthorDto getAuthorByName(String name) {
		return given()
			.param("query", name)
			.when()
			.get(BASE_URL + "/authors/search")
			.jsonPath()
			.getList("$", AuthorDto.class)
			.get(0);
	}

	private static LocalDate getCorrectAuthorBirthDate() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		LocalDate birthDate =
			format.parse("1827.05.26").toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		return birthDate;
	}
}
