package br.com.ajs.tarefas.service;

import java.util.List;
import java.util.Optional;

import br.com.ajs.tarefas.model.Tarefas;

public interface TarefasService {

	public List<Tarefas> findAll();

	public Optional<Tarefas> findById(Long id);

	public Optional<Tarefas> save(Tarefas tarefa);

	public Optional<Tarefas> update(Long id, Tarefas tarefa);

	public Boolean nomeTarefaDuplicado(Tarefas tarefa);;

	public Optional<Tarefas> exclude(Long id);

	public List<Tarefas> saveSort(List<Tarefas> lista);

}
