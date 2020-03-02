package estagiocarvaobarao.dal;

import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Cidade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        
        return null;
    }

   
    public Cidade getCidade(Cidade cid) {
        String sql = "select cid_cod,cid_nome from cidade where cid_cod = " + cid.getCid_cod();
        ResultSet rs = Banco.getCon().consultar(sql);
        try {
            if (rs.next()) {
                return new Cidade(rs.getInt("cid_cod"), rs.getString("cid_nome"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

   
}
