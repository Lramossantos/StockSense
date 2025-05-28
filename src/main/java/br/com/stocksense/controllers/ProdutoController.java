package br.com.stocksense.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.stocksense.model.Produto;
// import br.com.stocksense.repository.ProdutoRepository;

@Controller
public class ProdutoController {

    @GetMapping("/inserirProduto")
    public ModelAndView InsertProdutos(Produto produto) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("produto/inserirProduto");
        mv.addObject("produto", new Produto());
        return mv;
    }

    @PostMapping("/InsertProduto")
    public String insertProduto(@ModelAttribute Produto produto,
                                @RequestParam("imagemProduto") MultipartFile imagemFile) {

        if (!imagemFile.isEmpty()) {
            try {
                String nomeArquivo = imagemFile.getOriginalFilename();
                String diretorioDestino = "src/main/resources/static/img/produtos/";
                Path caminhoArquivo = Paths.get(diretorioDestino + nomeArquivo);

                Files.createDirectories(caminhoArquivo.getParent());
                Files.write(caminhoArquivo, imagemFile.getBytes());

                produto.setImagens("/img/produtos/" + nomeArquivo);

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/inserirProduto?erroImagem";
            }
        }        

        return "redirect:/produtos";
    }
}
