package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALProduto;
import java.util.List;

public class Produto {

    private int codigo;
    private int est_min;
    private int est_max;
    private String descricao;
    private double preco;
    private int estoque;
    private Categoria categoria;
    private boolean ativo;
    private double peso;

    public Produto(int codigo, int est_min, int est_max, String descricao, double preco, int estoque, boolean ativo, Categoria categoria,double peso) {
        this.codigo = codigo;
        this.est_min = est_min;
        this.est_max = est_max;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
        this.categoria = categoria;
        this.peso=peso;
    }
     public Produto(int codigo, int est_min, int est_max, String descricao, double preco, int estoque, boolean ativo, Categoria categoria) {
        this.codigo = codigo;
        this.est_min = est_min;
        this.est_max = est_max;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
        this.categoria = categoria;
       
    }

    public Produto(int codigo) {
        this.codigo = codigo;
    }

  

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Produto(int est_min, int est_max, String descricao, double preco, int estoque, boolean ativo, Categoria categoria,double peso) {
        this(0, est_min, est_max, descricao, preco, estoque, ativo, categoria,peso);
    }

    public Produto() {
        this(0, 0, 0, "", 0, 0, false, null,0.0);
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodico(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEst_min() {
        return est_min;
    }

    public void setEst_min(int est_min) {
        this.est_min = est_min;
    }

    public int getEst_max() {
        return est_max;
    }

    public void setEst_max(int est_max) {
        this.est_max = est_max;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public boolean salvar(Produto p) {
        DALProduto dal = new DALProduto();
        return dal.salvar(p);
    }

    public boolean apagar(int codigo) {
        DALProduto dal = new DALProduto();
        return dal.apagar(codigo);
    }
    public boolean alterar(Produto p) {
        DALProduto dal = new DALProduto();
        return dal.alterar(p);
    }
    public boolean validaProduto(String  descricao) {
        DALProduto dal = new DALProduto();
        return dal.validaProduto(descricao);
    }

    public List<Produto> get(String filtro) {
        DALProduto dal = new DALProduto();
        return dal.get(filtro);
    }

    public List<Produto> get(Double filtro) {
        DALProduto dal = new DALProduto();
        return dal.get(filtro);
    }

    public List<Produto> getCategoria(String filtro) {
        DALProduto dal = new DALProduto();
        return dal.getCategoria(filtro);
    }

    public Produto get(int cod) {
        DALProduto dal = new DALProduto();
        return dal.get(cod);
    }
     public int getCod() {
        DALProduto dal = new DALProduto();
        return dal.getCod();
    }

    public boolean CampoVazio(String valor) {
        boolean resultado = (valor.isEmpty() || valor.trim().isEmpty());
        return resultado;
    }

    public int retornaValor(String valor) {
        int res = 0;
        if (!valor.equals("")) {
            res = Integer.parseInt(valor);
        }
        return res;
    }

    @Override
    public String toString() {
        return  descricao ;
    }
    
}
