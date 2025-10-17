
//Implementação de Tabela Hash usando a técnica de Encadeamento para tratar colisões.

public class TabelaHashEncadeamento {
    private Node[] tabela;
    private int tamanho;
    private long colisoes = 0;

    public TabelaHashEncadeamento(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Node[tamanho];
        // Inicializa a tabela com valores nulos
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = null;
        }
    }

    // função hash primária
    private int hash(int chave) {
        return chave % tamanho;
    }

    public void inserir(Registro registro) {
        int chave = registro.getCodigo();
        int indice = hash(chave);

        Node novoNode = new Node(registro);

        // Se a posição na tabela está vazia, insere o novo nó ali
        if (tabela[indice] == null) {
            tabela[indice] = novoNode;
        } else {
            this.colisoes++;
            Node atual = tabela[indice];
            while (atual.getProximo() != null) {
                this.colisoes++;
                atual = atual.getProximo();
            }
            // Adiciona o novo nó no final da lista
            atual.setProximo(novoNode);
        }
    }

    public Registro buscar(int chave) {
        int indice = hash(chave);
        Node atual = tabela[indice];

        // percorre a lista encadeada na posição do índice
        while (atual != null) {
            if (atual.getRegistro().getCodigo() == chave) {
                return atual.getRegistro(); // achou
            }
            atual = atual.getProximo();
        }
        return null; // nao achou
    }

    public long getColisoes() {
        return this.colisoes;
    }


    public void analisarListas() {
        System.out.println("Análise de listas para Encadeamento não implementada neste exemplo.");
    }
}