package br.com.trains;

import java.util.ArrayList;
import java.util.HashSet;
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
	private List<Rota> rotas = new ArrayList<Rota>();

	private Set<Town> verticesNaoVisitados = new HashSet<Town>();

	public Grafo() {
	}

	public List<Rota> getRotas() {
		return rotas;
	}
	
	public void setRotas(List<Rota> rotas) {
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

	public void build(List<Rota> rotas) {
		setRotas(rotas);
		
		popularVerticesNaoVisitados();

		for (Town vertice : verticesNaoVisitados) {
			Town verticeMenorDistanciaEstimada = getVerticeMenorDistanciaEstimada(vertice);

			vertice.setVisitado(true);
			verticesNaoVisitados.remove(vertice);

			reajustarMenoresDistanciasEstimadas(verticeMenorDistanciaEstimada);
		}
	}
	
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
		}
		
		return distanciaTotal;
	}

	public void executar() {
		// 1) The distance of the route A-B-C.
		Integer distanciaVertices = getDistanciaVertices("A-B-C");
		
		// 2) The distance of the route A-D.
		distanciaVertices = getDistanciaVertices("A-D");
		
		// 3) The distance of the route A-D-C.
		distanciaVertices = getDistanciaVertices("A-D-C");
		
		// 4) The distance of the route A-E-B-C-D.
		distanciaVertices = getDistanciaVertices("A-E-B-C-D");
		
		// 5) The distance of the route A-E-D.
		distanciaVertices = getDistanciaVertices("A-E-D");
		
		// 6) The number of trips starting at C and ending at C with a maximum of 3
		// stops. In the sample data below, there are two such trips: C-D-C (2 stops).
		// and C-E-B-C (3 stops).
		
		// 7) The number of trips starting at A and ending at C with exactly 4 stops. In
		// the sample data below, there are three such trips: A to C (via B,C,D); A to C
		// (via D,C,D); and A to C (via D,E,B).
		
		// 8) The length of the shortest route (in terms of distance to travel) from A
		// to C.
		
		// 9) The length of the shortest route (in terms of distance to travel) from B
		// to B.
		
		// 10) The number of different routes from C to C with a distance of less than
		// 30. In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
		// CEBCEBC, CEBCEBCEBC.
		
	}
}
