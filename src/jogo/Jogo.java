package jogo;


import enums.Setor;
import enums.TipoJogo;
import exception.SetorEsgotadoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class Jogo {

    private String adversario;
    private LocalDate dataJogo;
    private TipoJogo tipoJogo;
    private Map<Setor, Integer> vagas = new HashMap<>();
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Jogo(String adversario, LocalDate dataJogo, TipoJogo tipoJogo) {
        this.adversario = adversario;
        this.dataJogo = dataJogo;
        this.tipoJogo = tipoJogo;
        for (Setor setor : Setor.values()) {
            vagas.put(setor, setor.getCapacidade());
        }
    }

    public String getAdversario() {
        return adversario;
    }

    public LocalDate getDataJogo() {
        return dataJogo;
    }

    public TipoJogo getTipoJogo() {
        return tipoJogo;
    }

    public Integer getVagas(Setor setor) {
        return vagas.get(setor);
    }

    public Double calcularValorBase(Setor setor){
        return tipoJogo.aplicarAcrescimo(setor.getPreco());
    }

    public void ocuparVaga(Setor setor) throws SetorEsgotadoException{
        if (getVagas(setor) <= 0){
            throw new SetorEsgotadoException("O setor escolhido não possui nenhuma vaga disponível");
        }
        vagas.put(setor, getVagas(setor) - 1);
    }

    public void liberarVaga(Setor setor){
        vagas.put(setor, getVagas(setor) + 1);
    }

    @Override
    public String toString() {
        return  "Coritiba x " + adversario + " | " + dataJogo.format(FORMATO) + " | " + tipoJogo;
    }
}
