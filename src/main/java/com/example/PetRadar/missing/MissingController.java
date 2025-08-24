package com.example.PetRadar.missing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missing")
public class MissingController {
    private final MissingService missingService;

    @GetMapping
    public ResponseEntity<List<MissingDTO>> getMissingList() {
        List<MissingDTO> missingList = missingService.getMissingList();
        return ResponseEntity.ok(missingList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissingDetailDTO> getMissingDetail(@PathVariable Long id) {
        MissingDetailDTO detail = missingService.getMissingDetail(id);
        return ResponseEntity.ok(detail);
    }

    @PostMapping
    public ResponseEntity<Void> createMissing(@RequestBody MissingDTO missingDTO, @AuthenticationPrincipal UserDetails userDetails) {
        missingService.createMissing(Long.parseLong(userDetails.getUsername()), missingDTO);
        return ResponseEntity.ok().build();
    }

}
