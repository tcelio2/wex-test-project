package com.wex.purchase.purchase.gateway.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataTreasuryRateValueResponse {
    @JsonProperty("data")
    private List<DataTreasuryRateResponse> data;
}
