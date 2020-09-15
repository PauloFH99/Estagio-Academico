/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Producao;
import estagiocarvaobarao.entidade.Producao_Produtos;
import estagiocarvaobarao.entidade.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class DALProducao_Produto {

    public List<Producao_Produtos> getporProducao(int cod) {
        List<Producao_Produtos> prod = new ArrayList();

        Produto aux = new Produto();

        String sql = "select * from producao_produto where cod_producao=" + cod;

        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                aux = aux.get(rs.getInt("cod_produto"));
                prod.add(new Producao_Produtos(rs.getInt("codigo"), new Producao(rs.getInt("cod_producao")), aux, rs.getInt("cod_produto"),
                        rs.getInt("qtde_pacotes"), rs.getDouble("qtde_total")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public boolean procura(Producao_Produtos prod, int cod) {
        String sql = "select * from producao_produto where cod_producao="
                + cod + " and cod_produto=" + prod.getProduto().getCodigo();
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {

        }
        return false;
    }

    public boolean apagar(Producao_Produtos prod) {
        DALArmazem dal = new DALArmazem();
        boolean aux = false;
        aux = dal.alterar(prod.getProduto().getCodigo(), prod.getQtde_pacotes(), "s");
        aux = Banco.getCon().manipular("delete from producao_produto where codigo=" + prod.getCodigo());
        return aux;
    }

    public Producao_Produtos qtdeprodAnterior(int codprod, int cod) {
        String sql = "select qtde_pacotes,qtde_total from producao_produto where cod_producao="
                + cod + " and cod_produto=" + codprod;
   
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return new Producao_Produtos(rs.getInt("qtde_pacotes"),rs.getDouble("qtde_total"));
            }
        } catch (SQLException ex) {

        }
        return null;
    }

}
