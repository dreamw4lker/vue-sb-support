package com.betanet.service.api;

import com.betanet.domain.Engineer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EngineerService {

    Page<Engineer> getEngineers(Pageable pageable);
}
