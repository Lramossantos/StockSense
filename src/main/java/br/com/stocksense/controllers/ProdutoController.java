package br.com.stocksense.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;  // Adicione esta linha no topo do arquivo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.stocksense.DAO.ProdutoDAO;
import br.com.stocksense.model.Produto;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoDAO produtoRepository;

    @GetMapping("/inserirProduto")
    public String inserirProdutoForm(@ModelAttribute Produto produto, Model model) {
        model.addAttribute("produto", produto == null ? new Produto() : produto);
        return "produto/inserirProduto";
    }

    @PostMapping("/InsertProduto")
    public ModelAndView insertProduto(@ModelAttribute Produto produto,
            @RequestParam(value = "imagemProduto", required = false) MultipartFile imagemFile,
            RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        // Verificar se o código já existe (CORREÇÃO: usando existsByCodigo em vez de existsById)
        if (produtoRepository.existsByCodigo(produto.getCodigo())) {
            redirectAttributes.addFlashAttribute("erro", "Já existe um produto com este código!");
            mv.setViewName("redirect:/inserirProduto");
            return mv;
        }

        // Configuração de datas
        if (produto.getId() == null) {
            produto.setDataCadastro(LocalDate.now());
            produto.setAtivo(true);
        }
        produto.setUltimaAtualizacao(LocalDateTime.now());

        // Processamento de imagem
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                String extensao = imagemFile.getOriginalFilename()
                    .substring(imagemFile.getOriginalFilename().lastIndexOf("."));
                String nomeArquivo = UUID.randomUUID().toString() + extensao;
                
                String diretorioDestino = "src/main/resources/static/img/produtos/";
                Path caminhoArquivo = Paths.get(diretorioDestino + nomeArquivo);

                Files.createDirectories(caminhoArquivo.getParent());
                Files.write(caminhoArquivo, imagemFile.getBytes());

                produto.setImagens("/img/produtos/" + nomeArquivo);

            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("erro", "Erro ao salvar imagem");
                mv.setViewName("redirect:/inserirProduto");
                return mv;
            }
        }

        try {
            produtoRepository.save(produto);
            redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar produto. Verifique os dados.");
            mv.setViewName("redirect:/inserirProduto");
            return mv;
        }

        mv.setViewName("redirect:/produtos-adicionados");
        return mv;
    }

    @GetMapping("produtos-adicionados")
    public ModelAndView listagemProdutos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Produto/listProduto");
        mv.addObject("produtosList", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        
        try {
            Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
            mv.setViewName("Produto/alterar");
            mv.addObject("produto", produto);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            mv.setViewName("redirect:/produtos-adicionados");
        }
        
        return mv;
    }

    @PostMapping("/alterar")
    public ModelAndView alterar(@ModelAttribute Produto produto,
            @RequestParam(value = "imagemProduto", required = false) MultipartFile imagemFile,
            RedirectAttributes redirectAttributes) {
        
        ModelAndView mv = new ModelAndView();

        // Verificar se outro produto já tem esse código (CORREÇÃO: usando Optional)
        Optional<Produto> produtoExistente = produtoRepository.findByCodigo(produto.getCodigo());
        if (produtoExistente.isPresent() && !produtoExistente.get().getId().equals(produto.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Já existe outro produto com este código!");
            mv.setViewName("redirect:/alterar/" + produto.getId());
            return mv;
        }

        // Atualizar data de modificação
        produto.setUltimaAtualizacao(LocalDateTime.now());

        // Processamento de imagem (se fornecida)
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                // Remove imagem antiga se existir
                if (produto.getImagens() != null && !produto.getImagens().isEmpty()) {
                    Path caminhoAntigo = Paths.get("src/main/resources/static" + produto.getImagens());
                    Files.deleteIfExists(caminhoAntigo);
                }

                // Gera nome único para o novo arquivo
                String extensao = imagemFile.getOriginalFilename()
                    .substring(imagemFile.getOriginalFilename().lastIndexOf("."));
                String nomeArquivo = UUID.randomUUID().toString() + extensao;
                
                String diretorioDestino = "src/main/resources/static/img/produtos/";
                Path caminhoArquivo = Paths.get(diretorioDestino + nomeArquivo);

                Files.createDirectories(caminhoArquivo.getParent());
                Files.write(caminhoArquivo, imagemFile.getBytes());

                produto.setImagens("/img/produtos/" + nomeArquivo);

            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar imagem");
                mv.setViewName("redirect:/alterar/" + produto.getId());
                return mv;
            }
        }

        try {
            produtoRepository.save(produto);
            redirectAttributes.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar produto. Verifique os dados.");
            mv.setViewName("redirect:/alterar/" + produto.getId());
            return mv;
        }

        mv.setViewName("redirect:/produtos-adicionados");
        return mv;
    }
    
    @GetMapping ("/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Integer id) {
    	produtoRepository.deleteById(id);
    	return "redirect:/produtos-adicionados";
    }
}