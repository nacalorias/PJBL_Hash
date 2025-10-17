import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int tamanhoDosDados = 100_000;

        int tamanhoDaTabela = 117_649;


        final long seed = 42L; // seed p gerar sempre os mesmos números

        // gera os dados
        System.out.println("Gerando " + tamanhoDosDados + " registros...");
        Registro[] dados = gerarDados(tamanhoDosDados, seed);
        System.out.println("Dados gerados.");
        System.out.println("===========================================");


        // testa tabela com encadeamento
        System.out.println("\n--- Teste: Encadeamento ---");
        TabelaHashEncadeamento tabelaEncadeamento = new TabelaHashEncadeamento(tamanhoDaTabela);

        long tempoInicial = System.currentTimeMillis();
        for (Registro reg : dados) {
            tabelaEncadeamento.inserir(reg);
        }
        long tempoFinal = System.currentTimeMillis();

        System.out.println("Tempo de inserção: " + (tempoFinal - tempoInicial) + " ms");
        System.out.println("Número de colisões: " + tabelaEncadeamento.getColisoes());


        // testa tabela com sondagem linear
        System.out.println("\n--- Teste: Sondagem Linear (Pode ser lento) ---");
        TabelaHashSondagemLinear tabelaLinear = new TabelaHashSondagemLinear(tamanhoDaTabela);

        tempoInicial = System.currentTimeMillis();
        for (Registro reg : dados) {
            tabelaLinear.inserir(reg);
        }
        tempoFinal = System.currentTimeMillis();

        System.out.println("Tempo de inserção: " + (tempoFinal - tempoInicial) + " ms");
        System.out.println("Número de colisões: " + tabelaLinear.getColisoes());


        // testa tabela com hash duplo
        System.out.println("\n--- Teste: Hash Duplo ---");
        TabelaHashDuplo tabelaDuplo = new TabelaHashDuplo(tamanhoDaTabela);

        tempoInicial = System.currentTimeMillis();
        for (Registro reg : dados) {
            tabelaDuplo.inserir(reg);
        }
        tempoFinal = System.currentTimeMillis();

        System.out.println("Tempo de inserção: " + (tempoFinal - tempoInicial) + " ms");
        System.out.println("Número de colisões: " + tabelaDuplo.getColisoes());
    }

    //método auxiliar p gerar array de registros

    private static Registro[] gerarDados(int quantidade, long seed) {
        Registro[] dados = new Registro[quantidade];
        Random random = new Random(seed);
        for (int i = 0; i < quantidade; i++) {
            // Gera um código de 9 dígitos
            dados[i] = new Registro(100_000_000 + random.nextInt(900_000_000));
        }
        return dados;
    }
}