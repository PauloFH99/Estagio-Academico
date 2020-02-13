package estagiocarvaobarao.dal;

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
    
    public static List<NivelFuncionario> get() {
        List<NivelFuncionario> nf = new ArrayList<>();
        
        String SQL = "select codigo,descricao from nivel_funcionario";
        
        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(SQL)) {
                    while(rs.next()){
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
    
     public  NivelFuncionario getNivel(int cod) {
        NivelFuncionario nf = new NivelFuncionario();
        
        String SQL = "select codigo,descricao from nivel_funcionario where codigo="+cod;
        
        try (Connection conn = Conexao.open()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery(SQL)) {
                    if(rs.next()){
                        nf=new NivelFuncionario(rs.getInt("codigo"),
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
