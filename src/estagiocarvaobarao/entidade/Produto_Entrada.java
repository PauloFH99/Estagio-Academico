package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALEntrada_Produtos;
import estagiocarvaobarao.dal.DALProduto_Entrada;

/**
 *
 * @author
 */
public class Produto_Entrada {

    private EntradaProdutos entrada_produtos;
    private Produto cod_produto;
    private String descri;
    private int codprod;
    private int qtde;
    private double unitario;
    private double total;

    public Produto_Entrada() {
    }

    public Produto_Entrada(Produto cod_produto, String descri, int codprod, int qtde, double unitario, double total) {
        this.cod_produto = cod_produto;
        this.descri = descri;
        this.codprod = codprod;
        this.qtde = qtde;
        this.unitario = unitario;
        this.total = total;
    }

    public Produto_Entrada(String descri, int codprod, int qtde, double unitario, double total) {
        this.descri = descri;
        this.codprod = codprod;
        this.qtde = qtde;
        this.unitario = unitario;
        this.total = total;
    }

    public Produto_Entrada(EntradaProdutos entrada_produtos) {
        this.entrada_produtos = entrada_produtos;
    }

    public EntradaProdutos getEntrada_produtos() {
        return entrada_produtos;
    }

    public void setEntrada_produtos(EntradaProdutos entrada_produtos) {
        this.entrada_produtos = entrada_produtos;
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

    public boolean verificaProd(int codP, int cod) {
        DALProduto_Entrada dal = new DALProduto_Entrada();
        return dal.verificaProd(codP, cod);
    }

    public int qtdeprodAnterior(int codP, int cod) {
        DALProduto_Entrada dal = new DALProduto_Entrada();
        return dal.qtdeprodAnterior(codP, cod);
    }

    public boolean apagar(int cod, int codP) {
        DALProduto_Entrada dal = new DALProduto_Entrada();
        return dal.apagar(cod,codP);
    }
}
