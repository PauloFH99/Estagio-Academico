/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Producao_Produtos;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Entrada;
import estagiocarvaobarao.entidade.Produto_Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALArmazem {

    public boolean salvar(List<Producao_Produtos> produtos) {
        String sql = "";
        boolean aux = false;
        for (Producao_Produtos produto : produtos) {
            sql = "insert into  estoquearmazem (quantidade,cod_produto) values(#A,#B)";
            sql = sql.replace("#A", "" + produto.getQtde_pacotes());
            sql = sql.replace("#B", "" + produto.getProduto().getCodigo());
            aux = Banco.getCon().manipular(sql);
        }
        if (!aux) {
            return aux;
        }
        return aux;
    }

    public boolean salvarV(List<Produto_Venda> produtos) {
        String sql = "";
        boolean aux = false;
        for (Produto_Venda produto : produtos) {
            sql = "insert into  estoquearmazem (quantidade,cod_produto) values(#A,#B)";
            sql = sql.replace("#A", "" + produto.getQtde());
            sql = sql.replace("#B", "" + produto.getCod_produto().getCodigo());
            aux = Banco.getCon().manipular(sql);
        }
        if (!aux) {
            return aux;
        }
        return aux;
    }

    public boolean salvarE(List<Produto_Entrada> Produtos) {
        String sql = "";
        boolean aux = false;
        for (Produto_Entrada produto : Produtos) {
            sql = "insert into  estoquearmazem (quantidade,cod_produto) values(#A,#B)";
            sql = sql.replace("#A", "" + produto.getQtde());
            sql = sql.replace("#B", "" + produto.getCod_produto().getCodigo());
            aux = Banco.getCon().manipular(sql);
        }
        if (!aux) {
            return aux;
        }
        return aux;
    }

    public boolean alterar(int cod, int quantidade, String tipo) {
        String sql = "";
        boolean aux = false;
        int aux_qtde = 0;
        Armazem aux_arma = new Armazem();
        aux_arma = aux_arma.get(cod);
        if (tipo.toLowerCase().equals("e")) {
            aux_qtde += aux_arma.getQuantidade() + quantidade;
        } else {
            aux_qtde += aux_arma.getQuantidade() - quantidade;
            if (aux_qtde < 0) {
                aux_qtde = 0;
            }
        }

        sql = "update estoquearmazem set quantidade=#A where cod_produto=" + cod;
        sql = sql.replace("#A", "" + aux_qtde);
        aux = Banco.getCon().manipular(sql);

        if (!aux) {
            return aux;
        }
        return aux;

    }

    public boolean getProduto(int cod) {
        String SQL = "select * from estoquearmazem where cod_produto = " + cod;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return true;

            }
        } catch (SQLException e) {
        }
        return false;
    }

    public Armazem get(int cod) {
        String SQL = "select * from estoquearmazem where cod_produto = " + cod;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), new Produto(rs.getInt("cod_produto")), rs.getInt("cod_produto"));

            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Armazem> get() {
        String SQL = "select e.codigo,e.cod_produto,e.quantidade,cp.descricao as cat "
                + "from estoquearmazem e,produto p,categoria_produto cp "
                + " where e.cod_produto=p.codigo  "
                + " and p.cod_categoria=cp.codigo "
                + " order by cp.descricao,p.peso";
        List<Armazem> a = new ArrayList();
        DALProduto dal = new DALProduto();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                a.add(new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), dal.get(rs.getInt("cod_produto")), rs.getInt("cod_produto"),rs.getString("cat")));

            }
        } catch (SQLException e) {
        }
        return a;
    }

    public Armazem get(String descricao) {
        String SQL = "select e.*,p.descricao from estoquearmazem e, produto p"
                + " where e.cod_produto = p.codigo"
                + " and p.descricao ilike  '%" + descricao + "%'";
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), new Produto(rs.getInt("cod_produto")), rs.getInt("cod_produto"));

            }
        } catch (SQLException e) {
        }
        return null;
    }

    public Armazem get(String categoria, String descricao) {
        String SQL = "select e.*,p.descricao from estoquearmazem e, produto p,categoria_produto c"
                + " where e.cod_produto = p.codigo"
                + " and p.cod_categoria = c.codigo"
                + " and p.descricao ilike  '%" + descricao + "%'"
                + " and c.descricao ilike  '%" + categoria + "%'";
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), new Produto(rs.getInt("cod_produto")), rs.getInt("cod_produto"));

            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean salvarP(int cod, int qtde) {
        String sql = "insert into  estoquearmazem (quantidade,cod_produto) values(#A,#B)";
        sql = sql.replace("#A", "" + qtde);
        sql = sql.replace("#B", "" + cod);
        return Banco.getCon().manipular(sql);
    }

    public boolean alterarP(int cod, int qtde) {
        String sql = "update  estoquearmazem set quantidade=#A,cod_produto=#B where cod_produto=" + cod;
        sql = sql.replace("#A", "" + qtde);
        sql = sql.replace("#B", "" + cod);
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from estoquearmazem where cod_produto="+codigo);
    }

}
