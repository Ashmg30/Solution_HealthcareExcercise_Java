package com.test.healthcare.integration;

import com.test.healthcare.HealthCarePortalApplication;
import com.test.healthcare.model.Dependent;
import com.test.healthcare.model.Enrollee;
import com.test.healthcare.repository.DependentRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.test.healthcare.stub.HealthCareStub.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthCarePortalApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HealthCarePortalAPITest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private DependentRepository dependentRepository;

    @Test
    public void test1CreateEnrolleeSuccessfully() {
        HttpEntity<Enrollee> httpEntity = new HttpEntity<>(generateEnrollee());
        ResponseEntity<Enrollee> result = template.postForEntity("/enrollees", httpEntity, Enrollee.class);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getEnrolleeId());
    }

    @Test
    public void test1CreateEnrolleeWithoutPhoneNumber() {
        HttpEntity<Enrollee> httpEntity = new HttpEntity<>(generateEnrolleeWithoutPhone());
        ResponseEntity<Enrollee> result = template.postForEntity("/enrollees", httpEntity, Enrollee.class);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getEnrolleeId());
        assertNull(result.getBody().getPhoneNumber());
    }

    @Test
    public void test1GetAllEnrollee() throws JSONException {

        ResponseEntity<String> response = template.getForEntity("/enrollees", String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray jsonArray = jsonObject.getJSONObject("_embedded").getJSONArray("enrollees");
        assertTrue(jsonArray.length() > 0);
        assertEquals(jsonArray.length(), 2);
    }

    @Test
    public void test1GetEnrollee() throws JSONException {
        URI uri = UriComponentsBuilder.newInstance().path("/enrollees/{id}").buildAndExpand("1").toUri();

        ResponseEntity<Enrollee> enrollee = template.getForEntity(uri, Enrollee.class);
        assertEquals(enrollee.getStatusCode(), HttpStatus.OK);
        assertNotNull(enrollee.getBody());
        assertEquals(enrollee.getBody().getName(), "TestEnrollee");
        assertEquals(enrollee.getBody().getPhoneNumber(), 9827177211L);
    }

    @Test
    public void test1UpdateEnrollee() {
        URI uri = UriComponentsBuilder.newInstance().path("/enrollees/{id}").buildAndExpand("1").toUri();

        ResponseEntity<Enrollee> response = template
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(generateEnrolleeToUpdate()), Enrollee.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getName(), "UpdatedEnrollee");
    }

    @Test
    public void test1ZDeleteEnrollee() {
        URI uri = UriComponentsBuilder.newInstance().path("/enrollees/{id}").buildAndExpand("2").toUri();
        template.delete(uri);
    }

    @Test
    public void test2CreateDependentSuccessfully() {
        URI uri = UriComponentsBuilder.newInstance().path("/dependents").build().toUri();
        ResponseEntity<Dependent> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>(generateDependent()), Dependent.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getDependentId());
        assertEquals(response.getBody().getName(), "TestDependent");
    }

    @Test
    public void test2LinkDependentToEnrollee() throws JSONException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");

        HttpEntity<String> enrolleeEntity = new HttpEntity<>("/enrollees/1", requestHeaders);
        ResponseEntity<String> response = template.exchange("/dependents/3/enrollee", HttpMethod.PUT, enrolleeEntity, String.class);

        ResponseEntity<Enrollee> enrollee = template.getForEntity("/dependents/3/enrollee", Enrollee.class);
        assertEquals(enrollee.getBody().getName(), "UpdatedEnrollee");

        ResponseEntity<String> dependent = template.getForEntity("/enrollees/1/dependents", String.class);
        JSONObject jsonObject = new JSONObject(dependent.getBody());
        JSONArray jsonArray = jsonObject.getJSONObject("_embedded").getJSONArray("dependents");
        assertTrue(jsonArray.length() > 0);
        assertEquals(jsonArray.getJSONObject(0).getString("name"), "TestDependent");
    }

    @Test
    public void test2RemoveDependentFromEnrollee() throws JSONException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");

        HttpEntity<String> enrolleeEntity = new HttpEntity<>("/enrollees/1", requestHeaders);
        ResponseEntity<String> response = template.exchange("/dependents/3/enrollee", HttpMethod.DELETE, enrolleeEntity, String.class);

        ResponseEntity<Enrollee> enrollee = template.getForEntity("/dependents/3/enrollee", Enrollee.class);
        assertNull(enrollee.getBody());

        ResponseEntity<String> dependent = template.getForEntity("/enrollees/1/dependents", String.class);
        JSONObject jsonObject = new JSONObject(dependent.getBody());
        JSONArray jsonArray = jsonObject.getJSONObject("_embedded").getJSONArray("dependents");
        assertEquals(jsonArray.length(), 0);
    }

    @Test
    public void test2UpdateDependentSuccessfully() throws JSONException {
        URI uri = UriComponentsBuilder.newInstance().path("/dependents/{id}").buildAndExpand("3").toUri();

        ResponseEntity<Dependent> response = template
                .exchange(uri, HttpMethod.PUT, new HttpEntity<>(generateDependentForUpdate()), Dependent.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getName(), "UpdatedDependent");
    }

    @Test
    public void test2ZDeleteDependent() {
        URI uri = UriComponentsBuilder.newInstance().path("/dependents/{id}").buildAndExpand("3").toUri();
        template.delete(uri);
    }


}
