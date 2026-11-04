package torcedor;

import enums.Plano;

public class Socio extends Torcedor {

    private Integer carteirinha;
    private Plano plano;

    public Socio(String nome, String cpf, Integer carteirinha, Plano plano) {
        super(nome, cpf);
        this.carteirinha = carteirinha;
        this.plano = plano;
    }

    public Integer getCarteirinha() {
        return carteirinha;
    }

    public Plano getPlano() {
        return plano;
    }

    @Override
    public Integer getNivel() {
        return plano.getNivel();
    }

    @Override
    public Double calcularPreco(Double valorBase) {
        return 0.0;
    }

    @Override
    public Boolean podeCancelar() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " | Sócio " + plano
                + " (R$ " + plano.getMensalidade() + "/mês)";
    }
}