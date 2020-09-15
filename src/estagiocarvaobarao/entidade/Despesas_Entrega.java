package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALProdutoVenda;
import java.util.List;

/**
 *
 
 */
public class Despesas_Entrega {

    private Despesas despesa;
    private Entrega entrega;
    private String descri;
    private double valor;

    public Despesas_Entrega() {
    }

    public Despesas_Entrega(Despesas despesa, Entrega entrega, String descri, double valor) {
        this.despesa = despesa;
        this.entrega = entrega;
        this.descri = descri;
        this.valor = valor;
    }
     public Despesas_Entrega(Despesas despesa, String descri, double valor) {
        this.despesa = despesa;
        this.descri = descri;
        this.valor = valor;
    }
    
    

    public Despesas getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesas despesa) {
        this.despesa = despesa;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    

   

    
}
