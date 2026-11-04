package enums;

public enum TipoJogo {
    NORMAL("Normal", 0.0),
    CLASSICO("Clássico", 0.5);

    private final String descricao;
    private final Double acrescimo;

    TipoJogo(String descricao, Double acrescimo) {
        this.descricao = descricao;
        this.acrescimo = acrescimo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public Double aplicarAcrescimo(Double preco) {
        return preco + (preco * acrescimo);
    }

    @Override
    public String toString() {
        return descricao;
    }

}