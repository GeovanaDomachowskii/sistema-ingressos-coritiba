package jogo;

import enums.Setor;

public class Ingresso {

    private static Integer totalIngressosCriados = 0;
    private Integer numeroIngresso;
    private Jogo jogo;
    private Setor setor;
    private Double valor;

    public Ingresso(Jogo jogo, Setor setor, Double valor) {
        this.jogo = jogo;
        this.setor = setor;
        this.valor = valor;
        totalIngressosCriados++;
        this.numeroIngresso = totalIngressosCriados;
    }

    public Integer getNumeroIngresso() {
        return numeroIngresso;
    }
    public Jogo getJogo() {
        return jogo;
    }

    public Setor getSetor() {
        return setor;
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public String toString(){
        return "Número do ingresso: " + numeroIngresso + " | " + jogo + " | " + setor.getNome() +  " | R$ " + String.format("%.2f", valor);
    }

}
