package torcedor;

public class TorcedorComum extends Torcedor {

    public TorcedorComum(String nome, String cpf) {
        super(nome, cpf);
    }

    @Override
    public Integer getNivel() {
        return 1;
    }

    @Override
    public Double calcularPreco(Double valorBase) {
        return valorBase;
    }

    @Override
    public Boolean podeCancelar() {
        return false;
    }
}