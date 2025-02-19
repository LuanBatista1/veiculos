package com.lu4n.Veiculos.service;

import java.util.List;

public interface IConverteDados{
    <T> List<T> obterLista(String json, Class<T> classe);

    <T> T obterDados(String json, Class<T> classe);
}
