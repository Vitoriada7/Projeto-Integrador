package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import model.Pessoa;
public class PessoaDAO {
	private Connection bd;
	
	public PessoaDAO() {
		this.bd = BancoDeDados.getBd();
	}
	
	//CREATE
	public void cadastrar(Pessoa p) throws SQLException {
		String query = """
				INSERT INTO pessoa
				VALUES (null, ?, ?, ?)
		""";
		
		PreparedStatement st = this.bd.prepareStatement(query);
		st.setNString(1, p.getNome());
		st.setString(2, p.getCpf());
		st.setString(3, p.getData());
		st.executeUpdate();
	}
	
	//DELETE - APAGA TODOS OS RASTROS
	public void deletar(Pessoa p) throws SQLException { 
		String query = """
				DELETE FROM pessoa
				WHERE cpf = ?
		""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setString(1, p.getCpf());
		st.executeUpdate();
	}
	
	//UPDATE
	public void atualizarNome(Pessoa p) throws SQLException{
		String query = """
				UPDATE pessoa
				SET nome = ?
				WHERE cpf = ?
	    """;
				
		PreparedStatement st = this.bd.prepareStatement(query);
		
		st.setString(1, p.getNome());
		st.setString(2, p.getCpf());
		st.executeUpdate();		
	}
	
	//READ
	public ArrayList<Pessoa> getAll() throws SQLException {
		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
		String query = "SELECT nome, cpf, data_nasc FROM pessoa";
		
		PreparedStatement st = this.bd.prepareStatement(query);
		ResultSet res = st.executeQuery();
			
		while (res.next()) {
			String nome = res.getString("nome");
			String cpf = res.getString("cpf");
			String dataNasc = res.getString("data_nasc");
			Pessoa p = new Pessoa(nome, cpf, dataNasc);
			lista.add(p);
		}
		return lista;
	}
	
	//ACESSA A TABELA "PESSOA" NO BD E RETORNA O OBJETO PESSOA COMPLETO
	public Pessoa findbyCpf(String umCpf) throws SQLException{
		String query = """
				SELECT cod_pessoa 
				FROM pessoa
				WHERE cpf = ?
				""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setString(1, umCpf);
		ResultSet res = st.executeQuery();
		
				
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			String nome = res.getString("nome");  
			String cpf = res.getString("cpf");
			String data_nasc = res.getString("data_nasc");   
			return new Pessoa(nome,cpf,data_nasc);
			
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return null;
		
	}
	
	//ACESSA A CHAVE PRIMÁRIA DA TABELA "PESSOA" NO BD E RETORNA
	public int getIdbyCpf(String umCpf) throws SQLException{ //localiza e retorna o id da pessoa
		String query = """
				SELECT cod_pessoa
				FROM pessoa
				WHERE cpf = ?
				""";
		PreparedStatement st = bd.prepareStatement(query);
		st.setString(1, umCpf);
		ResultSet res = st.executeQuery();
		
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			int id = res.getInt("cod_pessoa");
			return id;
			
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return 0;
	}
	
}