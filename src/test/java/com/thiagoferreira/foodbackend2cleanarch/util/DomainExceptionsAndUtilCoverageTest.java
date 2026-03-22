package com.thiagoferreira.foodbackend2cleanarch.util;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioCoreMapperException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteCoreMapperException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteDonoNaoExisteException;
import com.thiagoferreira.foodbackend2cleanarch.restaurante.core.exception.RestauranteExistenteException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioCoreMapperException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioCoreMapperException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import com.thiagoferreira.foodbackend2cleanarch.util.rule.ValidadorBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Cobertura de exceções de domínio e ValidadorBase")
class DomainExceptionsAndUtilCoverageTest {

    @Test
    void validacaoRegraNegocioExceptionExpoeContratoHttp() {
        ValidacaoRegraNegocioException ex = new ValidacaoRegraNegocioException("msg");
        assertThat(ex.getMessage()).isEqualTo("msg");
        assertThat(ex.getCode()).isEqualTo("REGRA_DE_NEGOCIO_VIOLADA");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
    }

    @Test
    void validadorBaseNaoPodeSerInstanciado() throws Exception {
        Constructor<ValidadorBase> ctor = ValidadorBase.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException wrapped = assertThrows(InvocationTargetException.class, ctor::newInstance);
        assertThat(wrapped.getCause()).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void validadorBaseRejeitaValorSoComEspacos() {
        assertThrows(ValidacaoRegraNegocioException.class,
                () -> ValidadorBase.validarCampoObrigatorio("   ", "campo"));
    }

    @Test
    void usuarioCoreMapperException() {
        var ex = new UsuarioCoreMapperException();
        assertThat(ex.getCode()).isEqualTo("usuario.usuarioDtoNulo");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).contains("Usuário");
    }

    @Test
    void tipoUsuarioCoreMapperException() {
        var ex = new TipoUsuarioCoreMapperException();
        assertThat(ex.getCode()).isEqualTo("usuario.tipoUsuarioDtoNulo");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).contains("Tipo de Usuário");
    }

    @Test
    void tipoUsuarioNaoEncontradoException() {
        var ex = new TipoUsuarioNaoEncontradoException();
        assertThat(ex.getCode()).isEqualTo("usuario.tipoUsuarioNaoExiste");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).contains("não foi encontrado");
    }

    @Test
    void usuarioNaoEncontradoException() {
        var ex = new UsuarioNaoEncontradoException();
        assertThat(ex.getCode()).isEqualTo("usuario.usuarioNaoExiste");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).contains("Usuário");
    }

    @Test
    void restauranteCoreMapperException() {
        var ex = new RestauranteCoreMapperException();
        assertThat(ex.getCode()).isEqualTo("restaurante.dtoNulo");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).contains("restaurante");
    }

    @Test
    void restauranteExistenteException() {
        var ex = new RestauranteExistenteException();
        assertThat(ex.getCode()).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).isNotNull();
    }

    @Test
    void restauranteDonoNaoExisteException() {
        var ex = new RestauranteDonoNaoExisteException();
        assertThat(ex.getCode()).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).isNotNull();
    }

    @Test
    void itemCardapioCoreMapperException() {
        var ex = new ItemCardapioCoreMapperException();
        assertThat(ex.getCode()).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).isNotNull();
    }

    @Test
    void itemCardapioExistenteException() {
        var ex = new ItemCardapioExistenteException();
        assertThat(ex.getCode()).isEqualTo("cardapio.itemCardapioJaExistente");
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).contains("cardápio");
    }

    @Test
    void itemCardapioNaoEncontradoException() {
        var ex = new ItemCardapioNaoEncontradoException();
        assertThat(ex.getCode()).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).isNotNull();
    }

    @Test
    void restauranteNaoEncontradoException() {
        var ex = new RestauranteNaoEncontradoException();
        assertThat(ex.getCode()).isNotNull();
        assertThat(ex.getHttpStatus()).isEqualTo(422);
        assertThat(ex.getMessage()).isNotNull();
    }
}
