package com.breakfast.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breakfast.domain.exception.EntidadeNaoEncontradaException;
import com.breakfast.domain.exception.NegocioException;
import com.breakfast.domain.model.CafeDaManha;
import com.breakfast.domain.model.Cliente;
import com.breakfast.domain.model.Produto;
import com.breakfast.domain.repository.ClienteRepository;
import com.breakfast.domain.repository.ProdutoRepository;
import com.breakfast.domain.repository.CafeDaManhaRepository;

@Service
public class GestaoCafeDaManhaService {
	
	@Autowired
	private CafeDaManhaRepository cafeDaManhaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public CafeDaManha criar(CafeDaManha cafeDaManha) {
		
		Cliente cliente = clienteRepository.findById(cafeDaManha.getCliente().getId())
							.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		cafeDaManha.setCliente(cliente);
		
		return cafeDaManhaRepository.save(cafeDaManha);
	}

	public Produto adicionarProduto(Long cafeManhaId, String descricao) {
		
		CafeDaManha cafeDaManha = buscar(cafeManhaId);

		Produto produto = new Produto();
		produto.setDescricao(descricao);
		produto.setCafeDaManha(cafeDaManha);
		
		return produtoRepository.save(produto);
	}
	
	private CafeDaManha buscar(Long ordemServicoId) {
		return cafeDaManhaRepository.findById(ordemServicoId)
										.orElseThrow(() -> new EntidadeNaoEncontradaException("Cafe da manhã não encontrado"));
	}
}
