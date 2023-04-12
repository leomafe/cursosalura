package br.com.alura.escolaalura.controller;

import br.com.alura.escolaalura.model.Aluno;
import br.com.alura.escolaalura.repository.AlunoRepository;
import br.com.alura.escolaalura.service.GeolocalizacaoService;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private GeolocalizacaoService geolocalizacaoService;

    @GetMapping("/aluno/cadastrar")
    public String cadastrar(Model model) {

        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastrar";
    }

    @PostMapping("/aluno/salvar")
    public String salvar(@ModelAttribute Aluno aluno) throws IOException, InterruptedException, ApiException {
        List<Double> latELong = geolocalizacaoService.obterLatELongPor(aluno.getContato());
        aluno.getContato().setCoordinates(latELong);
        repository.salvar(aluno);
        return  "redirect:/";
    }

    @GetMapping("aluno/listar")
    public String listar(Model model)  {
        List<Aluno> alunos = repository.obterTodosAlunos();
        model.addAttribute("alunos", alunos);
        return "aluno/listar";
    }

    @GetMapping("aluno/visualizar/{id}")
    public String visualizar(@PathVariable String id, Model model)  {
        Aluno aluno = repository.obterAlunoPor(id);
        model.addAttribute("aluno", aluno);
        return "aluno/visualizar";
    }

    @GetMapping("aluno/pesquisarnome")
    public String pesquisarNome()  {
        return "aluno/pesquisarnome";
    }

    @GetMapping("aluno/pesquisar")
    public String pesquisar(@RequestParam("nome") String nome, Model model)  {
        List<Aluno> alunos = repository.pesquisarPor(nome);
        model.addAttribute("alunos", alunos);
        return "aluno/pesquisarnome";
    }
}
