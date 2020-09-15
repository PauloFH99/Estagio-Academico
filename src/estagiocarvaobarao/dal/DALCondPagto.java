package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.CondPagto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALCondPagto {

    public boolean alterar(CondPagto cond) {
        String sql = "update condpagto set descricao='#1',avistaaprazo = '#2' where codigo=" + cond.getCodigo();
        sql = sql.replace("#1", cond.getDescricao());
        sql = sql.replace("#2", Character.toString(cond.getAvistaaprazo()));

        return Banco.getCon().manipular(sql);
    }

    public CondPagto get(CondPagto cond) {
        String sql = "select * from condpagto where codigo = " + cond.getCodigo();

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new CondPagto(rs.getInt("codigo"), rs.getString("descricao"), rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public CondPagto get(int cod) {
        String sql = "select * from condpagto where codigo = " + cod;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new CondPagto(rs.getInt("codigo"), rs.getString("descricao"), rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public int getN(String desc) {
        String sql = "select codigo from condpagto where descricao = '" + desc + "'";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public List<CondPagto> get(String filtro) {
        List<CondPagto> list = new ArrayList();

        String sql = "select * from condpagto";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new CondPagto(rs.getInt("codigo"), rs.getString("descricao"),
                        rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public List<CondPagto> get() {
        List<CondPagto> list = new ArrayList();
        String sql = "select * from condpagto where flag_funcao='outros'";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new CondPagto(rs.getInt("codigo"), rs.getString("descricao"),
                        rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public List<CondPagto> getVendas(String tabela) {
        List<CondPagto> list = new ArrayList();
        String sql = "";
        sql = "select * from condpagto where flag_funcao =" + "'outros'" + " or flag_funcao =" + "'venda'";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new CondPagto(rs.getInt("codigo"), rs.getString("descricao"),
                        rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public List<CondPagto> getCondDes() {
        List<CondPagto> list = new ArrayList();
        String sql = "";
        sql = "select * from condpagto where flag_funcao =" + "'outros'" + " or flag_funcao =" + "'despesa'";
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            while (rs.next()) {
                list.add(new CondPagto(rs.getInt("codigo"), rs.getString("descricao"),
                        rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao")));
            }
        } catch (Exception e) {
            System.out.println("erro ao pesquisar");
        }
        return list;
    }

    public static CondPagto getCondPag(int cod) {
        String sql = "select * from condpagto where codigo = " + cod;

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new CondPagto(rs.getInt("codigo"), rs.getString("descricao"),
                        rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public CondPagto getC(String cod) {
        String sql = "select * from condpagto where descricao = '" + cod+"'";

        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new CondPagto(rs.getInt("codigo"), rs.getString("descricao"),
                        rs.getString("avistaaprazo").charAt(0), rs.getString("flag_funcao"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

  

    
}
