/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.AcertoEstoque;
import estagiocarvaobarao.entidade.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALAcertoEstoque {

    public boolean salvar(AcertoEstoque ae) {
        String sql = "insert into acertoestoque (cod_produto,tipo,"
                + "emissao,cod_funcionario, observacoes,quantidade,estoque) "
                + "values(#1,'#2','#3',#4,'#5',#6,'#7')";
        sql = sql.replace("#1", "" + ae.getProduto().getCodigo());
        sql = sql.replace("#2", ae.getTipo());
        sql = sql.replace("#3", "" + ae.getEmissaoDate());
        sql = sql.replace("#4", "" + ae.getFuncionario().getCodigo());
        sql = sql.replace("#5", ae.getObservacoes());
        sql = sql.replace("#6", "" + ae.getQuantidade());
        sql = sql.replace("#7", ae.getEstoque());
        return Banco.getCon().manipular(sql);

    }

    public boolean alterarEstoque(AcertoEstoque ae) {
        String sql = "";
        DALProduto dalp = new DALProduto();
        Produto p = new Produto();
        int valor = 0;
        p = dalp.get(ae.getProduto().getCodigo());
        if (ae.getTipo().equals("Entrada")) {
            sql = "update produto set  estoque_fisico=#1 where codigo=" + ae.getProduto().getCodigo();
            valor = p.getEstoque() + ae.getQuantidade();
            sql = sql.replace("#1", "" + valor);

        }

        if (ae.getTipo().equals("Saída")) {
            sql = "update produto set  estoque_fisico=#1 where codigo=" + ae.getProduto().getCodigo();
            valor = p.getEstoque() - ae.getQuantidade();
            sql = sql.replace("#1", "" + valor);
        }
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(AcertoEstoque ae) {
        String sql = "update acertoestoque set cod_produto=#1,tipo = '#2',"
                + "emissao = '#3',cod_funcionario=#4, observacoes='#5',quantidade=#6,estoque = '#7'  where codigo=" + ae.getCodigo();
        sql = sql.replace("#1", "" + ae.getProduto().getCodigo());
        sql = sql.replace("#2", ae.getTipo());
        sql = sql.replace("#3", "" + ae.getEmissaoDate());
        sql = sql.replace("#4", "" + ae.getFuncionario().getCodigo());
        sql = sql.replace("#5", ae.getObservacoes());
        sql = sql.replace("#6", "" + ae.getQuantidade());
        sql = sql.replace("#7", ae.getEstoque());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from acertoestoque where codigo=" + codigo);
    }

    public AcertoEstoque get(int cod) {

        DALProduto dalp = new DALProduto();

        DALFuncionario dalf = new DALFuncionario();
        String sql = "select * from acertoestoque where codigo=" + cod;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                return new AcertoEstoque(rs.getInt("codigo"), rs.getDate("emissao"),
                        rs.getString("tipo"), dalp.get(rs.getInt("cod_produto")), dalf.get(rs.getInt("cod_funcionario")),
                        rs.getString("observacoes"), rs.getInt("quantidade"),rs.getString("estoque"));

            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public List<AcertoEstoque> get(Double filtro) {
        List<AcertoEstoque> prod = new ArrayList();
        DALProduto dalp = new DALProduto();

        DALFuncionario dalf = new DALFuncionario();
        String sql = "select * from acertoestoque ";
        if (filtro > 0) {
            sql += " where preco=" + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                prod.add(new AcertoEstoque(rs.getInt("codigo"), rs.getDate("emissao"),
                        rs.getString("tipo"), dalp.get(rs.getInt("cod_produto")), dalf.get(rs.getInt("cod_funfionario")),
                        rs.getString("observacoes"), rs.getInt("quantidade"),rs.getString("estoque")));

            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<AcertoEstoque> get(String filtro) {
        List<AcertoEstoque> prod = new ArrayList();

        DALProduto dalp = new DALProduto();
        DALFuncionario dalf = new DALFuncionario();
        String sql = "select * from acertoestoque";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                prod.add(new AcertoEstoque(rs.getInt("codigo"), rs.getDate("emissao"),
                        rs.getString("tipo"), dalp.get(rs.getInt("cod_produto")), dalf.get(rs.getInt("cod_funfionario")),
                        rs.getString("observacoes"), rs.getInt("quantidade"),rs.getString("estoque")));

            }
        } catch (SQLException ex) {

        }
        return prod;
    }

}
