
package estagiocarvaobarao.dal;



import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Estado;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class DALEstado {
    
     public boolean gravar(Estado e)
    {
        String sql="insert into estado (est_codigo,est_nome, est_sigla) values (#0,'#1','#2')";
         sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("estado","est_codigo")+1));
        sql=sql.replace("#1",e.getNome());
        sql=sql.replace("#2",e.getSigla());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Estado e)
    {
        String sql="update estado set est_nome='#1', est_sigla='#2' where est_codigo=#3";
        sql=sql.replace("#1",e.getNome());
        sql=sql.replace("#2",e.getSigla());
        sql=sql.replace("#3", ""+e.getCod());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Estado e)
    {
        return Banco.getCon().manipular("delete from estado where est_codigo="+e.getCod());
    }
    
    
    
    public Estado get(int cod)
    {
        Estado e=null;
        ResultSet rs = Banco.getCon().consultar("select * from estado where est_codigo="+cod);
        try
        {
          if(rs.next())
          {
            e=new Estado(rs.getInt("est_codigo"),rs.getString("est_nome"),
                    rs.getString("est_sigla"));
          }    
        }catch(Exception ex){System.out.println("erro ao pesquisar");}
        return e;        
    }
    public List<Estado> get(String filtro)
    {
       List<Estado> list=new ArrayList();
     
        String sql="select * from estado";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Estado(rs.getInt("est_codigo"),rs.getString("est_nome"),
                  rs.getString("est_sigla")   ));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return list;        
    }
    
}
