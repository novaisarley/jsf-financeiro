package com.arley.financeiro.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.arley.financeiro.model.Lancamento;
import com.arley.financeiro.model.Pessoa;
import com.arley.financeiro.model.TipoLancamento;
import com.arley.financeiro.util.JpaUtil;

@Named
@ViewScoped
public class ConsultaLancamentosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private List<Lancamento> lancamentos;
	
	@PostConstruct
	public void init() {
		cadastrar();
	}
	
	public void cadastrar() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro efetuado.",
				"Cliente " + this.nome + " cadastrado com sucesso.");

		context.addMessage(null, mensagem);

		// Exemplo cadastro
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		Pessoa cliente = new Pessoa();
		cliente.setNome("Indústria de Alimentos");

		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao("Venda de licença de software");
		lancamento.setPessoa(cliente);
		lancamento.setDataVencimento(Calendar.getInstance().getTime());
		lancamento.setDataPagamento(Calendar.getInstance().getTime());
		lancamento.setValor(new BigDecimal(103_000));
		lancamento.setTipo(TipoLancamento.RECEITA);

		manager.persist(cliente);
		manager.persist(lancamento);

		transaction.commit();
		manager.close();

	}

	public void buscarLancamentos() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		lancamentos = manager.createQuery("from Lancamento").getResultList();
		
		transaction.commit();
		manager.close();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	

}
