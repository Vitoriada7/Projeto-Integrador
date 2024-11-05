package model;
import persistencia.CartaoDAO;

public class Cartao{
	
	protected Passageiro dono;
	protected double saldo = 0;
	CartaoDAO cd = new CartaoDAO();

	public Cartao(Passageiro dono){
		this.dono = dono;
	}
	
	public void calcularTarifa(String m) {
		cd.getTarifa();

		if(m.equals("PASSE LIVRE +60") || m.equals("PASSE LIVRE +65") || m.equals("PCD")) {
			cd.setTarifa(0);

		}

		else if (m.equals("ESTUDANTE")) {
			cd.setTarifa(cd.getTarifa()*0.5);
		}
		
		else if (m.equals("PASSE ANTECIPADO")) {
			cd.setTarifa(cd.getTarifa());

		}
		else {
			System.out.println("Esta modalidade não está disponível.");

		}

	}

	public double recarregarSaldo(int numP) {
		this.saldo += numP*cd.getTarifa();

		return this.saldo;

	}

	public double getSaldo() {
		return this.saldo;

	}

}