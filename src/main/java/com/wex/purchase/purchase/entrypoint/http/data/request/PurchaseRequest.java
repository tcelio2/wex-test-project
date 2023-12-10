package com.wex.purchase.purchase.entrypoint.http.data.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PurchaseRequest {

    @NotBlank(message = "The field 'description' is required")
    @Size(max = 50, message = "The field 'description' must have at least 50 characters")
    private String description;
    @NotBlank(message = "The field 'purchaseAmount' is required")
    private String purchaseAmount;
    @NotBlank(message = "The field 'transactionDate' is required")
    private String transactionDate;
}
