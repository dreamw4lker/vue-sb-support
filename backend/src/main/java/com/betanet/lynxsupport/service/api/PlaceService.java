package com.betanet.lynxsupport.service.api;

import com.betanet.lynxsupport.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {
    Page<Place> getPlaces(Pageable pageable);
}
