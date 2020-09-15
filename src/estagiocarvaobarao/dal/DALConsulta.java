package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.AcertoEstoque;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Entrega;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.NivelFuncionario;
import estagiocarvaobarao.entidade.Producao;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Entrada;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DALConsulta {

    public List<?> get(String tabela, String filtro) {
        String SQL = "";

        //Cidade
        if (tabela.equals("cidade")) {
            SQL = " select cid_cod,cid_nome from cidade c inner join estado e on e.est_cod = c.est_cod ";

            if (!filtro.isEmpty()) {
                SQL = SQL + " where " + filtro;
            }
            SQL += " order by cid_cod";

            List<Cidade> cid = new ArrayList();

            ResultSet rs = Banco.getCon().consultar(SQL);
            try {
                while (rs.next()) {
                    cid.add(new Cidade(rs.getInt("cid_cod"), rs.getString("cid_nome")));
                }
            } catch (SQLException e) {
            }

            return cid;
        }
        if (tabela.equals("funcionario")) {
            List<Funcionario> func = new ArrayList();
            SQL = "select f.codigo,f.nome,f.cpf, f.cod_nivel,nf.descricao nivel,case when f.ativo = 'Ativo' then 'Ativo' when f.ativo = 'Não Ativo'"
                    + " then 'Não Ativo' end ativo from funcionario f"
                    + " inner join nivel_funcionario nf on nf.codigo = f.cod_nivel";

            if (!filtro.isEmpty()) {
                SQL = SQL + " where " + filtro;
            }

            SQL = SQL + " order by f.nome ";

            ResultSet rs = Banco.getCon().consultar(SQL);
            try {
                while (rs.next()) {
                    func.add(new Funcionario(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"), rs.getString("ativo"), new NivelFuncionario(rs.getInt("cod_nivel"), rs.getString("nivel"))));
                }
            } catch (SQLException e) {
            }

            return func;
        }

        if (tabela.equals("acertoestoque")) {
            List<AcertoEstoque> prod = new ArrayList();
            DALProduto dalp = new DALProduto();
            DALFuncionario dalf = new DALFuncionario();
            String sql = "select * from acertoestoque ";

            sql += " order by emissao";

            ResultSet rs = Banco.getCon().consultar(sql);
            try {
                while (rs.next()) {

                    prod.add(new AcertoEstoque(rs.getInt("codigo"), rs.getDate("emissao"),
                            rs.getString("tipo"), dalp.get(rs.getInt("cod_produto")), dalf.get(rs.getInt("cod_funcionario")),
                            rs.getString("observacoes"), rs.getInt("quantidade"), rs.getString("estoque")));

                }
            } catch (SQLException ex) {

            }
            return prod;

        }
        if (tabela.equals("despesas")) {

            List<Despesas> des = new ArrayList();
            DALCategoria dalc = new DALCategoria();
            String sql = "select * from despesas";
            if (!filtro.isEmpty()) {
                sql += " where " + filtro;
            }
            sql += " order by codigo";
            ResultSet rs = Banco.getCon().consultar(sql);
            try {
                while (rs.next()) {
                    des.add(new Despesas(rs.getInt("codigo"), rs.getString("descricao"), rs.getInt("dia_pagar")));
                }

            } catch (SQLException ex) {

            }
            return des;
        }
        if (tabela.equals("fornecedor")) {
            List<Fornecedor> forn = new ArrayList();
            DALCidade dalc = new DALCidade();
            DALCategoria dalcat = new DALCategoria();
            DALNivelFuncionario daln = new DALNivelFuncionario();
            String sql = "select * from fornecedor";
            if (!filtro.isEmpty()) {
                sql += " where " + filtro;
            }
            sql += " order by codigo";
            ResultSet rs = Banco.getCon().consultar(sql);
            try {
                while (rs.next()) {
                    forn.add(new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                            rs.getString("ativo"), rs.getString("logradouro"), rs.getString("bairro"),
                            rs.getString("numero"), dalc.get(rs.getInt("codcidade")), rs.getString("cep"),
                            rs.getString("nomecontato"), rs.getString("telefonecontato"), rs.getString("email"),
                            rs.getString("telefone"), rs.getString("razaosocial"), dalcat.get(rs.getInt("cod_categoria"))));

                }
            } catch (SQLException ex) {
            }
            return forn;
        }
        if (tabela.equals("contas_pagar")) {
            List<ContasPagar> contp = new ArrayList();
            DALFuncionario dalf = new DALFuncionario();
            DALFornecedor dalfor = new DALFornecedor();
            DALDespesas dald = new DALDespesas();
            DALCondPagto dalcp = new DALCondPagto();
            String sql = "select * from contas_pagar";
            if (!filtro.isEmpty() && !filtro.toLowerCase().equals("cheque")) {
                sql += " where " + filtro;
            }
            sql += " order by vencimento";
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

        return null;
    }

    public List<AcertoEstoque> getAcerto(String dtini, String dtfin) {
        List<AcertoEstoque> prod = new ArrayList();
        DALProduto dalp = new DALProduto();
        DALFuncionario dalf = new DALFuncionario();
        String sql = "select * from acertoestoque where emissao between '" + dtini + "' and '" + dtfin + "'";
        sql += " order by emissao";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                prod.add(new AcertoEstoque(rs.getInt("codigo"), rs.getDate("emissao"),
                        rs.getString("tipo"), dalp.get(rs.getInt("cod_produto")), dalf.get(rs.getInt("cod_funcionario")),
                        rs.getString("observacoes"), rs.getInt("quantidade"), rs.getString("estoque")));

            }
        } catch (SQLException ex) {

        }
        return prod;
    }

    public List<Produto_Venda> getProdutosVenda(int codigo) {
        String sql = "select pv.cod_produto,p.descricao,pv.quantidade,pv.unitario,pv.total"
                + " from produto_venda pv "
                + " inner join venda v on v.codigo = pv.cod_venda"
                + " inner join produto p on p.codigo = pv.cod_produto"
                + " where pv.cod_venda = " + codigo;

        List<Produto_Venda> prod = new ArrayList();

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Produto_Venda(rs.getString("descricao"), rs.getInt("cod_produto"), rs.getInt("quantidade"), rs.getDouble("unitario"), rs.getDouble("total")));
            }
        } catch (SQLException e) {
        }

        return prod;
    }

    public List<ContasPagar> getCheques() {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select cp.* from contas_pagar cp,condpagto c "
                + "where cp.cod_condicaopagamento = c.codigo  "
                + " and c.descricao ilike '%cheque%' ";
        sql += " order by vencimento";
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
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque"), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<ContasPagar> getCheques(String filtro, int flag) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "";
        if (filtro.isEmpty()) {
            sql = "select * from contas_pagar cp,condpagto c "
                    + " where cp.cod_condicaopagamento = c.codigo "
                    + " and c.descricao ilike '%cheque%'  "
                    + " order by vencimento";
        } else {
            if (flag == 1) {
                sql = "select cp.* from contas_pagar cp,condpagto c ,despesas d "
                        + "where cp.cod_condicaopagamento = c.codigo  "
                        + " and cp.cod_despesa = d.codigo "
                        + "and  d.descricao ilike '%" + filtro + "%' "
                        + "and c.descricao ilike '%cheque%' ";
                sql += " order by vencimento";
            } else {
                sql = "select cp.* from contas_pagar cp,condpagto c ,fornecedor f "
                        + "where cp.cod_condicaopagamento = c.codigo "
                        + " and cp.cod_fornecedor = f.codigo "
                        + "and  f.nomefantasia ilike '" + filtro + "%' "
                        + "and c.descricao ilike '%cheque%' ";
                sql += " order by vencimento";
            }
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
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque"), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<Produto_Entrada> getProdutosCompra(int codigo) {
        String sql = "select pe.cod_produto,p.descricao,pe.quantidade,pe.unitario,pe.total"
                + " from produto_entrada pe"
                + " inner join entrada_produtos e on e.codigo = pe.cod_entrada"
                + " inner join produto p on p.codigo = pe.cod_produto"
                + " where pe.cod_entrada = " + codigo;

        List<Produto_Entrada> prod = new ArrayList();

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Produto_Entrada(rs.getString("descricao"), rs.getInt("cod_produto"), rs.getInt("quantidade"), rs.getDouble("unitario"), rs.getDouble("total")));
            }
        } catch (SQLException e) {
        }

        return prod;
    }

    public List<Venda> getVendas(String filtro, String dtini, String dtfin) {
        String sql = "select v.*,c.nome from venda v inner join cliente c on c.codigo = v.cod_cliente  ";
        List<Venda> ven = new ArrayList();
        DALCliente dalc = new DALCliente();
        if (!filtro.isEmpty() && dtini.isEmpty()) {
            sql = sql + filtro;
        } else if (!dtini.isEmpty()) {
            sql = "";
            sql = "select * from venda where emissao between '" + dtini + "' and '" + dtfin + "'";
        }
        sql += " order by v.emissao desc";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                ven.add(new Venda(rs.getInt("codigo"), dalc.get(rs.getInt("cod_cliente")), new Funcionario(rs.getInt("cod_funcionario")), new CondPagto(rs.getInt("cod_condpagto")),
                        rs.getString("nome"), rs.getDate("emissao"), rs.getDouble("total"),
                        rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc"), rs.getString("estoque"), rs.getString("situacao")));
            }
        } catch (SQLException e) {
        }

        return ven;
    }

    public List<EntradaProdutos> getCompras(String filtro, String dtini, String dtfin) {
        String sql = "select e.*,f.nomefantasia from entrada_produtos e inner join fornecedor f on f.codigo = e.cod_fornecedor ";
        List<EntradaProdutos> com = new ArrayList();
        DALFornecedor dalf = new DALFornecedor();

        if (!filtro.isEmpty() && dtini.isEmpty()) {
            sql = sql + filtro;
        } else if (!dtini.isEmpty()) {
            sql = "";
            sql = "select * from entrada_produtos where emissao between '" + dtini + "' and '" + dtfin + "'";
        }

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                com.add(new EntradaProdutos(rs.getInt("codigo"), dalf.get(rs.getInt("cod_fornecedor")), new Funcionario(rs.getInt("cod_funcionario")), new CondPagto(rs.getInt("cod_condpagto")),
                        rs.getDate("emissao"), rs.getDouble("total"), rs.getInt("qtde_parcelas"), rs.getInt("diasentreparc")));
            }
        } catch (SQLException e) {
        }

        return com;
    }

    public List<ContasReceber> getReceber(int codigo) {
        String sql = "select emissao,parcela,valor,vencimento,data_pago,valor_pago,cod_condicaopagamento,emissorcheque,num_boleto from contas_receber where cod_venda = " + codigo;
        List<ContasReceber> rec = new ArrayList();
        DALCondPagto dal = new DALCondPagto();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                rec.add(new ContasReceber(rs.getString("parcela"), rs.getDouble("valor"),
                        rs.getDouble("valor_pago"), rs.getDate("emissao"), rs.getDate("data_pago"),
                        rs.getDate("vencimento"), dal.get(rs.getInt("cod_condicaopagamento")),
                        rs.getString("emissorcheque"), rs.getString("num_boleto")));
            }
        } catch (SQLException e) {
        }

        return rec;
    }

    public List<ContasPagar> getPagar(int codigo) {
        String sql = "select * from contas_pagar where cod_entrada = " + codigo;
        List<ContasPagar> rec = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfor = new DALFornecedor();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                rec.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
            }
        } catch (SQLException e) {
        }

        return rec;
    }

    public List<ContasPagar> getFiltros(String datainicio, String datafim, String filtrorb, String filtro) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        DALFornecedor dalfor = new DALFornecedor();
        String sql = "select * from contas_pagar  where vencimento  BETWEEN  '" + datainicio + "'" + "and '" + datafim + "'";
        if (filtrorb.equals("Pendente")) {
            sql += " and " + "valor_pendente >  0";
        } else if (filtrorb.equals("Baixado")) {
            sql += " and " + "valor_pendente =  0";
        }
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<ContasPagar> getBaixado(String filtro) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALFornecedor dalfor = new DALFornecedor();
        DALDespesas dald = new DALDespesas();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar  where valor_pendente =  0";
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<ContasPagar> getPen(String filtro) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar  where valor_pendente >  0";
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public Cidade getCidade(Cidade cid) {
        String sql = "select cid_cod,cid_nome from cidade where cid_cod = " + cid.getCid_cod();
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Cidade(rs.getInt("cid_cod"), rs.getString("cid_nome"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Produto> getProdutos(String filtro, int flag) {
        List<Produto> prod = new ArrayList();
        DALCategoria dalc = new DALCategoria();
        String sql = "select p.* from produto p, categoria_produto c ";
        if (flag == 1) {
            sql = "";
            sql = "select p.*,c.descricao from produto p, categoria_produto c "
                    + " where p.cod_categoria = c.codigo "
                    + " and (c.descricao ilike '%carvão saco de papel%' or c.descricao ilike '%carvão saco de rafia%')"
                    + " and p.ativo = 'true'"
                    + " order by c.descricao,p.peso";
        } else {
            if (!filtro.isEmpty()) {
                sql = sql + " where " + filtro + " and p.cod_categoria = c.codigo and ativo = 'true' order by c.descricao,p.peso";
            } else {
                sql = sql + " where  p.cod_categoria = c.codigo  and ativo = 'true' order by c.descricao,p.peso ";
            }
        }

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                prod.add(new Produto(rs.getInt("codigo"), rs.getInt("estq_min"),
                        rs.getInt("estq_max"), rs.getString("descricao"),
                        rs.getDouble("preco"), rs.getInt("estoque_fisico"),
                        rs.getBoolean("ativo"), dalc.get(rs.getInt("cod_categoria")), rs.getDouble("peso")));
            }
        } catch (SQLException e) {
        }

        return prod;
    }
     public List<Armazem> getProdutosArmazemAll(String filtro) {
        String SQL = "select e.codigo,e.cod_produto,e.quantidade,cp.descricao as categoria "
                + "from estoquearmazem e,produto p,categoria_produto cp "
                + " where e.cod_produto=p.codigo  "
                + " and p.cod_categoria = cp.codigo "
                + " order by cp.descricao,p.peso";
        List<Armazem> a = new ArrayList();
        DALProduto dal = new DALProduto();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                a.add(new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), 
                        dal.get(rs.getInt("cod_produto")), rs.getInt("cod_produto"),
                        rs.getString("categoria")));

            }
        } catch (SQLException e) {
        }
        return a;
    }

    public List<Armazem> getProdutosArmazem(String filtro, int cod_for) {
        Fornecedor f = new Fornecedor();
        f = f.get(cod_for);
        String cat = "";
        cat = f.getCategoria().getDescricao();
        if (cat.toLowerCase().equals("embalagem  de rafia")
                || cat.toLowerCase().equals("embalagem de papel")
                || cat.toLowerCase().equals("embalagem de papel real")) {
            cat = "Embalagem";
        }
        String SQL = "select e.codigo,e.cod_produto,e.quantidade,cp.descricao as categoria "
                + "from estoquearmazem e,produto p,categoria_produto cp "
                + " where e.cod_produto=p.codigo  "
                + " and p.cod_categoria = cp.codigo "
                + " and cp.descricao ilike '%" + cat + "%' "
                + " order by cp.descricao,p.peso";
        List<Armazem> a = new ArrayList();
        DALProduto dal = new DALProduto();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                a.add(new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), dal.get(rs.getInt("cod_produto")), rs.getInt("cod_produto"), rs.getString("categoria")));

            }
        } catch (SQLException e) {
        }
        return a;
    }

    public List<Armazem> getProdutosArmazemV(String filtro, int cod_cli) {
        String SQL = "select e.codigo,e.cod_produto,e.quantidade,cp.descricao as categoria from estoquearmazem e,produto p,categoria_produto cp "
                + " where e.cod_produto=p.codigo "
                + " and p.cod_categoria =cp.codigo"
                + " and p.cod_categoria = cp.codigo"
                + " and cp.descricao not ilike '%carvão bruto%' "
                + " and cp.descricao not ilike '%Embalagem%' "
                + " and cp.descricao not ilike '%acessórios%' "
                + "  order by cp.descricao,p.peso";

        if (cod_cli != 0) {
            SQL = "";
            SQL = "select e.codigo,e.cod_produto,e.quantidade,cp.descricao as categoria "
                    + "from estoquearmazem e,produto p,categoria_produto cp "
                    + " where e.cod_produto=p.codigo "
                    + " and p.cod_categoria =cp.codigo"
                    + " and p.cod_categoria = cp.codigo"
                    + " and cp.descricao not ilike '%carvão bruto%' "
                    + " and cp.descricao not ilike '%Embalagem%' "
                    + " and cp.descricao not ilike '%acessórios%' "
                    + "  order by cp.descricao,p.peso";
        }
        List<Armazem> a = new ArrayList();
        DALProduto dal = new DALProduto();
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            while (rs.next()) {
                a.add(new Armazem(rs.getInt("codigo"), rs.getInt("quantidade"), dal.get(rs.getInt("cod_produto")), rs.getInt("cod_produto"), rs.getString("categoria")));

            }
        } catch (SQLException e) {
        }
        return a;
    }

    public List<Producao> getProducao(String filtro, String ini, String fim) {
        String sql = "select * from producao";
        List<Producao> prod = new ArrayList();

        if (!ini.isEmpty()) {
            sql = "";
            sql = "select * from producao where emissao between '" + ini + "' and '" + fim + "'";
        }
        sql += " order by emissao desc";
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
     public List<Entrega> getEntregasCaminhao(String filtro, String ini, String fim) {
        String sql = "select codigo,emissao,rota from entrega";
        List<Entrega> entre = new ArrayList();
        DALProduto dalp = new DALProduto();

        if (!ini.isEmpty()) {
            sql = "";
            sql = "select * from producao where emissao between '" + ini + "' and '" + fim + "'";
        }
        sql += " order by emissao desc";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                entre.add(new Entrega(rs.getInt("codigo"),rs.getDate("emissao"),rs.getString("rota")));
            }
        } catch (SQLException e) {
        }

        return entre;
    }

    public List<Cliente> getClientes(String filtro) {
        String sql = "select codigo,nome from cliente";
        List<Cliente> cli = new ArrayList();

        if (!filtro.isEmpty()) {
            sql = sql + " where " + filtro + " and ativo = 'Ativo' and nome <> 'Venda Balcão'";
        } else {
            sql = sql + " where ativo = 'Ativo' and nome <> 'Venda Balcão' ";
        }

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                cli.add(new Cliente(rs.getInt("codigo"), rs.getString("nome")));
            }
        } catch (SQLException e) {
        }

        return cli;
    }

    public Cliente getClienteSaldo(Cliente cli) {
        String sql = "select * from cliente where codigo = " + cli.getCodigo() + " and ativo = 'Ativo'";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Cliente(rs.getInt("codigo"), rs.getString("nome"), rs.getDouble("saldo_devedor"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Venda> getVendasFiado(int codigo) {
        String sql = "select v.*,c.nome from venda v inner join cliente c on c.codigo = v.cod_cliente where v.fiado = 'S' and cod_cliente = " + codigo;
        List<Venda> ven = new ArrayList();

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                ven.add(new Venda(rs.getInt("codigo"), new Cliente(rs.getInt("cod_cliente")), rs.getDate("emissao"), rs.getDouble("total")));
            }
        } catch (SQLException e) {
        }

        return ven;
    }

    public List<ContasPagar> getDespesas(int cod_iddes) {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar  where cod_iddes=" + cod_iddes;
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque"), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

    public List<ContasPagar> listaDespesas() {
        List<ContasPagar> contp = new ArrayList();
        DALFuncionario dalf = new DALFuncionario();
        DALDespesas dald = new DALDespesas();
        DALFornecedor dalfor = new DALFornecedor();
        DALCondPagto dalcp = new DALCondPagto();
        String sql = "select * from contas_pagar  where cod_iddes <> 0 and cod_iddes is not null";
        sql += " order by codigo";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                contp.add(new ContasPagar(rs.getInt("codigo"), dald.get(rs.getInt("cod_despesa")), dalfor.get(rs.getInt("cod_fornecedor")),
                        rs.getString("parcela"), rs.getDouble("valor"), rs.getDouble("valor_pago"), rs.getDate("emissao"),
                        rs.getDate("data_pago"), rs.getDate("vencimento"), dalf.get(rs.getInt("cod_funcionario")),
                        dalcp.getCondPag(rs.getInt("cod_condicaopagamento")), rs.getInt("cod_iddes"), rs.getDouble("valor_pendente"), new EntradaProdutos(rs.getInt("cod_entrada")), rs.getDouble("valor_total"),
                        rs.getString("emissor_cheque"), rs.getInt("qtde_parcelas"), rs.getInt("dias_entreparc")));
            }
        } catch (SQLException ex) {

        }
        return contp;
    }

}
