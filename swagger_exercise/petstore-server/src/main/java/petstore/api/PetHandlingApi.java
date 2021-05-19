package petstore.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import petstore.model.Pet;
import utils.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PetHandlingApi implements PetApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(PetHandlingApi.class);

    private List<Pet> pets;

    public PetHandlingApi(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    @ApiOperation(value = "Finds Pets by status", nickname = "findPetsByStatus", notes = "Multiple status values can be provided with comma separated strings", response = Pet.class, responseContainer = "List", tags = {"pet",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Pet.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(value = "/pet/findByStatus",
            produces = {"application/json"})
    public ResponseEntity<List<Pet>> findPetsByStatus(@NotNull @ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @Valid @RequestParam(value = "status", required = true) List<String> statuses) {
        ArrayList<Pet> filteredPets = new ArrayList<>();
        LOGGER.info("Finding pets that have one the following statuses: {}.", statuses);
        for (String status: statuses) {
            try {
                Pet.StatusEnum.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.error("The given status {} is not a valid pet status. The valid statuses are {}.",
                        status, Pet.StatusEnum.values());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            updateFilteredPetsByStatus(filteredPets, status);
        }
        LOGGER.info("Successfully collected pets data by the given pet statuses {}", statuses);
        LOGGER.debug("The retrieved pets data is: {}", filteredPets);
        return new ResponseEntity<>(filteredPets, HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Find pet by ID", nickname = "getPetById", notes = "Returns a single pet", response = Pet.class, tags = {"pet",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Pet.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Pet not found")})
    @GetMapping(value = "/pet/{petId}",
            produces = {"application/json"})
    public ResponseEntity<Pet> getPetById(@ApiParam(value = "ID of pet to return", required = true) @PathVariable("petId") Long petId) {
        if (Boolean.TRUE.equals(isInvalidPetId(petId))) {
            LOGGER.error("Received a pet request with an invalid pet id.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return getPetByValidPetId(petId);
    }

    private Boolean isInvalidPetId(Long petId) {
        LOGGER.info("Checking if the given pet id {} is larger than the minimal valid pet id {}.",
                petId, Constants.VALID_PET_ID_MINIMAL_VALUE);
        return petId < Constants.VALID_PET_ID_MINIMAL_VALUE;
    }

    private ResponseEntity<Pet> getPetByValidPetId(Long petId) {
        for (Pet pet: pets) {
            if (pet.getId().equals(petId)) {
                LOGGER.info("Successfully collected a pet data by given pet id {}.", petId);
                return new ResponseEntity<>(pet, HttpStatus.OK);
            }
        }
        LOGGER.error("Could not find a pet with the following id: {}.", petId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private void updateFilteredPetsByStatus(List<Pet> filteredPets, String filterStatus) {
        for (Pet pet: pets) {
            if (pet.getStatus().toString().equals(filterStatus)) {
                filteredPets.add(pet);
            }
        }
    }
}