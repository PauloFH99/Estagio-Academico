/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALFornecedor;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Fornecedor {

    private int codigo;
    private String nomefantasia;
    private String cnpj;
    private String ativo;
    private String logradouro;
    private String bairro;
    private String numero;
    private Cidade cidade;
    private String cep;
    private String nomecontato;
    private String telefonecontato;
    private String email;
    private String telefone;
    private String razaosocial;
    private Categoria categoria;

    public Fornecedor() {
    }

    public Fornecedor(int codigo, String nomefantasia, String cnpj, String ativo, String logradouro, String bairro, String numero, Cidade cidade, String cep, String nomecontato, String telefonecontato, String email, String telefone, String razaosocial) {
        this.codigo = codigo;
        this.nomefantasia = nomefantasia;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.nomecontato = nomecontato;
        this.telefonecontato = telefonecontato;
        this.email = email;
        this.telefone = telefone;
        this.razaosocial = razaosocial;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public Fornecedor(String nomefantasia, String cnpj, String ativo, String logradouro, String bairro, String numero, Cidade cidade, String cep, String nomecontato, String telefonecontato, String email, String telefone, String razaosocial) {
        this.nomefantasia = nomefantasia;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.nomecontato = nomecontato;
        this.telefonecontato = telefonecontato;
        this.email = email;
        this.telefone = telefone;
        this.razaosocial = razaosocial;
    }

    public Fornecedor(int codigo, String nomefantasia, String cnpj, String ativo, String logradouro, String bairro, 
            String numero, Cidade cidade, String cep, String nomecontato, String telefonecontato, String email,
            String telefone, String razaosocial, Categoria categoria) {
        this.codigo = codigo;
        this.nomefantasia = nomefantasia;
        this.cnpj = cnpj;
        this.ativo = ativo;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.nomecontato = nomecontato;
        this.telefonecontato = telefonecontato;
        this.email = email;
        this.telefone = telefone;
        this.razaosocial = razaosocial;
        this.categoria = categoria;
    }
    

    public Fornecedor(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String isAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomecontato() {
        return nomecontato;
    }

    public void setNomecontato(String nomecontato) {
        this.nomecontato = nomecontato;
    }

    public String getTelefonecontato() {
        return telefonecontato;
    }

    public void setTelefonecontato(String telefonecontato) {
        this.telefonecontato = telefonecontato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return nomefantasia;
    }

    public boolean salvar(Fornecedor f) {
        DALFornecedor dal = new DALFornecedor();
        return dal.salvar(f);
    }

    public boolean alterar(Fornecedor f) {
        DALFornecedor dal = new DALFornecedor();
        return dal.alterar(f);
    }

    public boolean apagar(int cod) {
        DALFornecedor dal = new DALFornecedor();
        return dal.apagar(cod);
    }

    public Fornecedor get(int cod) {
        DALFornecedor dal = new DALFornecedor();
        return dal.get(cod);
    }

    public Fornecedor getF(String cod) {
        DALFornecedor dal = new DALFornecedor();
        return dal.getF(cod);
    }

    public List<Fornecedor> get(String filtro) {
        DALFornecedor dal = new DALFornecedor();
        return dal.get(filtro);
    }
}
