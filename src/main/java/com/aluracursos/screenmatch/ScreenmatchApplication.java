package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		var consumoApi = new ConsumoAPI();
//		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=mr.robot&apikey=a42cdf0e");
//		System.out.println(json);
//		ConvierteDatos conversor = new ConvierteDatos();
//		var datos = conversor.obtenerDatos(json, DatosSerie.class);
//		System.out.println(datos);
//		json= consumoApi.obtenerDatos("https://www.omdbapi.com/?t=mr.robot&Season=1&episode=1&apikey=a42cdf0e");
//		DatosEpisodio episodios = conversor.obtenerDatos(json, DatosEpisodio.class);
//		System.out.println(episodios);
//		List<DatosTemporada> temporadas = new ArrayList<>();
//		for (int i = 1; i < datos.totalTemporadas(); i++) {
//			json= consumoApi.obtenerDatos("https://www.omdbapi.com/?t=mr.robot&Season="+i+"&apikey=a42cdf0e");
//			var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
//			temporadas.add(datosTemporada);
//		}
//		temporadas.forEach(System.out::println);
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
