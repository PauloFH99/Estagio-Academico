/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALProducao_Produto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Producao_Produtos {

    private int codigo;
    private Producao producao;
    private Produto produto;
    private int codprod;
    private int qtde_pacotes;
    private double total;
    DALProducao_Produto dal = new DALProducao_Produto();

    public Producao_Produtos() {
    }

    public Producao_Produtos(Producao producao, Produto produto, int codprod, int qtde_pacote) {
        this.producao = producao;
        this.produto = produto;
        this.codprod = codprod;
        this.qtde_pacotes = qtde_pacote;

    }

    public Producao_Produtos(Produto produto, int codprod, int qtde_pacotes, double total) {
        this.produto = produto;
        this.codprod = codprod;
        this.qtde_pacotes = qtde_pacotes;
        this.total = total;
    }

    public Producao_Produtos(int codigo, Producao producao, Produto produto, int codprod, int qtde_pacotes) {
        this.codigo = codigo;
        this.producao = producao;
        this.produto = produto;
        this.codprod = codprod;
        this.qtde_pacotes = qtde_pacotes;
    }

    public Producao_Produtos(int codigo, Producao producao, Produto produto, int codprod, int qtde_pacotes, double total) {
        this.codigo = codigo;
        this.producao = producao;
        this.produto = produto;
        this.codprod = codprod;
        this.qtde_pacotes = qtde_pacotes;
        this.total = total;
    }

    public Producao_Produtos(int qtde_pacotes, double total) {
        this.qtde_pacotes = qtde_pacotes;
        this.total = total;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Producao getProducao() {
        return producao;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getCodprod() {
        return codprod;
    }

    public void setCodprod(int codprod) {
        this.codprod = codprod;
    }

    public int getQtde_pacotes() {
        return qtde_pacotes;
    }

    public void setQtde_pacotes(int qtde_pacotes) {
        this.qtde_pacotes = qtde_pacotes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Producao_Produtos> getporProducao(int cod) {
        return dal.getporProducao(cod);
    }

    public boolean procura(Producao_Produtos prod, int cod) {
        return dal.procura(prod, cod);
    }

    public Producao_Produtos qtdeprodAnterior(int codprod, int cod) {
        return dal.qtdeprodAnterior(codprod, cod);
    }

    public boolean apagar(Producao_Produtos prod) {
        return dal.apagar(prod);
    }

}
