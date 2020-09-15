/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALEmpresaParametros;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class EmpresaParametros {

    private int codigo;
    private String fantasia;
    private String razao_social;
    private String endereco;
    private String numero;
    private Cidade cidade;
    private String telefone;
    private String cnpj;
    private String logo_pequena;
    private String logo_grande;
    private String site;
    private String ie;
    private String email;
    private String cep;
    private String bairro;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public EmpresaParametros() {
    }

    public EmpresaParametros(int codigo) {
        this.codigo = codigo;
    }

    public EmpresaParametros(int codigo, String fantasia, String razao_social, String endereco, String numero, Cidade cidade, String telefone, String cnpj, String logo_pequena, String logo_grande, String site, String ie, String email, String Cep, String bairro) {
        this.codigo = codigo;
        this.fantasia = fantasia;
        this.razao_social = razao_social;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.logo_pequena = logo_pequena;
        this.logo_grande = logo_grande;
        this.site = site;
        this.ie = ie;
        this.email = email;
        this.cep = Cep;
        this.bairro = bairro;
    }

    public EmpresaParametros(int codigo, String fantasia, String razao_social, String endereco, String numero, Cidade cidade, String telefone, String cnpj, String site, String ie, String email, String Cep, String bairro) {
        this.codigo = codigo;
        this.fantasia = fantasia;
        this.razao_social = razao_social;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.telefone = telefone;
        this.cnpj = cnpj;

        this.site = site;
        this.ie = ie;
        this.email = email;
        this.cep = Cep;
        this.bairro = bairro;
    }

    public EmpresaParametros(String fantasia, String razao_social, String endereco, String numero, Cidade cidade, String telefone, String cnpj, String site, String ie, String email, String Cep, String bairro) {
        this.fantasia = fantasia;
        this.razao_social = razao_social;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.site = site;
        this.ie = ie;
        this.email = email;
        this.cep = Cep;
        this.bairro = bairro;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFantasia() {
        return fantasia;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLogo_pequena() {
        return logo_pequena;
    }

    public void setLogo_pequena(String logo_pequena) {
        this.logo_pequena = logo_pequena;
    }

    public String getLogo_grande() {
        return logo_grande;
    }

    public void setLogo_grande(String logo_grande) {
        this.logo_grande = logo_grande;
    }

    public boolean salvar(EmpresaParametros ep) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.salvar(ep);
    }

    public boolean alterar(EmpresaParametros ep) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.alterar(ep);
    }

    public boolean apagar(int codigo) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.apagar(codigo);
    }

    public List<EmpresaParametros> get(String filtro) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.get(filtro);
    }

    public EmpresaParametros get(int cod) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.getE(cod);
    }

    public boolean gravarFoto(EmpresaParametros e, FileInputStream foto, File arquivo) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.gravarFoto(e, foto, arquivo);
    }

    public InputStream getFoto(EmpresaParametros e) {
        DALEmpresaParametros dal = new DALEmpresaParametros();
        return dal.getFoto(e);
    }
}
