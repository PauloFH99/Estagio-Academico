/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALProdutoVenda {

    public boolean apagar(int cod) {
        boolean aux=Banco.getCon().manipular("delete from produto_venda where cod_venda=" + cod);
        return aux;
    }
    public boolean apagar(int cod,int codP) {
        boolean aux=Banco.getCon().manipular("delete from produto_venda where cod_venda=" + cod+" and cod_produto="+codP);
        return aux;
    }

    public List<Produto_Venda> getporVenda(int cod) {
        List<Produto_Venda> prod = new ArrayList();

        Produto aux = new Produto();

        String sql = "select * from produto_venda where cod_venda=" + cod;

        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                aux = aux.get(rs.getInt("cod_produto"));
                prod.add(new Produto_Venda(rs.getInt("quantidade"), new Venda(rs.getInt("cod_venda")),aux));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public boolean verificaProd(int codP, int cod) {
       String sql = "select codigo from produto_venda where cod_venda="
                + cod + " and cod_produto=" + codP;
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

    public int qtdeprodAnterior(int codP, int cod) {
         String sql = "select quantidade from produto_venda where cod_venda="
                + cod + " and cod_produto=" + codP;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return rs.getInt("quantidade");
            }
        } catch (SQLException ex) {

        }
        return 0;
    }

}
