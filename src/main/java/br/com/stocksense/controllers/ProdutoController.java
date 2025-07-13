package br.com.stocksense.controllers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.stocksense.DAO.ProdutoDAO;
import br.com.stocksense.model.Produto;
import jakarta.validation.Valid;

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

	@PostMapping("/InsertProduto")
	public String insertProduto(@Valid @ModelAttribute Produto produto, BindingResult bindingResult,
			@RequestParam("imagemProduto") MultipartFile imagemFile, RedirectAttributes redirectAttributes,
			Model model) {

		// Validação dos campos do formulário
		if (bindingResult.hasErrors()) {
			return "produto/inserirProduto";
		}

		// Validação de código único
		if (produtoRepository.existsByCodigo(produto.getCodigo())) {
			redirectAttributes.addFlashAttribute("erro", "Já existe um produto com este código!");
			return "redirect:/inserirProduto";
		}

		// Validação da imagem
		if (imagemFile.isEmpty()) {
			redirectAttributes.addFlashAttribute("erro", "Selecione uma imagem para o produto!");
			return "redirect:/inserirProduto";
		}

		// Verifica tipo de arquivo
		String contentType = imagemFile.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			redirectAttributes.addFlashAttribute("erro", "Arquivo não é uma imagem válida!");
			return "redirect:/inserirProduto";
		}

		// Processamento de imagem
		try {
			String caminhoImagem = salvarImagem(imagemFile);
			produto.setImagens(caminhoImagem);
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao processar imagem: " + e.getMessage());
			return "redirect:/inserirProduto";
		}

		// Configuração e salvamento
		produto.setDataCadastro(LocalDate.now());
		produto.setUltimaAtualizacao(LocalDateTime.now());
		produto.setAtivo(true);

		produtoRepository.save(produto);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		return "redirect:/produtos-adicionados";
	}

	private String salvarImagem(MultipartFile imagemFile) throws IOException {
		// Diretório dentro do projeto (melhor para desenvolvimento)
		String diretorioDestino = "upload-dir/"; // Crie esta pasta na raiz do projeto
		Path uploadPath = Paths.get(diretorioDestino);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String nomeArquivo = UUID.randomUUID() + "_" + imagemFile.getOriginalFilename();
		Path caminhoCompleto = uploadPath.resolve(nomeArquivo);

		// Salva a imagem original (opcional: pode redimensionar aqui)
		Files.copy(imagemFile.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

		return "/uploads/" + nomeArquivo; // Caminho relativo para acesso via web
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
		Produto produto = produtoRepository.findById(id).orElseThrow(
				() -> new RuntimeException("Produto não encontrado com id: " + id));
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
				String caminhoImagem = salvarImagem(imagemFile);
				produto.setImagens(caminhoImagem);
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
	
	@GetMapping("filtro-alunos")
	public ModelAndView filtroProdutos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Produto/filtroProdutos");
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}