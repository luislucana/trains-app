package br.com.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	
	// para cada vertice, armazenar a menor qtde de passos necessaria para se chegar ao mesmo
	private Map<Town, Integer> qtdeArestas = new HashMap<Town, Integer>();
	
	// para todos os vertices, armazenar todas suas distancias
	private Map<Town, List<Integer>> todasDistanciasVerticeMap = new HashMap<Town, List<Integer>>();

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
				//if (rota.getOrigem().equals(town) && !rota.getDestino().isVisitado()) {
				if (rota.getOrigem().equals(town)) {
					cidadesVizinhas.add(rota.getDestino());
				}
			}
		}

		return cidadesVizinhas;
	}

	private void reiniciarVerticesNaoVisitados() {
		verticesNaoVisitados.clear();
		for (Rota rota : rotas) {
			rota.getOrigem().setVisitado(Boolean.FALSE);
			rota.getDestino().setVisitado(Boolean.FALSE);
			rota.getOrigem().setMenorDistanciaEstimada(Integer.MAX_VALUE);
			rota.getDestino().setMenorDistanciaEstimada(Integer.MAX_VALUE);
			this.verticesNaoVisitados.add(rota.getOrigem());
			this.verticesNaoVisitados.add(rota.getDestino());
		}
		
		// estimativa do no/vertice inicial deve ser = 0
		rotas.get(0).getOrigem().setMenorDistanciaEstimada(Integer.valueOf(0));
	}

	private Town getVerticeMenorDistanciaEstimada(Set<Town> verticesNaoVisitados) {
		Town verticeMenorDistanciaEstimada = null;

		for (Town verticeVizinho : verticesNaoVisitados) {
			if (verticeMenorDistanciaEstimada == null) {
				verticeMenorDistanciaEstimada = verticeVizinho;
			} else {
				Integer distanciaEstimadaVizinho = verticeVizinho.getMenorDistanciaEstimada();

				if (distanciaEstimadaVizinho.intValue() < verticeMenorDistanciaEstimada.getMenorDistanciaEstimada().intValue()) {
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
		Integer qtdeArestasCaminho = Integer.valueOf(0);
		List<Town> verticesVizinhos = getDestinosVizinhos(vertice);

		if (verticesVizinhos != null && !verticesVizinhos.isEmpty()) {
			List<Integer> todasDistanciasVertice = new ArrayList<Integer>();
			
			for (Town proximoVertice : verticesVizinhos) {
				todasDistanciasVertice.clear();
				
				if (todasDistanciasVerticeMap.containsKey(proximoVertice)) {
					todasDistanciasVertice = todasDistanciasVerticeMap.get(proximoVertice);
				}
				todasDistanciasVertice.add(vertice.getMenorDistanciaEstimada() + getDistancia(vertice, proximoVertice));
				todasDistanciasVerticeMap.put(proximoVertice, todasDistanciasVertice);
				
				if (proximoVertice.getMenorDistanciaEstimada() > vertice.getMenorDistanciaEstimada()
						+ getDistancia(vertice, proximoVertice)) {

					// estimativa do vertice corrente ('vertice') + distancia entre 'vertice' e 'proximoVertice' (armazenada na aresta)
					Integer menorDistanciaDeFato = vertice.getMenorDistanciaEstimada() + getDistancia(vertice, proximoVertice);
					qtdeArestasCaminho++;
					
					proximoVertice.setMenorDistanciaEstimada(menorDistanciaDeFato);
					
					verticesNaoVisitados.add(proximoVertice);
				}
			}
		}
		
		qtdeArestas.put(vertice, qtdeArestasCaminho);
	}

	private void build() {
		reiniciarVerticesNaoVisitados();

		while (!verticesNaoVisitados.isEmpty()) {
			Town verticeMenorDistanciaEstimada = getVerticeMenorDistanciaEstimada(verticesNaoVisitados);

			verticeMenorDistanciaEstimada.setVisitado(true);
			verticesNaoVisitados.remove(verticeMenorDistanciaEstimada);

			reajustarMenoresDistanciasEstimadas(verticeMenorDistanciaEstimada);
		}
	}
	
	public void build(LinkedList<Rota> rotas) {
		setRotas(rotas);
		build();
	}
	
	public void build(String nomeVerticeInicial) {
		setVerticeInicial(nomeVerticeInicial);
		build();
	}
	
	public void build(String nomeVerticeInicial, LinkedList<Rota> rotas) {
		setRotas(rotas);
		setVerticeInicial(nomeVerticeInicial);
		build();
	}
	
	private void setVerticeInicial(String nomeVerticeInicial) {
		for (int i = 0; i < rotas.size(); i++) {
			if (rotas.get(i).getOrigem().getName().equals(nomeVerticeInicial)) {
				rotas.addFirst(rotas.remove(i));
				break;
			}
		}
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
	
	private Integer getNumberOfTrips(String nomeOrigem, String nomeDestino, Integer stops) {
		Integer trips = Integer.valueOf(0);
		
		build(nomeOrigem);
		Town townDestino = new Town(nomeDestino);
		
		if (nomeDestino.equals(nomeOrigem)) {
			trips++; // 1
			
			List<Town> destinosVizinhos = getDestinosVizinhos(rotas.get(0).getOrigem());
			int qtdePassosRestantes = 0;
			for (Town verticeVizinho : destinosVizinhos) {
				//System.out.println("verticevizinho: " + verticeVizinho.getName());
				build(verticeVizinho.getName());
				if (qtdeArestas.get(townDestino) != null && qtdePassosRestantes > qtdeArestas.get(townDestino)) {
					qtdePassosRestantes = qtdeArestas.get(townDestino);
				}
			}
			
			return qtdePassosRestantes + trips;
		}
		
		trips = qtdeArestas.get(townDestino);
		
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
		
		System.out.println("Output #1: " + (distanciaVertices != null ? String.valueOf(distanciaVertices) : msgNoSuchRoute));
		
		// 2) The distance of the route A-D.
		String entrada2 = "A-D";
		distanciaVertices = getDistanciaVertices(entrada2);
		
		System.out.println("Output #2: " + (distanciaVertices != null ? String.valueOf(distanciaVertices) : msgNoSuchRoute));
		
		// 3) The distance of the route A-D-C.
		String entrada3 = "A-D-C";
		distanciaVertices = getDistanciaVertices(entrada3);
		
		System.out.println("Output #3: " + (distanciaVertices != null ? String.valueOf(distanciaVertices) : msgNoSuchRoute));
		
		// 4) The distance of the route A-E-B-C-D.
		String entrada4 = "A-E-B-C-D";
		distanciaVertices = getDistanciaVertices(entrada4);
		
		System.out.println("Output #4: " + (distanciaVertices != null ? String.valueOf(distanciaVertices) : msgNoSuchRoute));
		
		// 5) The distance of the route A-E-D.
		String entrada5 = "A-E-D";
		distanciaVertices = getDistanciaVertices(entrada5);
		
		System.out.println("Output #5: " + (distanciaVertices != null ? String.valueOf(distanciaVertices) : msgNoSuchRoute));
		
		// 6) The number of trips starting at C and ending at C with a maximum of 3
		// stops. In the sample data below, there are two such trips: C-D-C (2 stops).
		// and C-E-B-C (3 stops).
		/*
		 * String startVerticeName = "C"; String endVerticeName = "C";
		 * build(startVerticeName); Integer numberOfTrips =
		 * getNumberOfTrips(startVerticeName, endVerticeName, null);
		 * System.out.println("Output #6: " + String.valueOf(numberOfTrips));
		 */
		
		// 7) The number of trips starting at A and ending at C with exactly 4 stops. In
		// the sample data below, there are three such trips: A to C (via B,C,D); A to C
		// (via D,C,D); and A to C (via D,E,B).
		/*
		 * startVerticeName = "A"; endVerticeName = "C"; build(startVerticeName);
		 */
		
		// 8) The length of the shortest route (in terms of distance to travel) from A
		// to C.
		//String entrada8 = "A-C";
		//distanciaVertices = getDistanciaVertices(entrada8);
		
		//System.out.println("Output #8: " + (distanciaVertices != null ? String.valueOf(distanciaVertices) : msgNoSuchRoute));
		
		// 9) The length of the shortest route (in terms of distance to travel) from B
		// to B.
		
		// 10) The number of different routes from C to C with a distance of less than
		// 30. In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
		// CEBCEBC, CEBCEBCEBC.
		
	}
}
