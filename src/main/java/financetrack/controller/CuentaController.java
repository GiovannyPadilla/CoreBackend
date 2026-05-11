package financetrack.controller;

import financetrack.dto.CuentaRequest;
import financetrack.dto.CuentaResponse;
import financetrack.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> listar(Authentication auth) {
        return ResponseEntity.ok(cuentaService.listarPorUsuario(auth.getName()));
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crear(
            @Valid @RequestBody CuentaRequest request,
            Authentication auth) {
        return ResponseEntity.ok(cuentaService.crear(request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id, Authentication auth) {
        cuentaService.eliminar(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
