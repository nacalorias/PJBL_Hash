
public class TabelaHashDuplo {
    private Registro[] tabela;
    private int tamanho;
    private final Registro LUGAR_VAZIO = null;
    private long colisoes = 0;

    public TabelaHashDuplo(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = LUGAR_VAZIO;
        }
    }

    // Primeira função hash
    private int hash1(int chave) {
        return chave % tamanho;
    }

    // Segunda função hash
    private int hash2(int chave) {
        return 1 + (chave % (tamanho - 1));
    }

    public void inserir(Registro registro) {
        int chave = registro.getCodigo();
        int indice = hash1(chave);

        if (tabela[indice] != LUGAR_VAZIO) {
            this.colisoes++;
            int passo = hash2(chave);
            int i = 1;
            while (tabela[indice] != LUGAR_VAZIO) {
                if(i > 1) this.colisoes++;

                indice = (hash1(chave) + i * passo) % tamanho;
                i++;
            }
        }

        tabela[indice] = registro;
    }

    public Registro buscar(int chave) {
        int indice = hash1(chave);
        int passo = hash2(chave);
        int i = 0;

        while (tabela[indice] != LUGAR_VAZIO) {
            if (tabela[indice].getCodigo() == chave) {
                return tabela[indice]; // Encontrou
            }
            i++;
            indice = (hash1(chave) + i * passo) % tamanho;

        }
        return null; // Não encontrou
    }

    public long getColisoes() {
        return this.colisoes;
    }

}