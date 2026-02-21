package br.com.alura.screenmatch.principal;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;

public class PrincipalComBusca {

	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner leitura = new Scanner(System.in);
		String busca = "";
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting()
				.create();
		
		List<Titulo> titulos = new ArrayList<>();
		// equalsIgnoreCase() para que ele funcione independentemente de o texto ser
		// digitado com letras maiúsculas ou minúsculas.
		
		while (!busca.equalsIgnoreCase("sair")) {
			System.out.println("Digite um filme para busca: ");
			busca = leitura.nextLine();

			if (busca.equalsIgnoreCase("sair")) {
				break;
			}

			String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=c727e294";
			try {
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();

				// O próximo passo para ver o resultado da API:
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				String json = response.body();
				System.out.println(json);

				// Gson gson = new Gson();
				// TituloOmdb meuTitulo = gson.fromJson(json, TituloOmdb.class);
				// System.out.println(meuTitulo);

				// CÓDIGO DE EXEMPLO DA DOCUMENTAÇÃO GSON

				// Configura o Gson para entender que as chaves no JSON (como "Title")
				// devem ser mapeadas para os atributos Java seguindo o padrão UpperCamelCase
				//Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

				// Aqui está a: transformamos a String 'json' em um objeto 'TituloOmdb'
				TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
				System.out.println("Título convertido: " + meuTituloOmdb);

				Titulo meuTitulo = new Titulo(meuTituloOmdb);
				System.out.println("Titulo já convertido");
				System.out.println(meuTitulo);

				// FileWriter escrita = new FileWriter("filmes.txt");
				//FileWriter escrita = new FileWriter("filmes.txt");
				//escrita.write(meuTitulo.toString());
				//escrita.close();
				
				  titulos.add(meuTitulo);

			} catch (NumberFormatException e) {
				System.out.println("Aconteceu um erro:");
				System.out.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println("Algum erro de argumento na busca, verifique o endereço");
			} catch (ErroDeConversaoDeAnoException e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println(titulos);
		
		FileWriter escrita = new FileWriter("fimes.json");
		escrita.write(gson.toJson(titulos));
		escrita.close();
		
		
		System.out.println("O sistema finalizou corretamente");

	}

}

//matrix
//dogville

