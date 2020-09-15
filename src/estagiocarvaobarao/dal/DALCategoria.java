package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALCategoria {

    public boolean salvar(Categoria c) {
        String sql = "insert into categoria_produto (descricao) values('#1')";
        sql = sql.replace("#1", c.getDescricao());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Categoria c) {
        String sql = "update categoria_produto set descricao='#1' where codigo=" + c.getCodigo();
        sql = sql.replace("#1", c.getDescricao());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from categoria_produto where codigo=" + codigo);
    }

    public Categoria get(int codigo) {
        Categoria cat = null;
        String sql = "select * from categoria_produto where codigo=" + codigo;
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                cat = new Categoria(rs.getInt("codigo"), rs.getString("descricao"));
            }
        } catch (SQLException ex) {

        }
        return cat;
    }

    public List<Categoria> get(String filtro) {
        List<Categoria> cat = new ArrayList();
        String sql = "select * from categoria_produto";

        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql+=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                cat.add(new Categoria(rs.getInt("codigo"), rs.getString("descricao")));
            }
        } catch (SQLException ex) {

        }
        return cat;
    }

    public int getC(String filtro) {
        List<Categoria> cat = new ArrayList();
        String sql = "select * from categoria_produto where descricao=" + "'" + filtro + "'";
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
