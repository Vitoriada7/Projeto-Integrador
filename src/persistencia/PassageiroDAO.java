package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Passageiro;
public class PassageiroDAO {
	private Connection bd;
	private PessoaDAO psdao=new PessoaDAO();
	
	public PassageiroDAO() {
		this.bd = BancoDeDados.getBd();
	}
	
	//CREATE
	public void beneficio(Passageiro ps) throws SQLException{
		String query = """
				INSERT INTO passageiro
				VALUES (?,?,?)
		""";
		PreparedStatement st = this.bd.prepareStatement(query);
		st.setInt(1, psdao.getIdbyCpf(ps.getCpf()));
		st.setInt(2, psdao.getIdbyCpf(ps.getCpf()));
		st.setString(3, ps.getTipo());
		st.executeUpdate();  
	}
	
	//UPDATE
	public void atualizarModalidade(Passageiro ps, String cpf) throws SQLException{
		PessoaDAO pdao = new PessoaDAO();
		String query = """
				UPDATE passageiro
				SET modalidade = ?
				WHERE cod_pass = ?
	    """;
				
		PreparedStatement st = this.bd.prepareStatement(query);
		st.setString(1, ps.getTipo());
		st.setInt(2, pdao.getIdbyCpf(cpf));
		st.executeUpdate();			
							
	}
	
	//ACESSA O ATRIBUTO "MODALIDADE" DA TABELA "PASSAGEIRO" NO BD E RETORNA
	public String getModalidade(String cpf) throws SQLException{
		String query = """
				SELECT modalidade
				FROM passageiro
				WHERE cod_pessoa = ?
				""";
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, psdao.getIdbyCpf(cpf));
		ResultSet res = st.executeQuery();
		
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			String modalidade = res.getString("modalidade");
			return modalidade;
			
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return "null";
	}
	
	//ACESSA A CHAVE PRIMÁRIA DA TABELA "PASSAGEIRO" NO BD E RETORNA
	public int getIdbyPessoa(String cpf) throws SQLException{
		String query = """
				SELECT cod_pass
				FROM passageiro
				WHERE cod_pessoa = ?
				""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, psdao.getIdbyCpf(cpf));
		ResultSet res = st.executeQuery();
		
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			int id = res.getInt("cod_pass");
			return id;
			
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return 0;
	}
}
	
	
	