/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALDespesas;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Despesas {

    private int codigo;
    private String descricao;
    private int dia_pagar;

    public Despesas() {
    }

    public Despesas(int codigo, String descricao, int dia_pagar) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.dia_pagar = dia_pagar;
    }

    public int getDia_pagar() {
        return dia_pagar;
    }

    public void setDia_pagar(int dia_pagar) {
        this.dia_pagar = dia_pagar;
    }

    public Despesas(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Despesas(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public boolean salvar(Despesas d) {
        DALDespesas dal = new DALDespesas();
        return dal.salvar(d);
    }

    public boolean alterar(Despesas d) {
        DALDespesas dal = new DALDespesas();
        return dal.alterar(d);
    }

    public boolean apagar(int cod) {
        DALDespesas dal = new DALDespesas();
        return dal.apagar(cod);
    }

    public List<Despesas> get(String filtro) {
        DALDespesas dal = new DALDespesas();
        return dal.get(filtro);
    }

    public Despesas get(int cod) {
        DALDespesas dal = new DALDespesas();
        return dal.get(cod);
    }

}
