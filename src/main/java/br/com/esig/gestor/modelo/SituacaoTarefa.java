package br.com.esig.gestor.modelo;

public enum SituacaoTarefa {
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída");

    private final String descricao;

    SituacaoTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}