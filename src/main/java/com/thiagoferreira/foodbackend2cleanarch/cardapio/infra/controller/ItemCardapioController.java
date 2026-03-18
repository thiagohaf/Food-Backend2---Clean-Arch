package com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.controller;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.AtualizarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.dto.CriarItemCardapioInput;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.AtualizarItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.BuscarItemCardapioPorIdUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.CriarItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.ExcluirItemCardapioUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.usecase.ListarItensCardapioPorRestauranteUseCase;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.dto.ItemCardapioRequest;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.infra.dto.ItemCardapioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/itens-cardapio")
@RequiredArgsConstructor
public class ItemCardapioController {

    private final CriarItemCardapioUseCase criarItemCardapioUseCase;
    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;
    private final ExcluirItemCardapioUseCase excluirItemCardapioUseCase;
    private final ListarItensCardapioPorRestauranteUseCase listarItensCardapioPorRestauranteUseCase;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCardapioResponse> criar(@Valid @RequestBody ItemCardapioRequest request) {
        CriarItemCardapioInput input = new CriarItemCardapioInput(
                UUID.randomUUID(),
                request.nome(),
                request.descricao(),
                request.preco(),
                true,
                request.fotoPath(),
                request.restauranteId()
        );

        ItemCardapio item = criarItemCardapioUseCase.executar(input);
        ItemCardapioResponse response = toResponse(item);

        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(item.getId())
                        .toUri())
                .body(response);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCardapioResponse> buscarPorId(@PathVariable UUID id) {
        ItemCardapio item = buscarItemCardapioPorIdUseCase.executar(id);
        return ResponseEntity.ok(toResponse(item));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemCardapioResponse>> listarPorRestaurante(@RequestParam UUID restauranteId) {
        List<ItemCardapioResponse> response = listarItensCardapioPorRestauranteUseCase.executar(restauranteId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCardapioResponse> atualizar(@PathVariable UUID id,
                                                          @Valid @RequestBody ItemCardapioRequest request) {
        AtualizarItemCardapioInput input = new AtualizarItemCardapioInput(
                request.nome(),
                request.descricao(),
                request.preco(),
                true,
                request.fotoPath()
        );

        ItemCardapio itemAtualizado = atualizarItemCardapioUseCase.executar(id, input);
        return ResponseEntity.ok(toResponse(itemAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirItemCardapioUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    private ItemCardapioResponse toResponse(ItemCardapio item) {
        return new ItemCardapioResponse(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.getFotoPath(),
                item.getRestauranteId()
        );
    }
}

