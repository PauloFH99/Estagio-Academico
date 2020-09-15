/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Despesas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALDespesas {

    public boolean salvar(Despesas d) {
        String sql = "insert into despesas (descricao,dia_pagar) values('#1','#2')";
        sql = sql.replace("#1", d.getDescricao());
        sql = sql.replace("#2", "" + d.getDia_pagar());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Despesas d) {
        String sql = "update despesas set descricao='#1',dia_pagar='#2' where codigo=" + d.getCodigo();
        sql = sql.replace("#1", d.getDescricao());
        sql = sql.replace("#2", "" + d.getDia_pagar());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from despesas where codigo=" + codigo);
    }

    public List<Despesas> get(String filtro) {
        List<Despesas> des = new ArrayList();

        String sql = "select * from despesas";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                des.add(new Despesas(rs.getInt("codigo"), rs.getString("descricao"), rs.getInt("dia_pagar")));
            }

        } catch (SQLException ex) {

        }
        return des;
    }

    public static Despesas get(int codigo) {
        String SQL = "select * from despesas where codigo = " + codigo;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new Despesas(rs.getInt("codigo"), rs.getString("descricao"), rs.getInt("dia_pagar"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int getD(String filtro) {
        List<Despesas> cat = new ArrayList();
        String sql = "select * from despesas where descricao like " + "'" + filtro + "'";
        int cod = 0;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                cod = rs.getInt("codigo");
            }
        } catch (SQLException ex) {

        }
        return cod;
    }

}
