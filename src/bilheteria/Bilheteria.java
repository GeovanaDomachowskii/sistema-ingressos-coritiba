package bilheteria;

import enums.Plano;
import enums.Setor;
import enums.TipoJogo;
import exception.CancelamentoNaoPermitidoException;
import exception.DadosInvalidosException;
import exception.SetorEsgotadoException;
import exception.SocioNaoEncontradoException;
import interfaces.OperacoesBilheteria;
import jogo.Ingresso;
import jogo.Jogo;
import torcedor.Socio;
import torcedor.Torcedor;
import torcedor.TorcedorComum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bilheteria implements OperacoesBilheteria {

    private static final String ARQUIVO_SOCIOS = "socios.txt";

    private List<Jogo> jogos = new ArrayList<>();
    private List<Socio> socios = new ArrayList<>();

    public Bilheteria() {
        criarJogos();
        carregarSocios();
    }

    private void criarJogos() {
        jogos.add(new Jogo("Athletico", LocalDate.of(2026, 11, 15), TipoJogo.CLASSICO));
        jogos.add(new Jogo("Mirassol", LocalDate.of(2026, 11, 22), TipoJogo.NORMAL));
        jogos.add(new Jogo("Remo", LocalDate.of(2026, 11, 29), TipoJogo.NORMAL));
    }

    private void carregarSocios() {
        try {
            List<String> registrosDoArquivo = Files.readAllLines(Path.of(ARQUIVO_SOCIOS));
            for (String registro : registrosDoArquivo) {
                String[] partes = registro.split(";");
                String cpf = partes[0].trim();
                String nome = partes[1].trim();
                Integer carteirinha = Integer.parseInt(partes[2].trim());
                Plano plano = Plano.valueOf(partes[3].trim());
                socios.add(new Socio(nome, cpf, carteirinha, plano));
            }
        } catch (IOException e) {
            System.out.println("Aviso!1!! não foi possível ler " + ARQUIVO_SOCIOS);
        }
    }

    @Override
    public List<Jogo> listarJogos() {
        return List.copyOf(jogos);
    }

    @Override
    public List<Setor> listarSetoresDisponiveis(Torcedor torcedor) {
        List<Setor> disponiveis = new ArrayList<>();
        for (Setor setor : Setor.values()) {
            if (setor.permiteAcesso(torcedor.getNivel())) {
                disponiveis.add(setor);
            }
        }
        return disponiveis;
    }

    @Override
    public TorcedorComum cadastrarTorcedorComum(String nome, String cpf) throws DadosInvalidosException {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome não pode ser um nome vazio");
        }
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new DadosInvalidosException("O CPF deve ter 11 números");
        }
        return new TorcedorComum(nome.trim(), cpf);
    }

    @Override
    public Socio autenticarSocio(String cpf, Integer carteirinha) throws SocioNaoEncontradoException {
        return socios.stream()
                .filter(s -> s.getCpf().equals(cpf) && s.getCarteirinha().equals(carteirinha))
                .findFirst()
                .orElseThrow(() -> new SocioNaoEncontradoException("Nenhum sócio com esses dados foi encontrado"));
    }

    @Override
    public Ingresso comprarIngresso(Torcedor torcedor, Jogo jogo, Setor setor) throws SetorEsgotadoException {
        jogo.ocuparVaga(setor);

        Double valor = torcedor.calcularPreco(jogo.calcularValorBase(setor));
        Ingresso ingresso = new Ingresso(jogo, setor, valor);
        torcedor.adicionarIngresso(ingresso);
        return ingresso;
    }

    @Override
    public List<Ingresso> consultarIngressos(Torcedor torcedor) {
        return torcedor.getIngressos();
    }

    @Override
    public void cancelarIngresso(Torcedor torcedor, Integer numero) throws CancelamentoNaoPermitidoException {
        if (!torcedor.podeCancelar()) {
            throw new CancelamentoNaoPermitidoException("Apenas sócios podem cancelar");
        }

        Ingresso ingresso = torcedor.buscarIngresso(numero);
        if (ingresso == null) {
            throw new CancelamentoNaoPermitidoException("Ingresso numero " + numero + " não encontrado");
        }

        ingresso.getJogo().liberarVaga(ingresso.getSetor());
        torcedor.removerIngresso(ingresso);
    }
}