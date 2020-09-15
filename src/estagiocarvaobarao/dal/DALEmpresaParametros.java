/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.EmpresaParametros;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo
 */
public class DALEmpresaParametros {

    public boolean salvar(EmpresaParametros e) {
        String sql = "insert into empresa_parametros (fantasia,razao_social,endereco,"
                + "cod_cidade,telefone,cnpj,email,cep,site,ie,numero,bairro) "
                + "values('#A','#B','#C',#D,'#E','#F','#G','#H','#I','#J','#L','#M')";
        sql = sql.replace("#A", e.getFantasia());
        sql = sql.replace("#B", e.getRazao_social());
        sql = sql.replace("#C", e.getEndereco());
        sql = sql.replace("#D", "" + e.getCidade().getCid_cod());
        sql = sql.replace("#E", e.getTelefone());
        sql = sql.replace("#F", e.getCnpj());
        sql = sql.replace("#G", e.getEmail());
        sql = sql.replace("#H", e.getCep());
        sql = sql.replace("#I", e.getSite());
        sql = sql.replace("#J", e.getIe());
        sql = sql.replace("#L", e.getNumero());
        sql = sql.replace("#M", e.getBairro());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(EmpresaParametros e) {
        String sql = "update empresa_parametros set fantasia='#A',razao_social='#B',endereco='#C',"
                + "cod_cidade=#D,telefone='#E',cnpj='#F',"
                + "email='#I',cep='#J',site='#L',ie='#M',numero='#N' where codigo=" + e.getCodigo();
        sql = sql.replace("#A", e.getFantasia());
        sql = sql.replace("#B", e.getRazao_social());
        sql = sql.replace("#C", e.getEndereco());
        sql = sql.replace("#D", "" + e.getCidade().getCid_cod());
        sql = sql.replace("#E", e.getTelefone());
        sql = sql.replace("#F", e.getCnpj());
        sql = sql.replace("#I", e.getEmail());
        sql = sql.replace("#J", e.getCep());
        sql = sql.replace("#L", e.getSite());
        sql = sql.replace("#M", e.getIe());
        sql = sql.replace("#N", "" + e.getNumero());

        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from empresa_parametros where codigo = " + codigo);
    }

    public List<EmpresaParametros> get(int filtro) {
        List<EmpresaParametros> empre = new ArrayList();
        DALCidade dalc = new DALCidade();
        String sql = "select * from empresa_parametros ";
        if (filtro > 0) {
            sql += " where cnpj=" + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                empre.add(new EmpresaParametros(rs.getInt("codigo"), rs.getString("fantasia"),
                        rs.getString("razao_social"), rs.getString("endereco"), rs.getString("numero"),
                        dalc.get(rs.getInt("cod_cidade")), rs.getString("telefone"), rs.getString("cnpj"),
                      rs.getString("site"),rs.getString("ie"), rs.getString("email"), rs.getString("cep"), rs.getString("bairro")));
            }
        } catch (SQLException ex) {

        }
        return empre;
    }

    public EmpresaParametros getE(int filtro) {
        EmpresaParametros empre = new EmpresaParametros();
        DALCidade dalc = new DALCidade();
        String sql = "select * from empresa_parametros ";
        if (filtro > 0) {
            sql += " where codigo=" + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                empre = new EmpresaParametros(rs.getInt("codigo"), rs.getString("fantasia"),
                        rs.getString("razao_social"), rs.getString("endereco"), rs.getString("numero"),
                        dalc.get(rs.getInt("cod_cidade")), rs.getString("telefone"), rs.getString("cnpj"),
                       rs.getString("site"),rs.getString("ie"), rs.getString("email"), rs.getString("cep"), rs.getString("bairro"));
            }
        } catch (SQLException ex) {

        }
        return empre;
    }

    public List<EmpresaParametros> get(String filtro) {
        List<EmpresaParametros> empre = new ArrayList();
        DALCidade dalc = new DALCidade();
        String sql = "select * from empresa_parametros";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql += " order by fantasia";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                empre.add(new EmpresaParametros(rs.getInt("codigo"), rs.getString("fantasia"),
                        rs.getString("razao_social"), rs.getString("endereco"), rs.getString("numero"),
                        dalc.get(rs.getInt("cod_cidade")), rs.getString("telefone"), rs.getString("cnpj"),
                        rs.getString("site"),rs.getString("ie"), rs.getString("email"), rs.getString("cep"), rs.getString("bairro")));
            }
        } catch (SQLException ex) {

        }
        return empre;
    }

    public boolean gravarFoto(EmpresaParametros e, FileInputStream foto, File arquivo) {

        try {
            PreparedStatement ps = Banco.getCon().getConnect(). prepareStatement("update empresa_parametros set logo = ? where codigo=?");
            ps.setBinaryStream(1, foto, arquivo.length());
            ps.setInt(2, e.getCodigo());
            ps.executeUpdate();
            ps.close();
            try {
                foto.close();
            } catch (IOException ex) {
                Logger.getLogger(DALEmpresaParametros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public InputStream getFoto(EmpresaParametros e) {
        InputStream is = null;
        try {
            PreparedStatement ps = Banco.getCon().getConnect().
                    prepareStatement("select logo from empresa_parametros where codigo=?");
            ps.setInt(1, e.getCodigo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byte[] bytes = rs.getBytes("logo");
                is = new ByteArrayInputStream(bytes);
            }
            ps.close();
        } catch (SQLException ex) {
            return null;
        }
        return is;
    }

}
