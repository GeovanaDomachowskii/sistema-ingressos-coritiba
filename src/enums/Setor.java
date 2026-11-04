package enums;

public enum Setor {
    RENATO_FOLLADOR("Setor Renato Follador", 120.0, 1, 10),
    SOCIAIS("Cadeiras Sociais", 200.0, 2, 5),
    PRO_TORK("Cadeiras Pro Tork", 300.0, 3, 2),
    CAMAROTE("Camarote (reserva)", 800.0, 4, 1);

    private final String nome;
    private final Double preco;
    private final Integer nivelMinimo;
    private final Integer capacidade;

    Setor(String nome, Double preco, Integer nivelMinimo, Integer capacidade) {
        this.nome = nome;
        this.preco = preco;
        this.nivelMinimo = nivelMinimo;
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getNivelMinimo() {
        return nivelMinimo;
    }
    public Integer getCapacidade() {
        return capacidade;
    }

    public Boolean permiteAcesso(Integer nivelTorcedor) {
        return nivelTorcedor >= nivelMinimo;
    }

    @Override
    public String toString() {
        return nome + " | nível " + nivelMinimo + " | R$ " + preco;
    }



}