package com.betanet.lynxsupport.service.api;

import com.betanet.lynxsupport.domain.Engineer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EngineerService {

    Page<Engineer> getEngineers(Pageable pageable);
}
