/*
package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cartao;
import model.Passageiro;
public class CartaoDAO {
	
	private Connection bd;
	private double tarifa;
	PassageiroDAO ps;	
	
	public CartaoDAO() {
		this.bd = BancoDeDados.getBd();
	}
	
	public double getTarifa() {
		return this.tarifa;
	}
	
	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}
	
	public double selectTarifa(String cpf) throws SQLException{  //busca o valor da tarifa no banco de dados
		String query = """
				SELECT tarifa
				FROM cartao
				WHERE cod_pass = ?
				""";		
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, ps.getIdbyPessoa(cpf));
		ResultSet res = st.executeQuery();
		boolean nenhum = true;
		while (res.next()) {
			nenhum = false;
			double tarifa = res.getDouble("tarifa");
			return tarifa;
		}
		if (nenhum) {
			System.out.println("Nenhum registro encontrado");
		}
		return 0;
	}
	
	public void recarregarCartao(Cartao c, Passageiro p) throws SQLException{
		String query = """
				UPDATE cartao
				SET saldo = saldo + ?
				WHERE cpf = ?   
		""";
		PreparedStatement st = bd.prepareStatement(query);
		st.setDouble(1, c.getSaldo());
		st.setString(2, p.getCpf());//teria que usar chave primaria da pessoa
		st.executeUpdate();
	}
}
*/



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
	
	
	public void criarCartao(Cartao c,PassageiroDAO ps, String cpf) throws SQLException { //  cadastrar usuário 
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
	
	public void deletarCartao(Cartao c, PassageiroDAO psdao, String cpf) throws SQLException { //  deletar usuário a partir do CPF
		String query = """
				DELETE FROM cartao
				WHERE cod_cartao = ?
		""";
		
		PreparedStatement st = bd.prepareStatement(query);
		st.setInt(1, getIdbyPass(cpf,psdao));
		st.executeUpdate();
	}
	
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
//}
	
	public double getSaldobyCpf(PassageiroDAO ps, String umCpf) throws SQLException{ //localiza e retorna o id da pessoa
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