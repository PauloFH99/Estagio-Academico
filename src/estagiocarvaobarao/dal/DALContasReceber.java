/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALContasReceber {

    public boolean salvar(ContasPagar cp, List<ContasPagar> Receber) {
//        String sql=" ";
//                sql = "insert into contas_pagar (cod_despesa,cod_entradaprodutos,"
//                + "parcela,valor,valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_condicaopagamento,cod_iddes)"
//                + " values(#A,#B,'#C','#D','#E','#F','#G','#H',#I,#J,#1)";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        boolean aux = false;
        int iddes = 1, rows = 0;
        //Gravando as parcelas
        try {
            Banco.conectar();
            ResultSet rs = Banco.getCon().consultar("select from contas_pagar");
            while (rs.next()) {
                rows = rs.getRow();
            }
            if (rows > 0) {
                iddes = Banco.getCon().getMaxPK("contas_pagar", "codigo") + 1;
            }
        } catch (Exception e) {
        }

        if (Receber != null) {
            for (ContasPagar receber : Receber) {
                String sql = " ";
                sql = "insert into contas_pagar (cod_despesa,cod_fornecedor,"
                        + "parcela,valor,valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_condicaopagamento,cod_iddes,valor_pendente,valor_total)"
                        + " values(#A,#B,'#C','#D','#E','#F','#G','#H',#I,#J,#L,#M,#N)";

                sql = sql.replace("#A", "" + cp.getDespesas().getCodigo());
                sql = sql.replace("#B", "" + cp.getFornecedor().getCodigo());
                sql = sql.replace("#C", "" + receber.getParcela());
                sql = sql.replace("#D", "" + receber.getValor());
                sql = sql.replace("#E", "" + receber.getValor_pago());
                sql = sql.replace("#F", "" + formato.format(receber.getEmissaoDate()));
                if (receber.getData_pago() != null) {
                    sql = sql.replace("#G", "" + formato.format(receber.getData_pago()));
                } else {
                    sql = sql.replace("'#G'", "null");
                }
                sql = sql.replace("#H", "" + formato.format(receber.getVencimentoDate()));
                sql = sql.replace("#I", "" + cp.getFuncionario().getCodigo());
                sql = sql.replace("#J", "" + cp.getCondicaopagamento().getCodigo());
                if (cp.getCod_iddes() == 0) {
                    sql = sql.replace("#L", "" + iddes);
                } else {
                    sql = sql.replace("#L", "" + cp.getCod_iddes());
                }
                sql = sql.replace("#M", "" + receber.getValor_pendente());
                sql = sql.replace("#N", "" + receber.getValor_total());
                aux = Banco.getCon().manipular(sql);
            }

            if (!aux) {
                return aux;
            }

        }

        return aux;

    }

    public boolean alterar(ContasPagar cp) {
        String sql = "update  contas_pagar set cod_despesa=#A,cod_fornecedor=#B,"
                + "parcela='#C',valor='#D',valor_pago='#E',emissao='#F',data_pago='#G',"
                + "vencimento='#H',cod_funcionario=#I,cod_condicaopagamento=#J,cod_iddes='#L',valor_pendente='#M',valor_total='#N'where codigo=" + cp.getCodigo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        sql = sql.replace("#A", "" + cp.getDespesas().getCodigo());
        sql = sql.replace("#B", "" + cp.getFornecedor().getCodigo());
        sql = sql.replace("#C", "" + cp.getParcela());
        sql = sql.replace("#D", "" + cp.getValor());
        sql = sql.replace("#E", "" + cp.getValor_pago());
        sql = sql.replace("#F", "" + cp.getEmissaoDate());
        if (cp.getData_pago() != null) {
            sql = sql.replace("#G", "" + formato.format(cp.getData_pago()));
        } else {
            sql = sql.replace("'#G'", "null");
        }

        sql = sql.replace("#H", "" + cp.getVencimento());
        sql = sql.replace("#I", "" + cp.getFuncionario().getCodigo());
        sql = sql.replace("#J", "" + cp.getCondicaopagamento().getCodigo());
        sql = sql.replace("#L", "" + cp.getCod_iddes());
        sql = sql.replace("#M", "" + cp.getValor_pendente());
        sql = sql.replace("#N", "" + cp.getValor_total());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(ContasPagar cp, List<ContasPagar> Receber) {
        boolean aux = false;

        if (Receber != null) {
            for (ContasPagar receber : Receber) {
                if (receber.getCod_iddes() == cp.getCod_iddes()) {
                    aux = Banco.getCon().manipular("delete from contas_pagar where cod_iddes=" + cp.getCod_iddes());
                    aux = true;
                } else {
                    aux = false;
                }
            }

        }
        return aux;
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from contas_receber where cod_venda=" + cod);
    }

    public ContasReceber get(int cod) {

        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        DALCliente dalc = new DALCliente();
        String sql = "select * from contas_receber where codigo =" + cod;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalc.get(rs.getInt("cod_cliente")), rs.getDouble("valor_pendente"),
                        rs.getString("emissorcheque"),rs.getString("num_boleto"));
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public List<ContasPagar> get(String filtro) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro + "order by codigo";
        }
        sql += " order by codigo";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), new Fornecedor(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), rs.getDouble("valor_total")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<ContasPagar> getConta(int coddes) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar where cod_iddes=" + coddes;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), new Fornecedor(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), rs.getDouble("valor_total")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public static int getCodigoConta() {
        return Banco.getCon().getMaxPK("contas_pagar", "codigo");
    }

    public List<ContasReceber> getFiado(int cod, String ini, String fim) {
        List<ContasReceber> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALCliente dalc = new DALCliente();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select ce.* from contas_receber ce,venda v "
                + "where ce.cod_venda = v.codigo "
                + "and v.fiado = 'S' "
                + "and ce.cod_cliente =" + cod + " "
                + "and  ce.emissao  BETWEEN  '" + ini + "'" + "and '" + fim + "'";

        sql += " order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                contp.add(new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalc.get(rs.getInt("cod_cliente")), rs.getDouble("valor_pendente"),
                        rs.getString("emissorcheque"),rs.getString("num_boleto")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }
    public List<ContasReceber> getFiado(int cod) {
        List<ContasReceber> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALCliente dalc = new DALCliente();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select ce.* from contas_receber ce,venda v "
                + "where ce.cod_venda = v.codigo "
                + "and v.fiado = 'S' "
                + "and ce.cod_cliente =" + cod ;
               

        sql += " order by v.emissao ,ce.valor_pago desc";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                contp.add(new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalc.get(rs.getInt("cod_cliente")), rs.getDouble("valor_pendente"),
                        rs.getString("emissorcheque"),rs.getString("num_boleto")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public boolean alterar(ContasReceber cont) {
        String sql = "update  contas_receber set valor_pago='#A',data_pago='#B',"
                + "valor_pendente='#C' where cod_cliente=" + cont.getCliente().getCodigo()+ "and cod_venda="+cont.getVenda().getCodigo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        sql = sql.replace("#A", "" + cont.getValor_pago());

        
            sql = sql.replace("#B",""+cont.getData_pagoDate());
       
        sql = sql.replace("#C", "" + cont.getValor_pendente());

        return Banco.getCon().manipular(sql);

    }

   
    public boolean apagarVenda(int cod) {
        return Banco.getCon().manipular("delete from contas_receber where cod_venda=" + cod);
    }

    public boolean salvar(ContasReceber receber, Cliente cliente, CondPagto condpagto, Funcionario funcionario, int codVenda,String emissor) {
       SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        boolean aux = false;
        String sql = "";
        sql = "insert into contas_receber (cod_venda,parcela,valor,valor_pago,emissao,data_pago,vencimento,"
                + "cod_funcionario,cod_condicaopagamento,emissorcheque,cod_cliente,num_boleto) values "
                + "(#A,'#B','#C','#D','#E',#F,'#G',#H,#I,'#J',#K,'#M')";

        sql = sql.replace("#A", "" + codVenda);
        sql = sql.replace("#B", receber.getParcela());
        sql = sql.replace("#C", "" + receber.getValor());
        sql = sql.replace("#D", "" + receber.getValor_pago());
        sql = sql.replace("#E", formato.format(receber.getEmissaoDate()));

        if (receber.getData_pagoDate() != null) {
            sql = sql.replace("#F", "'" + formato.format(receber.getData_pagoDate()) + "'");
        } else {
            sql = sql.replace("#F", "null");
        }

        sql = sql.replace("#G", formato.format(receber.getVencimentoDate()));
        sql = sql.replace("#H", "" + funcionario.getCodigo());
        sql = sql.replace("#I", "" + condpagto.getCodigo());
        if (!emissor.isEmpty()) {
            sql = sql.replace("#J", emissor);
        } else {
            sql = sql.replace("#J", "");
        }
        sql = sql.replace("#K", "" + cliente.getCodigo());
        sql = sql.replace("#M", receber.getNum_boleto());
        return Banco.getCon().manipular(sql);
    }

    public ContasReceber getVendaCond(int codigo) {
       ContasReceber contp = new ContasReceber();
        DALFuncionario dalf = new DALFuncionario();
        DALCliente dalc = new DALCliente();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_receber where cod_venda =" + codigo ;

        sql += " order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                return new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalc.get(rs.getInt("cod_cliente")), rs.getDouble("valor_pendente"),
                        rs.getString("emissorcheque"),rs.getString("num_boleto"));
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public boolean estornar(ContasReceber cr) {
        String sql = "update  contas_receber set valor_pago='#A',data_pago='#B',"
                + "valor_pendente='#C' where codigo=" + cr.getCodigo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        sql = sql.replace("#A", "" + cr.getValor_pago());
        if (cr.getData_pagoDate() != null) {
            sql = sql.replace("#B", "" + formato.format(cr.getData_pago()));
        } else {
            sql = sql.replace("'#B'", "null");
        }

        sql = sql.replace("#C", "" + cr.getValor_pendente());

        return Banco.getCon().manipular(sql);
    }

    public List<ContasReceber> getAll(int cod) {
        List<ContasReceber>  contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALCliente dalc = new DALCliente();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_receber where codigo =" + cod ;

        sql += " order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                contp.add(new ContasReceber(rs.getInt("codigo"), new Venda(rs.getInt("cod_venda")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalc.get(rs.getInt("cod_cliente")), rs.getDouble("valor_pendente"),
                        rs.getString("emissorcheque"),rs.getString("num_boleto")));
            }
        } catch (SQLException ex) {

        }
        return null;
    }

}
