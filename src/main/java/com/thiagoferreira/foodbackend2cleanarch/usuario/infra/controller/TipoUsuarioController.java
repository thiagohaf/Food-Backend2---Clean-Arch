package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioResponse;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarTipoUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.AtualizarTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.BuscarTipoUsuarioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.CriarTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ExcluirTipoUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ListarTiposUsuarioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
@RequiredArgsConstructor
public class TipoUsuarioController {

    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;
    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;
    private final ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TipoUsuarioResponse> criar(@Valid @RequestBody TipoUsuarioRequest request) {
        CriarTipoUsuarioInput input = new CriarTipoUsuarioInput(UUID.randomUUID(), request.nome());
        TipoUsuario tipoUsuario = criarTipoUsuarioUseCase.executar(input);
        TipoUsuarioResponse response = toResponse(tipoUsuario);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(tipoUsuario.getId())
                        .toUri())
                .body(response);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TipoUsuarioResponse> buscarPorId(@PathVariable UUID id) {
        TipoUsuario tipoUsuario = buscarTipoUsuarioPorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(toResponse(tipoUsuario));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoUsuarioResponse>> listar() {
        List<TipoUsuario> tipos = listarTiposUsuarioUseCase.listarTodos();
        List<TipoUsuarioResponse> response = tipos.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TipoUsuarioResponse> atualizar(@PathVariable UUID id,
                                                         @Valid @RequestBody TipoUsuarioRequest request) {
        AtualizarTipoUsuarioInput input = new AtualizarTipoUsuarioInput(request.nome());
        TipoUsuario tipoUsuario = atualizarTipoUsuarioUseCase.executar(id, input);
        return ResponseEntity.ok(toResponse(tipoUsuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirTipoUsuarioUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private TipoUsuarioResponse toResponse(TipoUsuario tipoUsuario) {
        return new TipoUsuarioResponse(tipoUsuario.getId(), tipoUsuario.getNome());
    }
}
