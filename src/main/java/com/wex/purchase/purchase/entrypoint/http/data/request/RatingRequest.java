package com.wex.purchase.purchase.entrypoint.http.data.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RatingRequest {

    @NotBlank(message = "The field 'currency' is required")
    private String currency;
    @NotBlank(message = "The field 'trackingId' is required")
    private String trackingId;
    private String country;
}
