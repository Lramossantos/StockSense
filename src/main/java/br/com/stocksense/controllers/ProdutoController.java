package br.com.stocksense.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
			RedirectAttributes redirectAttributes,
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

		// Configuração e salvamento
		produto.setDataCadastro(LocalDate.now());
		produto.setUltimaAtualizacao(LocalDateTime.now());
		produto.setAtivo(true);

		produtoRepository.save(produto);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		return "redirect:/produtos-adicionados";
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
	public ModelAndView alterar(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();

		// Busca o produto existente no banco
		Optional<Produto> produtoExistenteOptional = produtoRepository.findById(produto.getId());

		if (!produtoExistenteOptional.isPresent()) {
			redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
			mv.setViewName("redirect:/produtos-adicionados");
			return mv;
		}

		Produto produtoExistente = produtoExistenteOptional.get();

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