package com.wex.purchase.purchase.usecase;

import com.wex.purchase.purchase.entrypoint.http.data.request.RatingRequest;
import com.wex.purchase.purchase.entrypoint.http.data.response.RatingResponse;

import java.util.List;

public interface RatingUseCase {

    List<RatingResponse> retrieveRate(RatingRequest ratingRequest);
}
