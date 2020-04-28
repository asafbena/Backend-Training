package petstore.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import petstore.model.Pet;
import utils.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PetHandlingApi implements PetApi {
    private List<Pet> pets;

    public PetHandlingApi(List<Pet> pets) {
        this.pets = pets;
    }

    @ApiOperation(value = "Finds Pets by status", nickname = "findPetsByStatus", notes = "Multiple status values can be provided with comma separated strings", response = Pet.class, responseContainer = "List", tags = {"pet",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Pet.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @RequestMapping(value = "/pet/findByStatus",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Pet>> findPetsByStatus(@NotNull @ApiParam(value = "Status values that need to be considered for filter", required = true, allowableValues = "available, pending, sold") @Valid @RequestParam(value = "status", required = true) List<String> status) {
        ArrayList<Pet> filteredPets = new ArrayList<Pet>();
        for (String singleStatus: status) {
            try {
                Pet.StatusEnum.valueOf(singleStatus.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<List<Pet>>(HttpStatus.BAD_REQUEST);
            }
            updateFilteredPetsByStatus(filteredPets, singleStatus);
        }
        return new ResponseEntity<List<Pet>>(filteredPets, HttpStatus.OK);
    }

    @ApiOperation(value = "Find pet by ID", nickname = "getPetById", notes = "Returns a single pet", response = Pet.class, tags = {"pet",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Pet.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Pet not found")})
    @RequestMapping(value = "/pet/{petId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Pet> getPetById(@ApiParam(value = "ID of pet to return", required = true) @PathVariable("petId") Long petId) {
        if (isInvalidPetId(petId)) {
            return new ResponseEntity<Pet>(HttpStatus.BAD_REQUEST);
        }

        for (Pet pet: pets) {
            if (pet.getId().equals(petId)) {
                return new ResponseEntity<Pet>(pet, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
    }

    private Boolean isInvalidPetId(Long petId) {
        return petId < Constants.VALID_PET_ID_MINIMAL_VALUE;
    }

    private void updateFilteredPetsByStatus(List<Pet> filteredPets, String filterStatus) {
        for (Pet pet: pets) {
            if (pet.getStatus().toString().equals(filterStatus)) {
                filteredPets.add(pet);
            }
        }
    }
}