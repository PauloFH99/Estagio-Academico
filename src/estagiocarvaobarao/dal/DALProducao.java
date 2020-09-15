/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Producao;
import estagiocarvaobarao.entidade.Producao_Produtos;
import estagiocarvaobarao.ui.TelaProducaoController.AuxValor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo
 */
public class DALProducao {

    public boolean salvar(Producao producao, List<Producao_Produtos> produtos) {
        boolean aux = false;
        String sql = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        DALArmazem dalarma = new DALArmazem();
        //Inserindo a Producao
        sql = "insert into producao (cod_funcionario,emissao,total,qtde_moinha,qtde_perda) values "
                + "(#A,'#B',#C,#D,#E)";

        sql = sql.replace("#A", "" + producao.getFuncionario().getCodigo());
        try {
            sql = sql.replace("#B", producao.getEmissao());
        } catch (ParseException ex) {
            Logger.getLogger(DALProducao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = sql.replace("#C", "" + producao.getTotal());
        sql = sql.replace("#D", "" + producao.getQtde_moinhaKg());
        sql = sql.replace("#E", "" + producao.getQtde_perdaKg());
        aux = Banco.getCon().manipular(sql);

        if (aux == false) {
            return aux;
        }

        //Pegando o máx código
        int codProducao = Banco.getCon().getMaxPK("producao", "codigo");
        int novo = 0;
        //Gravando os produtos           
        for (Producao_Produtos produto : produtos) {

            sql = "insert into producao_produto(cod_produto,cod_producao,qtde_pacotes,qtde_total) values "
                    + "(#A,#B,#C,#D)";

            sql = sql.replace("#A", "" + produto.getCodprod());
            sql = sql.replace("#B", "" + codProducao);
            sql = sql.replace("#C", "" + produto.getQtde_pacotes());
            sql = sql.replace("#D", "" + produto.getTotal());
            aux = Banco.getCon().manipular(sql);
            if (dalarma.getProduto(produto.getCodprod())) {
                dalarma.alterar(produto.getCodprod(), produto.getQtde_pacotes(), "e");
            } else {
                novo = 1;
            }
        }
        if (novo == 1) {
            dalarma.salvar(produtos);
        }
        if (!aux) {
            return aux;
        }
        return aux;

    }

    public boolean alterar(Producao producao, List<Producao_Produtos> produtos, List<AuxValor> auxqtde) {
        boolean aux = false;
        String sql = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        DALArmazem dalarma = new DALArmazem();
        //Inserindo a Producao
        sql = "update  producao set cod_funcionario=#A,emissao='#B',total=#C,qtde_moinha=#D,qtde_perda=#E where codigo=" + producao.getCodigo();

        sql = sql.replace("#A", "" + producao.getFuncionario().getCodigo());
        try {
            sql = sql.replace("#B", producao.getEmissao());
        } catch (ParseException ex) {
            Logger.getLogger(DALProducao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = sql.replace("#C", "" + producao.getTotal());
        sql = sql.replace("#D", "" + producao.getQtde_moinhaKg());
        sql = sql.replace("#E", "" + producao.getQtde_perdaKg());
        aux = Banco.getCon().manipular(sql);

        if (aux == false) {
            return aux;
        }

        //Pegando o máx código
        int codProducao = producao.getCodigo();
        int novo = 0, aux_qtdepacotes = 0;
        //Alterar os produtos           
        for (Producao_Produtos produto : produtos) {
            if (produto.procura(produto, codProducao)) {
                sql = "update  producao_produto set cod_produto=#A,cod_producao=#B,qtde_pacotes=#C,qtde_total=#D   where codigo=" + produto.getCodigo();
                sql = sql.replace("#A", "" + produto.getCodprod());
                sql = sql.replace("#B", "" + produto.getProducao().getCodigo());
                sql = sql.replace("#C", "" + produto.getQtde_pacotes());
                sql = sql.replace("#D", "" + produto.getTotal());
                aux = Banco.getCon().manipular(sql);
            } else {
                sql = "";
                sql = "insert into producao_produto(cod_produto,cod_producao,qtde_pacotes,qtde_total) values "
                        + "(#A,#B,#C,#D)";

                sql = sql.replace("#A", "" + produto.getCodprod());
                sql = sql.replace("#B", "" + codProducao);
                sql = sql.replace("#C", "" + produto.getQtde_pacotes());
                sql = sql.replace("#D", "" + produto.getTotal());
                aux = aux = Banco.getCon().manipular(sql);
            }
        }

        if (!aux) {
            return aux;
        }
        return aux;

    }

    public boolean apagar(Producao producao) {
        return Banco.getCon().manipular("delete from producao where codigo=" + producao.getCodigo());
    }

    public Producao get(int cod) {
        String sql = "select * from producao where codigo =" + cod;
        List<Producao> prod = new ArrayList();

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return new Producao(rs.getInt("codigo"), new Funcionario(rs.getInt("cod_funcionario")),
                        rs.getDate("emissao"), rs.getDouble("total"), rs.getInt("qtde_moinha"), rs.getInt("qtde_perda"));
            }
        } catch (SQLException e) {
        }

        return null;
    }

    public List<Producao> get(String filtro) {
        String sql = "select codigo,cod_funcionario,emissao,total from producao";
        List<Producao> prod = new ArrayList();

        if (!filtro.isEmpty()) {
            sql = sql + " where emissao=" + filtro;
        }
        sql += " order by emissao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Producao(rs.getInt("codigo"), new Funcionario(rs.getInt("cod_funcionario")),
                        rs.getDate("emissao"), rs.getDouble("total"), rs.getInt("qtde_moinha"), rs.getInt("qtde_perda")));
            }
        } catch (SQLException e) {
        }

        return prod;
    }

}
