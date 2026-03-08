package com.thiagoferreira.foodbackend2cleanarch.cardapio.core.rule;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.domain.ItemCardapio;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.ValidacaoRegraNegocioException;

import java.math.BigDecimal;

import static com.thiagoferreira.foodbackend2cleanarch.util.rule.ValidadorBase.validarCampoObrigatorio;

public class ItemCardapioValidador {

    public static void validar(ItemCardapio item) {
        if (item == null) {
            throw new ValidacaoRegraNegocioException("O item do cardápio não pode ser nulo.");
        }

        validarCampoObrigatorio(item.getNome(), "Nome do Item");
        validarCampoObrigatorio(item.getDescricao(), "Descrição");

        validarPreco(item.getPreco());

        if (item.getRestauranteId() == null) {
            throw new ValidacaoRegraNegocioException("O item deve pertencer a um restaurante válido (ID não pode ser nulo).");
        }
    }

    private static void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacaoRegraNegocioException("O preço do item deve ser obrigatório e maior que zero.");
        }
    }
}
