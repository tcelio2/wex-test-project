package com.wex.purchase.purchase.gateway.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataTreasuryRateResponse {

    @JsonProperty("record_date")
    private String recordDate; //"2023-09-30",
    @JsonProperty("country")
    private String country; //"Afghanistan",
    @JsonProperty("currency")
    private String currency; //"Afghani",
    @JsonProperty("country_currency_desc")
    private String countryCurrencyDesc; //"Afghanistan-Afghani",
    @JsonProperty("exchange_rate")
    private String exchangeRate; //"77.86",
    @JsonProperty("effective_date")
    private String effectiveDate; //"2023-09-30",
    @JsonProperty("src_line_nbr")
    private String srcLineNbr; //"1",
    @JsonProperty("record_fiscal_year")
    private String recordFiscalYear; //"2023",
    @JsonProperty("record_fiscal_quarter")
    private String recordFiscalQuarter; //"4",
    @JsonProperty("record_calendar_year")
    private String recordCalendarYear; //"2023",
    @JsonProperty("record_calendar_quarter")
    private String recordCalendarQuarter; //"3",
    @JsonProperty("record_calendar_month")
    private String recordCalendarMonth; //"09",
    @JsonProperty("record_calendar_day")
    private String recordCalendarDay; //"30"
}
