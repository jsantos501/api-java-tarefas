package br.com.ajs.tarefas.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.ajs.tarefas.model.Tarefas;
import br.com.ajs.tarefas.repository.TarefasRepository;

@Service
public class TarefasServiceImpl implements TarefasService {
	
	public final BigDecimal MIL = new BigDecimal("1000.00");

	@Autowired
	private TarefasRepository tarefasRepository;
	
	
	
	private boolean maiorQueMil(Tarefas tarefa) {
		
		return MIL.compareTo(tarefa.getCusto()) <= 0;
	}

	@Override
	public List<Tarefas> findAll() {
		List<Tarefas> resultado = tarefasRepository.findAll();
		
		return resultado.stream()
				.sorted((o1, o2) -> o1.getOrdemApresentacao().compareTo(o2.getOrdemApresentacao()))
				.collect(Collectors.toList());
	}
	@Override
	public Optional<Tarefas> findById(Long id) {
		return tarefasRepository.findById(id);
	}
	@Override
	public  Optional<Tarefas> save(Tarefas tarefa) {
		List<Tarefas> lista = tarefasRepository.findAll();
		Integer maxOrdemApresentacao = -1;
		
		if(lista.size() > 0) {
			maxOrdemApresentacao = lista.stream()
				.map(o1 -> o1.getOrdemApresentacao())
				.max(Comparator.naturalOrder())
				.get();
		}
		
		if(tarefa != null && tarefa.getNomeTarefa() != null && !nomeTarefaDuplicado(tarefa)) {
			tarefa.setOrdemApresentacao(++maxOrdemApresentacao);
			tarefa.setFlag(maiorQueMil(tarefa));
			return Optional.of(tarefasRepository.save(tarefa));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<Tarefas> update(Long id, Tarefas tarefa) {
			
		 return tarefasRepository.findById(id)
			 .map(reg -> {
				 reg.setNomeTarefa(tarefa.getNomeTarefa());
				 reg.setCusto(tarefa.getCusto());
				 reg.setDataLimite(tarefa.getDataLimite());
				 return Optional.of(tarefasRepository.save(reg));})
			 .orElse(Optional.empty());
	}
	
	public Boolean nomeTarefaDuplicado(Tarefas tarefa){
		return tarefasRepository.findAll().stream()
				.filter(o1 -> o1.getNomeTarefa().equalsIgnoreCase(tarefa.getNomeTarefa()))
				.findFirst()
				.isPresent();
	}
	
	@Override
	public Optional<Tarefas> exclude(Long id) {
		return tarefasRepository.findById(id)
				.map(registro -> {
					tarefasRepository.deleteById(id);
					ResponseEntity.noContent().build();
					return Optional.of(registro);})
				.orElse(Optional.empty());		
	}

	@Override
	public List<Tarefas> saveSort(List<Tarefas> lista) {
		return tarefasRepository.saveAll(lista);
	}
	
}
