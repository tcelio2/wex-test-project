package com.wex.purchase.purchase.gateway.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelTreasuryRateResponse {

    @JsonProperty("record_date")
    private String recordDate;

    @JsonProperty("country")
    private String country;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("country_currency_desc")
    private String countryCurrencyDesc;

    @JsonProperty("exchange_rate")
    private String exchangeRate;

    @JsonProperty("effective_date")
    private String effectiveDate;

    @JsonProperty("src_line_nbr")
    private String srcLineNbr;

    @JsonProperty("record_fiscal_year")
    private String recordFiscalYear;

    @JsonProperty("record_fiscal_quarter")
    private String recordFiscalQuarter;

    @JsonProperty("record_calendar_year")
    private String recordCalendarYear;

    @JsonProperty("record_calendar_quarter")
    private String recordCalendarQuarter;

    @JsonProperty("record_calendar_month")
    private String recordCalendarMonth;

    @JsonProperty("record_calendar_day")
    private String recordCalendarDay;
}
