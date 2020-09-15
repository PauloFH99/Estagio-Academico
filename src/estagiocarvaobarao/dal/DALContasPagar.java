/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
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
public class DALContasPagar {

    public boolean salvar(ContasPagar cp, List<ContasPagar> Receber, Funcionario funcionario, String emissor_cheque, int qtde_parcelas) {

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
                        + "parcela,valor,valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_condicaopagamento,cod_iddes,valor_pendente,valor_total,emissor_cheque,qtde_parcelas)"
                        + " values(#A,#B,'#C','#D','#E','#F','#G','#H',#I,#J,#L,#M,#N,'#O',#P)";

                sql = sql.replace("#A", "" + cp.getDespesas().getCodigo());
                if (cp.getFornecedor() != null) {
                    sql = sql.replace("#B", "" + cp.getFornecedor().getCodigo());
                } else {
                    sql = sql.replace("#B", "null");
                }
                sql = sql.replace("#C", "" + receber.getParcela());
                sql = sql.replace("#D", "" + receber.getValor());
                sql = sql.replace("#E", "" + receber.getValor_pago());
                sql = sql.replace("#F", "" + formato.format(receber.getEmissaoDate()));
                if (receber.getData_pagoDate() != null) {
                    sql = sql.replace("#G", "" + formato.format(receber.getData_pagoDate()));
                } else {
                    sql = sql.replace("'#G'", "null");
                }
                sql = sql.replace("#H", "" + formato.format(receber.getVencimentoDate()));
                sql = sql.replace("#I", "" + funcionario.getCodigo());
                sql = sql.replace("#J", "" + cp.getCondicaopagamento().getCodigo());
                if (cp.getCod_iddes() == 0) {
                    sql = sql.replace("#L", "" + iddes);
                } else {
                    sql = sql.replace("#L", "" + cp.getCod_iddes());
                }
                sql = sql.replace("#M", "" + receber.getValor_pendente());
                sql = sql.replace("#N", "" + receber.getValor_total());
                sql = sql.replace("#O", emissor_cheque);
                sql = sql.replace("#P", "" + qtde_parcelas);
                aux = Banco.getCon().manipular(sql);
            }

            if (!aux) {
                return aux;
            }

        }

        return aux;

    }

    public boolean alterar(ContasPagar cp, Fornecedor fornecedor, CondPagto condpagto, Funcionario funcionario) {
        String sql = "update  contas_pagar set cod_despesa=#A,cod_fornecedor=#B,"
                + "parcela='#C',valor='#D',valor_pago='#E',emissao='#F',data_pago='#G',"
                + "vencimento='#H',cod_funcionario=#I,cod_condicaopagamento=#J,cod_iddes='#L',"
                + "valor_pendente='#M',valor_total='#N' where cod_entrada=" + cp.getCodigo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        if (cp.getDespesas() != null) {
            sql = sql.replace("#A", "" + cp.getDespesas().getCodigo());
        } else {
            sql = sql.replace("#A", "null");
        }
        sql = sql.replace("#B", "" + fornecedor.getCodigo());
        sql = sql.replace("#C", "" + cp.getParcela());
        sql = sql.replace("#D", "" + cp.getValor());
        sql = sql.replace("#E", "" + cp.getValor_pago());
        sql = sql.replace("#F", "" + cp.getEmissaoDate());
        if (cp.getData_pagoDate() != null) {
            sql = sql.replace("#G", "" + formato.format(cp.getData_pago()));
        } else {
            sql = sql.replace("'#G'", "null");
        }

        sql = sql.replace("#H", "" + cp.getVencimento());
        sql = sql.replace("#I", "" + funcionario.getCodigo());
        sql = sql.replace("#J", "" + condpagto.getCodigo());
        sql = sql.replace("#L", "" + cp.getCod_iddes());
        sql = sql.replace("#M", "" + cp.getValor_pendente());
        sql = sql.replace("#N", "" + cp.getValor_total());
        return Banco.getCon().manipular(sql);
    }

    public boolean compensarCheque(ContasPagar cp) {
        String sql = "update  contas_pagar set valor_pago='#A',data_pago='#B',"
                + "valor_pendente='#C' where codigo=" + cp.getCodigo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        sql = sql.replace("#A", "" + cp.getValor_pago());
        sql = sql.replace("#B", "" + cp.getData_pago());
        sql = sql.replace("#C", "" + cp.getValor_pendente());

        return Banco.getCon().manipular(sql);

    }

    public boolean estornar(ContasPagar cp) {
        String sql = "update  contas_pagar set valor_pago='#A',data_pago='#B',"
                + "valor_pendente='#C' where codigo=" + cp.getCodigo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        sql = sql.replace("#A", "" + cp.getValor_pago());
        if (cp.getData_pagoDate() != null) {
            sql = sql.replace("#B", "" + formato.format(cp.getData_pago()));
        } else {
            sql = sql.replace("'#B'", "null");
        }

        sql = sql.replace("#C", "" + cp.getValor_pendente());

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

    public boolean apagar(ContasPagar cp) {
        return Banco.getCon().manipular("delete from contas_pagar where codigo=" + cp.getCodigo());
    }

    public boolean apagarCompra(int cod) {
        return Banco.getCon().manipular("delete from contas_pagar where cod_entrada=" + cod);
    }

    public ContasPagar get(int cod) {

        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar where codigo =" + cod;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), new Fornecedor(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"),
                        new EntradaProdutos(), rs.getDouble("valor_total"), rs.getString("emissor_cheque"));
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public ContasPagar getCompra(int cod) {

        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar where cod_entrada =" + cod;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), new Fornecedor(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"),
                        new EntradaProdutos(), rs.getDouble("valor_total"), rs.getString("emissor_cheque"));
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public List<ContasPagar> get(String filtro) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfor = new DALFornecedor();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar";
        if (!filtro.isEmpty()) {
            int codCond = 0;
            codCond = dalcp.getN(filtro);
            sql += " where  cod_condicaopagamento=" + codCond + "order by vencimento";
        } else {
            sql += " order by vencimento";
        }
        Fornecedor fornecedor = new Fornecedor();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                fornecedor = dalfor.get(rs.getInt("cod_fornecedor"));
                if (fornecedor == null || fornecedor.getNomefantasia().isEmpty()) {
                    fornecedor = new Fornecedor();
                    fornecedor.setNomefantasia(dald.get(rs.getInt("cod_despesa")).getDescricao());
                }

                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), fornecedor,
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
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
        DALFornecedor dalfor = new DALFornecedor();
        String sql = "select * from contas_pagar where cod_iddes=" + coddes;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<ContasPagar> getCheque(int cod) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        DALFornecedor dalfor = new DALFornecedor();
        String sql = "select * from contas_pagar where codigo=" + cod;
        sql += " order by codigo";
        Fornecedor fornecedor = new Fornecedor();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                fornecedor = dalfor.get(rs.getInt("cod_fornecedor"));
                if (fornecedor == null || fornecedor.getNomefantasia().isEmpty()) {
                    fornecedor = new Fornecedor();
                    fornecedor.setNomefantasia(dald.get(rs.getInt("cod_despesa")).getDescricao());
                }
                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), fornecedor,
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public static int getCodigoConta() {
        return Banco.getCon().getMaxPK("contas_pagar", "codigo");
    }

    public boolean verificaPagamento(List<ContasPagar> aux) {
        boolean auxC = false;
        if (aux != null) {
            for (ContasPagar receber : aux) {
                ResultSet rs = Banco.getCon().consultar("select codigo from contas_pagar where cod_iddes= " + receber.getCod_iddes() + " and data_pago is not null");
                try {
                    while (rs.next()) {
                        return true;
                    }
                } catch (SQLException ex) {

                }
            }

        }
        return auxC;
    }

    public boolean alterar(List<ContasPagar> ReceberLD, Despesas despesa, Funcionario funcionario, String emissor_cheque, int qtde_parcelas) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        boolean aux = false;

        //alterando as parcelas 
        if (ReceberLD != null) {
            ReceberLD.get(0).apagarDespesa(ReceberLD.get(0).getCod_iddes());
            for (ContasPagar receber : ReceberLD) {
                String sql = " ";
                sql = "insert into contas_pagar (cod_despesa,cod_fornecedor,"
                        + "parcela,valor,valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_condicaopagamento,cod_iddes,valor_pendente,valor_total,emissor_cheque,qtde_parcelas)"
                        + " values(#A,#B,'#C','#D','#E','#F','#G','#H',#I,#J,#L,#M,#N,'#O',#P)";

                sql = sql.replace("#A", "" + despesa.getCodigo());
                if (receber.getFornecedor() != null) {
                    sql = sql.replace("#B", "" + receber.getFornecedor().getCodigo());
                } else {
                    sql = sql.replace("#B", "null");
                }
                sql = sql.replace("#C", "" + receber.getParcela());
                sql = sql.replace("#D", "" + receber.getValor());
                sql = sql.replace("#E", "" + receber.getValor_pago());
                sql = sql.replace("#F", "" + formato.format(receber.getEmissaoDate()));
                if (receber.getData_pagoDate() != null) {
                    sql = sql.replace("#G", "" + formato.format(receber.getData_pagoDate()));
                } else {
                    sql = sql.replace("'#G'", "null");
                }
                sql = sql.replace("#H", "" + formato.format(receber.getVencimentoDate()));
                sql = sql.replace("#I", "" + funcionario.getCodigo());
                sql = sql.replace("#J", "" + receber.getCondicaopagamento().getCodigo());
                sql = sql.replace("#L", "" + receber.getCod_iddes());
                sql = sql.replace("#M", "" + receber.getValor_pendente());
                sql = sql.replace("#N", "" + receber.getValor_total());
                sql = sql.replace("#O", emissor_cheque);
                sql = sql.replace("#P", "" + qtde_parcelas);
                aux = Banco.getCon().manipular(sql);
            }
        }
        return aux;
    }

    public boolean salvar(ContasPagar c, Fornecedor fornecedor, CondPagto condpagto, Funcionario funcionario, int codEntrada, String emissor) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        boolean aux = false;
        String sql = "";
        sql = "insert into contas_pagar (cod_entrada,parcela,valor,valor_pago,emissao,data_pago,vencimento,"
                + "cod_funcionario,cod_condicaopagamento,emissor_cheque,cod_fornecedor,cod_iddes) values "
                + "(#A,'#B','#C','#D','#E',#F,'#G',#H,#I,'#J',#K,#L)";

        sql = sql.replace("#A", "" + codEntrada);
        sql = sql.replace("#B", c.getParcela());
        sql = sql.replace("#C", "" + c.getValor());
        sql = sql.replace("#D", "" + c.getValor_pago());
        sql = sql.replace("#E", formato.format(c.getEmissaoDate()));

        if (c.getData_pagoDate() != null) {
            sql = sql.replace("#F", "'" + formato.format(c.getData_pagoDate()) + "'");
        } else {
            sql = sql.replace("#F", "null");
        }

        sql = sql.replace("#G", formato.format(c.getVencimentoDate()));
        sql = sql.replace("#H", "" + funcionario.getCodigo());
        sql = sql.replace("#I", "" + condpagto.getCodigo());
        if (!emissor.isEmpty()) {
            sql = sql.replace("#J", emissor);
        } else {
            sql = sql.replace("#J", "");
        }
        sql = sql.replace("#K", "" + fornecedor.getCodigo());
        sql = sql.replace("#L", "" + c.getCod_iddes());
        return Banco.getCon().manipular(sql);
    }

    public boolean verificaExiste(int cod_entrada, String parcela) {
        String sql = "select parcela from contas_pagar where cod_entrada=" + cod_entrada;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                String aux = rs.getString("parcela");
                if (aux.charAt(0) == parcela.charAt(0)) {
                    return true;
                }
            }
        } catch (SQLException ex) {

        }
        return false;
    }

    public boolean apagarDespesa(int cod_iddes) {
        return Banco.getCon().manipular("delete from contas_pagar where cod_iddes=" + cod_iddes);
    }

    public boolean gravarEntrega(ContasPagar cp, int codEntrada) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        String sql = "";
        sql = "insert into contas_pagar (cod_iddes,valor,valor_pago,emissao,data_pago,vencimento,"
                + "cod_funcionario,cod_condicaopagamento,cod_despesa) values "
                + "(#A,'#B','#C','#D','#E','#F',#G,#H,#I)";

        sql = sql.replace("#A", "" + codEntrada);
        sql = sql.replace("#B", "" + cp.getValor());
        sql = sql.replace("#C", "" + cp.getValor());
        sql = sql.replace("#D", formato.format(cp.getEmissaoDate()));
        sql = sql.replace("#E", formato.format(cp.getEmissaoDate()));
        sql = sql.replace("#F", formato.format(cp.getEmissaoDate()));
        sql = sql.replace("#G", "" + cp.getFuncionario().getCodigo());
        sql = sql.replace("#H", "" + cp.getCondicaopagamento().getCodigo());
        sql = sql.replace("#I", "" + cp.getDespesas().getCodigo());
        return Banco.getCon().manipular(sql);
    }

}
