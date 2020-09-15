package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALContasReceber;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class ContasReceber {

    private int codigo;
    private Venda venda;
    private String parcela;
    private double valor;
    private double valor_pago;
    private Date emissao;
    private Date data_pago;
    private Date vencimento;
    private Funcionario funcionario;
    private Cliente cliente;
    private double valor_pendente;
    private double valor_total;
    private CondPagto condicaopagamento;
    private String emissorcheque;
    private String num_boleto;

    public ContasReceber(int codigo, Venda venda, String parcela, double valor, double valor_pago, Date emissao,
            Date data_pago, Date vencimento, double valor_total, double valor_pendente, String num_boleto) {
        this.codigo = codigo;
        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.valor_total = valor_total;
        this.valor_pendente = valor_pendente;
        this.num_boleto = num_boleto;
    }

    public ContasReceber(int codigo, Venda venda, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente, double valor_pendente) {
        this.codigo = codigo;
        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.valor_pendente = valor_pendente;
    }

    public ContasReceber() {
    }

    public ContasReceber(int codigo, Venda venda, String parcela, double valor, Date emissao, Date vencimento) {
        this.codigo = codigo;
        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.emissao = emissao;
        this.vencimento = vencimento;
    }

    public ContasReceber(int codigo, Venda venda, String parcela, double valor, double valor_pago,
            Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente,
            double valor_pendente, String emissorcheque, String num_boleto) {
        this.codigo = codigo;
        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.valor_pendente = valor_pendente;
        this.emissorcheque = emissorcheque;
        this.num_boleto = num_boleto;
    }

    public ContasReceber(int codigo, Venda venda, String parcela, double valor, double valor_pago,
            Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, Cliente cliente) {
        this.codigo = codigo;
        this.venda = venda;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    public ContasReceber(String parcela, double valor, double valor_pago,
            Date emissao, Date data_pago, Date vencimento,
            CondPagto condicaopagamento, String emissorcheque, String num_boleto) {
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.condicaopagamento = condicaopagamento;
        this.emissorcheque = emissorcheque;
        this.num_boleto = num_boleto;
    }

    public String getNum_boleto() {
        return num_boleto;
    }

    public void setNum_boleto(String num_boleto) {
        this.num_boleto = num_boleto;
    }

    public String getEmissorcheque() {
        return emissorcheque;
    }

    public void setEmissorcheque(String emissorcheque) {
        this.emissorcheque = emissorcheque;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Venda getVenda() {
        return venda;
    }

    public CondPagto getCondicaopagamento() {
        return condicaopagamento;
    }

    public void setCondicaopagamento(CondPagto condicaopagamento) {
        this.condicaopagamento = condicaopagamento;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(double valor_pago) {
        this.valor_pago = valor_pago;
    }

    public String getEmissao() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(emissao);
    }

    public Date getEmissaoDate() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getData_pago() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (data_pago == null) {
            return "";
        } else {
            return formato.format(data_pago);
        }
    }

    public Date getData_pagoDate() {
        return data_pago;
    }

    public void setData_pago(Date data_pago) {
        this.data_pago = data_pago;
    }

    public String getVencimento() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (vencimento == null) {
            return "";
        } else {
            return formato.format(vencimento);
        }
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getVencimentoDate() {
        return vencimento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getValor_pendente() {
        return valor_pendente;
    }

    public void setValor_pendente(double valor_pendente) {
        this.valor_pendente = valor_pendente;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public boolean alterar(ContasReceber cont) {
        DALContasReceber dal = new DALContasReceber();
        return dal.alterar(cont);
    }

    public boolean apagar(int cod) {
        DALContasReceber dal = new DALContasReceber();
        return dal.apagar(cod);
    }

    public ContasReceber get(int cod) {
        DALContasReceber dal = new DALContasReceber();
        return dal.get(cod);
    }

    public List<ContasReceber> getAll(int cod) {
        DALContasReceber dal = new DALContasReceber();
        return dal.getAll(cod);
    }

    public List<ContasReceber> getFiado(int cod) {
        DALContasReceber dal = new DALContasReceber();
        return dal.getFiado(cod);
    }

    public boolean apagarVenda(int cod) {
        DALContasReceber conta = new DALContasReceber();
        return conta.apagarVenda(cod);
    }

    public boolean salvar(ContasReceber receber, Cliente cliente, CondPagto condpagto, Funcionario funcionario, int codVenda, String emissor) {
        DALContasReceber conta = new DALContasReceber();
        return conta.salvar(receber, cliente, condpagto, funcionario, codVenda, emissor);
    }

    public ContasReceber getVendaCond(int codigo) {
        DALContasReceber conta = new DALContasReceber();
        return conta.getVendaCond(codigo);
    }

    public boolean estornar(ContasReceber cr) {
        DALContasReceber conta = new DALContasReceber();
        return conta.estornar(cr);
    }

}
