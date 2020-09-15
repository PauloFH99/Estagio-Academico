package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Armazem;

import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;

import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto_Entrada;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALEntrada_Produtos {

    public boolean gravar(EntradaProdutos entrada_produtos, Fornecedor fornecedor, Funcionario funcionario, List<Produto_Entrada> Produtos, List<ContasPagar> Receber,
            CondPagto condpagto, Double total, LocalDate emissao, boolean fiado, int qtde, int diasentre, String emissor) {
        boolean aux = false;
        String sql = "";
        int codEntrada = 0;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        //Inserindo a Compra
        sql = "insert into entrada_produtos (cod_fornecedor,cod_funcionario,emissao,total,cod_condpagto,fiado,qtde_parcelas,diasentreparc) values "
                + "(#A,#B,'#C',#D,#E,'#F',#G,#H)";

        sql = sql.replace("#A", "" + fornecedor.getCodigo());
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
        aux = Banco.getCon().manipular(sql);

        if (!aux) {
            return aux;
        }

        //Pegando o máx código
        codEntrada = Banco.getCon().getMaxPK("entrada_produtos", "codigo");
        DALArmazem dalarma = new DALArmazem();
        int novo = 0;
        //Gravando os produtos           
        for (Produto_Entrada produto : Produtos) {
            sql = "";
            sql = "insert into produto_entrada(cod_produto,quantidade,unitario,total,cod_entrada) values "
                    + "(#A,#B,#C,#D,#E)";

            sql = sql.replace("#A", "" + produto.getCodprod());
            sql = sql.replace("#B", "" + produto.getQtde());
            sql = sql.replace("#C", "" + produto.getUnitario());
            sql = sql.replace("#D", "" + produto.getTotal());
            sql = sql.replace("#E", "" + codEntrada);

            aux = Banco.getCon().manipular(sql);
            if (dalarma.getProduto(produto.getCodprod())) {
                dalarma.alterar(produto.getCod_produto().getCodigo(), produto.getQtde(), "e");
            } else {
                novo = 1;
            }
        }
        if (novo == 1) {
            dalarma.salvarE(Produtos);
        }

        if (!aux) {
            return aux;
        }

        //Gravando as parcelas  
        if (Receber != null) {
            for (ContasPagar receber : Receber) {
                sql = "";
                sql = "insert into contas_pagar (cod_entrada,parcela,valor,valor_pago,emissao,data_pago,vencimento,"
                        + "cod_funcionario,cod_condicaopagamento,emissor_cheque,cod_fornecedor) values "
                        + "(#A,'#B','#C','#D','#E',#F,'#G',#H,#I,'#J',#K)";

                sql = sql.replace("#A", "" + codEntrada);
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
                    sql = sql.replace("#G", formato.format(receber.getVencimentoDate()));
                } else {
                    sql = sql.replace("#G", "null");
                }

                sql = sql.replace("#H", "" + funcionario.getCodigo());
                sql = sql.replace("#I", "" + condpagto.getCodigo());
                sql = sql.replace("#J", emissor);
                sql = sql.replace("#K", "" + fornecedor.getCodigo());
                aux = Banco.getCon().manipular(sql);
            }

            if (!aux) {
                return aux;
            }
        }

        return aux;
    }

    public EntradaProdutos getEntrada(int codigo) {

        String sql = "select e.*,f.nomefantasia from entrada_produtos e inner join fornecedor f on f.codigo = e.cod_fornecedor where e.codigo = " + codigo;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new EntradaProdutos(rs.getInt("codigo"), new Fornecedor(rs.getInt("cod_fornecedor")), new Funcionario(rs.getInt("cod_funcionario")),
                        new CondPagto(rs.getInt("cod_condpagto")), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"));
            }
        } catch (SQLException e) {
        }

        return null;
    }

    public boolean apagar(int codigo, List<Produto_Entrada> Produtos) {
        String sql = "delete from entrada_produtos where codigo = " + codigo;
        boolean aux = false;
        aux = Banco.getCon().manipular(sql);
        Armazem arm = new Armazem();
        for (Produto_Entrada produto : Produtos) {
            aux = arm.alterar(produto.getCodprod(), produto.getQtde(), "s");
        }
        return aux;
    }

    public boolean verificarParcelaPaga(int codigo) {
        String sql = "select codigo from contas_pagar where cod_entrada = " + codigo + " and data_pago is not null";

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

        String sql = "insert into contas_pagar (parcela,valor,valor_pago,emissao,data_pago,vencimento,cod_funcionario,cod_cliente) values "
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

    public EntradaProdutos get(int cod) {
        EntradaProdutos e = null;

        String sql = "select * from entrada_produtos where codigo =" + cod;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                e = new EntradaProdutos(rs.getInt("codigo"));
            }
        } catch (SQLException ex) {

        }
        return e;
    }

    public boolean alterar(int cod, Fornecedor fornecedor, Funcionario funcionario, List<Produto_Entrada> Produtos, List<ContasPagar> ReceberE, CondPagto condpagto, Double total, LocalDate emissao, boolean fiado, int qtdeparc, int diasentre, String emissor) {
        boolean aux = false;
        String sql = "";
        int codEntrada = 0;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");

        //Alterando a Compra
        sql = "update  entrada_produtos set cod_fornecedor=#A,cod_funcionario=#B,emissao='#C',total=#D,cod_condpagto=#E,fiado='#F',qtde_parcelas=#G,diasentreparc=#H where codigo= " + cod;

        sql = sql.replace("#A", "" + fornecedor.getCodigo());
        sql = sql.replace("#B", "" + funcionario.getCodigo());
        sql = sql.replace("#C", emissao.format(formatter));
        sql = sql.replace("#D", "" + total);
        sql = sql.replace("#E", "" + condpagto.getCodigo());
        if (fiado) {
            sql = sql.replace("#F", "S");
        } else {
            sql = sql.replace("#F", "N");
        }
        sql = sql.replace("#G", "" + qtdeparc);
        sql = sql.replace("#H", "" + diasentre);
        aux = Banco.getCon().manipular(sql);

        if (!aux) {
            return aux;
        }

        //Pegando o código
        codEntrada = cod;
        DALArmazem dalarma = new DALArmazem();
        int novo = 0;
        //ALterando os produtos           
        for (Produto_Entrada produto : Produtos) {
            sql = "";
            if (produto.verificaProd(produto.getCodprod(), cod)) {

                int qtde_ant = produto.qtdeprodAnterior(produto.getCodprod(), cod);

                sql = "update produto_entrada set quantidade=#B,unitario=#C,total=#D where cod_entrada=" + cod + " and cod_produto=" + produto.getCodprod();
                sql = sql.replace("#B", "" + produto.getQtde());
                sql = sql.replace("#C", "" + produto.getUnitario());
                sql = sql.replace("#D", "" + produto.getTotal());
                aux = Banco.getCon().manipular(sql);
                if (dalarma.getProduto(produto.getCodprod())) {
                    dalarma.alterar(produto.getCodprod(), qtde_ant, "s");
                    dalarma.alterar(produto.getCodprod(), produto.getQtde(), "e");
                } else {
                    novo = 1;
                }
            } else {
                sql = "insert into produto_entrada(cod_produto,quantidade,unitario,total,cod_entrada) values "
                        + "(#A,#B,#C,#D,#E)";

                sql = sql.replace("#A", "" + produto.getCodprod());
                sql = sql.replace("#B", "" + produto.getQtde());
                sql = sql.replace("#C", "" + produto.getUnitario());
                sql = sql.replace("#D", "" + produto.getTotal());
                sql = sql.replace("#E", "" + codEntrada);

                aux = Banco.getCon().manipular(sql);
                if (dalarma.getProduto(produto.getCodprod())) {
                    dalarma.alterar(produto.getCodprod(), produto.getQtde(), "e");
                } else {
                    novo = 1;
                }

            }

        }

        if (novo == 1) {
            dalarma.salvarE(Produtos);
        }

        if (!aux) {
            return aux;
        }

        //Gravando as parcelas  
        if (ReceberE != null) {
            ReceberE.get(0).apagarCompra(codEntrada);
            for (ContasPagar receber : ReceberE) {
                receber.salvar(receber, fornecedor, condpagto, funcionario, codEntrada, emissor);
            }
            if (!aux) {
                return aux;
            }
        }

        return aux;
    }

}
