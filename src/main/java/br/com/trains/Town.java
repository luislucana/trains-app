package br.com.trains;

/**
 * Representa um no.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Town {
	
	private String name;
	
	private Boolean visitado = Boolean.FALSE;
	
	private Integer menorDistanciaEstimada = Integer.MAX_VALUE;
	
	public Town(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Informe um nome valido.");
		}
		
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(Boolean visitado) {
		this.visitado = visitado;
	}

	public Integer getMenorDistanciaEstimada() {
		return menorDistanciaEstimada;
	}

	public void setMenorDistanciaEstimada(Integer menorDistanciaEstimada) {
		this.menorDistanciaEstimada = menorDistanciaEstimada;
	}

	@Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        
        if (object == null)
            return false;
        
        if (getClass() != object.getClass())
            return false;
        
        Town townToCompare = (Town) object;
        
        if (name != null && name.equals(townToCompare.name)) {
        	return true;
        }
        
        return false;
    }

	@Override
	public String toString() {
		return String.valueOf(this.name);
	}
}
