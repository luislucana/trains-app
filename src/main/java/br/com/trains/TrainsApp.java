package br.com.trains;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class TrainsApp {

	public static void main(String[] args) {
		System.out.println("Informe um grafo: ");
		
		// Input: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		String[] splittedInput = readInput();
		
		LinkedList<Rota> rotas = parseToRotas(splittedInput);
		
		Grafo grafo = new Grafo();
		grafo.build(rotas);
		grafo.executar();
	}
	
	private static String[] readInput() {
		Scanner scanner = new Scanner(System.in);
		String grafoInformado = scanner.nextLine();
		//String grafoInformado = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		
		scanner.close();
		
		if (grafoInformado == null || grafoInformado.trim().length() == 0) {
			throw new IllegalArgumentException("Input invalido.");
		}
		
		grafoInformado = grafoInformado.replaceAll(" ", "");
		
		String[] splittedInput = grafoInformado.split(",");
		
		return splittedInput;
	}
	
	private static LinkedList<Rota> parseToRotas(String[] rotasStr) {
		LinkedList<Rota> listaRotas = new LinkedList<Rota>();
		
		for (String rotaStr : rotasStr) {
			String origem = rotaStr.substring(0, 1);
			String destino = rotaStr.substring(1, 2);
			String distanciaStr = rotaStr.substring(2, rotaStr.length());
			
			if (!isIntegerNumber(distanciaStr)) {
				throw new IllegalArgumentException("Informe uma distancia valida.");
			}
			
			Town townOrigem = new Town(origem);
			Town townDestino = new Town(destino);
			Integer distancia = Integer.valueOf(distanciaStr);
			
			Rota rota = new Rota(townOrigem, townDestino, distancia);
			
			listaRotas.add(rota);
		}
		
		return listaRotas;
	}
	
	private static Boolean isIntegerNumber(String s) {
		Boolean isIntegerNumber = Boolean.FALSE;
		
		try {
			Integer.parseInt(s);
			
			isIntegerNumber = Boolean.TRUE;
		} catch (NumberFormatException nfe) {
			isIntegerNumber = Boolean.FALSE;
		}
		
		return isIntegerNumber;
	}
}
