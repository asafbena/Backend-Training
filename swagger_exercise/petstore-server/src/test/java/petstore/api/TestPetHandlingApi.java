package petstore.api;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import petstore.model.Pet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.DataExtractionUtils;
import utils.TestsConstants;

public class TestPetHandlingApi {
    private ArrayList<Pet> pets = new ArrayList<Pet>();
    private PetHandlingApi petHandlingApi;

    public TestPetHandlingApi() {
        buildPetsDB();
        petHandlingApi = new PetHandlingApi(pets);
    }

    @Test
    public void testGetPetByInvalidId() {
        ResponseEntity<Pet> responseEntity = petHandlingApi.getPetById(TestsConstants.PET_INVALID_ID);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPetByIdOfNotFoundPet() {
        ResponseEntity<Pet> responseEntity = petHandlingApi.getPetById(TestsConstants.NOT_FOUND_PET_ID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPetByIdSuccessfulRequestStatusCode() {
        ResponseEntity<Pet> responseEntity = petHandlingApi.getPetById(TestsConstants.EXISTING_PET_ID);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPetById() {
        Pet filteredPet = petHandlingApi.getPetById(TestsConstants.EXISTING_PET_ID).getBody();
        Pet expectedPet = DataExtractionUtils.extractPetData(TestsConstants.PET_DATA_BY_ID);
        Assertions.assertEquals(expectedPet, filteredPet);
    }

    @Test
    public void testGetPetsByInvalidStatus() {
        ResponseEntity<List<Pet>> responseEntity = petHandlingApi.findPetsByStatus(TestsConstants.INVALID_PET_STATUSES);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testFindPetsByStatusSuccessfulRequestStatusCode() {
        ResponseEntity<List<Pet>> responseEntity = petHandlingApi.findPetsByStatus(
                TestsConstants.EXISTING_PET_STATUSES);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFindPetsByStatus() {
        List<Pet> filteredPets = petHandlingApi.findPetsByStatus(TestsConstants.EXISTING_PET_STATUSES).getBody();
        assertPetsCollectionEquality(TestsConstants.EXISTING_PETS_BY_STATUSES, filteredPets);
    }

    private void assertPetsCollectionEquality(List<Pet> expectedPets, List<Pet> receivedPets) {
        Assertions.assertTrue(receivedPets.containsAll(expectedPets));
        Assertions.assertTrue(expectedPets.containsAll(receivedPets));
    }

    private void buildPetsDB() {
        try {
            DataExtractionUtils.extractPetsData(pets);
        } catch (IOException e) {
            System.out.println("IOE exception: " + e);
        } catch (ParseException e) {
            System.out.println("parse exception: " + e);
        }
    }
}
