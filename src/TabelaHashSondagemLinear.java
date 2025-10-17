
public class TabelaHashSondagemLinear {
    private Registro[] tabela;
    private int tamanho;
    private final Registro LUGAR_VAZIO = null;
    private long colisoes = 0;

    public TabelaHashSondagemLinear(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = LUGAR_VAZIO;
        }
    }

    // função hash primária
    private int hash(int chave) {
        return chave % tamanho;
    }

    public void inserir(Registro registro) {
        int chave = registro.getCodigo();
        int indice = hash(chave);
        int i = 0;

        // procura por uma posição vazia usando sondagem linear
        while (tabela[indice] != LUGAR_VAZIO) {
            this.colisoes++;
            i++;
            indice = (hash(chave) + i) % tamanho; // próximo índice
        }

        tabela[indice] = registro;
    }

    public Registro buscar(int chave) {
        int indice = hash(chave);
        int i = 0;

        while (tabela[indice] != LUGAR_VAZIO) {
            if (tabela[indice].getCodigo() == chave) {
                return tabela[indice]; // achou
            }
            i++;
            indice = (hash(chave) + i) % tamanho;

        }
        return null; // nao achou
    }

    public long getColisoes() {
        return this.colisoes;
    }

}