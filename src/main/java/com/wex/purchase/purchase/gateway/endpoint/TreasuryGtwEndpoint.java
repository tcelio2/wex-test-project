package com.wex.purchase.purchase.gateway.endpoint;

import com.wex.purchase.purchase.gateway.data.constants.TreasuryConstants;
import com.wex.purchase.purchase.gateway.data.response.TreasuryRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Map;

@FeignClient(name = TreasuryConstants.NAME_TREASURY_SERVICE, url = TreasuryConstants.HOST_TREASURY_SERVICE)
public interface TreasuryGtwEndpoint {

    @RequestMapping(method = RequestMethod.GET, value = TreasuryConstants.ENDPOINT_TREASURY_SERVICE)
    TreasuryRateResponse consultTreasuryRate(Map<String, String> allParams);
}
