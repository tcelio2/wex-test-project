package com.wex.purchase.purchase.gateway.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaTreasuryRateResponse {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("labels")
    private LabelTreasuryRateResponse labels;
    @JsonProperty("dataTypes")
    private DateTypesTreasuryRateResponse dataTypes;
    @JsonProperty("dataFormats")
    private DateFormatsTreasuryRateResponse dataFormats;
    @JsonProperty("total-count")
    private Integer totalCount;
    @JsonProperty("total-pages")
    private Integer totalPages;
}
