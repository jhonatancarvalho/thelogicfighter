package database;

import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import main.Assets;
import screens.GameScreen;
import screens.LoadScreen;
import entities.BotoesPrincipais;

@Entity
@Table(name = "RECORDS")
public class Records {

	@Id
	@Basic(optional = false)
	@Column(name = "FASE")
	private String fase;

	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@Basic(optional = false)
	@Column(name = "PONTOS")
	private Integer pontos;

	public Records(){}

	public Records(Integer id, String fase, Integer pontos) {
		this.id = id;
		this.fase = fase;
		this.pontos = pontos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Records other = (Records) obj;
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Records [id=" + id + ", fase=" + fase + ", pontos=" + pontos
				+ "]";
	}

	public void cadastrarRecords() { 
		Assets.em.getTransaction().begin();
		Assets.em.persist(this);
		Assets.em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public Integer getRecords() {
		Query hqlEs = Assets.em.createQuery("FROM Records E WHERE E.id = "+GameScreen.usuario.getId()+" AND E.fase = '"+LoadScreen.faseAtual+"'");
		ArrayList<Records> statusPlayer;
		try {
			statusPlayer = (ArrayList<Records>) hqlEs.getResultList();
		} catch (Exception e) {
			return 0;
		}
		if (statusPlayer.size()<= 0){
			return 0;
		}
		return statusPlayer.get(0).getPontos();
	}

	// atualiza as informações do records no banco de dados.
	public void atualizaRecords() {
		Query hqlEs = Assets.em.createQuery("FROM Records E WHERE E.id = "+GameScreen.usuario.getId()+" AND E.fase = '"+LoadScreen.faseAtual+"'");
		@SuppressWarnings("unchecked")
		ArrayList<Records> listaSt = (ArrayList<Records>)hqlEs.getResultList();	
		for (Records st : listaSt){	
			if (BotoesPrincipais.getPoints() > st.getPontos()){
				st.setPontos(BotoesPrincipais.getPoints());
			}
			Assets.em.getTransaction().begin();
			Assets.em.merge(st);
			Assets.em.getTransaction().commit();
		}	
	}

	// verifica se ja existe record cadastrado para fase.
	@SuppressWarnings({ "unchecked" })
	public boolean checkRecords() {
		Query hqlEs = Assets.em.createQuery("FROM Records E WHERE E.id = "+GameScreen.usuario.getId()+" AND E.fase = '"+LoadScreen.faseAtual+"'");
		ArrayList<Records> listaSt;	
		try {
			listaSt = (ArrayList<Records>)hqlEs.getResultList();	
		} catch (Exception e) {
			return false;
		}
		if (listaSt.size()<= 0){
			return false;
		}
		return true;
	}

}
