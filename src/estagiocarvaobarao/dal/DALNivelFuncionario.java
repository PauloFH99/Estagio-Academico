package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.banco.Conexao;
import estagiocarvaobarao.entidade.NivelFuncionario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALNivelFuncionario {

    public boolean salvar(NivelFuncionario nf) {
        String sql = "insert into nivel_funcionario (descricao) values ('#A') ";
        sql = sql.replace("#A", nf.getDescricao());
        return Banco.getCon().manipular(sql);
    }

    public boolean alterar(NivelFuncionario nf) {
        String sql = "update nivel_funcionario set descricao='#A' where codigo =" + nf.getCodigo();
        sql = sql.replace("#A", nf.getDescricao());
        return Banco.getCon().manipular(sql);
    }

    public boolean apagar(int codigo) {
        return Banco.getCon().manipular("delete from nivel_funcionario where codigo=" + codigo);
    }

    public List<NivelFuncionario> get() {
        List<NivelFuncionario> nf = new ArrayList<>();

        String SQL = "select codigo,descricao from nivel_funcionario order by codigo";

        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(SQL)) {
                    while (rs.next()) {
                        nf.add(new NivelFuncionario(rs.getInt("codigo"),
                                rs.getString("descricao")));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNivelFuncionario.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return nf;
    }

    public List<NivelFuncionario> get(String filtro) {
        List<NivelFuncionario> nf = new ArrayList();

        String sql = "select * from nivel_funcionario";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        sql +=" order by descricao";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {

                nf.add(new NivelFuncionario(rs.getInt("codigo"),
                        rs.getString("descricao")));

            }
        } catch (SQLException ex) {

        }
        return nf;
    }

    public NivelFuncionario get(int cod) {
        NivelFuncionario nf = new NivelFuncionario();

        String SQL = "select codigo,descricao from nivel_funcionario where codigo=" + cod;

        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(SQL)) {
                    if (rs.next()) {
                        nf = new NivelFuncionario(rs.getInt("codigo"),
                                rs.getString("descricao"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNivelFuncionario.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return nf;
    }

    public NivelFuncionario getA(String filtro) {
        NivelFuncionario nf = new NivelFuncionario();

        String SQL = "select codigo,descricao from nivel_funcionario where descricao='" + filtro+"'";

        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(SQL)) {
                    if (rs.next()) {
                        nf = new NivelFuncionario(rs.getInt("codigo"),
                                rs.getString("descricao"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNivelFuncionario.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return nf;
    }
}
