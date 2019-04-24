package edu.josegc789.companyform.controller;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.josegc789.companyform.model.domain.dtos.CompanyDto;
import edu.josegc789.companyform.model.domain.dtos.PersonDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.Collections;
import static io.restassured.RestAssured.given;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyStep{
    @LocalServerPort
    private int port;

    private Response response;
    private RequestSpecification request;
    private CompanyDto param;

    @Given("a company")
    public void a_company(){
        param = CompanyDto.builder()
                .name("Test")
                .owner(PersonDto.builder().name("Test").build())
                .employees(Collections.singletonList(PersonDto.builder().name("Test").build()))
                .build();
        request = given().port(port).body(param);
    }

    @When("a user sends a post")
    public void a_user_sends_a_post(){
        response = request.when().header("Content-Type", "application/json").post("/form");
        log.info("response: " + response.prettyPrint());
    }

    @Then("the status should be 200")
    public void the_status_should_be_200(){
        response.then().assertThat().statusCode(200);
    }
}
