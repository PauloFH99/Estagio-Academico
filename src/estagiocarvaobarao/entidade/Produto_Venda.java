package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALProdutoVenda;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class Produto_Venda {

    private Venda venda;
    private Produto cod_produto;
    private String descri;
    private int codprod;
    private int qtde;

    public Produto_Venda(Produto cod_produto, String descri, int codprod, int qtde, double unitario, double total) {
        this.cod_produto = cod_produto;
        this.descri = descri;
        this.codprod = codprod;
        this.qtde = qtde;
        this.unitario = unitario;
        this.total = total;
    }

    public Produto_Venda(String descri, int codprod, int qtde, double unitario, double total) {
        this.descri = descri;
        this.codprod = codprod;
        this.qtde = qtde;
        this.unitario = unitario;
        this.total = total;
    }

    public Produto_Venda(Venda venda) {
        this.venda = venda;
    }

    public Produto_Venda(int quantidade, Venda venda, Produto produto) {
        this.cod_produto = produto;
        this.venda = venda;
        this.qtde = quantidade;

    }

    public Venda getVenda() {
        return venda;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public int getCodprod() {
        return codprod;
    }

    public void setCodprod(int codprod) {
        this.codprod = codprod;
    }
    private double unitario;
    private double total;

    public Produto_Venda() {
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getCod_produto() {
        return cod_produto;
    }

    public void setCod_produto(Produto cod_produto) {
        this.cod_produto = cod_produto;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public double getUnitario() {
        return unitario;
    }

    public void setUnitario(double unitario) {
        this.unitario = unitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean apagar(int cod) {
        DALProdutoVenda dal = new DALProdutoVenda();
        return dal.apagar(cod);
    }
     public boolean apagar(int cod,int codP) {
        DALProdutoVenda dal = new DALProdutoVenda();
        return dal.apagar(cod,codP);
    }

    public List<Produto_Venda> getporVenda(int cod) {
        DALProdutoVenda dal = new DALProdutoVenda();
        return dal.getporVenda(cod);
    }

    public boolean verificaProd(int codP, int cod) {
          DALProdutoVenda dal = new DALProdutoVenda();
        return dal.verificaProd(codP,cod);
    }
     public int qtdeprodAnterior(int codP,int cod){
        DALProdutoVenda dal = new DALProdutoVenda();
        return dal.qtdeprodAnterior(codP,cod);
    }

    
}
