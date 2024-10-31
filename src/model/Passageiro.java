//Passageiro.java

package model;

import java.time.LocalDate;
import java.time.Period;

public class Passageiro extends Pessoa {

	protected String tipo;
	protected int idade;
	
	public Passageiro() {
		
	}
	
	public Passageiro(String umNome,String umCpf, String data){
		super(umNome,umCpf,data);
	}

	public String getTipo() {
		return this.tipo;
	}
	
	public String modalidade(int r) {
		
		String dataNascimentoStr = super.getData();
		
		if (dataNascimentoStr == null || dataNascimentoStr.isEmpty()) {
		throw new IllegalArgumentException("Nulo ou vazio");
		
		}
		
		LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr); //para calcular a idade da pessoa
		LocalDate dataAtual = LocalDate.now();
		
		Period periodo = Period.between(dataNascimento, dataAtual);
		this.idade=periodo.getYears();
		
		
		if(r==1) {
			if(this.idade>=60) {
				this.tipo="PASSE LIVRE +60";
				
			}else
				System.out.println("VOCÊ NÃO POSSUI IDADE SUFICIENTE");
		}
		else
			if(r==2) {
				if(this.idade>=65) {
					this.tipo = "PASSE LIVRE +65";
					
				}
				else 
					System.out.println("VOCÊ NÃO POSSUI IDADE SUFICIENTE");
			}
			else 
				if(r==3) {
					this.tipo="PCD";
					
				}
				else 
					if(r==4) {
						this.tipo="ESTUDANTE";
						
					}
					else 
						if(r==5) {
							this.tipo="PASSE ANTECIPADO";
		}
		
		return this.tipo;
	}
	
	
}
