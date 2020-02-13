package estagiocarvaobarao.entidade;

public class Cidade {
    private int cid_cod;
    private int est_sgl;
    private String cid_nome;

    public Cidade() {
    }

    public Cidade(int cid_cod, String cid_nome) {
        this.cid_cod = cid_cod;
        this.cid_nome = cid_nome;
    }

    public Cidade(int cid_cod) {
        this.cid_cod = cid_cod;
    }
      
    public Cidade(int cid_cod, int est_sgl, String cid_nome) {
        this.cid_cod = cid_cod;
        this.est_sgl = est_sgl;
        this.cid_nome = cid_nome;
    }

    public int getCid_cod() {
        return cid_cod;
    }

    public void setCid_cod(int cid_cod) {
        this.cid_cod = cid_cod;
    }

    public int getEst_sgl() {
        return est_sgl;
    }

    public void setEst_sgl(int est_sgl) {
        this.est_sgl = est_sgl;
    }

    public String getCid_nome() {
        return cid_nome;
    }

    public void setCid_nome(String cid_nome) {
        this.cid_nome = cid_nome;
    }
}
