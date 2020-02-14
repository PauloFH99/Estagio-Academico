package estagiocarvaobarao.entidade;
public class Produto
{
    private int codigo;
    private int est_min;
    private int est_max;
    private String descricao;
    private double preco;
    private int estoque;
    private Categoria categoria;
    private boolean ativo;
    public Produto(int codigo, int est_min, int est_max, String descricao, double preco, int estoque,boolean ativo,Categoria categoria) {
        this.codigo = codigo;
        this.est_min = est_min;
        this.est_max = est_max;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
        this.categoria = categoria;
    }

    public Produto(int codigo) {
        this.codigo = codigo;
    }

    public Produto(int codigo, String descricao, double preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    

    public Produto(int est_min,int est_max,String descricao,double preco,int estoque,boolean ativo,Categoria categoria) 
    {
        this(0,est_min,est_max,descricao,preco,estoque,ativo,categoria);
    }
    
    public Produto() 
    {
        this(0,0,0,"",0,0,false,null);
    }   

    

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

   

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodico(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEst_min() {
        return est_min;
    }

    public void setEst_min(int est_min) {
        this.est_min = est_min;
    }

    public int getEst_max() {
        return est_max;
    }

    public void setEst_max(int est_max) {
        this.est_max = est_max;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    
    
    
}