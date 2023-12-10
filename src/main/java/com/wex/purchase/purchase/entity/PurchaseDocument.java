package com.wex.purchase.purchase.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Data
@Document(collection = "compras")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDocument implements Serializable {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal purchaseAmount;
    private LocalDateTime transactionDate;
}
