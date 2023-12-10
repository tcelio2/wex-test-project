package com.wex.purchase.purchase.utils;

import com.wex.purchase.purchase.usecase.impl.PurchaseUseCaseImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyUtils {
    private static final Logger LOGGER = LogManager.getLogger(CurrencyUtils.class);


    public static BigDecimal parseToBigDecimal(final String amount, final Locale locale) throws ParseException {
        LOGGER.info("Starting convert of currency value. {}", amount);
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
    }
}
