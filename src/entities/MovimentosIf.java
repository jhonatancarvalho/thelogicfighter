package entities;

import java.util.ArrayList;

public class MovimentosIf {

	String cabecalho = "";
	ArrayList<String> movimentos = new ArrayList<>();

	public MovimentosIf() {
	}

	public String getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	public ArrayList<String> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(ArrayList<String> movimentos) {
		this.movimentos = movimentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cabecalho == null) ? 0 : cabecalho.hashCode());
		result = prime * result
				+ ((movimentos == null) ? 0 : movimentos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentosIf other = (MovimentosIf) obj;
		if (cabecalho == null) {
			if (other.cabecalho != null)
				return false;
		} else if (!cabecalho.equals(other.cabecalho))
			return false;
		if (movimentos == null) {
			if (other.movimentos != null)
				return false;
		} else if (!movimentos.equals(other.movimentos))
			return false;
		return true;
	}
	
}
