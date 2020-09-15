/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALContasPagar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Paulo
 */
public class ContasPagar {

    private int codigo;
    private Despesas despesas;
    private Fornecedor fornecedor;
    private String parcela;
    private double valor;
    private double valor_pago;
    private Date emissao;
    private Date data_pago;
    private Date vencimento;
    private Funcionario funcionario;
    private CondPagto condicaopagamento;
    private int cod_iddes;
    private double valor_pendente;
    private EntradaProdutos entradaprodutos;
    private double valor_total;
    private String emissor_cheque;
    private int qtde_parcela;
    private int dias_entre;

    public ContasPagar(int codigo, Despesas despesas, Fornecedor fornecedor, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondPagto condicaopagamento, int cod_iddes, double valor_pendente, EntradaProdutos entradaprodutos, double valor_total, String emissor_cheque) {
        this.codigo = codigo;
        this.despesas = despesas;
        this.fornecedor = fornecedor;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.cod_iddes = cod_iddes;
        this.valor_pendente = valor_pendente;
        this.entradaprodutos = entradaprodutos;
        this.valor_total = valor_total;
        this.emissor_cheque = emissor_cheque;
    }

    public ContasPagar(int codigo, Despesas despesas, Fornecedor fornecedor, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondPagto condicaopagamento, int cod_iddes, double valor_pendente, EntradaProdutos entradaprodutos, double valor_total, String emissor_cheque, int qtde_parcelas, int dias_entreparc) {
        this.codigo = codigo;
        this.despesas = despesas;
        this.fornecedor = fornecedor;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.cod_iddes = cod_iddes;
        this.valor_pendente = valor_pendente;
        this.entradaprodutos = entradaprodutos;
        this.valor_total = valor_total;
        this.emissor_cheque = emissor_cheque;
        this.qtde_parcela = qtde_parcelas;
        this.dias_entre = dias_entreparc;
    }

    public ContasPagar(int codigo, CondPagto condicaopagamento, EntradaProdutos entradaprodutos, String parcela, double valor, double valor_pago, Date emissao,
            Date data_pago, Date vencimento, double valor_total, double valor_pendente) {
        this.codigo = codigo;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.valor_pendente = valor_pendente;
        this.entradaprodutos = entradaprodutos;
        this.valor_total = valor_total;
        this.condicaopagamento = condicaopagamento;

    }

    public ContasPagar(int codigo, CondPagto condicaopagamento, EntradaProdutos entradaprodutos, String parcela, double valor, double valor_pago, Date emissao,
            Date data_pago, Date vencimento, double valor_total, double valor_pendente, int cod_iddes) {
        this.codigo = codigo;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.valor_pendente = valor_pendente;
        this.entradaprodutos = entradaprodutos;
        this.valor_total = valor_total;
        this.condicaopagamento = condicaopagamento;
        this.cod_iddes = cod_iddes;
    }

    public ContasPagar(int codigo, EntradaProdutos entrada_produtos, String parcela, Double valor, Double valor_pago, Date emissao, Date data_pago, Date vencimento, Double valor_total) {
        this.codigo = codigo;
        this.entradaprodutos = entrada_produtos;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.valor_total = valor_total;
    }

    public ContasPagar(int codigo, Funcionario funcionario, Despesas despesas, CondPagto condicaopagamento, Date emissao, Date vencimento, double valor_total) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.vencimento = vencimento;
        this.valor_total = valor_total;
        this.despesas = despesas;
    }

    public ContasPagar(Date emissao, Funcionario funcionario, Despesas despesas, double valor, CondPagto condicaopagamento) {
        this.emissao = emissao;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.vencimento = emissao;
         this.valor = valor;
        this.valor_total = valor;
        this.valor_pago = valor;
        this.data_pago = emissao;
        this.despesas = despesas;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public int getCod_iddes() {
        return cod_iddes;
    }

    public void setCod_iddes(int cod_iddes) {
        this.cod_iddes = cod_iddes;
    }

    public ContasPagar(int codigo, Despesas despesas, Fornecedor fornecedor, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondPagto condicaopagamento, int cod_iddes, double valor_pendente, EntradaProdutos entradaprodutos, double valor_total) {
        this.codigo = codigo;
        this.despesas = despesas;
        this.fornecedor = fornecedor;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.cod_iddes = cod_iddes;
        this.valor_pendente = valor_pendente;
        this.entradaprodutos = entradaprodutos;
        this.valor_total = valor_total;
    }

    public ContasPagar(int codigo, Despesas despesas, Fornecedor fornecedor, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondPagto condicaopagamento, int cod_iddes) {
        this.codigo = codigo;
        this.despesas = despesas;
        this.fornecedor = fornecedor;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.cod_iddes = cod_iddes;
    }

    public ContasPagar() {
    }

    public ContasPagar(int codigo, String parcela, double valor, Date emissao, Date vencimento) {
        this.codigo = codigo;
        this.parcela = parcela;
        this.valor = valor;
        this.emissao = emissao;
        this.vencimento = vencimento;
    }

    public ContasPagar(int codigo, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, double valor_total) {
        this.codigo = codigo;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.valor_total = valor_total;
    }

    public ContasPagar(int codigo, Despesas despesas, Fornecedor fornecedor, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondPagto condicaopagamento, int cod_iddes, double valor_pendente, double valor_total) {
        this.codigo = codigo;
        this.despesas = despesas;
        this.fornecedor = fornecedor;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
        this.cod_iddes = cod_iddes;
        this.valor_pendente = valor_pendente;
        this.valor_total = valor_total;
    }

    public ContasPagar(Despesas despesas, Fornecedor fornecedor, String parcela, double valor, double valor_pago, Date emissao, Date data_pago, Date vencimento, Funcionario funcionario, CondPagto condicaopagamento) {
        this.despesas = despesas;
        this.fornecedor = fornecedor;
        this.parcela = parcela;
        this.valor = valor;
        this.valor_pago = valor_pago;
        this.emissao = emissao;
        this.data_pago = data_pago;
        this.vencimento = vencimento;
        this.funcionario = funcionario;
        this.condicaopagamento = condicaopagamento;
    }

    public CondPagto getCondicaopagamento() {
        return condicaopagamento;
    }

    public void setCondicaopagamento(CondPagto condicaopagamento) {
        this.condicaopagamento = condicaopagamento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Despesas getDespesas() {
        return despesas;
    }

    public void setDespesas(Despesas despesas) {
        this.despesas = despesas;
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

    public String getEmissao() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(emissao);

    }

    public Date getEmissaoDate() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getData_pagoDate() {
        return data_pago;
    }

    public String getData_pago() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (data_pago != null) {
            return formato.format(data_pago);
        } else {
            return "";
        }
    }

    public void setData_pago(Date data_pago) {
        this.data_pago = data_pago;
    }

    public String getVencimento() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(vencimento);
    }

    public Date getVencimentoDate() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public double getValor_pendente() {
        return valor_pendente;
    }

    public String getEmissor_cheque() {
        return emissor_cheque;
    }

    public void setEmissor_cheque(String emissor_cheque) {
        this.emissor_cheque = emissor_cheque;
    }

    public void setValor_pendente(double valor_pendente) {
        this.valor_pendente = valor_pendente;
    }

    public EntradaProdutos getEntradaprodutos() {
        return entradaprodutos;
    }

    public void setEntradaprodutos(EntradaProdutos entradaprodutos) {
        this.entradaprodutos = entradaprodutos;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getQtde_parcela() {
        return qtde_parcela;
    }

    public void setQtde_parcela(int qtde_parcela) {
        this.qtde_parcela = qtde_parcela;
    }

    public int getDias_entre() {
        return dias_entre;
    }

    public void setDias_entre(int dias_entre) {
        this.dias_entre = dias_entre;
    }

    public boolean salvar(ContasPagar c, List<ContasPagar> lista, Funcionario funcionario, String emissor_cheque, int qtde_parcelas) {
        DALContasPagar conta = new DALContasPagar();
        return conta.salvar(c, lista, funcionario, emissor_cheque, qtde_parcelas);
    }

    public boolean salvar(ContasPagar c, Fornecedor fornecedor, CondPagto condpagto, Funcionario funcionario, int codEntrada, String emissor) {
        DALContasPagar conta = new DALContasPagar();
        return conta.salvar(c, fornecedor, condpagto, funcionario, codEntrada, emissor);
    }

    public boolean alterar(ContasPagar c, Fornecedor fornecedor, CondPagto condpagto, Funcionario funcionario) {
        DALContasPagar conta = new DALContasPagar();
        return conta.alterar(c, fornecedor, condpagto, funcionario);
    }

    public boolean compensarCheque(ContasPagar c) {
        DALContasPagar conta = new DALContasPagar();
        return conta.compensarCheque(c);
    }

    public boolean apagar(ContasPagar cod, List<ContasPagar> lista) {
        DALContasPagar conta = new DALContasPagar();
        return conta.apagar(cod, lista);
    }

    public boolean apagar(ContasPagar cod) {
        DALContasPagar conta = new DALContasPagar();
        return conta.apagar(cod);
    }

    public boolean apagarCompra(int cod) {
        DALContasPagar conta = new DALContasPagar();
        return conta.apagarCompra(cod);
    }

    public List<ContasPagar> get(String filtro) {
        DALContasPagar conta = new DALContasPagar();
        return conta.get(filtro);
    }

    public List<ContasPagar> getConta(int coddes) {
        DALContasPagar conta = new DALContasPagar();
        return conta.getConta(coddes);
    }

    public List<ContasPagar> getCheque(int cod) {
        DALContasPagar conta = new DALContasPagar();
        return conta.getCheque(cod);
    }

    public ContasPagar get(int cod) {
        DALContasPagar conta = new DALContasPagar();
        return conta.get(cod);
    }

    public ContasPagar getCompra(int cod) {
        DALContasPagar conta = new DALContasPagar();
        return conta.getCompra(cod);
    }

    public boolean estornar(ContasPagar cp) {
        DALContasPagar conta = new DALContasPagar();
        return conta.estornar(cp);
    }

    public boolean verificaPagamento(ContasPagar contaspagar) {
        DALContasPagar conta = new DALContasPagar();
        List<ContasPagar> aux = new ArrayList();
        aux = getConta(contaspagar.getCod_iddes());
        return conta.verificaPagamento(aux);

    }

    public boolean alterar(List<ContasPagar> ReceberLD, Despesas despesa, Funcionario funcionario, String emissor_cheque, int qtde_parcelas) {
        DALContasPagar conta = new DALContasPagar();
        return conta.alterar(ReceberLD, despesa, funcionario, emissor_cheque, qtde_parcelas);
    }

    public boolean verificaExiste(int cod_entrada, String parcela) {
        DALContasPagar conta = new DALContasPagar();
        return conta.verificaExiste(cod_entrada, parcela);
    }

    public boolean apagarDespesa(int cod_iddes) {
        DALContasPagar conta = new DALContasPagar();
        return conta.apagarDespesa(cod_iddes);
    }

    public boolean gravarEntrega(ContasPagar cp,int codEntrada) {
       DALContasPagar conta = new DALContasPagar();
        return conta.gravarEntrega(cp,codEntrada);
    }

}
