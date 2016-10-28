package database;

import java.util.ArrayList;
import java.util.Properties;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import entities.Player;

import screens.GameScreen;
import screens.LoadScreen;

import main.Assets;

@Entity
@Table(name = "STATUS")
public class Status {

	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@Basic(optional = false)
	@Column(name = "LVL")
	private int lvl;

	@Basic(optional = false)
	@Column(name = "XP")
	private int xp;

	@Basic(optional = false)
	@Column(name = "HP")
	private int hp;

	@Basic(optional = false)
	@Column(name = "SP")
	private int sp;

	@Basic(optional = false)
	@Column(name = "ATK")
	private int atk;

	@Basic(optional = false)
	@Column(name = "DEF")
	private int def;

	public Status(){}

	public Status(Integer id, int lvl, int xp, int hp, int sp, int atk, int def) {
		this.id = id;
		this.lvl = lvl;
		this.xp = xp;
		this.hp = hp;
		this.sp = sp;
		this.atk = atk;
		this.def = def;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Status other = (Status) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

	@Override
	public String toString() {
		return "Status [id=" + id + ", lvl=" + lvl + ", xp=" + xp + ", hp="
				+ hp + ", sp=" + sp + ", atk=" + atk + ", def=" + def + "]";
	}

	public void cadastrarStatus() { 
		Assets.em.getTransaction().begin();
		Assets.em.persist(this);
		Assets.em.getTransaction().commit();
	}

	// retorna lista status do usuario logado -
	// respctivamente: xp, hp, sp, atk, def
	@SuppressWarnings("unchecked")
	public Status getStatus() {
		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (propertiesFase.getProperty("playerSaveLoadDb").equals("true")){	
			Query hqlEs = Assets.em.createQuery("FROM Status E WHERE E.id = "+GameScreen.usuario.getId());
			ArrayList<Status> statusPlayer = (ArrayList<Status>) hqlEs.getResultList();
			return statusPlayer.get(0);
		} else {
			int level = Integer.parseInt(propertiesFase.getProperty("levelFase"));
			int xpTotal = Integer.parseInt(propertiesFase.getProperty("expFase")); 
			int hpTotal = Integer.parseInt(propertiesFase.getProperty("lifeFase"));
			int spTotal = Integer.parseInt(propertiesFase.getProperty("staminaFase"));
			int forca = Integer.parseInt(propertiesFase.getProperty("forcaFase"));
			int  defesa = Integer.parseInt(propertiesFase.getProperty("defesaFase"));
			return new Status(GameScreen.usuario.getId(), level, xpTotal, hpTotal, spTotal, forca, defesa); 
		}
	}

	// atualiza as informações do status no banco de dados.
	public void atualizaStatus() {
		Properties propertiesFase = LoadScreen.getMapConfig(LoadScreen.faseAtual);
		if (propertiesFase.getProperty("playerSaveLoadDb").equals("true")){	
			Query hqlEs = Assets.em.createQuery("FROM Status E WHERE E.id = "+GameScreen.usuario.getId());
			@SuppressWarnings("unchecked")
			ArrayList<Status> listaSt = (ArrayList<Status>)hqlEs.getResultList();	
			for (Status st : listaSt){					
				st.setLvl(Player.level);
				st.setXp((int) Player.xpAtual);
				st.setHp((int) Player.hpTotal);
				st.setSp((int) Player.spTotal);
				st.setAtk(Player.forca);
				st.setDef(Player.defesa);
				GameScreen.statusUsuario = st;
				Assets.em.getTransaction().begin();
				Assets.em.merge(st);
				Assets.em.getTransaction().commit();
			}	
		}
	}

}
