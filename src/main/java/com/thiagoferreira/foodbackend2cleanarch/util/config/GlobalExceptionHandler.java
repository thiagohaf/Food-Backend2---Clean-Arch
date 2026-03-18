package com.thiagoferreira.foodbackend2cleanarch.util.config;

import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.ItemCardapioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.cardapio.core.exception.RestauranteNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.TipoUsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.usuario.core.exception.UsuarioNaoEncontradoException;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.ValidacaoRegraNegocioException;
import com.thiagoferreira.foodbackend2cleanarch.util.exception.SystemBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TITLE_VALIDACAO = "Validação de regra de negócio";
    private static final String TITLE_NOT_FOUND = "Recurso não encontrado";
    private static final String DETAIL_ERRO_INESPERADO = "Erro inesperado";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + (err.getDefaultMessage() != null ? err.getDefaultMessage() : "inválido"))
                .collect(Collectors.joining("; "));
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problem.setTitle("Erro de validação");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(ValidacaoRegraNegocioException.class)
    public ResponseEntity<ProblemDetail> handleValidacaoRegraNegocio(ValidacaoRegraNegocioException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage() != null ? ex.getMessage() : TITLE_VALIDACAO
        );
        problem.setTitle(TITLE_VALIDACAO);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
    }

    @ExceptionHandler(SystemBaseException.class)
    public ResponseEntity<ProblemDetail> handleSystemBaseException(SystemBaseException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getHttpStatus());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problem.setTitle(status.getReasonPhrase());
        problem.setProperty("code", ex.getCode());
        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler({
            RestauranteNaoEncontradoException.class,
            UsuarioNaoEncontradoException.class,
            TipoUsuarioNaoEncontradoException.class,
            ItemCardapioNaoEncontradoException.class
    })
    public ResponseEntity<ProblemDetail> handleNaoEncontrado(Exception ex) {
        String detail = ex.getMessage() != null ? ex.getMessage() : TITLE_NOT_FOUND;
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail);
        problem.setTitle(TITLE_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleErroInesperado(Exception ex) {
        ex.printStackTrace();
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                DETAIL_ERRO_INESPERADO
        );
        problem.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}
