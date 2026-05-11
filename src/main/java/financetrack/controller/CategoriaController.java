package financetrack.controller;

import financetrack.entity.Categoria;
import financetrack.exception.BusinessException;
import financetrack.exception.ResourceNotFoundException;
import financetrack.repository.CategoriaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new BusinessException("Ya existe una categoría con ese nombre");
        }
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody Categoria body) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
        cat.setNombre(body.getNombre());
        cat.setDescripcion(body.getDescripcion());
        cat.setColor(body.getColor());
        cat.setIcono(body.getIcono());
        return ResponseEntity.ok(categoriaRepository.save(cat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
