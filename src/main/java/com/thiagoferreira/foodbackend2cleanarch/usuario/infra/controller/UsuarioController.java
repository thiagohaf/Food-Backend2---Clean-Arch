package com.thiagoferreira.foodbackend2cleanarch.usuario.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.TipoUsuarioResponse;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.UsuarioRequest;
import com.thiagoferreira.foodbackend2cleanarch.usuario.infra.dto.UsuarioResponse;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.TipoUsuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.domain.Usuario;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.AtualizarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.dto.CriarUsuarioInput;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.AtualizarUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.BuscarUsuarioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.CriarUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ExcluirUsuarioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.usecase.ListarUsuariosUseCase;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Usuários", description = "Endpoints para gestão de usuários")
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final ExcluirUsuarioUseCase excluirUsuarioUseCase;

    @Operation(summary = "Criar usuário")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest request) {
        CriarUsuarioInput input = new CriarUsuarioInput(
                UUID.randomUUID(),
                request.nome(),
                request.tipoUsuarioId()
        );
        Usuario usuario = criarUsuarioUseCase.executar(input);
        UsuarioResponse response = toResponse(usuario);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(usuario.getId())
                        .toUri())
                .body(response);
    }

    @Operation(summary = "Buscar usuário por id")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID id) {
        Usuario usuario = buscarUsuarioPorIdUseCase.buscarPorId(id);
        return ResponseEntity.ok(toResponse(usuario));
    }

    @Operation(summary = "Listar usuários")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<Usuario> usuarios = listarUsuariosUseCase.listarTodos();
        List<UsuarioResponse> response = usuarios.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar usuário")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable UUID id,
                                                     @Valid @RequestBody UsuarioRequest request) {
        AtualizarUsuarioInput input = new AtualizarUsuarioInput(request.nome(), request.tipoUsuarioId());
        Usuario usuario = atualizarUsuarioUseCase.executar(id, input);
        return ResponseEntity.ok(toResponse(usuario));
    }

    @Operation(summary = "Excluir usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirUsuarioUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        TipoUsuario tipo = usuario.getTipoUsuario();
        TipoUsuarioResponse tipoResponse = new TipoUsuarioResponse(tipo.getId(), tipo.getNome());
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), tipoResponse);
    }
}
