package com.wex.purchase.purchase.gateway.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreasuryRateResponse {

    private List<DataTreasuryRateResponse> data;
    private MetaTreasuryRateResponse meta;
    private LinksTreasuryRateResponse links;

}
