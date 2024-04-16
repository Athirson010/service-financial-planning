package io.github.athirson010.financialPlanning.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Erro {
    private final List<String> errosEncontrados = new ArrayList<>();
}
