package estagiocarvaobarao.entidade;

public class NivelFuncionario {
        private int codigo;
        private String descricao;

    public NivelFuncionario() {
    }

    public NivelFuncionario(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public NivelFuncionario(int codigo) {
        this.codigo = codigo;
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
              
}
