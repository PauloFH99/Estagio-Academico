/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Fornecedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class DALFornecedor {

    public boolean salvar(Fornecedor f) {
        String sql = "insert into fornecedor (nomefantasia,cnpj,ativo,logradouro,bairro,"
                + "numero,codcidade,cep,nomecontato,razaosocial,telefone,telefonecontato,email) "
                + "values('#A','#B',#C,'#D','#E','#F',#G,'#H','#I','#J','#L','#M','#N')";
        sql = sql.replace("#A", f.getNomefantasia());
        sql = sql.replace("#B", f.getCnpj());
        sql = sql.replace("#C", "" + f.isAtivo());
        sql = sql.replace("#D", f.getLogradouro());
        sql = sql.replace("#E", f.getBairro());
        sql = sql.replace("#F", f.getNumero());
        sql = sql.replace("#G", "" + f.getCidade().getCid_cod());
        sql = sql.replace("#H", f.getCep());
        sql = sql.replace("#I", f.getNomecontato());
        sql = sql.replace("#J", f.getRazaosocial());
        sql = sql.replace("#L", f.getTelefone());
        sql = sql.replace("#M", f.getTelefonecontato());
        sql = sql.replace("#N", f.getEmail());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(Fornecedor f) {
        String sql = "update  fornecedor set nomefantasia='#A',cnpj='#B',ativo=#C,logradouro='#D',bairro='#E',"
                + "numero='#F',codcidade=#G,cep='#H',nomecontato='#I',razaosocial='J',telefone='#L',"
                + "telefonecontato='#M',email='#N' where codigo =" + f.getCodigo();
        sql = sql.replace("#A", f.getNomefantasia());
        sql = sql.replace("#B", f.getCnpj());
        sql = sql.replace("#C", "" + f.isAtivo());
        sql = sql.replace("#D", f.getLogradouro());
        sql = sql.replace("#E", f.getBairro());
        sql = sql.replace("#F", f.getNumero());
        sql = sql.replace("#G", "" + f.getCidade().getCid_cod());
        sql = sql.replace("#H", f.getCep());
        sql = sql.replace("#I", f.getNomecontato());
        sql = sql.replace("#J", f.getRazaosocial());
        sql = sql.replace("#L", f.getTelefone());
        sql = sql.replace("#M", f.getTelefonecontato());
        sql = sql.replace("#N", f.getEmail());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int cod) {
        return Banco.getCon().manipular("delete from fornecedor where codigo =" + cod);
    }

    public List<Fornecedor> get(String filtro) {
        List<Fornecedor> forn = new ArrayList();
        DALCidade dalc = new DALCidade();
        DALNivelFuncionario daln = new DALNivelFuncionario();
        String sql = "select * from fornecedor";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                forn.add(new Fornecedor(rs.getInt("codigo"), rs.getString("nomefantasia"), rs.getString("cnpj"),
                        rs.getBoolean("ativo"), rs.getString("logradouro"), rs.getString("bairro"),
                        rs.getString("numero"), dalc.get(rs.getInt("codcidade")), rs.getString("cep"),
                        rs.getString("nomecontato"), rs.getString("razaosocial"), rs.getString("telefone"),
                        rs.getString("telefonecontato"), rs.getString("email")));

            }
        } catch (SQLException ex) {

        }
        return forn;
    }

}
