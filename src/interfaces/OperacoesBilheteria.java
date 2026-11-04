package interfaces;

import enums.Setor;
import exception.CancelamentoNaoPermitidoException;
import exception.DadosInvalidosException;
import exception.SetorEsgotadoException;
import exception.SocioNaoEncontradoException;
import jogo.Ingresso;
import jogo.Jogo;
import torcedor.Socio;
import torcedor.Torcedor;
import torcedor.TorcedorComum;

import java.util.List;

public interface OperacoesBilheteria {

    List<Jogo> listarJogos();

    List<Setor> listarSetoresDisponiveis(Torcedor torcedor);

    TorcedorComum cadastrarTorcedorComum(String nome, String cpf)
            throws DadosInvalidosException;

    Socio autenticarSocio(String cpf, Integer carteirinha)
            throws SocioNaoEncontradoException;

    Ingresso comprarIngresso(Torcedor torcedor, Jogo jogo, Setor setor)
            throws SetorEsgotadoException;

    List<Ingresso> consultarIngressos(Torcedor torcedor);

    void cancelarIngresso(Torcedor torcedor, Integer numero)
            throws CancelamentoNaoPermitidoException;
}