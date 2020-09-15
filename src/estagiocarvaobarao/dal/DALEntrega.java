/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Despesas_Entrega;
import estagiocarvaobarao.entidade.Entrega;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALEntrega {

    public List<Entrega> getInit() {

        List<Entrega> entre = new ArrayList();
        List<Produto> prod = new ArrayList();
        DALCategoria dalc = new DALCategoria();
        String sql = "select p.* from produto p,categoria_produto cp  "
                + " where p.cod_categoria = cp.codigo "
                + " and cp.descricao ilike '%carvão saco%'"
                + " order by p.peso";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Produto(rs.getInt("codigo"), rs.getInt("estq_min"), rs.getInt("estq_max"),
                        rs.getString("descricao"), rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                        rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso")));

            }
        } catch (SQLException ex) {

        }
        for (Produto produto : prod) {
            entre.add(new Entrega(produto, 0, 0, 0));
        }
        return entre;
    }

    public boolean gravar(List<Despesas_Entrega> Despesas, List<Entrega> Entregas,
            LocalDate emissao, Funcionario funcionario, String rota) {
        boolean aux = false;
        DALArmazem arm = new DALArmazem();
        int codEntrega = 0;
        String sql = "";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        sql = "insert into entrega(emissao,"
                + "cod_funcionario,rota) "
                + "values ('#A',#B,'#C')";
        sql = sql.replace("#A", emissao.format(formatter));
        sql = sql.replace("#B", "" + funcionario.getCodigo());
        sql = sql.replace("#C", rota);
        aux = Banco.getCon().manipular(sql);
        codEntrega = Banco.getCon().getMaxPK("entrega", "codigo");
        for (Entrega entrega : Entregas) {
            sql = "insert into produtos_entrega(cod_produto,cod_entrega,carga,venda,retorno) "
                    + "values (#A,#B,'#C','#D','#E')";
            sql = sql.replace("#A", "" + entrega.getProduto().getCodigo());
            sql = sql.replace("#B", "" + codEntrega);
            sql = sql.replace("#C", "" + entrega.getCarga());
            sql = sql.replace("#D", "" + entrega.getVenda());
            sql = sql.replace("#E", "" + entrega.getRetorno());
            aux = Banco.getCon().manipular(sql);
            if (entrega.getRetorno() > 0) {
                arm.alterar(entrega.getProduto().getCodigo(), entrega.getRetorno(), "e");
            }
        }
        if (aux == false) {
            return aux;
        }
        if (!Despesas.isEmpty()) {
            CondPagto cdp = new CondPagto();
            cdp = cdp.getC("à vista");
            for (Despesas_Entrega despesa : Despesas) {
                ContasPagar cp = new ContasPagar(java.sql.Date.valueOf(emissao), funcionario, despesa.getDespesa(), despesa.getValor(), cdp);
                aux = cp.gravarEntrega(cp, codEntrega);
            }

        }
        return aux;
    }

    public List<Entrega> getAll(int cod_entrega) {
        List<Entrega> entre = new ArrayList();
        List<Produto> prod = new ArrayList();
        DALProduto dalp = new DALProduto();
        String sql = "select pe.* from produtos_entrega pe,produto p "
                + " where pe.cod_produto = p.codigo and pe.cod_entrega=" + cod_entrega
                + " order by p.peso";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                entre.add(new Entrega(dalp.get(rs.getInt("cod_produto")), rs.getInt("carga"),
                        rs.getInt("venda"), rs.getInt("retorno")));

            }
        } catch (SQLException ex) {

        }

        return entre;
    }

    public boolean alterar(int cod, List<Despesas_Entrega> Despesas, List<Entrega> Entregas, LocalDate emissao, Funcionario funcionario, String rota) {
        boolean aux = false;
        String sql = "";
        int codEntrega = 0, qtde_ant = 0;
        ContasPagar rec = new ContasPagar();
        DALArmazem arm = new DALArmazem();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        sql = "update  entrega set emissao='#A',"
                + "cod_funcionario='#B',rota='#C' where codigo= " + cod;
        sql = sql.replace("#A", emissao.format(formatter));
        sql = sql.replace("#B", "" + funcionario.getCodigo());
        sql = sql.replace("#C", rota);
        aux = Banco.getCon().manipular(sql);
        if (!aux) {
            return aux;
        }
        for (Entrega entrega : Entregas) {
            qtde_ant = entrega.getQtdeAnt(entrega.getProduto().getCodigo(), cod);
            sql = "update produtos_entrega set cod_produto=#A,cod_entrega=#B,carga='#C',venda='#D',retorno='#E' where cod_entrega=" + cod + " "
                    + " and cod_produto=" + entrega.getProduto().getCodigo();
            sql = sql.replace("#A", "" + entrega.getProduto().getCodigo());
            sql = sql.replace("#B", "" + cod);
            sql = sql.replace("#C", "" + entrega.getCarga());
            sql = sql.replace("#D", "" + entrega.getVenda());
            sql = sql.replace("#E", "" + entrega.getRetorno());

            aux = Banco.getCon().manipular(sql);
            if (entrega.getRetorno() > 0) {
                arm.alterar(entrega.getProduto().getCodigo(), qtde_ant, "s");
                arm.alterar(entrega.getProduto().getCodigo(), entrega.getRetorno(), "e");
            }
        }
        if (aux == false) {
            return aux;
        }
        if (!Despesas.isEmpty()) {
            CondPagto cdp = new CondPagto();
            cdp = cdp.getC("à vista");
            if (rec.apagarDespesa(codEntrega)) {
                for (Despesas_Entrega despesa : Despesas) {
                    ContasPagar cp = new ContasPagar(java.sql.Date.valueOf(emissao), funcionario, despesa.getDespesa(), despesa.getValor(), cdp);
                    aux = cp.gravarEntrega(cp, codEntrega);
                }
            }
        }
        return aux;

    }

    public int getQtdeAnt(int codP, int cod) {
        String sql = "select retorno from produtos_entrega where codigo=" + cod + " and cod_produto=" + codP;
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return rs.getInt("retorno");
            }
        } catch (SQLException ex) {

        }

        return 0;
    }

    public boolean apagar(int cod) {
       String sql = "delete from entrega where codigo = " + cod;
        return Banco.getCon().manipular(sql);
    }

    public List<Entrega> getProds(int cod) {
        List<Entrega> entre = new ArrayList();
        List<Produto> prod = new ArrayList();
        DALProduto dalp = new DALProduto();
        String sql = "select pe.* from produtos_entrega pe,produto p "
                + " where pe.cod_produto = p.codigo and pe.cod_entrega=" + cod
                + " order by p.peso";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                entre.add(new Entrega(dalp.get(rs.getInt("cod_produto")), rs.getInt("carga"),
                        rs.getInt("venda"), rs.getInt("retorno"),rs.getInt("cod_entrega")));

            }
        } catch (SQLException ex) {

        }

        return entre;
    }

    public boolean apagarProds(int cod) {
          String sql = "delete from produtos_entrega where cod_entrega = " + cod;
        return Banco.getCon().manipular(sql);
    }
}
