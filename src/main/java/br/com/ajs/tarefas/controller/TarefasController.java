package br.com.ajs.tarefas.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.com.ajs.tarefas.repository.TarefasRepository;

@RestController
@RequestMapping("/api/tarefas")
public class TarefasController {

	
	@Autowired
	private TarefasRepository tarefasRepository;
	
	@GetMapping("/")
	public List<Tarefas> findAll() {
		
		List<Tarefas> resultado = tarefasRepository.findAll();
		
		return resultado.stream()
				.sorted((o1, o2) -> o1.getOrdemApresentacao().compareTo(o2.getOrdemApresentacao()))
				.collect(Collectors.toList());
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tarefas> find(@PathVariable Long id) {
		
		return tarefasRepository.findById(id)
			.map(resultado -> ResponseEntity.ok().body(resultado))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/")
	public ResponseEntity<Tarefas> include(@RequestBody Tarefas tarefa) {
		List<Tarefas> lista = tarefasRepository.findAll();
		Integer maxOrdemApresentacao = 0;
		
		if(lista.size() > 0) {
		
			maxOrdemApresentacao = lista.stream()
				.map(o1 -> o1.getOrdemApresentacao())
				.max(Comparator.naturalOrder())
				.get();
				
		}
		
		if(tarefa != null && tarefa.getNomeTarefa() != null && !nomeTarefaDuplicado(tarefa)) {
			tarefa.setOrdemApresentacao(++maxOrdemApresentacao);
			return new ResponseEntity<Tarefas>(tarefasRepository.save(tarefa), 
					HttpStatus.CREATED);
			
		}else {
			return ResponseEntity.badRequest().body(null);
		}
	
	}


	
	@PutMapping("/{id}")
	public ResponseEntity<Tarefas> update(@PathVariable Long id, @RequestBody Tarefas tarefa) {

	if(nomeTarefaDuplicado(tarefa)) 
		return ResponseEntity.badRequest().build();
		
	return tarefasRepository.findById(id)
		.map(registroEcontrado -> {
			registroEcontrado.setNomeTarefa(tarefa.getNomeTarefa());
			registroEcontrado.setCusto(tarefa.getCusto());
			registroEcontrado.setDataLimite(tarefa.getDataLimite());
		
			return ResponseEntity.ok().body(tarefasRepository.save(registroEcontrado));})

		.orElse(ResponseEntity.notFound().build());

	}
	
	private Boolean nomeTarefaDuplicado(Tarefas tarefa){
		return tarefasRepository.findAll().stream()
				.filter(o1 -> o1.getNomeTarefa().equalsIgnoreCase(tarefa.getNomeTarefa()))
				.findFirst()
				.isPresent();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> exclude(@PathVariable Long id) {
		
		return tarefasRepository.findById(id)
					.map(registro -> {
						tarefasRepository.deleteById(id);
						return ResponseEntity.noContent().build();})
			
					.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/reordenar")
	public List<Tarefas> reSort(@RequestBody List<Tarefas> lista) {
		
		List<Tarefas> resultado = tarefasRepository.saveAll(lista);
	
		return resultado;
	}
	
}


