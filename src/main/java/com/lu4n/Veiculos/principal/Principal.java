package com.lu4n.Veiculos.principal;

import com.lu4n.Veiculos.model.DadosMarcas;
import com.lu4n.Veiculos.model.Modelo;
import com.lu4n.Veiculos.model.Veiculo;
import com.lu4n.Veiculos.service.ConsumirApi;
import com.lu4n.Veiculos.service.ConverteDados;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumirApi consumo = new ConsumirApi();
    private ConverteDados converte = new ConverteDados();
    private Integer op;
    private String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void menu() {
        String endereco = " ";

        System.out.println("Seja bem vindo ao sistema para verificar a fipe dos veiculos selecionados");
        System.out.printf("           Menu       \n Escolha uma das opções:\n 1- Carro\n 2- Caminhão\n 3- Moto\n 0- para sair\n : ");
        var opcao = leitura.nextInt();
        switch (opcao) {
            case 1:
                endereco = URL_BASE + "carros/marcas";
                break;
            case 2:
                endereco = URL_BASE + "caminhoes/marcas";
                break;
            case 3:
                endereco = URL_BASE + "motos/marcas";
                break;
        }

        var json = consumo.consumirApi(endereco);
        System.out.println(json);
        var marcas = converte.obterLista(json, DadosMarcas.class);
        marcas.stream().sorted(Comparator.comparing(DadosMarcas::codigo)).forEach(System.out::println);

        System.out.println("informe o codigo da marca que deseja buscar");
        var codigo = leitura.nextInt();
        leitura.nextLine();
        endereco = endereco + "/" + codigo + "/modelos";
        json = consumo.consumirApi(endereco);
        System.out.println(json);


        var modeloLista = converte.obterDados(json, Modelo.class);
        modeloLista.modelos().stream().sorted(Comparator.comparing(DadosMarcas::codigo)).forEach(System.out::println);

        System.out.println("informe um trecho do nome do veiculo que deseja buscar");
        var nomeVeiculo = leitura.nextLine();

        List<DadosMarcas> modelosFiltrados =
                modeloLista.modelos()
                        .stream()
                        .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                        .collect(Collectors.toList());

        modelosFiltrados.forEach(System.out::println);
        System.out.println("por favor, digite o codigo do veiculo para buscar o valor: ");
        var codigoVeiculo = leitura.nextInt();
        leitura.nextLine();

        endereco = endereco + "/" + codigoVeiculo + "/anos";
        json = consumo.consumirApi(endereco);
        List<DadosMarcas> anos = converte.obterLista(json, DadosMarcas.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for(int i=0;i<anos.size();i++){
            var enderecosAnos = endereco + "/" + anos.get(i).codigo();
            System.out.println(enderecosAnos);
            json = consumo.consumirApi(enderecosAnos);
            Veiculo veiculo = converte.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        veiculos.forEach(System.out::println);
        System.out.println();
        System.out.println("Aperte 1 - para reiniciar ou 0 - para finalizar");
        op = leitura.nextInt();
        leitura.nextLine();
        if(op == 1)
            menu();
        else
            return;
    }
}
