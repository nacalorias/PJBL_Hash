# **Análise de Desempenho de Tabelas Hash**

Este projeto, desenvolvido como parte da avaliação da disciplina de Estrutura de Dados, tem como objetivo implementar e analisar o desempenho de três diferentes técnicas de tratamento de colisão em Tabelas Hash: Encadeamento, Sondagem Linear e Hash Duplo.

## **1. Metodologia e Implementação**

O ambiente de desenvolvimento utilizado foi o IntelliJ IDEA com Java (OpenJDK 23). O projeto foi estruturado para testar a performance de inserção de um grande volume de dados em cada uma das tabelas hash implementadas.

### **Técnicas de Hashing Implementadas**

Foram implementadas as três seguintes abordagens para tratamento de colisão:

1.  **Tabela Hash com Encadeamento (Chaining):** Nesta técnica, cada posição da tabela hash aponta para uma lista encadeada. Quando ocorre uma colisão, o novo elemento é simplesmente adicionado ao final da lista correspondente.

2.  **Tabela Hash com Sondagem Linear (Linear Probing):** Uma técnica de endereçamento aberto. Se a posição inicial calculada pela função hash já está ocupada, o algoritmo verifica a próxima posição sequencial (`i+1`, `i+2`, ...) até encontrar um espaço livre.

3.  **Tabela Hash com Hash Duplo (Double Hashing):** Uma versão mais sofisticada do endereçamento aberto. Quando ocorre uma colisão, uma segunda função hash calcula um "passo" para sondar a tabela, saltando posições em vez de verificar sequencialmente. Isso ajuda a mitigar o problema de agrupamento.

### **Configuração do Experimento**

Para a primeira rodada de testes, a seguinte configuração foi utilizada:

* **Conjunto de Dados:** 100.000 registros, com chaves de 9 dígitos geradas aleatoriamente.
* **Seed do Gerador:** `42L`, para garantir que todas as técnicas fossem testadas com o mesmo conjunto de dados.
* **Tamanho da Tabela Hash:** `117.649` (um número primo maior que o conjunto de dados, resultando em um fator de carga de aproximadamente 85%).

## **2. Resultados**

A execução do teste com 100.000 registros produziu os seguintes resultados para a operação de **inserção**:

| Técnica de Hashing | Tempo de Inserção (ms) | Número de Colisões |
| :----------------- | :--------------------- | :----------------- |
| Encadeamento       | 28 ms                  | 42.363             |
| Sondagem Linear    | 12 ms                  | 289.477            |
| Hash Duplo         | 16 ms                  | 122.768            |

## **3. Análise e Discussão**

A análise dos dados revela conclusões importantes sobre o comportamento de cada algoritmo.

### **Análise de Colisões**

O resultado mais evidente é a **discrepância no número de colisões**.

* A **Sondagem Linear** apresentou o pior desempenho, com **289.477** colisões, quase 7 vezes mais que o Encadeamento. Isso demonstra na prática o seu principal defeito: o **agrupamento primário (primary clustering)**. Conforme a tabela enche, os dados se aglomeram em longas sequências, forçando novas inserções a percorrer longos caminhos para encontrar um espaço vago.
* O **Encadeamento** teve o menor número de "eventos de colisão" (42.363), pois a colisão apenas indica a necessidade de adicionar um novo nó a uma lista existente.
* O **Hash Duplo** (122.768 colisões) provou sua eficácia em mitigar o agrupamento, resultando em um número de colisões significativamente menor que a Sondagem Linear.

### **Análise do Tempo de Inserção**

De forma contraintuitiva, o algoritmo com mais colisões foi o mais rápido.

* A **Sondagem Linear (12 ms)**, apesar de seu alto número de colisões, foi a mais rápida. Isso se deve a um fenômeno chamado **localidade de cache**. Como a verificação é feita em posições de memória adjacentes (`vetor[i]`, `vetor[i+1]`), o processador consegue otimizar essas leituras, tornando o processo extremamente rápido.
* O **Encadeamento (28 ms)** foi o mais lento. A criação de novos objetos `Node` para cada colisão e os "saltos" na memória para percorrer a lista encadeada causam perdas de performance (cache misses), tornando a operação mais custosa.
* O **Hash Duplo (16 ms)** ficou em uma posição intermediária, pois seus saltos pela memória também não se beneficiam da localidade de cache, mas ele não tem o custo de criar novos objetos como o Encadeamento.

## **4. Conclusão**

Para o cenário de teste com 100.000 registros, a **Sondagem Linear apresentou uma vitória enganosa**. Embora tenha sido a mais rápida em tempo de execução, seu altíssimo número de colisões demonstra uma ineficiência algorítmica fundamental. Este é um forte indicativo de que, em testes com um fator de carga maior ou com conjuntos de dados maiores (1 milhão ou 10 milhões), seu desempenho irá degradar drasticamente, tornando-se a opção mais lenta.

O **Hash Duplo** se mostrou a solução mais balanceada, combinando um bom tempo de execução com um controle de colisões muito superior à Sondagem Linear. Para uma aplicação robusta e escalável, ele representa a escolha mais segura e eficiente entre as técnicas de endereçamento aberto testadas.
