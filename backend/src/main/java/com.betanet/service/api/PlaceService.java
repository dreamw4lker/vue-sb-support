package com.betanet.service.api;

import com.betanet.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {
    Page<Place> getPlaces(Pageable pageable);
}
