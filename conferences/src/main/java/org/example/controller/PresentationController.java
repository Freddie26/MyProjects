package org.example.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller("/presentations")
public class PresentationController {

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(null);
    }

}
