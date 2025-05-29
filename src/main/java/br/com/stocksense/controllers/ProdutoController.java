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

import br.com.stocksense.model.Produto;
import br.com.stocksense.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/inserirProduto")
	public String inserirProdutoForm(@ModelAttribute Produto produto) {
		return "produto/inserirProduto";
	}

	@PostMapping("/InsertProduto")
	public String insertProduto(@ModelAttribute Produto produto,
			@RequestParam("imagemProduto") MultipartFile imagemFile) {

		// Definir dataCadastro e ultimaAtualizacao
		if (produto.getId() == null) {
			produto.setDataCadastro(LocalDate.now());
			produto.setAtivo(true); // se quiser definir ativo padrão
		}
		produto.setUltimaAtualizacao(LocalDateTime.now());

		// Salvar imagem
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

		// Salvar produto no banco
		produtoRepository.save(produto);

		return "redirect:/produtos";
	}
}
