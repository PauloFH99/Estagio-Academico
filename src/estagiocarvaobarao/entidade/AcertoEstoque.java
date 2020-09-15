/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALAcertoEstoque;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class AcertoEstoque {

    private int codigo;
    private Date emissao;
    private String tipo;
    private Produto produto;
    private Funcionario funcionario;
    private String observacoes;
    private int quantidade;
    private String estoque;

    public AcertoEstoque() {
    }

    public AcertoEstoque(int codigo) {
        this.codigo = codigo;
    }

    public AcertoEstoque(int codigo, String observacoes) {
        this.codigo = codigo;
        this.observacoes = observacoes;
    }

    public AcertoEstoque(int codigo, Date emissao, String tipo, Produto produto, Funcionario funcionario, String observacoes, int quantidade, String estoque) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.tipo = tipo;
        this.produto = produto;
        this.funcionario = funcionario;
        this.observacoes = observacoes;
        this.quantidade = quantidade;
        this.estoque = estoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean salvar(AcertoEstoque ae) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.salvar(ae);
    }

    public boolean alterar(AcertoEstoque ae) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.alterar(ae);
    }

    public boolean apagar(int cod) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.apagar(cod);
    }

    public boolean alterarestoque(AcertoEstoque ae) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.alterarEstoque(ae);
    }

    public AcertoEstoque get(int cod) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.get(cod);
    }

    public List<AcertoEstoque> get(Double filtro) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.get(filtro);
    }

    public List<AcertoEstoque> get(String filtro) {
        DALAcertoEstoque dal = new DALAcertoEstoque();
        return dal.get(filtro);
    }

    public void pesquisarProduto(Produto prod, JFXTextField txcodprod, JFXTextField txprod, JFXTextField txqtde) {
        Produto produto = new Produto();
        produto = produto.get(prod.getCodigo());
        prod = produto;
        if (produto != null) {
            txcodprod.setText(String.valueOf(produto.getCodigo()));
            txprod.setText(produto.getDescricao());
            if (produto != null) {
                txqtde.setText(String.valueOf(produto.getEstoque()));
            } else {
                txqtde.setText("0");
            }
        } else {
            txcodprod.setText("0");
            txprod.setText("Valor digitado não encontrado...");
        }
    }
}
