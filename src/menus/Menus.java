package menus;

import bilheteria.Bilheteria;
import enums.Setor;
import exception.*;
import jogo.Ingresso;
import jogo.Jogo;
import torcedor.Socio;
import torcedor.Torcedor;
import torcedor.TorcedorComum;

import java.util.List;
import java.util.Scanner;

public class Menus {

    private static final Scanner sc = new Scanner(System.in);
    private static final Bilheteria bilheteria = new Bilheteria();

    public static void menuBoasVindas() {
        System.out.println("======================================");
        System.out.println("   SISTEMA DE INGRESSOS - CORITIBA FC   ");
        System.out.println("=====================================");

        Integer opcao;
        do {
            System.out.println("""
                    Bem-vindo(a) ao Coritiba FC!

                    1 - Sou torcedor comum
                    2 - Sou sócio
                    0 - Encerrar programa
                    """);
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> {
                    TorcedorComum torcedor = telaCadastroComum();
                    menuPrincipal(torcedor);
                }
                case 2 -> {
                    Torcedor torcedor = telaLoginSocio();
                    menuPrincipal(torcedor);
                }
                case 0 -> System.out.println("Programa encerrado. Até logo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void menuPrincipal(Torcedor torcedor) {
        System.out.println("\nOlá, " + torcedor.getNome() + "!");

        Integer opcao;
        do {
            System.out.println("""

                    ========== MENU PRINCIPAL ==========
                    1 - Comprar ingresso
                    2 - Consultar meus ingressos
                    3 - Cancelar compra
                    0 - Sair
                    """);
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> telaComprar(torcedor);
                case 2 -> telaConsultar(torcedor);
                case 3 -> telaCancelar(torcedor);
                case 0 -> System.out.println("Até logo!");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static Integer lerOpcao() {
        System.out.print("Escolha a opção desejada: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static TorcedorComum telaCadastroComum() {
        while (true) {
            System.out.print("Digite seu nome: ");
            String nome = sc.nextLine();
            System.out.print("Digite seu CPF, apenas numeros: ");
            String cpf = sc.nextLine();

            try {
                return bilheteria.cadastrarTorcedorComum(nome, cpf);
            } catch (DadosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage() + ". Tente novamente\n");
            }
        }
    }

    private static Torcedor telaLoginSocio() {
        while (true) {
            System.out.print("Digite seu CPF, digite apenas numeros: ");
            String cpf = sc.nextLine();
            System.out.print("Digite o numero da carteirinha: ");

            Integer carteirinha;
            try {
                carteirinha = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("A carteirinha deve ter só números.\n");
                continue;
            }

            try {
                Socio socio = bilheteria.autenticarSocio(cpf, carteirinha);
                System.out.println("Plano " + socio.getPlano() + " confirmado.");
                return socio;
            } catch (SocioNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
                System.out.println("""
                    1 - Tentar de novo
                    2 - Entrar como torcedor comum
                    """);
                Integer escolha = lerOpcao();
                if (escolha.equals(2)) {
                    return telaCadastroComum();
                }
            }
        }
    }

    private static void telaComprar(Torcedor torcedor) {
        List<Jogo> jogos = bilheteria.listarJogos();

        System.out.println("JOGOS DISPONÍVEIS");
        for (int i = 0; i < jogos.size(); i++) {
            System.out.println((i + 1) + " - " + jogos.get(i));
        }
        System.out.println("0 - Voltar");

        Integer opcaoJogo = lerOpcao();
        if (opcaoJogo.equals(0)) {
            return;
        }
        if (opcaoJogo < 1 || opcaoJogo > jogos.size()) {
            System.out.println("Opção inválida!");
            return;
        }
        Jogo jogo = jogos.get(opcaoJogo - 1);

        List<Setor> setores = bilheteria.listarSetoresDisponiveis(torcedor);
        while (true) {
            System.out.println("\n======== SETORES ==========");
            for (int i = 0; i < setores.size(); i++) {
                System.out.println((i + 1) + " - " + setores.get(i) + " | vagas: " + jogo.getVagas(setores.get(i)));
            }
            System.out.println("0 - Voltar");

            Integer opcaoSetor = lerOpcao();
            if (opcaoSetor.equals(0)) {
                return;
            }
            if (opcaoSetor < 1 || opcaoSetor > setores.size()) {
                System.out.println("Opção inválida!");
                continue;
            }
            Setor setor = setores.get(opcaoSetor - 1);

            Double valor = torcedor.calcularPreco(jogo.calcularValorBase(setor));
            System.out.println("\nResumo: " + jogo + " | " + setor.getNome() + " | R$ " + String.format("%.2f", valor));
            System.out.print("Confirmar? Digite s para sim e n para não(S/N): ");
            if (!sc.nextLine().equalsIgnoreCase("S")) {
                System.out.println("Compra não realizada. Verifique se você digitou corretamente");
                return;
            }

            try {
                Ingresso ingresso = bilheteria.comprarIngresso(torcedor, jogo, setor);
                if (torcedor instanceof Socio) {
                    System.out.println("Check-in realizado!");
                } else {
                    System.out.println("Compra realizada!");
                }
                System.out.println(ingresso);
                return;
            } catch (SetorEsgotadoException e) {
                System.out.println("Erro: " + e.getMessage() + ". Escolha outro setor.");
            }
        }
    }

    private static void telaConsultar(Torcedor torcedor) {
        List<Ingresso> ingressos = bilheteria.consultarIngressos(torcedor);

        if (ingressos.isEmpty()) {
            System.out.println("Você ainda não tem ingressos.");
            return;
        }

        System.out.println("\nMEUS INGRESSOS =========");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }

    private static void telaCancelar(Torcedor torcedor) {
        if (!torcedor.podeCancelar()) {
            System.out.println("Apenas sócio pode cancelar!.");
            return;
        }

        List<Ingresso> ingressos = bilheteria.consultarIngressos(torcedor);
        if (ingressos.isEmpty()) {
            System.out.println("Nenhum ingresso para cancelar.");
            return;
        }

        System.out.println("\nCANCELAR INGRESSO ==========");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
        System.out.print("Digite o número do ingresso: ");

        Integer numero;
        try {
            numero = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            return;
        }

        System.out.print("Confirmar cancelamento? (S/N): ");
        if (!sc.nextLine().equalsIgnoreCase("S")) {
            System.out.println("Cancelamento não realizado.");
            return;
        }

        try {
            bilheteria.cancelarIngresso(torcedor, numero);
            System.out.println("Cancelamento realizado. A vaga foi liberada.");
        } catch (CancelamentoNaoPermitidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}