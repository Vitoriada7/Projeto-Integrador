package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cartao;
import model.Passageiro;
import model.Pessoa;
public class CartaoDAO {
	
	private Connection bd;	
	
	public CartaoDAO() {
		this.bd = BancoDeDados.getBd();
	}
	
	//CREATE
	public void criarCartao(Cartao c,PassageiroDAO ps, String cpf) throws SQLException {
		String query = """
				INSERT INTO cartao
				VALUES (null, ?, ?, ?)
		""";
		
		PreparedStatement st = this.bd.prepareStatement(query);
		st.setInt(1, ps.getIdbyPessoa(cpf));
		st.setDouble(2, c.calcularTarifa(ps.getModalidade(cpf)));
		st.setDouble(3, c.getSaldo());
		st.executeUpdate();
	}
	
	//UPDATE
	public void recarregarCartao(double saldo, PassageiroDAO ps, String cpf) throws SQLException{
		String query = """
				UPDATE cartao
				SET saldo = saldo +  ?
				WHERE cod_pass = ?   
		""";
		PreparedStatement st = bd.prepareStatement(query);
		st.setDouble(1, saldo);
		st.setInt(2, ps.getIdbyPessoa(cpf));
		st.executeUpdate();
	}
	
	//DELETE - EXLUI SOMENTE O CARTAO
	public void deletarCartao(Cartao c, PassageiroDAO psdao, String cpf) throws SQLException {
		String query = """
				DELETE FROM cartao
				WHERE cod_cartao = ?
		""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, getIdbyPass(cpf,psdao));
		st.executeUpdate();
	}
	
	//ACESSA A CHAVE PRIMÁRIA DA TABELA "CARTÃO" NO BD E RETORNA
	public int getIdbyPass(String cpf, PassageiroDAO psdao) throws SQLException{
		String query = """
				SELECT cod_cartao
				FROM cartao
				WHERE cod_pass = ?
				""";
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, psdao.getIdbyPessoa(cpf));
		ResultSet res = st.executeQuery();
		
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			int idC = res.getInt("cod_cartao");
			return idC;
			
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return 0;
	}
	
	//ACESSA O SALDO DA TABELA "CARTÃO" NO BD E RETORNA
	public double getSaldobyCpf(PassageiroDAO ps, String umCpf) throws SQLException{
		String query = """
				SELECT saldo
				FROM cartao
				WHERE cod_pass = ?
				""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, ps.getIdbyPessoa(umCpf));
		ResultSet res = st.executeQuery();
		
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			double saldo = res.getDouble("saldo");
			return saldo;
			
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return 0;
		
		
	}
}