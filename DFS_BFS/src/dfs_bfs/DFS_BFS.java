package dfs_bfs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class DFS_BFS {

    int TAM;
    
    public static void main(String[] args) {
        new DFS_BFS();
    }

    DFS_BFS() {
        int teste = 0;
        long[] time = new long[120];
        File arquivo = new File("testesaida.txt");
        String str = "";
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            FileWriter fw = new FileWriter(arquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            int x = 0;
            for (int cont = 0; cont < 120; cont++) {
                Random ran = new Random();
                x = 0;
                while (x == 0) {
                    x = ran.nextInt(10);
                }
                x = (x*600);
                TAM = x;//define o tamanho do grafico
                long tempo = 0;
                boolean[][] GRAFO;
                tempo = System.currentTimeMillis();
                GRAFO = new boolean[TAM][TAM];
                GRAFO = CRIAGRAFICO(GRAFO);//criação do grafico
                GRAFO = BUSCABFS(GRAFO);//buscador bfs
                GRAFO = BUSCADFS(GRAFO);//buscador dfs
                tempo = tempo - System.currentTimeMillis();
                tempo = tempo * -1;
                time[teste] = (tempo);
                if (tempo > 0) {
                    teste++;
                } else {
                    cont--;
                }
            }
            for (teste = 0; teste < 120; teste++) {
                for (int teste2 = 0; teste2 < 119; teste2++) {
                    if (time[teste2] < time[teste2 + 1]) {
                        long pivo = time[teste2];
                        time[teste2] = time[teste2 + 1];
                        time[teste2 + 1] = pivo;
                    }
                }
            }
            for (teste = 10; teste < 110; teste++) {
                str = String.valueOf(time[teste]);
                bw.write(str);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

    boolean[][] CRIAGRAFICO(boolean[][] GRAFO) {
        for (int cont = 1; cont < (TAM * 2); cont++) {
            Random random = new Random();
            int num = random.nextInt(TAM - 1);
            int num2 = random.nextInt(TAM - 1);
            GRAFO[num][num2] = true;
        }
        return GRAFO;
    }

    boolean[][] BUSCADFS(boolean[][] GRAFO) {
        //System.out.printf("\n----------------------------------------- ");
        boolean[] VISITADOS = new boolean[TAM];//vetor booleano de vertices visitados
        int NUM = 0;//contador de vertices iniciais
        for (int i = 0; i < TAM; ++i) {//loop para percorrer o vetor
            if (!VISITADOS[i]) {// se visitado[i] for falso entra
                ++NUM;
                //System.out.printf("\nBusca DFS na parte " + NUM + " do grafo iniciando no vértice " + i);
                DFS(i, VISITADOS, GRAFO);//manda o indice do vertice para o codigo de busca
            }
        }
        if (NUM == 1) {//se so houve um vertice inicial entra
            //System.out.printf("\n\nO grafo é conexo!");
        } else {
            //System.out.printf("\n\nO grafo não é conexo!");
        }
        //System.out.printf("\nForam encontrados " + NUM + " vértice inicial\n");
        return GRAFO;
    }

    void DFS(int a, boolean[] VISITADOS, boolean[][] GRAFO) {
        //System.out.printf("\nVisitando o vértice " + a);
        VISITADOS[a] = true;//define para o indice deste vertice que ele já foi visitado
        for (int cont = 0; cont < TAM; ++cont) {//loop para consultar todos os vertices possiveis
            if (GRAFO[a][cont] && !VISITADOS[cont]) {// se o grafo indicar que existe caminho entra
                //System.out.printf("\nIndo para o vértice " + cont);
                DFS(cont, VISITADOS, GRAFO);// recursiva para a mesma função com o novo indice
            }
        }
        //System.out.printf("\nVoltando para o vértice " + a);
    }

    boolean[][] BUSCABFS(boolean[][] GRAFO) {//mesmo método do dfs para chamar o código de busca
        //System.out.printf("\n----------------------------------------- ");
        boolean[] VISITADOS = new boolean[TAM];
        int NUM = 0;
        for (int i = 0; i < TAM; ++i) {
            if (!VISITADOS[i]) {
                ++NUM;
                //System.out.printf("\nBusca BFS na parte " + NUM + " do grafo iniciando no vértice " + i);
                BFS(i, VISITADOS, GRAFO);//chamada do código de busca passando o índice
            }
        }
        if (NUM == 1) {
            //System.out.printf("\n\nO grafo é conexo!");
        } else {
            //System.out.printf("\n\nO grafo não é conexo!");
        }
        //System.out.printf("\nForam encontrados " + NUM + " vértice inicial\n");
        return GRAFO;
    }

    void BFS(int inicio, boolean[] VISITADOS, boolean[][] GRAFO) {
        Queue<Integer> Q = new LinkedList<Integer>();//cria a pilha
        Q.offer(inicio);//adiciona o primeiro indice recebido á pilha
        VISITADOS[inicio] = true;//define que o indice foi visitado
        while (!Q.isEmpty()) {//enquanto a pilha não esvaziar fica no loop
            int a = Q.poll();//variavel inteira "a" recebe o que foi deletado da pilha
            for (int i = 0; i < TAM; ++i) {//llop para percorrer todos os vértices possiveis
                if (GRAFO[a][i] && !VISITADOS[i]) {//se houver arco entra
                    Q.offer(i);//adiciona o novo vértice na pilha
                    VISITADOS[i] = true;//define como vértice visitado
                    //System.out.printf("\nIndo para o vértice " + i);
                }
            }//fim da recursividade de teste de arco
            //System.out.printf("\nVoltando para o vértice " + a);
        }
        
        //System.out.printf("\nfinalizado a busca BFS que iniciou no vértice " + inicio);
    }
   
}