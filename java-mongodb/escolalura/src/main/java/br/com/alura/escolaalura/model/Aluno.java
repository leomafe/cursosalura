package br.com.alura.escolaalura.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aluno {

    private ObjectId id;

    private String nome;

    private Date dataNascimento;

    private Curso curso;

    private List<Nota> notas;

    private Contato contato;

    private  List<Habilidade> habilidades;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Nota> getNotas() {
        this.notas = this.notas != null ? notas : new ArrayList<>();
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Habilidade> getHabilidades() {
        this.habilidades = this.habilidades !=null ? habilidades : new ArrayList<>();
        return habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

    public Contato getContato() {
        return contato;
    }
    public void setContato(Contato contato) {
        this.contato = contato;
    }
    //

    public Aluno criarId() {
        this.id = new ObjectId();
        return this;
    }

    public void adicionar(Habilidade habilidade) {
        getHabilidades().add(habilidade);

    }

    public void adicionar(Nota nota) {
        getNotas().add(nota);
    }


}
