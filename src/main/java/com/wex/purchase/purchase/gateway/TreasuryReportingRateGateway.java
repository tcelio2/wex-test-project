package com.wex.purchase.purchase.gateway;

import com.wex.purchase.purchase.gateway.data.response.TreasuryRateResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TreasuryReportingRateGateway {
    TreasuryRateResponse getTreasuryRate(Map<String, String> allParams);
}
