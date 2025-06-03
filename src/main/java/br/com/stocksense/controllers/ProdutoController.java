package br.com.stocksense.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.stocksense.DAO.ProdutoDAO;
import br.com.stocksense.model.Produto;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoDAO produtoRepository;

    @GetMapping("/inserirProduto")
    public String inserirProdutoForm(@ModelAttribute Produto produto) {
        return "produto/inserirProduto";
    }

    @PostMapping("/InsertProduto")
    public ModelAndView insertProduto(@ModelAttribute Produto produto,
            @RequestParam(value = "imagemProduto", required = false) MultipartFile imagemFile) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/Produto/listProduto");

        // Definir dataCadastro e ultimaAtualizacao
        if (produto.getId() == null) {
            produto.setDataCadastro(LocalDate.now());
            produto.setAtivo(true);
        }
        produto.setUltimaAtualizacao(LocalDateTime.now());

        // Salvar imagem (se fornecida)
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                String nomeArquivo = imagemFile.getOriginalFilename();
                String diretorioDestino = "src/main/resources/static/img/produtos/";
                Path caminhoArquivo = Paths.get(diretorioDestino + nomeArquivo);

                Files.createDirectories(caminhoArquivo.getParent());
                Files.write(caminhoArquivo, imagemFile.getBytes());

                produto.setImagens("/img/produtos/" + nomeArquivo);

            } catch (IOException e) {
                e.printStackTrace();
                mv.addObject("erro", "Erro ao salvar imagem");
                return mv;
            }
        }

        // Salvar produto no banco
        produtoRepository.save(produto);

        return mv;
    }
}