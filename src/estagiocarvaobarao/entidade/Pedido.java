/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALPedido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Pedido {

    private int codigo;
    private Date emissao;
    private Cliente cliente;
    private Produto produto;
    private int qtdepacotes;
    private int cod_venda;
    private int qtdepacKg;
    private String situacao;
    DALPedido dal = new DALPedido();

    public Pedido() {
    }

    public Pedido(int codigo, Date emissao, Cliente cliente, Produto produto, int cod_venda, int qtdepacotes, int qtdepacKg, String situacao) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.cliente = cliente;
        this.produto = produto;
        this.cod_venda = cod_venda;
        this.qtdepacotes = qtdepacotes;
        this.qtdepacKg = qtdepacKg;
        this.situacao = situacao;
    }

    public Pedido(Date emissao, int cod_venda, String situacao) {
        this.emissao = emissao;
        this.cod_venda = cod_venda;
        this.situacao = situacao;
    }

    public String getEmissao() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(emissao);

    }

    public Date getEmissaoDate() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQtdepacotes() {
        return qtdepacotes;
    }

    public void setQtdepacotes(int qtdepacotes) {
        this.qtdepacotes = qtdepacotes;
    }

    public int getCod_venda() {
        return cod_venda;
    }

    public void setCod_venda(int cod_venda) {
        this.cod_venda = cod_venda;
    }

    public int getQtdepacKg() {
        return qtdepacKg;
    }

    public void setQtdepacKg(int qtdepacKg) {
        this.qtdepacKg = qtdepacKg;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public boolean alterar(int cod_venda, String situacao) {
        return dal.alterar(cod_venda, situacao);
    }

    public List<Pedido> getPedido(int cli, Date emissao) {
        return dal.getPedido(cli, emissao);
    }

}
