package com.wex.purchase.purchase.usecase.impl;

import com.wex.purchase.purchase.entity.PurchaseDocument;
import com.wex.purchase.purchase.entrypoint.http.data.request.PurchaseRequest;
import com.wex.purchase.purchase.entrypoint.http.exceptions.BadRequestException;
import com.wex.purchase.purchase.repository.PurchaseRepository;
import com.wex.purchase.purchase.usecase.PurchaseUseCase;
import com.wex.purchase.purchase.usecase.exceptions.PurchaseGenericError;
import com.wex.purchase.purchase.utils.CurrencyUtils;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class PurchaseUseCaseImpl implements PurchaseUseCase {
    private static final Logger LOGGER = LogManager.getLogger(PurchaseUseCaseImpl.class);
    private static final String REGEX_US_CURRENT = "^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$";
    private final PurchaseRepository purchaseRepository;

    @Override
    public String makePurchase(PurchaseRequest purchaseRequest) {
        LOGGER.info("Starting analyze and persistence of purchase. Amount: {}",purchaseRequest.getPurchaseAmount());
        String correlationId = UUID.randomUUID().toString();
        try {
            validate(purchaseRequest);
            PurchaseDocument purchaseDocument = PurchaseDocument.builder()
                    .id(correlationId)
                    .purchaseAmount(CurrencyUtils.parseToBigDecimal(purchaseRequest.getPurchaseAmount(), Locale.US))
                    .description(purchaseRequest.getDescription())
                    .transactionDate(LocalDateTime.parse(purchaseRequest.getTransactionDate()))
                    .build();
            return purchaseRepository.save(purchaseDocument).getId();
        } catch (BadRequestException ex1) {
            throw ex1;
        } catch (Exception ex) {
            throw new PurchaseGenericError(ex);
        }
    }

    private void validate(PurchaseRequest request) {
        LOGGER.info("Starting validating of request of date and currency. Date field: {} Currency {}", request.getTransactionDate(), request.getPurchaseAmount());
        try {
            LocalDateTime parse = LocalDateTime.parse(request.getTransactionDate());
        } catch (Exception ex) {
            throw new BadRequestException("The format of the date field 'transactionDate' must be 'YYYY-mm-DDThh:mm:ss-TZ'");
        }

        Matcher matcher = Pattern.compile(REGEX_US_CURRENT).matcher(request.getPurchaseAmount());
        if (!matcher.matches()) {
            throw new BadRequestException("The format of the current field 'purchaseAmount' must be '$xx,xxx.xx'");
        }
    }

}
