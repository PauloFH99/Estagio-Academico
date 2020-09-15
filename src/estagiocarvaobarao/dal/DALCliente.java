/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Cidade;
import estagiocarvaobarao.entidade.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALCliente {

    public boolean salvar(Cliente c) {
        String sql = "insert into cliente (nome,data,cpf,endereco,bairro,email,limite_fiado,"
                + "cep,cod_cidade,ativo,numero,saldo_devedor,telefone) values('#A','#B','#C','#D','#E','#F','#G','"
                + "#H',#I,'#J','#k','#L','#M')";
        sql = sql.replace("#A", c.getNome());
        sql = sql.replace("#B", "" + c.getData());
        sql = sql.replace("#C", c.getCpf());
        sql = sql.replace("#D", c.getEndereco());
        sql = sql.replace("#E", c.getBairro());
        sql = sql.replace("#F", c.getEmail());
        sql = sql.replace("#G", "" + c.getLimite_fiado());
        sql = sql.replace("#H", c.getCep());
        sql = sql.replace("#I", "" + c.getCidade().getCid_cod());
        sql = sql.replace("#J", c.getAtivo());
        sql = sql.replace("#k", c.getNumero());
        sql = sql.replace("#L", "" + c.getSaldo_devedor());
        sql = sql.replace("#M", c.getTelefone());
        return Banco.getCon().manipular(sql);

    }

    public boolean alterar(Cliente c) {
        String sql = "update cliente set  nome='#A',data='#B',cpf='#C',endereco='#D',bairro='#E',email='#F',limite_fiado='#G',"
                + "cep='#H',cod_cidade=#I,ativo='#J',numero='#k',saldo_devedor='#L',telefone='#M' where codigo=" + c.getCodigo();
        sql = sql.replace("#A", c.getNome());
        sql = sql.replace("#B", "" + c.getData());
        sql = sql.replace("#C", c.getCpf());
        sql = sql.replace("#D", c.getEndereco());
        sql = sql.replace("#E", c.getBairro());
        sql = sql.replace("#F", c.getEmail());
        sql = sql.replace("#G", "" + c.getLimite_fiado());
        sql = sql.replace("#H", c.getCep());
        sql = sql.replace("#I", "" + c.getCidade().getCid_cod());
        sql = sql.replace("#J", c.getAtivo());
        sql = sql.replace("#k", c.getNumero());
        sql = sql.replace("#L", "" + c.getSaldo_devedor());
        sql = sql.replace("#M", c.getTelefone());

        return Banco.getCon().manipular(sql);
    }

    public boolean alterarSaldo(Cliente c, Double valor, String tipo) {
        if (c.getSaldo_devedor() - valor < 0 && tipo.equals("-")) {
            valor = c.getSaldo_devedor();
        }

        String sql = "update cliente set saldo_devedor=saldo_devedor" + tipo + "" + valor + " where codigo = " + c.getCodigo();
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from cliente where codigo=" + codigo);
    }

    public Cliente get(int codigo) {

        String SQL = "select * from cliente where codigo = " + codigo;
        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                return new Cliente(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"), rs.getString("endereco"),
                        rs.getString("numero"), rs.getString("cep"), new Cidade(rs.getInt("cod_cidade")),
                        rs.getString("bairro"), rs.getDate("data"), rs.getString("ativo"),
                        rs.getString("email"), rs.getString("telefone"), rs.getDouble("saldo_devedor"), rs.getDouble("limite_fiado"));

            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Cliente> get(String filtro) {
        List<Cliente> cli = new ArrayList();
        DALCategoria dalc = new DALCategoria();
        String sql = "select * from cliente";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql += " order by nome";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                cli.add(new Cliente(rs.getInt("codigo"), rs.getString("nome"), rs.getString("cpf"), rs.getString("endereco"),
                        rs.getString("numero"), rs.getString("cep"), new Cidade(rs.getInt("cod_cidade")),
                        rs.getString("bairro"), rs.getDate("data"), rs.getString("ativo"),
                        rs.getString("email"), rs.getString("telefone"), rs.getDouble("saldo_devedor"), rs.getDouble("limite_fiado")));
            }

        } catch (SQLException ex) {

        }
        return cli;
    }

    public boolean verificaSaldo(int codcli, double total) {
        String SQL = "select c.limite_fiado,c.saldo_devedor from cliente c where codigo = " + codcli;

        ResultSet rs = Banco.getCon().consultar(SQL);
        try {
            if (rs.next()) {
                Cliente cli = new Cliente(rs.getDouble("saldo_devedor"), rs.getDouble("limite_fiado"));
                Double divida = cli.getLimite_fiado() - cli.getSaldo_devedor();
                if (total <= divida) {
                    return true;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }
}
