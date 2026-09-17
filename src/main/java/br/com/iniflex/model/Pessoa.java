package br.com.iniflex.model;

import java.time.LocalDate;
import java.util.Objects;

public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = Objects.requireNonNull(nome, "nome");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "dataNascimento");
    }

    public String getNome() { return nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    public void setNome(String nome) { this.nome = Objects.requireNonNull(nome, "nome"); }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "dataNascimento");
    }
}
