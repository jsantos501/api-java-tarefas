package br.com.ajs.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ajs.tarefas.model.Tarefas;

public interface TarefasRepository extends JpaRepository<Tarefas, Long>{

}
