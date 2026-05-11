package financetrack.controller;

import financetrack.dto.TransaccionRequest;
import financetrack.dto.TransaccionResponse;
import financetrack.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<List<TransaccionResponse>> listar(Authentication auth) {
        return ResponseEntity.ok(transaccionService.listarPorUsuario(auth.getName()));
    }

    @PostMapping
    public ResponseEntity<TransaccionResponse> crear(
            @Valid @RequestBody TransaccionRequest request,
            Authentication auth) {
        return ResponseEntity.ok(transaccionService.crear(request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id, Authentication auth) {
        transaccionService.eliminar(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
