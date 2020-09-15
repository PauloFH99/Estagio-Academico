/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALProducao;
import estagiocarvaobarao.ui.TelaProducaoController.AuxValor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Producao {

    private int codigo;
    private Funcionario Funcionario;
    private Date emissao;
    private double total;
    private int qtde_moinhaKg;
    private int qtde_perdaKg;
    private DALProducao dal = new DALProducao();

    public Producao() {
    }

    public Producao(int codigo, Funcionario Funcionario, Date emissao, double total) {
        this.codigo = codigo;
        this.Funcionario = Funcionario;
        this.emissao = emissao;
        this.total = total;
    }

    public Producao(int codigo, Funcionario Funcionario, Date emissao, double total, int qtde_moinhaKg, int qtde_perdaKg) {
        this.codigo = codigo;
        this.Funcionario = Funcionario;
        this.emissao = emissao;
        this.total = total;
        this.qtde_moinhaKg = qtde_moinhaKg;
        
        this.qtde_perdaKg = qtde_perdaKg;
    }
    

    public Producao(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getQtde_moinhaKg() {
        return qtde_moinhaKg;
    }

    public void setQtde_moinhaKg(int qtde_moinhaKg) {
        this.qtde_moinhaKg = qtde_moinhaKg;
    }

    public int getQtde_perdaKg() {
        return qtde_perdaKg;
    }

    public void setQtde_perdaKg(int qtde_perdaKg) {
        this.qtde_perdaKg = qtde_perdaKg;
    }
    
    public Funcionario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(Funcionario Funcionario) {
        this.Funcionario = Funcionario;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean salvar(Producao producao, List<Producao_Produtos> produtos) {
        return dal.salvar(producao, produtos);
    }

    public boolean apagar(Producao producao) {
        return dal.apagar(producao);
    }

    public boolean alterar(Producao producao, List<Producao_Produtos> Produtos, List<AuxValor> aux) {
        return dal.alterar(producao, Produtos, aux);
    }

}
