package com.breakfast.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.breakfast.api.model.ProdutoInput;
import com.breakfast.api.model.ProdutoModel;
import com.breakfast.domain.exception.EntidadeNaoEncontradaException;
import com.breakfast.domain.model.Produto;
import com.breakfast.domain.model.CafeDaManha;
import com.breakfast.domain.repository.CafeDaManhaRepository;
import com.breakfast.domain.service.GestaoCafeDaManhaService;

@RestController
@RequestMapping("/cafes-manha/{cafeManhaId}/produtos")
public class ProdutoController {
	
	@Autowired
	private GestaoCafeDaManhaService gestaoCafeDaManha;
	
	@Autowired
	private CafeDaManhaRepository cafeDaManhaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long cafeManhaId, 
										@Valid @RequestBody ProdutoInput produtoInput ) {
		
		Produto produto = gestaoCafeDaManha.adicionarProduto(cafeManhaId, produtoInput.getDescricao());
		
		return toModel(produto);
	}
	
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long cafeManhaId) {
		CafeDaManha cafeDaManha = cafeDaManhaRepository.findById(cafeManhaId)
										.orElseThrow(() -> new EntidadeNaoEncontradaException("Café da manhã não encontrado"));
		
		return toCollectionModel(cafeDaManha.getProdutos());
	}

	private List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
		return produtos.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}

	private ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}

}
