/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALEntrega;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Entrega {

    private int codigo;
    private Date emissao;
    private String rota;
    private Produto produto;
    private int carga;
    private int venda;
    private int retorno;
    private int codentrega;
    DALEntrega dal = new DALEntrega();

    public Entrega() {
    }

    public Entrega(int codigo, Date emissao, String rota) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.rota = rota;
    }

    public Entrega(int codigo, Produto produto, int codentrega, int carga, int venda, int retorno) {
        this.codigo = codigo;
        this.produto = produto;
        this.codentrega = codentrega;
        this.carga = carga;
        this.venda = venda;
        this.retorno = retorno;
    }

    public Entrega(Produto produto, int carga, int venda, int retorno) {
        this.produto = produto;
        this.carga = carga;
        this.venda = venda;
        this.retorno = retorno;
    }

    public Entrega(Produto produto, int carga, int venda, int retorno, int codentrega) {
        this.produto = produto;
        this.carga = carga;
        this.venda = venda;
        this.retorno = retorno;
        this.codentrega = codentrega;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
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

    public int getVenda() {
        return venda;
    }

    public void setVenda(int venda) {
        this.venda = venda;
    }

    public int getRetorno() {
        return retorno;
    }

    public void setRetorno(int retorno) {
        this.retorno = retorno;
    }

    public int getCodentrega() {
        return codentrega;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public void setCodentrega(int codentrega) {
        this.codentrega = codentrega;
    }

    public List<Entrega> getInit() {
        return dal.getInit();
    }

    public List<Entrega> getAll(int cod_entrega) {
        return dal.getAll(cod_entrega);
    }

    public boolean gravar(List<Despesas_Entrega> Despesas, List<Entrega> Entregas, LocalDate emissao,
            Funcionario funcionario, String rota) {
        return dal.gravar(Despesas, Entregas, emissao, funcionario, rota);
    }

    public boolean alterar(int cod, List<Despesas_Entrega> Despesas, List<Entrega> Entregas, LocalDate emissao, Funcionario funcionario, String rota) {
        return dal.alterar(cod, Despesas, Entregas, emissao, funcionario, rota);
    }

    public int getQtdeAnt(int codP, int cod) {
        return dal.getQtdeAnt(codP, cod);
    }

    public boolean apagar(int cod) {
        return dal.apagar(cod);
    }

    public List<Entrega> getProds(int cod) {
        return dal.getProds(cod);
    }

    public boolean apagarProds(int cod) {
         return dal.apagarProds(cod);
    }

}
