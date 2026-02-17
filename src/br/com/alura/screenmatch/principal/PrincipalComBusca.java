package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.Gson;

import br.com.alura.screenmatch.modelos.Titulo;

public class PrincipalComBusca {

	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner leitura = new Scanner(System.in);
		System.out.println("Digite um filme para busca: ");
		var busca = leitura.nextLine();
		String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=123";

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endereco)).build();

		// O próximo passo para ver o resultado da API:
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		//System.out.println(response.body());
		
        String json = response.body();
        System.out.println(json);
		
        Gson gson = new Gson();
        Titulo meuTitulo = gson.fromJson(json, Titulo.class);
        System.out.println("Título: " + meuTitulo.getNome());

	}	
	
}
