package br.com.alura.escolaalura.controller;

import br.com.alura.escolaalura.model.Aluno;
import br.com.alura.escolaalura.model.Habilidade;
import br.com.alura.escolaalura.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HabilidadeControler {
    
    @Autowired
    private AlunoRepository repository;

    @GetMapping("/habilidade/cadastrar/{id}")
    public String cadastrar(@PathVariable String id, Model model) {

        Aluno aluno = repository.obterAlunoPor(id);
        model.addAttribute("aluno", aluno);
        model.addAttribute("habilidade", new Habilidade());
        return "habilidade/cadastrar";
    }

    @PostMapping("habilidade/salvar/{id}")
    public String salvar(@PathVariable String id, @ModelAttribute Habilidade habilidade) {

        Aluno aluno = repository.obterAlunoPor(id);
        aluno.adicionar(habilidade);
        repository.salvar(aluno);
        return  "redirect:/aluno/listar";
    }
}
