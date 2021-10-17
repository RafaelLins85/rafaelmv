package com.breakfast.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breakfast.domain.exception.NegocioException;
import com.breakfast.domain.model.Cliente;
import com.breakfast.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository repositorio;
	
	public Cliente salvar(Cliente cliente) {
		
		Cliente clienteExistente = repositorio.findByCpf(cliente.getCpf());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com este CPF.");
		}
		
		return repositorio.save(cliente);
	}

	public void excluir(Long clienteId) {
		repositorio.deleteById(clienteId);
	}
	
	
}
