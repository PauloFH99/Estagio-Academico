/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class DALProduto_Entrada {

    public boolean verificaProd(int codP, int cod) {
        String sql = "select codigo from produto_entrada where cod_entrada="
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
        String sql = "select quantidade from produto_entrada where cod_entrada="
                + cod + " and cod_produto=" + codP;
      
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return rs.getInt("quantidade");
            }
        } catch (SQLException ex) {

        }
        return 0;
    }

    public boolean apagar(int cod, int codP) {
        boolean aux = Banco.getCon().manipular("delete from produto_entrada where cod_entrada=" + cod + " and cod_produto=" + codP);
        return aux;
    }
}
