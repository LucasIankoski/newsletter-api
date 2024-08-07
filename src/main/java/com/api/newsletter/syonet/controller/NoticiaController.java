package com.api.newsletter.syonet.controller;

import com.api.newsletter.syonet.application.NoticiaService;
import com.api.newsletter.syonet.dtos.NoticiaDTO;
import com.api.newsletter.syonet.entities.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    private final NoticiaService noticiaService;

    @Autowired
    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarNoticia(NoticiaDTO noticiaDTO){
        try {

            if(noticiaDTO.titulo().isEmpty())
                throw new IllegalArgumentException("O preenchimento do título é obrigatório");

            if(noticiaDTO.descricao().isEmpty())
                throw new IllegalArgumentException("O preenchimento da descrição é obrigatório");

            Noticia noticia = Noticia.builder()
                    .title(noticiaDTO.titulo())
                    .description(noticiaDTO.descricao())
                    .link(noticiaDTO.link())
                    .build();

            noticiaService.cadastrarNoticia(noticia);

            return new ResponseEntity<>("Notícia cadastrada com sucesso.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public List<Noticia> listarNoticiasNaoProcessadas(){
        return noticiaService.filtrarNaoProcessadas();
    }

}
