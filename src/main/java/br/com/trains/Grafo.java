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
		popularSetVerticesNaoVisitados();
	}

	public List<Rota> getRotas() {
		return rotas;
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

	private void popularSetVerticesNaoVisitados() {
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

	public void obterMelhorCaminho() {
		while (!verticesNaoVisitados.isEmpty()) {
			for (Town vertice : verticesNaoVisitados) {
				Town verticeMenorDistanciaEstimada = getVerticeMenorDistanciaEstimada(vertice);

				vertice.setVisitado(true);
				verticesNaoVisitados.remove(vertice);

				reajustarMenoresDistanciasEstimadas(verticeMenorDistanciaEstimada);
			}
		}
	}
}
