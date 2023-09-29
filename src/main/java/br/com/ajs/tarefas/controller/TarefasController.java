package br.com.ajs.tarefas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ajs.tarefas.model.Tarefas;
import br.com.ajs.tarefas.service.TarefasService;

@RestController
@RequestMapping("/api/tarefas")
public class TarefasController {

	@Autowired
	private TarefasService service;
	
	
	@GetMapping("/")
	public List<Tarefas> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tarefas> find(@PathVariable Long id) {
		return service.findById(id)
			.map(resultado -> ResponseEntity.ok().body(resultado))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/")
	public ResponseEntity<Tarefas> include(@RequestBody Tarefas tarefa) {
		return service.save(tarefa)
			.map(resultado -> ResponseEntity.ok().body(resultado))
			.orElse(ResponseEntity.badRequest().build());
	}


	
	@PutMapping("/{id}")
	public ResponseEntity<Tarefas> update(@PathVariable Long id, @RequestBody Tarefas tarefa) {

		if(service.nomeTarefaDuplicado(tarefa)) 
			return ResponseEntity.badRequest().build();
			
		return service.update(id, tarefa)
		  	.map(reg -> ResponseEntity.ok().body(reg))
		  	.orElse(ResponseEntity.notFound().build());
	  
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> exclude(@PathVariable Long id) {
		return service.exclude(id)
			  	.map(reg -> ResponseEntity.noContent().build())
			  	.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/reordenar")
	public List<Tarefas> reSort(@RequestBody List<Tarefas> lista) {
		return service.saveSort(lista);
	}
}
