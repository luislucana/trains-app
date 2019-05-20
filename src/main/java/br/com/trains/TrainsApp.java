package br.com.trains;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class TrainsApp {

	public static void main(String[] args) {
		System.out.println("Informe um grafo");
		
		Grafo grafo = new Grafo();
		
		// AB5
		Town townOrigem = new Town("A");
		Town townDestino = new Town("B");
		Rota rota = new Rota(townOrigem, townDestino, Integer.valueOf(5));
		
		grafo.addRota(rota);
		
		// BC4
		townOrigem = new Town("B");
		townDestino = new Town("C");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(4));
		
		grafo.addRota(rota);
		
		// CD8
		townOrigem = new Town("C");
		townDestino = new Town("D");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(8));
		
		grafo.addRota(rota);
		
		// DC8
		townOrigem = new Town("D");
		townDestino = new Town("C");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(8));
		
		grafo.addRota(rota);
		
		// DE6
		townOrigem = new Town("D");
		townDestino = new Town("E");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(6));
		
		grafo.addRota(rota);
		
		// AD5
		townOrigem = new Town("A");
		townDestino = new Town("D");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(5));
		
		grafo.addRota(rota);
		
		// CE2
		townOrigem = new Town("C");
		townDestino = new Town("E");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(2));
		
		grafo.addRota(rota);
		
		// EB3
		townOrigem = new Town("E");
		townDestino = new Town("B");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(3));
		
		grafo.addRota(rota);
		
		// AE7
		townOrigem = new Town("A");
		townDestino = new Town("E");
		rota = new Rota(townOrigem, townDestino, Integer.valueOf(7));
		
		grafo.addRota(rota);
		// Input: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		grafo.executar();
	}
}
