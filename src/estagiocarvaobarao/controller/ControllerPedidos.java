/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import estagiocarvaobarao.entidade.Pedido;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author Paulo
 */
public class ControllerPedidos {

    Venda v = new Venda();
    Produto_Venda pv = new Produto_Venda();

    public List<Venda> buscar(int codcli, String emissao, String situacao) {
        if (codcli != 0 && emissao.isEmpty() && situacao.isEmpty()) {
            return v.getPedidoCod(codcli);
        } else if (codcli == 0 && !emissao.isEmpty() && situacao.isEmpty()) {
            return v.getPedidoEmissao(emissao);
        } else if (codcli == 0 && emissao.isEmpty() && !situacao.isEmpty()) {
            return v.getPedidoSitu(situacao);
        } else if (codcli != 0 && emissao.isEmpty() && !situacao.isEmpty()) {
            return v.getPedidoSituCli(codcli,situacao);
        } else {
            return v.getPedidoAll(codcli, emissao, situacao);
        }

    }

    public List<Produto_Venda> buscarProd(int codvenda) {
        return pv.getporVenda(codvenda);
    }

    public boolean alterar(int codvenda, String situacao) {
        List<Venda> itemsV = new ArrayList();
        return v.alterarSituacao(codvenda, situacao);
    }
     public boolean alterarSituacaoItems(ObservableList<Venda> items) {
        List<Venda> itemsV = new ArrayList();
        itemsV = items;
        return v.alterarSituacaoItems(items);
    }

    

}
