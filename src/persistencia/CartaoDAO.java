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
