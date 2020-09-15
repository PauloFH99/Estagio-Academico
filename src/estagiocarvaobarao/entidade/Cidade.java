package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALCidade;
import java.util.List;

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

    @Override
    public String toString() {
        return cid_nome;
    }
    public List<Cidade>get(String filtro){
        DALCidade cid = new DALCidade();
        return cid.get(filtro);
    }
    public Cidade get(int filtro){
        DALCidade cid = new DALCidade();
        return cid.get(filtro);
    }
    public Cidade get(Cidade filtro){
        DALCidade cid = new DALCidade();
        return cid.get(filtro.getCid_cod());
    }
}
