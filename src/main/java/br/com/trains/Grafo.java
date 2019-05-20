package br.com.trains;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Grafo, definido aqui como um conjunto de Rotas (arestas).
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Grafo {
	// arestas
	private LinkedList<Rota> rotas = new LinkedList<Rota>();

	private Set<Town> verticesNaoVisitados = new HashSet<Town>();

	public Grafo() {
	}

	public LinkedList<Rota> getRotas() {
		return rotas;
	}
	
	public void setRotas(LinkedList<Rota> rotas) {
		this.rotas = rotas;
	}

	public void addRota(Rota novaRota) {
		// TODO verificar se rota ja existe

		this.getRotas().add(novaRota);
	}

	public Set<Town> getVerticesNaoVisitados() {
		return this.verticesNaoVisitados;
	}

	// Dado um vertice (town), obter os vertices vizinhos.
	private List<Town> getDestinosVizinhos(Town town) {
		List<Town> cidadesVizinhas = new ArrayList<Town>();

		if (this.rotas != null && !this.rotas.isEmpty()) {
			// iterar sobre as arestas
			for (Rota rota : rotas) {
				// if (rota.getOrigem().equals(town)) {
				if (rota.getOrigem().equals(town) && !rota.getDestino().isVisitado()) {
					cidadesVizinhas.add(rota.getDestino());
				}
			}
		}

		return cidadesVizinhas;
	}

	private void popularVerticesNaoVisitados() {
		for (Rota rota : rotas) {
			rota.getOrigem().setVisitado(Boolean.FALSE);
			rota.getDestino().setVisitado(Boolean.FALSE);
			this.verticesNaoVisitados.add(rota.getOrigem());
			this.verticesNaoVisitados.add(rota.getDestino());
		}
	}

	private Town getVerticeMenorDistanciaEstimada(Town verticeOrigem) {
		Town verticeMenorDistanciaEstimada = null;

		List<Town> verticesVizinhos = getDestinosVizinhos(verticeOrigem);

		for (Town verticeVizinho : verticesVizinhos) {
			if (verticeMenorDistanciaEstimada == null) {
				verticeMenorDistanciaEstimada = verticeVizinho;
			} else {
				Integer distanciaEstimadaVizinho = verticeVizinho.getMenorDistanciaEstimada();

				if (distanciaEstimadaVizinho.intValue() < verticeMenorDistanciaEstimada.getMenorDistanciaEstimada()
						.intValue()) {
					verticeMenorDistanciaEstimada = verticeVizinho;
				}
			}
		}

		return verticeMenorDistanciaEstimada;
	}

	public Integer getDistancia(Town origem, Town destino) {
		Integer distancia = null;

		// iteracao para encontrar a aresta
		// encontrada a aresta, retornar a distancia entre os vertices
		for (Rota rota : rotas) {
			if (rota.getOrigem().getName().equals(origem.getName())
					&& rota.getDestino().getName().equals(destino.getName())) {
				distancia = rota.getDistancia();
				break;
			}
		}

		return distancia;
	}

	public void reajustarMenoresDistanciasEstimadas(Town vertice) {
		List<Town> verticesVizinhos = getDestinosVizinhos(vertice);

		if (verticesVizinhos != null && !verticesVizinhos.isEmpty()) {
			for (Town proximoVertice : verticesVizinhos) {
				if (proximoVertice.getMenorDistanciaEstimada() > vertice.getMenorDistanciaEstimada()
						+ getDistancia(vertice, proximoVertice)) {

					proximoVertice.setMenorDistanciaEstimada(
							vertice.getMenorDistanciaEstimada() + getDistancia(vertice, proximoVertice));
				}
			}
		}
	}

	private void build() {
		popularVerticesNaoVisitados();

		for (Town vertice : verticesNaoVisitados) {
			Town verticeMenorDistanciaEstimada = getVerticeMenorDistanciaEstimada(vertice);

			vertice.setVisitado(true);
			verticesNaoVisitados.remove(vertice);

			reajustarMenoresDistanciasEstimadas(verticeMenorDistanciaEstimada);
		}
	}
	
	public void build(LinkedList<Rota> rotas) {
		setRotas(rotas);
		build();
	}
	
	/**
	 * 
	 * @param nomeVerticeInicial
	 */
	public void build(String nomeVerticeInicial) {
		
		for (int i = 0; i < rotas.size(); i++) {
			if (rotas.get(i).getOrigem().getName().equals(nomeVerticeInicial)) {
				rotas.addFirst(rotas.remove(i));
			}
		}
		
		build();
	}
	
	/*
	 * O parametro 'caminho' deve estar no formato X-X-X, onde X corresponde ao nome do vertice (Town).
	 */
	public Integer getDistanciaVertices(String caminho) {
		Integer distanciaTotal = Integer.valueOf(0);
		
		List<Town> verticesList = new ArrayList<Town>();
		
		String[] verticeNames = caminho.split("-");
		for (String verticeName : verticeNames) {
			verticesList.add(new Town(verticeName));
		}
		
		int i = 0;
		while (i < verticesList.size() - 1) {
			Integer distancia = getDistancia(verticesList.get(i), verticesList.get(i + 1));
			
			if (distancia == null) {
				distanciaTotal = null;
				break;
			}
			
			distanciaTotal = distanciaTotal + distancia;
			i++;
		}
		
		return distanciaTotal;
	}
	
	private Integer trips(String nomeOrigem, String nomeDestino, Integer stops) {
		Integer trips = Integer.valueOf(0);
		
		Town townOrigem = new Town(nomeOrigem);
		Town townDestino = new Town(nomeDestino);
		
		List<Town> destinosVizinhos = getDestinosVizinhos(townOrigem);
		
		if (destinosVizinhos == null || destinosVizinhos.isEmpty()) {
			return null;
		}
		
		int i = 0;
		Town townVizinho = destinosVizinhos.get(i);
		for (int j = 0; j < destinosVizinhos.size(); j++) {
			do {
				//destinosVizinhos.
				
				if (!townVizinho.getName().equals(townDestino.getName())) {
					destinosVizinhos = getDestinosVizinhos(townVizinho);
				}
				
				i++;
				trips++;
			} while (destinosVizinhos != null && i < destinosVizinhos.size() && true);
		}
		
		for (int j = 0; j < destinosVizinhos.size(); j++) {
			while (destinosVizinhos != null && !destinosVizinhos.contains(townDestino)) {
				destinosVizinhos = getDestinosVizinhos(destinosVizinhos.get(j));
			}
		}
		
		
		
		return trips;
	}

	/**
	 * 
	 */
	public void executar() {
		String msgNoSuchRoute = "NO SUCH ROUTE";
		// 1) The distance of the route A-B-C.
		String entrada1 = "A-B-C";
		Integer distanciaVertices = getDistanciaVertices(entrada1);
		
		System.out.println(distanciaVertices != null ? "Output #1: " + String.valueOf(distanciaVertices) : msgNoSuchRoute);
		
		// 2) The distance of the route A-D.
		String entrada2 = "A-D";
		distanciaVertices = getDistanciaVertices(entrada2);
		
		System.out.println(distanciaVertices != null ? "Output #2: " + String.valueOf(distanciaVertices) : msgNoSuchRoute);
		
		// 3) The distance of the route A-D-C.
		String entrada3 = "A-D-C";
		distanciaVertices = getDistanciaVertices(entrada3);
		
		System.out.println(distanciaVertices != null ? "Output #3: " + String.valueOf(distanciaVertices) : msgNoSuchRoute);
		
		// 4) The distance of the route A-E-B-C-D.
		String entrada4 = "A-E-B-C-D";
		distanciaVertices = getDistanciaVertices(entrada4);
		
		System.out.println(distanciaVertices != null ? "Output #4: " + String.valueOf(distanciaVertices) : msgNoSuchRoute);
		
		// 5) The distance of the route A-E-D.
		String entrada5 = "A-E-D";
		distanciaVertices = getDistanciaVertices(entrada5);
		
		System.out.println(distanciaVertices != null ? "Output #5: " + String.valueOf(distanciaVertices) : msgNoSuchRoute);
		
		// 6) The number of trips starting at C and ending at C with a maximum of 3
		// stops. In the sample data below, there are two such trips: C-D-C (2 stops).
		// and C-E-B-C (3 stops).
		String startVerticeName = "C";
		String endVerticeName = "C";
		build(startVerticeName);
		
		// 7) The number of trips starting at A and ending at C with exactly 4 stops. In
		// the sample data below, there are three such trips: A to C (via B,C,D); A to C
		// (via D,C,D); and A to C (via D,E,B).
		startVerticeName = "A";
		endVerticeName = "C";
		build(startVerticeName);
		
		// 8) The length of the shortest route (in terms of distance to travel) from A
		// to C.
		
		// 9) The length of the shortest route (in terms of distance to travel) from B
		// to B.
		
		// 10) The number of different routes from C to C with a distance of less than
		// 30. In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
		// CEBCEBC, CEBCEBCEBC.
		
	}
}
