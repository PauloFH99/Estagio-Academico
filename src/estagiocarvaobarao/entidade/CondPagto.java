package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALCondPagto;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class CondPagto {

    private int codigo;
    private String descricao;
    private char avistaaprazo;
    private String flag_funcao;

    public CondPagto() {
    }

    public CondPagto(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public CondPagto(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public CondPagto(int codigo, String descricao, char avistaaprazo) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.avistaaprazo = avistaaprazo;

    }

    public CondPagto(int codigo, String descricao, char avistaaprazo, String flag_funcao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.avistaaprazo = avistaaprazo;
        this.flag_funcao = flag_funcao;
    }

    public String getFlag_funcao() {
        return flag_funcao;
    }

    public void setFlag_funcao(String flag_funcao) {
        this.flag_funcao = flag_funcao;
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

    public char getAvistaaprazo() {
        return avistaaprazo;
    }

    public void setAvistaaprazo(char avistaaprazo) {
        this.avistaaprazo = avistaaprazo;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public boolean alterar(CondPagto condpagto) {
        DALCondPagto dal = new DALCondPagto();
        return dal.alterar(condpagto);
    }

    public List<CondPagto> get(String filtro) {
        DALCondPagto dal = new DALCondPagto();
        return dal.get(filtro);
    }

    public List<CondPagto> getVendas(String tabela) {
        DALCondPagto dal = new DALCondPagto();
        return dal.getVendas(tabela);
    }

    public List<CondPagto> getCondDes() {
        DALCondPagto dal = new DALCondPagto();
        return dal.getCondDes();
    }

    public CondPagto get(int cod) {
        DALCondPagto dal = new DALCondPagto();
        return dal.get(cod);
    }
     public CondPagto getC(String cod) {
        DALCondPagto dal = new DALCondPagto();
        return dal.getC(cod);
    }

    public List<CondPagto> get() {
        DALCondPagto dal = new DALCondPagto();
        return dal.get();
    }

 

}
