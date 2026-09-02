package com.hotel.reservas.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()).toList();

        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                        "Erro de Validação",
                        mensagens);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ReservaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> tratarReservaNaoEncontrada(ReservaNaoEncontradaException ex) {
        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                        "Reserva não encontrada",
                        List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> tratarErroConversao(MethodArgumentTypeMismatchException ex) {
        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                        "Parâmetro inválido",
                        List.of("O valor informado para '" + ex.getName() + "' é inválido."));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "Usuário não encontrado",
                List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<ErrorResponse> tratarOperacaoNaoPermitida(OperacaoNaoPermitidaException ex) {
        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
                "Operação não permitida",
                List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(SenhaAtualIncorretaException.class)
    public ResponseEntity<ErrorResponse> tratarSenhaAtualIncorreta(SenhaAtualIncorretaException ex) {
        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Senha incorreta",
                List.of(ex.getMessage()));


        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> tratarEmailJaCadastrado(
            EmailJaCadastradoException ex) {

        ErrorResponse response =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
                "Conflito",
                List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> tratarJsonInvalido(HttpMessageNotReadableException ex) {

        ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                Collections.singletonList("O JSON enviado para é inválido ou possui um valor incompatível."));

        return ResponseEntity.badRequest().body(response);
    }
}
