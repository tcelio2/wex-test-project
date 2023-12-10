package com.wex.purchase.purchase.entrypoint.http;

import com.wex.purchase.purchase.entrypoint.http.data.request.RatingRequest;
import com.wex.purchase.purchase.entrypoint.http.data.response.ErrorResponse;
import com.wex.purchase.purchase.entrypoint.http.data.response.RatingResponse;
import com.wex.purchase.purchase.entrypoint.http.exceptions.BadRequestException;
import com.wex.purchase.purchase.usecase.RatingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/rate")
@AllArgsConstructor
public class TreasuryReportingRateController {
    private static final Logger LOGGER = LogManager.getLogger(TreasuryReportingRateController.class);

    private final RatingUseCase ratingUseCase;

    @Operation(
            summary = "Rating Endpoint",
            tags = {
                    "rating endpoint"
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating report Success"),
                    @ApiResponse(responseCode = "400", description = "Business error."),
                    @ApiResponse(responseCode = "500", description = "Internal Server error.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> startRating(@Valid @RequestBody RatingRequest ratingRequest) {
        LOGGER.info("Starting rating. Tracking ID: {}", ratingRequest.getTrackingId());
        try {
            List<RatingResponse> retrieveRateList = ratingUseCase.retrieveRate(ratingRequest);
            return ResponseEntity.ok(retrieveRateList);
        } catch (BadRequestException ex1) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Validation Failed");
            errorResponse.setDetails(Collections.singletonList(ex1.getMessage()));
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception ex) {
            LOGGER.error("Internal Server Error. {}", ex.getMessage());
        }
        return null;
    }
}
