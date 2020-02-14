package estagiocarvaobarao.banco;
public class Banco 
{
    private static Conexao con;
    public static boolean conectar()
    {
        con = new Conexao();
        return con.conectar("jdbc:postgresql://localhost/", "carvaobarao", "postgres", "postgres123"); 
    }
    public static Conexao getCon()
    {  
        return con;
    }    
}