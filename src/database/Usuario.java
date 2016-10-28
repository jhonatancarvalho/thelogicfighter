package database;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import main.Assets;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Basic(optional = false)
	@Column(name = "NOME")
	private String nome;

	@Basic(optional = false)
	@Column(name = "SENHA")
	private String senha;

	@Basic(optional = false)
	@Column(name = "FIRSTLOGIN")
	private Integer firstLogin;

	public Usuario() {}

	public Usuario(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Integer firstLogin) {
		this.firstLogin = firstLogin;
	}

	//cadastra um novo usuario
	public boolean cadastrarUsuario() { 
		if (!getNome().equals("") && !getSenha().equals("")){
			if(verificaNomeUsuario()){
				setFirstLogin(0);
				Assets.em.getTransaction().begin();
				Assets.em.persist(this);
				Assets.em.getTransaction().commit();
				Status status = new Status(getId(), 0, 0, Integer.parseInt(Assets.config.getProperty("lifeInicial")), Integer.parseInt(Assets.config.getProperty("staminaInicial")), Integer.parseInt(Assets.config.getProperty("forcaInicial")), Integer.parseInt(Assets.config.getProperty("defesaInicial")));
				status.cadastrarStatus();
				return true;
			}
		}
		return false;
	}

	//verifica se usuario com este nome ja esta na base, retorna true quando não acha nome igual na base.
	public boolean verificaNomeUsuario() {
		ArrayList<String> lista = new ArrayList<String>();
		Query hqlEs = Assets.em.createQuery("FROM Usuario");
		@SuppressWarnings("unchecked")
		ArrayList<Usuario> listaEsc = (ArrayList<Usuario>)hqlEs.getResultList();	
		for (Usuario es : listaEsc){					
			lista.add(es.getNome());
		}	
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).equals(this.getNome())){
				return false;
			}
		}
		return true;
	}

	//verifica se dados colocados estão corretos
	public boolean verificaUsuario() {
		ArrayList<String> lista = new ArrayList<String>();
		Query hqlEs = Assets.em.createQuery("FROM Usuario");
		@SuppressWarnings("unchecked")
		ArrayList<Usuario> listaEsc = (ArrayList<Usuario>)hqlEs.getResultList();	
		for (Usuario es : listaEsc){					
			lista.add(es.getNome()+"_"+es.getSenha()+"_"+es.getId());
		}	

		String[] vetor;
		boolean verificaLogin = false;
		for (String ord : lista){
			vetor = ord.split("_");
			if (vetor[0].equals(getNome()) && vetor[1].equals(getSenha())){
				setId(Integer.parseInt(vetor[2]));
				verificaLogin = true;
			}
		}
		return verificaLogin;
	}


	public void exclui (){
		ArrayList<String> lista = new ArrayList<String>();
		Query hqlEs = Assets.em.createQuery("FROM Usuario");
		@SuppressWarnings("unchecked")
		ArrayList<Usuario> listaEsc = (ArrayList<Usuario>)hqlEs.getResultList();	
		for (Usuario es : listaEsc){					
			lista.add(es.getNome());

			if(es.getNome().equals(this.getNome())){
				//usuario.setId(es.getId());
				Assets.em.getTransaction().begin();
				Assets.em.remove(Assets.em.merge(es));
				Assets.em.getTransaction().commit();
			}

			System.out.println(es.getNome());
			System.out.println(es.getSenha());
			System.out.println(es.getId());
		}	
		//		
		//		em.getTransaction().begin();
		//		em.remove(em.merge(usuario));
		//		em.getTransaction().commit();
	}


	//retorna lista com usuarios cadastrados
	public ArrayList<String> getUsuarios() {
		ArrayList<String> lista = new ArrayList<String>();
		Query hqlEs = Assets.em.createQuery("FROM Usuario");
		@SuppressWarnings("unchecked")
		ArrayList<Usuario> listaEsc = (ArrayList<Usuario>)hqlEs.getResultList();	
		for (Usuario es : listaEsc){					
			lista.add(es.getNome());
			System.out.println(es.getNome());
		}	
		return lista;
	}

	//retorna lista com usuarios cadastrados
	public boolean verificaFirstLogin() {
		Query hqlEs = Assets.em.createQuery("FROM Usuario");
		@SuppressWarnings("unchecked")
		ArrayList<Usuario> listaEsc = (ArrayList<Usuario>)hqlEs.getResultList();	
		for (Usuario es : listaEsc){					
			if (es.getNome().equals(getNome())){
				if (es.getFirstLogin() == 0){
					es.setFirstLogin(1);
					Assets.em.getTransaction().begin();
					Assets.em.merge(es);
					Assets.em.getTransaction().commit();
					return true;
				}
			}
		}	
		return false;
	}

}
