package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.controller.dto.PresentationBrowseOutDto;
import org.example.controller.dto.RegisterPresentationInDto;
import org.example.service.PresentationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.example.util.UserUtils.getUserId;

@AllArgsConstructor
@RestController
public class PresentationController {

    private final PresentationService presentationService;

    @GetMapping("/presentations/all")
    public ResponseEntity<Map<String, PresentationBrowseOutDto>> findAll() {
        Map<String, PresentationBrowseOutDto> roomToPresentationInfo = presentationService.findAllPresentations();
        return ResponseEntity.ok(roomToPresentationInfo);
    }

    @PostMapping("/presentations")
    public ResponseEntity<?> newPresentation(@RequestBody RegisterPresentationInDto dto) {
        boolean added = presentationService.registerNewPresentation(dto, getUserId());
        HttpStatus httpStatus = added ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).build();
    }

    @PutMapping("/presentations/{presentationId}")
    public ResponseEntity<?> editPresentation(@PathVariable Long presentationId, RegisterPresentationInDto dto) {
        boolean updated = presentationService.updatePresentation(presentationId, dto, getUserId());
        HttpStatus httpStatus = updated ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).build();
    }

    @DeleteMapping("/presentations/{presentationId}")
    public ResponseEntity<?> deletePresentation(@PathVariable Long presentationId) {
        boolean deleted = presentationService.deletePresentation(presentationId);
        HttpStatus httpStatus = deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).build();
    }

    @PostMapping("/register-to-presentation/{presentationId}")
    public ResponseEntity<?> registerToPresentation(@PathVariable Long presentationId) {
        boolean registered = presentationService.registerToPresentation(presentationId, getUserId());
        HttpStatus httpStatus = registered ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).build();
    }
}
