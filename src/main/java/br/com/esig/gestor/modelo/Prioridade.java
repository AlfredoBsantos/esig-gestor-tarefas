package br.com.esig.gestor.modelo;

public enum Prioridade {
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");

    private final String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}