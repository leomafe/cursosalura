package br.com.alura.escolaalura.model;

public class Curso {

    private String nome;

    public Curso() {}

    public Curso(String nome) {
        this.nome =  nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}