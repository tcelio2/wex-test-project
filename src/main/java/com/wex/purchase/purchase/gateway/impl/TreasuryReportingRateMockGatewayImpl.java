package com.wex.purchase.purchase.gateway.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wex.purchase.purchase.config.Profiles;
import com.wex.purchase.purchase.entrypoint.http.exceptions.BadRequestException;
import com.wex.purchase.purchase.gateway.TreasuryReportingRateGateway;
import com.wex.purchase.purchase.gateway.data.response.DataTreasuryRateResponse;
import com.wex.purchase.purchase.gateway.data.response.DataTreasuryRateValueResponse;
import com.wex.purchase.purchase.gateway.data.response.TreasuryRateResponse;
import com.wex.purchase.purchase.usecase.exceptions.PurchaseGenericError;
import com.wex.purchase.purchase.usecase.impl.PurchaseUseCaseImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@Profile(Profiles.LOCAL)
public class TreasuryReportingRateMockGatewayImpl implements TreasuryReportingRateGateway {

    private static final Logger LOGGER = LogManager.getLogger(TreasuryReportingRateMockGatewayImpl.class);

    @Override
    public TreasuryRateResponse getTreasuryRate(Map<String, String> allParams) {
        LOGGER.info("Get Treasury rating using parameters through Gateway");
        DataTreasuryRateValueResponse dataTreasuryRateValueResponse = getjsonList();

        if (isNull(dataTreasuryRateValueResponse.getData()) ||
                CollectionUtils.isEmpty(dataTreasuryRateValueResponse.getData())) {
            throw new BadRequestException("Erro: Document nao encontrada na base de dados!");
        }

        String currency = allParams.get("currency");
        String country = allParams.get("country");

        List<DataTreasuryRateResponse> responses = dataTreasuryRateValueResponse.getData().stream()
                .filter(c -> currency == null || c.getCurrency().equalsIgnoreCase(currency))
                .filter(c -> country == null || c.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());

        return TreasuryRateResponse.builder()
                .data(responses)
                .build();
    }

    private DataTreasuryRateValueResponse getjsonList() {
        try {
            InputStream inJson = DataTreasuryRateValueResponse.class.getResourceAsStream("/data/reportRating.json");
            return new ObjectMapper().readValue(inJson, DataTreasuryRateValueResponse.class);
        } catch (Exception ex) {
            throw new PurchaseGenericError("Error");
        }
    }
}
