package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE ="https://www.omdbapi.com/?t=";
    private final String API_KEY ="&apikey=a42cdf0e";
    private ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json= consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ", "+")+API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        // busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i < datos.totalTemporadas(); i++) {
            json= consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+"&Season="+i+API_KEY);
            var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
       // temporadas.forEach(System.out::println);
        //mostrar solo el titulo de los episodios para las temporadas 
//        for (int i = 0; i < datos.totalTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size() ; j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        //convertir todas las informaicones a una lista del tipo datos episodios
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
//        //top 5 episodios
//        System.out.println("Top 5 episodios ");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A)"+ e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segunda ordenacion (M>m)"+e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("tercer filtro Mayusculas"+e))
//                .limit(5)
//                .forEach(System.out::println);
        //Convirtiendo datos  a una lista tipo episodios
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                        .collect(Collectors.toList());
        episodios.forEach(System.out::println);

//        //Busqueda de episodios a partir de x año
//        System.out.println("Indica el año del cual deseas ver el episio");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate fechaBusqueda = LocalDate.of(fecha,1,1);
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada:" + e.getTemporada()+
//                                "\n Episodio:"+e.getTitulo()+
//                                    "\n Fecha de lanzamiento:"+e.getFechaDeLanzamiento().format(dtf)
//                ));

        // busca episodios por pedazo del titulo
//        System.out.println("por favor escriba el titulo del episodio que desea ver:");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("Los datos son:"+episodioBuscado.get());
//        }
//        else {
//            System.out.println("Episodio no encontrado");
//        }
        Map<Integer,Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("media de las evaluaciones:"+est.getAverage());
        System.out.println("episodio mejor evaluado:"+est.getMax());
        System.out.println("episodio peor evaluado:"+est.getMin());

    }
}
