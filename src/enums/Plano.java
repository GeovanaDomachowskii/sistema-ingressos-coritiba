package enums;

public enum Plano {
    COMUM(1, 89.90),
    VIP(2, 179.90),
    PREMIUM(3, 289.90),
    DIAMANTE(4, 599.90);

    private final Integer nivel;
    private final Double mensalidade;

    Plano(Integer nivel, Double mensalidade) {
        this.nivel = nivel;
        this.mensalidade = mensalidade;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Double getMensalidade() {
        return mensalidade;
    }
}