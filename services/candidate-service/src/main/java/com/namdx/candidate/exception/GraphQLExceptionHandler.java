package com.namdx.candidate.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        switch (ex) {
            case EntityNotFoundException entityNotFoundException -> {
                return buildError(ErrorType.NOT_FOUND, ex.getMessage(), env);
            }
            case AccessDeniedException accessDeniedException -> {
                return buildError(ErrorType.FORBIDDEN, ex.getMessage(), env);
            }
            case IllegalStateException illegalStateException -> {
                return buildError(ErrorType.BAD_REQUEST, ex.getMessage(), env);
            }
            case IllegalArgumentException illegalArgumentException -> {
                return buildError(ErrorType.BAD_REQUEST, ex.getMessage(), env);
            }
            case ConstraintViolationException constraintViolationException -> {
                return buildError(ErrorType.BAD_REQUEST, "Validation failed: " + ex.getMessage(), env);
            }
            case MethodArgumentNotValidException validationEx -> {
                Map<String, Object> extensions = new HashMap<>();
                validationEx.getBindingResult().getFieldErrors().forEach(error ->
                        extensions.put(error.getField(), error.getDefaultMessage())
                );
                return GraphqlErrorBuilder.newError(env)
                        .message("Validation failed")
                        .errorType(ErrorType.BAD_REQUEST)
                        .extensions(extensions)
                        .build();
            }
            default -> {
            }
        }

        return buildError(ErrorType.INTERNAL_ERROR, "An unexpected error occurred: " + ex.getMessage(), env);
    }

    private GraphQLError buildError(ErrorType errorType, String message, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .message(message)
                .errorType(errorType)
                .build();
    }
}