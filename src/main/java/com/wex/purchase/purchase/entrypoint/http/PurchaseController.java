package com.wex.purchase.purchase.entrypoint.http;

import com.wex.purchase.purchase.entrypoint.http.data.request.PurchaseRequest;
import com.wex.purchase.purchase.entrypoint.http.data.response.ErrorResponse;
import com.wex.purchase.purchase.entrypoint.http.data.response.PurchaseResponse;
import com.wex.purchase.purchase.entrypoint.http.exceptions.BadRequestException;
import com.wex.purchase.purchase.usecase.PurchaseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;


@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    private static final Logger LOGGER = LogManager.getLogger(PurchaseController.class);
    private static final String SUCCESS_FLAG = "Success";
    @Autowired
    private PurchaseUseCase purchaseUseCase;


    @Operation(
            summary = "Purchase Endpoint",
            tags = {
                    "purchase endpoint"
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Purchase report Success"),
                    @ApiResponse(responseCode = "400", description = "Business error."),
                    @ApiResponse(responseCode = "500", description = "Internal Server error.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> startPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest) {
        LOGGER.info("Starting purchase. PurchaseAmount: {}", purchaseRequest.getPurchaseAmount());
        try {
            String purchaseId = purchaseUseCase.makePurchase(purchaseRequest);
            PurchaseResponse purchaseResponse = new PurchaseResponse();
            purchaseResponse.setMessage(SUCCESS_FLAG);
            purchaseResponse.setTrackingId(purchaseId);
            return ResponseEntity.ok(purchaseResponse);
        } catch (BadRequestException ex1) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Validation Failed");
            errorResponse.setDetails(Collections.singletonList(ex1.getMessage()));
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception ex) {
            LOGGER.error("==?>", ex);
        }
        return null;
    }
}
