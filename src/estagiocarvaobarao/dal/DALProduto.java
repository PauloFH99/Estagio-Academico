package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALProduto {

    public boolean salvar(Produto p) {
        String sql = "insert into produto (descricao,estq_min,estq_max,preco,ativo,estoque_fisico,cod_categoria,peso) "
                + "values('#1','#2','#3','#4','#5','#6',#7,'#8')";

        sql = sql.replace("#1", p.getDescricao());
        sql = sql.replace("#2", "" + p.getEst_min());
        sql = sql.replace("#3", "" + p.getEst_max());
        sql = sql.replace("#4", "" + p.getPreco());
        sql = sql.replace("#5", "" + p.isAtivo());
        sql = sql.replace("#6", "" + p.getEstoque());
        sql = sql.replace("#7", "" + p.getCategoria().getCodigo());
        sql = sql.replace("#8", "" + p.getPeso());

        return Banco.getCon().manipular(sql);

    }

    public boolean alterar(Produto p) {
        String sql = "update produto set descricao='#1',estq_min = '#2',estq_max = '#3',preco='#4', estoque_fisico='#5',cod_categoria=#6,ativo='#7',peso='#8'"
                + "where codigo=" + p.getCodigo();
        sql = sql.replace("#1", p.getDescricao());
        sql = sql.replace("#2", "" + p.getEst_min());
        sql = sql.replace("#3", "" + p.getEst_max());
        sql = sql.replace("#4", "" + p.getPreco());
        sql = sql.replace("#5", "" + p.getEstoque());
        sql = sql.replace("#6", "" + p.getCategoria().getCodigo());
        sql = sql.replace("#7", "" + p.isAtivo());
        sql = sql.replace("#8", "" + p.getPeso());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from produto where codigo=" + codigo);
    }

    public List<Produto> get(Double filtro) {
        List<Produto> prod = new ArrayList();
        DALCategoria dalc = new DALCategoria();
        String sql = "select * from produto ";
        if (filtro > 0) {
            sql += " where preco=" + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                if (rs.getBoolean("ativo") != false) {
                    prod.add(new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"),
                            rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                            rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso")));
                }
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Produto> get(String filtro) {
        List<Produto> prod = new ArrayList();
        DALCategoria dalc = new DALCategoria();
        String sql = "select p.* from produto p,categoria_produto cp ";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
            sql += " and  p.cod_categoria = cp.codigo";
        } else {
            sql += " where  p.cod_categoria = cp.codigo";
        }
        sql += " order by cp.descricao,p.peso,p.descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                if (rs.getBoolean("ativo") != false) {
                    prod.add(new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"), rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                            rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso")));
                }
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public Produto get(int cod) {
        Produto prod = null;
        DALCategoria dalc = new DALCategoria();
        String sql = "select * from produto where codigo =" + cod;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                prod = new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"),
                        rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                        rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso"));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Produto> getCategoria(String filtro) {
        List<Produto> prod = new ArrayList();
        DALCategoria dalc = new DALCategoria();
        String sql = "select * from produto where cod_categoria=";
        int cod_c;
        cod_c = dalc.getC(filtro);
        if (!filtro.isEmpty()) {
            sql += "'" + cod_c + "'";
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                if (rs.getBoolean("ativo") != false) {
                    prod.add(new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"),
                            rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                            rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso")));
                }
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public Produto getQtde(Integer codigo) {
        String sql = "select * from produto where codigo = " + codigo;
        DALCategoria dalc = new DALCategoria();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"),
                        rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                        rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso"));

            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public boolean validaProduto(String descricao) {
        String sql = "select * from produto where descricao like '%" + descricao + "%'";
        DALCategoria dalc = new DALCategoria();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                Produto p = new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"),
                        rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"), rs.getBoolean("ativo"),
                        dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso"));
                return true;
            }
        } catch (SQLException ex) {

        }
        return false;
    }

    public int getCod() {
        return Banco.getCon().getMaxPK("produto", "codigo");
    }
}
