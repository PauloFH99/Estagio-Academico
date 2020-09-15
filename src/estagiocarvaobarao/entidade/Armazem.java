/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALArmazem;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Armazem {
    private int codigo;
    private int quantidade;
    private Produto produto;
    private int cod_prod;
    private String categoria;
    DALArmazem dal = new DALArmazem();
            
    public Armazem() {
    }

    public Armazem(int codigo, int quantidade, Produto produto) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public Armazem(int codigo, int quantidade, Produto produto, int cod_prod) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.produto = produto;
        this.cod_prod = cod_prod;
    }

    public Armazem(int codigo, int quantidade, Produto produto, int cod_prod, String categoria) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.produto = produto;
        this.cod_prod = cod_prod;
        this.categoria = categoria;
    }
    

    public int getCodigo() {
        return codigo;
    }

    public int getCod_prod() {
        return cod_prod;
    }

    public void setCod_prod(int cod_prod) {
        this.cod_prod = cod_prod;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    public boolean salvar(List<Producao_Produtos> produtos){
        return dal.salvar(produtos);
    }
    public boolean alterar(int cod,int quantidade,String tipo){
    return dal.alterar(cod,quantidade,tipo);
    }
    public boolean getProduto(int cod){
        return dal.getProduto(cod);
    }
    public Armazem get(int cod){
        return dal.get(cod);
    }
    public Armazem get(String descricao){
        return dal.get(descricao);
    }
     public Armazem get(String categoria,String descricao){
        return dal.get(categoria,descricao);
    }

    public List<Armazem> get() {
        return dal.get();
    }

    public boolean salvarP(int cod, int qtde) {
        return dal.salvarP(cod,qtde);
    }

    public boolean alterarP(int cod, int qtde) {
        return dal.alterarP(cod,qtde);
    }

    public boolean apagar(int codigo) {
       return dal.apagar(codigo);
    }
    
 
}
