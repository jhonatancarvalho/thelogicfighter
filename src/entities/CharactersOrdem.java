package entities;

import java.util.ArrayList;

public class CharactersOrdem {
	
	Integer positionX;
	Integer positionY;
	Integer hpTotal;
	Integer hpAtual;
	Integer spTotal;
	Integer spAtual;
	Integer forca;
	Integer defesa;
	
	public CharactersOrdem(){}
	
	public CharactersOrdem(Integer positionX, Integer positionY,
			Integer hpTotal, Integer hpAtual, Integer spTotal, Integer spAtual,
			Integer forca, Integer defesa) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.hpTotal = hpTotal;
		this.hpAtual = hpAtual;
		this.spTotal = spTotal;
		this.spAtual = spAtual;
		this.forca = forca;
		this.defesa = defesa;
	}
	public Integer getPositionX() {
		return positionX;
	}
	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}
	public Integer getPositionY() {
		return positionY;
	}
	public void setPositionY(Integer positionY) {
		this.positionY = positionY;
	}
	public Integer getHpTotal() {
		return hpTotal;
	}
	public void setHpTotal(Integer hpTotal) {
		this.hpTotal = hpTotal;
	}
	public Integer getHpAtual() {
		return hpAtual;
	}
	public void setHpAtual(Integer hpAtual) {
		this.hpAtual = hpAtual;
	}
	public Integer getSpTotal() {
		return spTotal;
	}
	public void setSpTotal(Integer spTotal) {
		this.spTotal = spTotal;
	}
	public Integer getSpAtual() {
		return spAtual;
	}
	public void setSpAtual(Integer spAtual) {
		this.spAtual = spAtual;
	}
	public Integer getForca() {
		return forca;
	}
	public void setForca(Integer forca) {
		this.forca = forca;
	}
	public Integer getDefesa() {
		return defesa;
	}
	public void setDefesa(Integer defesa) {
		this.defesa = defesa;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defesa == null) ? 0 : defesa.hashCode());
		result = prime * result + ((forca == null) ? 0 : forca.hashCode());
		result = prime * result + ((hpAtual == null) ? 0 : hpAtual.hashCode());
		result = prime * result + ((hpTotal == null) ? 0 : hpTotal.hashCode());
		result = prime * result
				+ ((positionX == null) ? 0 : positionX.hashCode());
		result = prime * result
				+ ((positionY == null) ? 0 : positionY.hashCode());
		result = prime * result + ((spAtual == null) ? 0 : spAtual.hashCode());
		result = prime * result + ((spTotal == null) ? 0 : spTotal.hashCode());
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
		CharactersOrdem other = (CharactersOrdem) obj;
		if (defesa == null) {
			if (other.defesa != null)
				return false;
		} else if (!defesa.equals(other.defesa))
			return false;
		if (forca == null) {
			if (other.forca != null)
				return false;
		} else if (!forca.equals(other.forca))
			return false;
		if (hpAtual == null) {
			if (other.hpAtual != null)
				return false;
		} else if (!hpAtual.equals(other.hpAtual))
			return false;
		if (hpTotal == null) {
			if (other.hpTotal != null)
				return false;
		} else if (!hpTotal.equals(other.hpTotal))
			return false;
		if (positionX == null) {
			if (other.positionX != null)
				return false;
		} else if (!positionX.equals(other.positionX))
			return false;
		if (positionY == null) {
			if (other.positionY != null)
				return false;
		} else if (!positionY.equals(other.positionY))
			return false;
		if (spAtual == null) {
			if (other.spAtual != null)
				return false;
		} else if (!spAtual.equals(other.spAtual))
			return false;
		if (spTotal == null) {
			if (other.spTotal != null)
				return false;
		} else if (!spTotal.equals(other.spTotal))
			return false;
		return true;
	}
	
	public CharactersOrdem convertePlayer(Player player){
		CharactersOrdem character = new CharactersOrdem((int) player.position.x, (int) player.position.y, (int) Player.hpTotal, (int) Player.hpAtual, (int) Player.spTotal, (int) Player.spAtual, Player.forca, Player.defesa);
		return character;
	}
	
	public CharactersOrdem converteInimigo(Inimigo inimigo){
		CharactersOrdem character = new CharactersOrdem((int) inimigo.position.x, (int) inimigo.position.y, (int) inimigo.hpTotal, (int) inimigo.hpAtual, (int) inimigo.spTotal, (int) inimigo.spAtual, inimigo.forca, inimigo.defesa);
		return character;
	}
	
	public ArrayList<CharactersOrdem> ordenaLista(ArrayList<CharactersOrdem> lista){
		for(int i = 0; i < lista.size(); i++) {  
			for(int j = i; j < lista.size(); j++) {  
				if((int) lista.get(i).getPositionY() < (int) lista.get(j).getPositionY()) {  
					CharactersOrdem x = lista.get(j);  
					lista.set(j, lista.get(i));
					lista.set(i, x);  
				}  
			}  
		}
		return lista;
	}

}
