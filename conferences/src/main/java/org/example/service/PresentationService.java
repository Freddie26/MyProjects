package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.PresentationRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PresentationService {

    private final PresentationRepository presentationRepository;

    public List<Object> findActualPresentations() {
        presentationRepository.findActualPresentations();

        return Collections.emptyList();
    }

}
