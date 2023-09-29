package br.com.ajs.tarefas.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tarefas")
public class Tarefas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String nomeTarefa;
	
	@Column
	private BigDecimal custo;
	
	@Column
	private String dataLimite;
	
	@Column
	private Integer ordemApresentacao;
	
	@Column
	private Boolean flag;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeTarefa() {
		return nomeTarefa;
	}
	public void setNomeTarefa(String nomeTarefa) {
		this.nomeTarefa = nomeTarefa;
	}
	public BigDecimal getCusto() {
		return custo;
	}
	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	public String getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(String dataLimite) {
		this.dataLimite = dataLimite;
	}
	public Integer getOrdemApresentacao() {
		return ordemApresentacao;
	}
	public void setOrdemApresentacao(Integer ordemApresentacao) {
		this.ordemApresentacao = ordemApresentacao;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
}
