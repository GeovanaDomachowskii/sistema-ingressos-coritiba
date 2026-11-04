package torcedor;

import jogo.Ingresso;

import java.util.ArrayList;
import java.util.List;

public abstract class Torcedor {

    private String nome;
    private String cpf;
    private List<Ingresso> ingressos = new ArrayList<>();

    public Torcedor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public List<Ingresso> getIngressos() {
        return List.copyOf(ingressos);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public abstract Integer getNivel();

    public abstract Double calcularPreco(Double valorBase);

    public abstract Boolean podeCancelar();

    public  void adicionarIngresso(Ingresso ingresso){
        ingressos.add(ingresso);
    }
    public void removerIngresso(Ingresso ingresso){
        ingressos.remove(ingresso);
    }
    public Ingresso buscarIngresso(Integer numero){
        return ingressos.stream()
                .filter(i -> i.getNumeroIngresso().equals(numero))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
}