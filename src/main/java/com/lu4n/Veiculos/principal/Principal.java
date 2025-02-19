package com.lu4n.Veiculos.principal;

import com.lu4n.Veiculos.model.DadosMarcas;
import com.lu4n.Veiculos.service.ConsumirApi;
import com.lu4n.Veiculos.service.ConverteDados;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumirApi consumo = new ConsumirApi();
    private ConverteDados converte = new ConverteDados();
    private Integer op;
    private String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void menu(){
    String endereco = " ";

    System.out.println("Seja bem vindo ao sistema para verificar a fipe dos veiculos selecionados");
    System.out.printf("           Menu       \n Escolha uma das opções:\n 1- Carro\n 2- Caminhão\n 3- Moto\n 0- para sair\n : ");
    var opcao = leitura.nextInt();
        switch (opcao){
            case 1:
                endereco = URL_BASE+"carros/marcas";
                break;
            case 2:
                endereco = URL_BASE+"caminhoes/marcas";
                break;
            case 3:
                endereco = URL_BASE+"motos/marcas";
                break;
        }

    var json = consumo.consumirApi(endereco);
    System.out.println(json);
    var marcas = converte.obterLista(json,DadosMarcas.class);
    marcas.stream()
            .sorted(Comparator.comparing(DadosMarcas::codigo))
            .forEach(System.out::println);


    }
}
