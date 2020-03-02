package estagiocarvaobarao.dal;



import estagiocarvaobarao.banco.Banco;
import estagiocarvaobarao.entidade.Cidade;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALCidade 
{
     public boolean gravar(Cidade c)
    {
        String sql="insert into cidade (cid_nome, est_cod) values ('#1',#2)";
        sql=sql.replace("#1",c.getCid_nome());
        sql=sql.replace("#2", ""+c.getEst_sgl());
        return Banco.getCon().manipular(sql);
    }
  
    public Cidade get(int cod)
    {
        Cidade c=null;
        DALEstado dale=new DALEstado();
        ResultSet rs = Banco.getCon().consultar("select * from cidade where cid_cod="+cod);
        try
        {
          if(rs.next())
          {
            c=new Cidade(rs.getInt("cid_cod"),rs.getInt("est_cod"),rs.getString("cid_nome"));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return c;        
    }
    public List<Cidade> get(String filtro)
    {
        List<Cidade> list=new ArrayList();
        DALEstado dale=new DALEstado();
        String sql="select * from cidade";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Cidade(rs.getInt("cid_cod"),rs.getInt("est_cod"),rs.getString("cid_nome")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return list;        
    }
    
    
}
