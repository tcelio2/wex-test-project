package com.wex.purchase.purchase.gateway.impl;

import com.wex.purchase.purchase.config.Profiles;
import com.wex.purchase.purchase.gateway.TreasuryReportingRateGateway;
import com.wex.purchase.purchase.gateway.data.response.TreasuryRateResponse;
import com.wex.purchase.purchase.gateway.endpoint.TreasuryGtwEndpoint;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Profile(Profiles.PRD)
public class TreasuryReportingRateGatewayImpl implements TreasuryReportingRateGateway {

    private TreasuryGtwEndpoint treasuryGtwEndpoint;

    @Override
    public TreasuryRateResponse getTreasuryRate(Map<String, String> allParams) {
        try {
            TreasuryRateResponse treasuryRateResponse = treasuryGtwEndpoint.consultTreasuryRate(allParams);
            return treasuryRateResponse;
        } catch (Exception ex) {

        }
        return null;
    }
}
