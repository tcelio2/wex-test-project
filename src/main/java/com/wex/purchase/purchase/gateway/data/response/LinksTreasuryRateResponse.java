package com.wex.purchase.purchase.gateway.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinksTreasuryRateResponse {

    @JsonProperty("self")
    private String self;

    @JsonProperty("first")
    private String first;

    @JsonProperty("prev")
    private String prev;

    @JsonProperty("next")
    private String next;

    @JsonProperty("last")
    private String last;
}
