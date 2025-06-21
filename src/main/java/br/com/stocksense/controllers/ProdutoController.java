package br.com.stocksense.controllers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.stocksense.DAO.ProdutoDAO;
import br.com.stocksense.model.Produto;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoDAO produtoRepository;

    // Formulário de inserção
    @GetMapping("/inserirProduto")
    public String inserirProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/inserirProduto";
    }

    // Processar inserção
    @PostMapping("/InsertProduto")
    public String insertProduto(@ModelAttribute Produto produto,
                              @RequestParam(value = "imagemProduto", required = false) MultipartFile imagemFile,
                              RedirectAttributes redirectAttributes) {

        // Validação de código único
        if (produtoRepository.existsByCodigo(produto.getCodigo())) {
            redirectAttributes.addFlashAttribute("erro", "Já existe um produto com este código!");
            return "redirect:/inserirProduto";
        }

        // Configuração de datas
        produto.setDataCadastro(LocalDate.now());
        produto.setUltimaAtualizacao(LocalDateTime.now());
        produto.setAtivo(true);

        // Processamento de imagem como ícone
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                processarImagemComoIcone(imagemFile, produto);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar imagem: " + e.getMessage());
                return "redirect:/inserirProduto";
            }
        }

        try {
            produtoRepository.save(produto);
            redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar produto. Verifique os dados.");
            return "redirect:/inserirProduto";
        }

        return "redirect:/produtos-adicionados";
    }

    // Método para processar imagem como ícone
    private void processarImagemComoIcone(MultipartFile imagemFile, Produto produto) throws IOException {
        String nomeArquivo = UUID.randomUUID().toString() + ".png";
        String diretorioDestino = "src/main/resources/static/img/icons/";
        Path caminhoIcone = Paths.get(diretorioDestino + nomeArquivo);

        Files.createDirectories(caminhoIcone.getParent());
        
        BufferedImage imagemOriginal = ImageIO.read(imagemFile.getInputStream());
        BufferedImage imagemRedimensionada = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = imagemRedimensionada.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(imagemOriginal, 0, 0, 256, 256, null);
        g.dispose();
        
        ImageIO.write(imagemRedimensionada, "png", caminhoIcone.toFile());
        
        produto.setImagens("/img/icons/" + nomeArquivo);
    }

    // Listagem de produtos
    @GetMapping("/produtos-adicionados")
    public String listagemProdutos(Model model) {
        model.addAttribute("produtosList", produtoRepository.findAll());
        return "produto/listProduto";
    }

    // Formulário de alteração
    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Produto/alterar");
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
        mv.addObject("produto", produto);
        return mv;
    }

    @PostMapping("/alterar")
    public ModelAndView alterar(@ModelAttribute Produto produto,
                              @RequestParam(value = "imagemProduto", required = false) MultipartFile imagemFile,
                              RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

        // Busca o produto existente no banco
        Optional<Produto> produtoExistenteOptional = produtoRepository.findById(produto.getId());

        if (!produtoExistenteOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
            mv.setViewName("redirect:/produtos-adicionados");
            return mv;
        }

        Produto produtoExistente = produtoExistenteOptional.get();

        // Processamento do ícone
        if (imagemFile != null && !imagemFile.isEmpty()) {
            try {
                processarImagemComoIcone(imagemFile, produto);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar imagem: " + e.getMessage());
                mv.setViewName("redirect:/produtos-adicionados");
                return mv;
            }
        } else {
            // Mantém o ícone antigo se nenhum novo foi enviado
            produto.setImagens(produtoExistente.getImagens());
        }

        // Preserva a data de cadastro e atualiza a última atualização
        produto.setDataCadastro(produtoExistente.getDataCadastro());
        produto.setUltimaAtualizacao(LocalDateTime.now());

        // Salva alterações
        produtoRepository.save(produto);

        redirectAttributes.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        mv.setViewName("redirect:/produtos-adicionados");
        return mv;
    }

    // Excluir produto
    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            produtoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir produto");
        }
        return "redirect:/produtos-adicionados";
    }
}