package br.com.alura.escolaalura.model;

public class Habilidade {

    public Habilidade(){}

    public Habilidade(String nome, String nivel){

        this.nome = nome;
        this.nivel = nivel;

    }

    private String nome;

    private String nivel;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
