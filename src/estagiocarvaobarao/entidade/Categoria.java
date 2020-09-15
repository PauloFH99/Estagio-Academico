package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALCategoria;
import java.util.List;

public class Categoria
{
    private int codigo;
    private String descricao;

    public Categoria(int codigo, String descricao)
    {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Categoria(String descricao)
    {
        this(0,descricao);
    }

    public Categoria(int codigo) {
        this.codigo = codigo;
    }

    public Categoria() 
    {
        this(0,"");
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return  descricao ;
    }
    public boolean salvar(Categoria c)
    {
        DALCategoria dal =new DALCategoria();
        return dal.salvar(c);
    }
     public boolean alterar(Categoria c)
    {
        DALCategoria dal =new DALCategoria();
        return dal.alterar(c);
    }
      public boolean apagar(int cod)
    {
        DALCategoria dal =new DALCategoria();
        return dal.apagar(cod);
    }
     public List<Categoria> get(String filtro) {
         DALCategoria dal = new DALCategoria();
         return dal.get(filtro);
     }
     public Categoria get(int filtro) {
         DALCategoria dal = new DALCategoria();
         return dal.get(filtro);
     }
   
}