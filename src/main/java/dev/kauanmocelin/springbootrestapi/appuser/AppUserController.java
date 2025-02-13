package dev.kauanmocelin.springbootrestapi.appuser;

import dev.kauanmocelin.springbootrestapi.appuser.request.AppUserPutRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    @Operation(summary = "List all users", description = "List all users", tags = {"user"})
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<List<AppUser>> listAll() {
        return ResponseEntity.ok(appUserService.findAll());
    }

    @GetMapping(path = "/{user-id}")
    @Operation(summary = "Find user by Id", description = "Find user by Id", tags = {"user"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "User does not exist in the database with id")
    })
    public ResponseEntity<AppUser> findById(@PathVariable("user-id") Long appUserId) {
        return ResponseEntity.ok(appUserService.findByIdOrThrowBadRequestException(appUserId));
    }

    @DeleteMapping(path = "/{user-id}")
    @Operation(summary = "Delete user", description = "Delete user", tags = {"user"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "When user does no exist in the database")
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable("user-id") Long appUserId) {
        appUserService.delete(appUserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    @Operation(summary = "Update user", description = "Update user", tags = {"user"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "When user does not exist in the database or email already has been used")
    })
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid AppUserPutRequestBody appUserPutRequestBody) {
        appUserService.replace(appUserPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
