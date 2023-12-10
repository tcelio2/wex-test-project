package com.wex.purchase.purchase.repository;

import com.wex.purchase.purchase.entity.PurchaseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends MongoRepository<PurchaseDocument,  String> {
}
