package com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.domain.Restaurante;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.AtualizarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.dto.CriarRestauranteInput;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.AtualizarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.BuscarRestaurantePorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.CriarRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ExcluirRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.usecase.ListarRestaurantesUseCase;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.dto.CriarRestauranteRequest;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.infra.dto.RestauranteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Restaurantes", description = "Endpoints para gestão de restaurantes")
@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final ListarRestaurantesUseCase listarRestaurantesUseCase;
    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private final ExcluirRestauranteUseCase excluirRestauranteUseCase;

    @Operation(summary = "Criar restaurante")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestauranteResponse> criar(@RequestBody CriarRestauranteRequest request) {
        CriarRestauranteInput input = new CriarRestauranteInput(
                UUID.randomUUID(),
                request.nome(),
                request.endereco(),
                request.tipoCozinha(),
                request.horarioFuncionamento(),
                request.donoId()
        );
        Restaurante restaurante = criarRestauranteUseCase.executar(input);
        RestauranteResponse response = toResponse(restaurante);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(restaurante.getId())
                        .toUri())
                .body(response);
    }

    @Operation(summary = "Buscar restaurante por id")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable UUID id) {
        Restaurante restaurante = buscarRestaurantePorIdUseCase.executar(id);
        return ResponseEntity.ok(toResponse(restaurante));
    }

    @Operation(summary = "Listar restaurantes")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestauranteResponse>> listar() {
        List<Restaurante> restaurantes = listarRestaurantesUseCase.executar();
        List<RestauranteResponse> response = restaurantes.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar restaurante")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable UUID id,
                                                         @RequestBody AtualizarRestauranteInput input) {
        Restaurante restaurante = atualizarRestauranteUseCase.executar(id, input);
        return ResponseEntity.ok(toResponse(restaurante));
    }

    @Operation(summary = "Excluir restaurante")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirRestauranteUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    private RestauranteResponse toResponse(Restaurante restaurante) {
        return new RestauranteResponse(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDonoId()
        );
    }
}
