package com.gk_tabajara;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Anotacao;
import persistencia.AnotacaoDAO;

public class Main {
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		AnotacaoDAO dao = null;
		int option = 0;

		do {
			// adm, 123
			System.out.println("===== Login Usuario =====");
			System.out.print("Usuario: ");
			String user = in.nextLine();

			System.out.print("Senha: ");
			String senha = in.nextLine();

			try {
				dao = new AnotacaoDAO(user, senha);
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}
			
		} while (dao == null);

		do {
				System.out.println("\n===== Menu Lindo =====");
				System.out.println("1: Nova anotação;");
				System.out.println("2: Deletar anotação;");
				System.out.println("3: Alterar anotação;");
				System.out.println("4: Copiar uma anotação;");
				System.out.println("5: Listar anotacoes;");
				System.out.println("6: Sair.");
				System.out.print("Escolha uma opção: ");

				try {
					option = Integer.parseInt(in.nextLine());
				} catch (Exception e) {
					System.out.print("Erro: " + e.getMessage());
				}

				System.out.println();

				try {
				switch (option) {
						
					case 1 -> {
						try{
							Anotacao anotacao = new Anotacao();

							System.out.print("Titulo: ");
							String titulo = in.nextLine();
							anotacao.setTitulo(titulo);

							System.out.print("Descricao: ");
							String descricao = in.nextLine();
							anotacao.setDescricao(descricao);

							System.out.print("Cor (#FFFFFF): ");
							String cor = in.nextLine();
							anotacao.setCor(cor);

							LocalDateTime data_hora = LocalDateTime.now();
							anotacao.setDataHora(data_hora);

							String selecao;
							System.out.print("Adicionar foto? (s/n): ");
							selecao = in.nextLine();
							if (selecao.toLowerCase().startsWith("s")) {
								System.out.print("Caminho da foto: ");
								String caminho = in.nextLine();
								anotacao.setFotoByPath(caminho);
							} else {
								anotacao.setFoto(null);
							}

							anotacao = dao.inserir(anotacao);
							System.out.println("Anotacao inserida: " + anotacao);

						} catch (Exception e) {
							System.out.println("Erro: " + e.getMessage());
						}
				}
				
				case 2 -> {
					System.out.print("id: ");
					int id = Integer.parseInt(in.nextLine());

					Anotacao anotacao = dao.obter(id);

					if(anotacao == null){
						System.out.println("Erro: id nao encontrado");
						return;
					}

					dao.deletar(id);
					System.out.println("Anotacao excluida: " + anotacao);
				}
				
				case 3 -> {
					System.out.print("id: ");
					int id = Integer.parseInt(in.nextLine());

					Anotacao anotacao = dao.obter(id);

					if(anotacao == null){
						System.out.println("Erro: Anotacao nao encontrada");
						return;
					}

					System.out.println(anotacao);

					String selecao;

					System.out.print("Atualizar titulo? (s/n): ");
					selecao = in.nextLine();
					if (selecao.toLowerCase().startsWith("s")) {
						System.out.print("Novo titulo: ");
						anotacao.setTitulo(in.nextLine());
					}
					
					System.out.print("Atualizar descricao? (s/n): ");
					selecao = in.nextLine();
					if (selecao.toLowerCase().startsWith("s")) {
						System.out.print("Nova descricao: ");
						anotacao.setDescricao(in.nextLine());
					}
					
					System.out.print("Atualizar cor? (s/n): ");
					selecao = in.nextLine();
					if (selecao.toLowerCase().startsWith("s")) {
						System.out.print("Nova cor (#FFFFFF): ");
						anotacao.setCor(in.nextLine());
					}
					
					LocalDateTime data_hora = LocalDateTime.now();
					anotacao.setDataHora(data_hora);

					System.out.print("Atualizar foto? (s/n): ");
					selecao = in.nextLine();
					if (selecao.toLowerCase().startsWith("s")) {
						System.out.print("Nova foto (caminho): ");
						try {
							anotacao.setFotoByPath(in.nextLine());
						} catch (Exception e) {
							System.out.println("Erro: " + e.getMessage());
						}
					}

					System.out.print("Aprovar atualizacao? (s/n): ");
					selecao = in.nextLine();
					if (selecao.toLowerCase().startsWith("s")) {
						dao.atualizar(anotacao);
						System.out.println("Anotacao alterada: " + anotacao);
					} else {
						System.out.println("Atualizacao cancelada");
					}	
						
				}

				case 4 -> {
					System.out.print("id: ");
					int id = Integer.parseInt(in.nextLine());

					Anotacao anotacao = dao.obter(id);

					if(anotacao==null){
						System.out.println("Erro: Anotacao nao encontrada");
						return;
					}

					Anotacao clone = anotacao.clonar();
					clone.setDataHora(LocalDateTime.now());

					dao.inserir(clone);
					System.out.println("Nova anotacao: " + clone);
				}
				
				case 5 -> {
					System.out.print("Listar (asc ou desc): ");
					String ordem = in.nextLine();

					ArrayList<Anotacao> anotacoes = dao.listar(ordem);

					for (Anotacao anotacao : anotacoes) {
						System.out.println(anotacao);
					}
				}

				case 6 -> System.out.println("Hasta la vista, baby.\n");
				
				default -> System.out.println("Opcao invalida.");
			}
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}

		} while (option != 6);

		in.close();
	}
}