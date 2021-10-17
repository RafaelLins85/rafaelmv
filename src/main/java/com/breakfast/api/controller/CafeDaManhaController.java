package com.breakfast.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.breakfast.api.model.CafeDaManhaInput;
import com.breakfast.api.model.CafeDaManhaModel;
import com.breakfast.domain.model.CafeDaManha;
import com.breakfast.domain.repository.CafeDaManhaRepository;
import com.breakfast.domain.service.GestaoCafeDaManhaService;

@RestController
@RequestMapping("/cafes-manha")
public class CafeDaManhaController {

	@Autowired
	private GestaoCafeDaManhaService gestaoCafeDaManha;
	
	@Autowired
	private CafeDaManhaRepository cafeDaManhaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CafeDaManhaModel criar(@Valid @RequestBody CafeDaManhaInput cafeDaManhaInput) {
		
		CafeDaManha cafeDaManha = toEntity(cafeDaManhaInput);
		
		return toModel(gestaoCafeDaManha.criar(cafeDaManha));
	}
	
	@GetMapping
	public List<CafeDaManhaModel> listar() {
		return toCollectionModel(cafeDaManhaRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<CafeDaManhaModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<CafeDaManha> cafeDaManha = cafeDaManhaRepository.findById(ordemServicoId);
		
		if(cafeDaManha.isPresent()) {
			CafeDaManhaModel cafeDaManhaModel = toModel(cafeDaManha.get());
			
			return  ResponseEntity.ok(cafeDaManhaModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private CafeDaManhaModel toModel(CafeDaManha cafeDaManha) {
		return modelMapper.map(cafeDaManha, CafeDaManhaModel.class);
	}
	
	private List<CafeDaManhaModel> toCollectionModel(List<CafeDaManha> ordensServico) {
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	private CafeDaManha toEntity(CafeDaManhaInput cafeDaManhaInput) {
		return modelMapper.map(cafeDaManhaInput, CafeDaManha.class);
	}
}

