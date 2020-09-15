package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Pedido;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import static estagiocarvaobarao.ui.TelaVendaController.total_ant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALVenda {

    public boolean gravar(Venda venda, Cliente cli, Funcionario funcionario, List<Produto_Venda> Produtos, List<ContasReceber> Receber,
            CondPagto condpagto, Double total, LocalDate emissao, boolean fiado, int qtde, int diasentre,
            String emissorcheque, String estoque, String situacao) {
        boolean aux = false;
        String sql = "";
        String sqlfiado = "";
        int codVenda = 0;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        //Inserindo a Venda
        sql = "insert into venda (cod_cliente,cod_funcionario,emissao,total,cod_condpagto,fiado,qtde_parcelas,"
                + "diasentreparc,estoque,situacao) values "
                + "(#A,#B,'#C',#D,#E,'#F',#G,#H,'#I','#J')";

        sql = sql.replace("#A", "" + cli.getCodigo());
        sql = sql.replace("#B", "" + funcionario.getCodigo());
        sql = sql.replace("#C", emissao.format(formatter));
        sql = sql.replace("#D", "" + total);
        sql = sql.replace("#E", "" + condpagto.getCodigo());
        if (fiado) {
            sql = sql.replace("#F", "S");
        } else {
            sql = sql.replace("#F", "N");
        }
        sql = sql.replace("#G", "" + qtde);
        sql = sql.replace("#H", "" + diasentre);
        sql = sql.replace("#I", "" + estoque);
        sql = sql.replace("#J", situacao);
        aux = Banco.getCon().manipular(sql);

        if (aux == false) {
            return aux;
        }
        DALArmazem dalarma = new DALArmazem();
        int novo = 0;
        //Pegando o máx código
        codVenda = Banco.getCon().getMaxPK("venda", "codigo");
        if (fiado) {//se for fiado
            sqlfiado = "insert into contas_receber (cod_venda,parcela,valor,emissao,"
                    + "cod_funcionario,cod_cliente,cod_condicaopagamento) "
                    + "values (#A,'#B','#C','#D',#E,#F,#G)";
            sqlfiado = sqlfiado.replace("#A", "" + codVenda);
            sqlfiado = sqlfiado.replace("#B", "1/1");
            sqlfiado = sqlfiado.replace("#C", "" + total);
            sqlfiado = sqlfiado.replace("#D", emissao.format(formatter));
            sqlfiado = sqlfiado.replace("#E", "" + funcionario.getCodigo());
            sqlfiado = sqlfiado.replace("#F", "" + cli.getCodigo());
            sqlfiado = sqlfiado.replace("#G", "" + condpagto.getCodigo());
            aux = Banco.getCon().manipular(sqlfiado);

        }
        //Gravando os produtos           
        for (Produto_Venda produto : Produtos) {

            sql = "insert into produto_venda(cod_produto,quantidade,unitario,total,cod_venda) values "
                    + "(#A,#B,#C,#D,#E)";

            sql = sql.replace("#A", "" + produto.getCodprod());
            sql = sql.replace("#B", "" + produto.getQtde());
            sql = sql.replace("#C", "" + produto.getUnitario());
            sql = sql.replace("#D", "" + produto.getTotal());
            sql = sql.replace("#E", "" + codVenda);
            aux = Banco.getCon().manipular(sql);
            if (estoque.equals("a")) {
                if (dalarma.getProduto(produto.getCodprod())) {
                    dalarma.alterar(produto.getCod_produto().getCodigo(), produto.getQtde(), "S");
                } else {
                    novo = 1;
                }
            }
        }
        if (estoque.equals("a")) {
            if (novo == 1) {
                dalarma.salvarV(Produtos);
            }
        }

        if (aux == false) {
            return aux;
        }

        //Gravando as parcelas  
        if (Receber != null) {
            for (ContasReceber receber : Receber) {

                sql = "insert into contas_receber (cod_venda,parcela,valor,valor_pago,emissao,"
                        + "data_pago,vencimento,cod_funcionario,cod_cliente,valor_pendente,emissorcheque,"
                        + "num_boleto,cod_condicaopagamento) values "
                        + "(#A,'#B','#C','#D','#E',#F,#G,#H,#I,'#J','#K','#M',#N)";

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
                if (receber.getVencimentoDate() != null) {
                    sql = sql.replace("#G", "'" + formato.format(receber.getVencimentoDate()) + "'");
                } else {
                    sql = sql.replace("#G", "null");
                }

                sql = sql.replace("#H", "" + funcionario.getCodigo());
                sql = sql.replace("#I", "" + cli.getCodigo());
                sql = sql.replace("#J", "" + receber.getValor_pendente());
                sql = sql.replace("#K", emissorcheque);
                sql = sql.replace("#M", receber.getNum_boleto());
                sql = sql.replace("#N", "" + condpagto.getCodigo());
                aux = Banco.getCon().manipular(sql);
            }

            if (!aux) {
                return aux;
            }
        }

        return aux;
    }

    public Venda getVenda(int codigo) {
        DALCategoria dalc = new DALCategoria();
        String sql = "select v.*,c.nome from venda v inner join cliente c on c.codigo = v.cod_cliente where v.codigo = " + codigo;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Venda(rs.getInt("codigo"), new Cliente(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"));
            }
        } catch (SQLException e) {
        }

        return null;
    }

    public boolean atualizarSaldoDevedor(int codcliente, double valor) {
        String sql = "update cliente set saldo_devedor = saldo_devedor + " + valor + " where codigo = " + codcliente;
        return Banco.getCon().manipular(sql);
    }

    public boolean atualizarSaldoDevedorAlteracao(int codcliente, double valor) {
        boolean aux = false;
        String sql = "update cliente set saldo_devedor = saldo_devedor - " + total_ant + " where codigo = " + codcliente;
        aux = Banco.getCon().manipular(sql);
        sql = "";
        sql = "update cliente set saldo_devedor = saldo_devedor + " + valor + " where codigo = " + codcliente;
        Banco.getCon().manipular(sql);
        return aux;
    }

    public boolean apagar(int codigo) {
        String sql = "delete from venda where codigo = " + codigo;
        return Banco.getCon().manipular(sql);
    }

    public boolean verificarParcelaPaga(int codigo) {
        String sql = "select codigo from contas_receber where cod_venda = " + codigo + " and data_pago is not null";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }

        return false;

    }

    public boolean gerarReceberFiado(int codcliente, double valor, int codfuncionario) {
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        d = cal.getTime();

        String sql = "insert into receber (parcela,valor,valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_cliente) values "
                + "('#A',#B,#C,'#D','#E','#F',#G,#H)";

        sql = sql.replace("#A", "1/1");
        sql = sql.replace("#B", "" + valor);
        sql = sql.replace("#C", "" + valor);
        sql = sql.replace("#D", formato.format(d));
        sql = sql.replace("#E", formato.format(d));
        sql = sql.replace("#F", formato.format(d));
        sql = sql.replace("#G", "" + codfuncionario);
        sql = sql.replace("#H", "" + codcliente);

        return Banco.getCon().manipular(sql);
    }

    public Venda get(int cod) {
        Venda prod = null;
        DALCategoria dalc = new DALCategoria();
        String sql = "select * from venda where codigo =" + cod;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                prod = new Venda(rs.getInt("codigo"));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public boolean alterar(int cod, Cliente cliente, Funcionario funcionario, List<Produto_Venda> Produtos,
            List<ContasReceber> Receber, CondPagto condpagto, Double total, LocalDate emissao,
            boolean fiado, int qtdeparc, int diasentre, String emissor, String estoque, String situacao) {
        boolean aux = false;
        String sql = "";
        int codVenda = 0;
        ContasReceber rec = new ContasReceber();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        //Alterando a Venda
        sql = "update  venda set cod_cliente=#A,cod_funcionario=#B,emissao='#C',total=#D,"
                + "cod_condpagto=#E,fiado='#F',qtde_parcelas=#G,diasentreparc=#H,estoque='#I',situacao='#J' where codigo= " + cod;

        sql = sql.replace("#A", "" + cliente.getCodigo());
        sql = sql.replace("#B", "" + funcionario.getCodigo());
        sql = sql.replace("#C", emissao.format(formatter));
        sql = sql.replace("#D", "" + total);
        sql = sql.replace("#E", "" + condpagto.getCodigo());
        if (fiado) {
            sql = sql.replace("#F", "S");
            rec.apagarVenda(codVenda);
        } else {
            sql = sql.replace("#F", "N");
        }
        sql = sql.replace("#G", "" + qtdeparc);
        sql = sql.replace("#H", "" + diasentre);
        sql = sql.replace("#I", estoque);
        sql = sql.replace("#J", situacao);
        aux = Banco.getCon().manipular(sql);

        if (!aux) {
            return aux;
        }

        //Pegando o código
        codVenda = cod;
        DALArmazem dalarma = new DALArmazem();
        int novo = 0;
        //ALterando os produtos           
        for (Produto_Venda produto : Produtos) {
            sql = "";
            if (produto.verificaProd(produto.getCodprod(), cod)) {

                int qtde_ant = produto.qtdeprodAnterior(produto.getCodprod(), cod);

                sql = "update produto_venda set quantidade=#B,unitario=#C,total=#D where cod_venda=" + cod + " and cod_produto=" + produto.getCodprod();
                sql = sql.replace("#B", "" + produto.getQtde());
                sql = sql.replace("#C", "" + produto.getUnitario());
                sql = sql.replace("#D", "" + produto.getTotal());
                aux = Banco.getCon().manipular(sql);
                if (estoque.equals("a")) {
                    if (dalarma.getProduto(produto.getCodprod())) {
                        dalarma.alterar(produto.getCodprod(), qtde_ant, "e");
                        dalarma.alterar(produto.getCodprod(), produto.getQtde(), "s");
                    } else {
                        novo = 1;
                    }
                } else {
//                    if (dalarma.getProduto(produto.getCodprod())) {
//                        dalarma.alterar(produto.getCodprod(), qtde_ant, "s");
//                        dalarma.alterar(produto.getCodprod(), produto.getQtde(), "e");
//                    } else {
//                        novo = 1;
//                    }
                }
            } else {
                sql = "insert into produto_venda(cod_produto,quantidade,unitario,total,cod_venda) values "
                        + "(#A,#B,#C,#D,#E)";

                sql = sql.replace("#A", "" + produto.getCodprod());
                sql = sql.replace("#B", "" + produto.getQtde());
                sql = sql.replace("#C", "" + produto.getUnitario());
                sql = sql.replace("#D", "" + produto.getTotal());
                sql = sql.replace("#E", "" + codVenda);

                aux = Banco.getCon().manipular(sql);
                if (estoque.equals('a')) {
                    if (dalarma.getProduto(produto.getCodprod())) {
                        dalarma.alterar(produto.getCodprod(), produto.getQtde(), "e");
                    } else {
                        novo = 1;
                    }
                } else {

                }
            }
        }
        if (estoque.equals('a')) {
            if (novo == 1) {
                dalarma.salvarV(Produtos);
            }
        } else {
        }

        if (!aux) {
            return aux;
        }

        //Gravando as parcelas  
        if (Receber != null && !Receber.isEmpty()) {
            Receber.get(0).apagarVenda(codVenda);
            for (ContasReceber receber : Receber) {
                receber.salvar(receber, cliente, condpagto, funcionario, codVenda, emissor);
            }
            if (!aux) {
                return aux;
            }
        }

        return aux;
    }

    public List<Venda> getFiado(int codigo) {
        List<Venda> prod = new ArrayList();
        DALCliente dalc = new DALCliente();
        String sql = "select * from venda where cod_cliente =" + codigo + " and fiado ='S' order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Venda> getPedidoCod(int codcli) {
        List<Venda> prod = new ArrayList();
        DALCliente dalc = new DALCliente();
        String sql = "select * from venda where cod_cliente =" + codcli + " and fiado ='N' order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"), rs.getString("situacao")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Venda> getPedidoEmissao(String emissao) {
        List<Venda> prod = new ArrayList();
        DALCliente dalc = new DALCliente();
        String sql = "select * from venda where emissao ='" + emissao + "' and fiado ='N' order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"), rs.getString("situacao")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Venda> getPedidoSitu(String situacao) {
        List<Venda> prod = new ArrayList();
        DALCliente dalc = new DALCliente();
        String sql = "select * from venda where situacao ='" + situacao + "' and fiado ='N' order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"), rs.getString("situacao")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Venda> getPedidoAll(int codcli, String emissao, String situacao) {
        List<Venda> prod = new ArrayList();
        DALCliente dalc = new DALCliente();
        String sql = "select * from venda where cod_cliente =" + codcli + " "
                + "and situacao ='" + situacao + "' "
                + "and emissao='" + emissao + "' "
                + " and situacao='" + situacao + "' and fiado ='N' order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"), rs.getString("situacao")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public boolean alterarSituacao(int codvenda, String situacao) {
        String sql = "";
        sql = "update  venda set situacao='#A' where codigo=" + codvenda;
        sql = sql.replace("#A", situacao);
        return Banco.getCon().manipular(sql);
    }

    public List<Venda> getPedidoSituCli(int codcli, String situacao) {
        List<Venda> prod = new ArrayList();
        DALCliente dalc = new DALCliente();
        String sql = "select * from venda where cod_cliente =" + codcli + " "
                + " and situacao='" + situacao + "' and fiado ='N' order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"), rs.getString("situacao")));
            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public boolean alterarSituacaoItems(List<Venda> itemsV) {
        String sql = "";
        boolean aux = false;
        for (Venda venda : itemsV) {
            sql = "update  venda set situacao='#A' where codigo=" + venda.getCodigo();
            if (venda.isSelected()) {
                sql = sql.replace("#A", "Entrega");
                aux = Banco.getCon().manipular(sql);
            }
        }

        return aux;
    }
}
