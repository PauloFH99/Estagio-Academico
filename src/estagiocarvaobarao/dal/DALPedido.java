/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Pedido;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALPedido {

    public boolean alterar(int codvenda,String situacao) {
        String sql = "update  venda set situacao='#A' where codigo="+codvenda;
        sql = sql.replace("#A",situacao);
        return Banco.getCon().manipular(sql);
    }

    public List<Pedido> getPedido(int cli, Date emissao) {
        String data = emissao.toString();
        String sql = "select v.codigo,v.emissao,v.cod_cliente,pv.cod_produto,pv.cod_venda,pv.quantidade,pv.total,v.situacao"
                + " from venda v,produto_venda pv,produto p,categoria_produto cp "
                + " where v.cod_cliente=" + cli + ""
                + " and v.emissao='" + data + "'"
                + " and pv.cod_venda =v.codigo"
                + " and pv.cod_produto =p.codigo"
                + " and p.cod_categoria =cp.codigo "
                + " and p.cod_categoria = cp.codigo "
                + " and cp.descricao not ilike '%carvão bruto%' "
                + " and cp.descricao not ilike '%Embalagem%' "
                + " and cp.descricao not ilike '%acessórios%'  "
                + "order by cp.descricao,p.peso";

        List<Pedido> pedi = new ArrayList();
        DALCliente dalc = new DALCliente();
        DALProduto dalp = new DALProduto();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                pedi.add(new Pedido(rs.getInt("codigo"), rs.getDate("emissao"),
                        dalc.get(rs.getInt("cod_cliente")), dalp.get(rs.getInt("cod_produto")),
                        rs.getInt("cod_venda"), rs.getInt("quantidade"), rs.getInt("total"), rs.getString("situacao")));
            }
        } catch (SQLException ex) {

        }
        return pedi;
    }

}
