package br.com.trains;

/**
 * Representa uma aresta.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Rota {

	private Town origem;
	
	private Town destino;
	
	private Integer distancia;
	
	public Rota(Town origem, Town destino, Integer distancia) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
	}

	public Town getOrigem() {
		return origem;
	}

	public void setOrigem(Town origem) {
		this.origem = origem;
	}

	public Town getDestino() {
		return destino;
	}

	public void setDestino(Town destino) {
		this.destino = destino;
	}

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	
	@Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        
        if (object == null)
            return false;
        
        if (getClass() != object.getClass())
            return false;
        
        //Rota rotaToCompare = (Rota) object;
        
        //if (origem != null && origem.getId().equals(rotaToCompare.getOrigem().getId())) {
        	//return true;
        //}
        
        return false;
    }

	@Override
	public String toString() {
		return String.valueOf(this.getOrigem()) + String.valueOf(this.getDestino()) + String.valueOf(this.getDistancia());
	}
}
