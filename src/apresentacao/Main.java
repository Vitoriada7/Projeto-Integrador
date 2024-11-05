//Main.java
// :)
package apresentacao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Pessoa;
import model.Passageiro;
import persistencia.PassageiroDAO;
import persistencia.PessoaDAO;

public class Main {

	public static void main(String[] args) throws SQLException {
		try {
			Scanner teclado = new Scanner(System.in);
			
				System.out.println("Bem-Vindo a Coleo!");
				
				System.out.println("1-Cadastrar | 2- Listar cadastros | 3-Atualizar | 4-Exluir | 5- Recarregar cartão ");
				int op = teclado.nextInt();
				teclado.nextLine();
				
				if(op==1) { //  cadastrar usuário e passar a modalidade para o passageiro
					try {
					System.out.println("Informe o nome:");
					String nome = teclado.nextLine();
					System.out.println("Informe o cpf");
					String cpf = teclado.nextLine();
					System.out.println("Informe a data de nascimento: ");
					String data_nasc = teclado.nextLine();
					
					Passageiro ps = new Passageiro(nome,cpf,data_nasc);
					
					Pessoa p = new Pessoa(nome, cpf, data_nasc);
					
					PassageiroDAO psd = new PassageiroDAO();  
					
					PessoaDAO pd = new PessoaDAO();
					
					pd.cadastrar(p);
					
					System.out.println("Escolha uma dessas modalidades? (Informe o número correspondente)");
					System.out.println("1-PASSE LIVRE +60");
					System.out.println("2-PASSE LIVRE +65");
					System.out.println("3-PCD");
					System.out.println("4-ESTUDANTE");
					System.out.println("5-PASSE ANTECIPADO");
					int op2 = teclado.nextInt();
					
					System.out.println(ps.modalidade(op2)); //  temporário, só pra testar na hora de rodar
					
					psd.beneficio(ps);
					
					
				} catch (SQLIntegrityConstraintViolationException e) {
					// tentativa de registro duplicado
					System.out.println("\n*** Esse CPF já foi cadastrado!");
					//continue;   *ele naõ roda quando eu uso o "continue"
				}
				System.out.println("\nUsuário cadastrado com sucesso!");
					
				}else
					if(op==2) { //  listar pessoas cadastradas
						
						PessoaDAO pd = new PessoaDAO();
						ArrayList<Pessoa> lista = pd.getAll();
						if (lista.size() == 0) {
							System.out.println("\nO cadastro está vazio.");
						} else {
							System.out.println("\nPessoas cadastradas:");
							for (Pessoa p: lista) {
								System.out.println(p);
							}
						}
						
					
					}else 
						if(op==3) {
							System.out.println("O que você deseja atualizar?");
							System.out.println("1-Nome cadastrado || 2-Modalidade ");
							int opcao = teclado.nextInt();
							teclado.nextLine();  //  limpar linha
							
							if(opcao==1) { //  Atualizar nome   *funciona :)
								
							             	
								System.out.println("Informe o cpf do cadastro que você deseja atualizar o nome: ");
								String cpf = teclado.nextLine();
								
								System.out.println("Informe o novo nome: ");
								String nome = teclado.nextLine();
								
								Pessoa p = new Pessoa();
								PessoaDAO pdao = new PessoaDAO();
								
								p.setNome(nome);
								
								p.setCpf(cpf);
								
								pdao.atualizarNome(p);
								
							}else
								if(opcao==2) {  //atualizar o nome  *esta apontando erro
									
									System.out.println("Informe o cpf do cadastro que você deseja atualizar a modalidade: ");
									String cpf = teclado.nextLine();
									
									System.out.println("Informe a sua data de nascimento: ");
									String data = teclado.nextLine();
									
									System.out.println("Escolha uma dessas modalidades? (Informe o número correspondente)");
									System.out.println("1-PASSE LIVRE +60");
									System.out.println("2-PASSE LIVRE +65");
									System.out.println("3-PCD");
									System.out.println("4-ESTUDANTE");
									System.out.println("5-PASSE ANTECIPADO");
									int r = teclado.nextInt();
									
									Passageiro ps = new Passageiro();
									PassageiroDAO psd = new PassageiroDAO();
									Pessoa p = new Pessoa();
									
									p.setData(data);
									
									System.out.println(p.getData());
									
									ps.modalidade(r);
									
									System.out.println(p.getData());
									
									psd.atualizarModalidade(ps, cpf);
									
									
								}else 
									System.out.println("Esta opção não está disponível!");
					
							
						}else
							if(op==4) { // deletar usuário   * funciona
								
								System.out.println("Informe o cpf");
								String cpf = teclado.nextLine();
								
								PessoaDAO pd = new PessoaDAO();
								
								Pessoa p = new Pessoa();
								
								p.setCpf(cpf);
								
								pd.deletar(p);
								
								
							}else 
								if(op==5) {
									
									
								}

				
				teclado.close();
			
		}catch (SQLException e){
			// ocorreu algum erro inesperado, vamos imprimir o stack trace
			e.printStackTrace();
		}finally {
			System.out.println();
		}
		
		
	}

}