package com.backend.crm.routes.services;

import com.backend.crm.routes.repositories.WSUSerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WSUSerService {
    private final WSUSerRepository repository;
}
