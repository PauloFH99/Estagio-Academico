
package estagiocarvaobarao.entidade;

/**
 *
 * @author Aluno
 */
public class Estado {
    private int cod;
    private String nome, sigla;

    public Estado(int cod) {
        this.cod = cod;
    }

   
    public Estado(int cod, String nome, String sigla) {
        this.cod = cod;
        this.sigla = sigla;
        this.nome = nome;
    }

    public Estado(String nome, String sigla) {
        cod=0;
        this.sigla = sigla;
        this.nome = nome;
    }

    public Estado() {
        this(0,"","");
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
}
