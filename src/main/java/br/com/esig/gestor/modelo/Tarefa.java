package br.com.esig.gestor.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descricao;

    @Column(nullable = false)
    private String responsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Column
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // ALTERAÇÃO PRINCIPAL: Valor padrão definido diretamente aqui!
    private SituacaoTarefa situacao = SituacaoTarefa.EM_ANDAMENTO;

    public Tarefa() {
    }

    // O MÉTODO @PrePersist FOI REMOVIDO.

    // --- Getters e Setters (continuam idênticos) ---

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public SituacaoTarefa getSituacao() { return situacao; }
    public void setSituacao(SituacaoTarefa situacao) { this.situacao = situacao; }
}