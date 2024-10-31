//PessoaDAO.java

package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import model.Pessoa;

public class PessoaDAO {
	private Connection bd;
	
	public PessoaDAO() {
		this.bd = BancoDeDados.getBd();
	}
	
	public void cadastrar(Pessoa p) throws SQLException { //  cadastrar usuário 
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
	
	
	
	public void deletar(Pessoa p) throws SQLException { //  deletar usuário a partir do CPF
		String query = """
				DELETE FROM pessoa
				WHERE cpf = ?
		""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setString(1, p.getCpf());
		st.executeUpdate();
	}
	

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
	
	public int getIdbyCpf(String umCpf) throws SQLException{
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