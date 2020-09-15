package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALVenda;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.omg.CORBA.BAD_CONTEXT;

/**
 *
 * @author Felipe
 */
public class Venda {

    private int codigo;
    private Cliente Cliente;
    private Funcionario Funcionario;
    private CondPagto condPagto;
    private String nome;
    private Date emissao;
    private Double total;
    private String fiado;
    private int qtde_parcelas;
    private int diasentreparc;
    private String estoque;
    private String situacao;
    private Boolean situ_entrega;
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    public Venda() {
    }

    public Venda(int codigo) {
        this.codigo = codigo;
    }

    public Venda(int codigo, Cliente Cliente, Funcionario Funcionario, Date emissao, Double total) {
        this.codigo = codigo;
        this.Cliente = Cliente;
        this.Funcionario = Funcionario;
        this.emissao = emissao;
        this.total = total;
    }

    public Venda(int codigo, Cliente Cliente, Date emissao, Double total) {
        this.codigo = codigo;
        this.Cliente = Cliente;
        this.emissao = emissao;
        this.total = total;
    }

    public Venda(int codigo, Cliente Cliente, Funcionario Funcionario) {
        this.codigo = codigo;
        this.Cliente = Cliente;
        this.Funcionario = Funcionario;
    }

    public Venda(int codigo, String nome, Date emissao, Double total) {
        this.codigo = codigo;
        this.nome = nome;
        this.emissao = emissao;
        this.total = total;
    }

    public Venda(int codigo, Cliente cliente, Funcionario funcionario, CondPagto condpagto, String nome, Date emissao, Double total, int qtde_parcelas, int diasentreparc) {
        this.codigo = codigo;
        this.Cliente = cliente;
        this.Funcionario = funcionario;
        this.condPagto = condpagto;
        this.nome = nome;
        this.emissao = emissao;
        this.total = total;
        this.qtde_parcelas = qtde_parcelas;
        this.diasentreparc = diasentreparc;
    }

    public Venda(int codigo, Cliente cliente, Funcionario funcionario, CondPagto condpagto, String nome,
            Date emissao, Double total, int qtde_parcelas, int diasentreparc, String situacao) {
        this.codigo = codigo;
        this.Cliente = cliente;
        this.Funcionario = funcionario;
        this.condPagto = condpagto;
        this.nome = nome;
        this.emissao = emissao;
        this.total = total;
        this.qtde_parcelas = qtde_parcelas;
        this.diasentreparc = diasentreparc;
        this.situacao = situacao;
    }

    public Venda(int codigo, Cliente cliente, Funcionario funcionario, CondPagto condpagto,
            String nome, Date emissao, Double total, int qtde_parcelas, int diasentreparc, String estoque, String situacao) {
        this.codigo = codigo;
        this.Cliente = cliente;
        this.Funcionario = funcionario;
        this.condPagto = condpagto;
        this.nome = nome;
        this.emissao = emissao;
        this.total = total;
        this.qtde_parcelas = qtde_parcelas;
        this.diasentreparc = diasentreparc;
        this.estoque = estoque;
        this.situacao = situacao;
    }

    public Venda(int codigo, Date emissao, double total, Cliente cliente) {
        this.codigo = codigo;
        this.emissao = emissao;
        this.total = total;
        this.Cliente = cliente;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public CondPagto getCondPagto() {
        return condPagto;
    }

    public void setCondPagto(CondPagto condPagto) {
        this.condPagto = condPagto;
    }

    public String getFiado() {
        return fiado;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setFiado(String fiado) {
        this.fiado = fiado;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public Boolean getSitu_entrega() {
        return situ_entrega;
    }

    public void setSitu_entrega(Boolean situ_entrega) {
        this.situ_entrega = situ_entrega;
    }

    public void setCliente(Cliente Cliente) {
        this.Cliente = Cliente;
    }

    public Funcionario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(Funcionario Funcionario) {
        this.Funcionario = Funcionario;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getQtde_parcelas() {
        return qtde_parcelas;
    }

    public void setQtde_parcelas(int qtde_parcelas) {
        this.qtde_parcelas = qtde_parcelas;
    }

    public int getDiasentreparc() {
        return diasentreparc;
    }

    public void setDiasentreparc(int diasentreparc) {
        this.diasentreparc = diasentreparc;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public boolean gravar(Venda venda, Cliente cliente, Funcionario funcionario, List<Produto_Venda> Produtos,
            List<ContasReceber> Receber, CondPagto condpagto, Double total, LocalDate value,
            boolean fiado, int qtde, int diasentre, String emissorcheque, String estoque, String situacao) {
        DALVenda dal = new DALVenda();
        return dal.gravar(venda, cliente, funcionario, Produtos, Receber, condpagto, total,
                value, fiado, qtde, diasentre, emissorcheque, estoque, situacao);
    }

    public boolean apagar(int codigo) {
        DALVenda dal = new DALVenda();
        return dal.apagar(codigo);
    }

    public void atualizarSaldoDevedor(int parseInt, Double total) {
        DALVenda dal = new DALVenda();
        dal.atualizarSaldoDevedor(parseInt, total);
    }

    public void atualizarSaldoDevedorAlteracao(int parseInt, Double total) {
        DALVenda dal = new DALVenda();
        dal.atualizarSaldoDevedorAlteracao(parseInt, total);
    }

    public boolean verificarParcelaPaga(int codigo) {
        DALVenda dal = new DALVenda();
        return dal.verificarParcelaPaga(codigo);
    }

    public boolean alterar(int cod, Cliente cliente, Funcionario funcionario, List<Produto_Venda> Produtos, List<ContasReceber> Receber, CondPagto condPagto, Double total, LocalDate value,
            boolean fiado, int qtdeparc, int diasentre, String emissor, String estoque, String situacao) {
        DALVenda dal = new DALVenda();
        return dal.alterar(cod, cliente, funcionario, Produtos, Receber, condPagto, total,
                value, fiado, qtdeparc, diasentre, emissor, estoque, situacao);
    }

    public List<Venda> getFiado(int codigo) {
        DALVenda dal = new DALVenda();
        return dal.getFiado(codigo);
    }

    public List<Venda> getPedidoCod(int codcli) {
        DALVenda dal = new DALVenda();
        return dal.getPedidoCod(codcli);
    }

    public List<Venda> getPedidoEmissao(String emissao) {
        DALVenda dal = new DALVenda();
        return dal.getPedidoEmissao(emissao);
    }

    public List<Venda> getPedidoSitu(String situacao) {
        DALVenda dal = new DALVenda();
        return dal.getPedidoSitu(situacao);
    }

    public List<Venda> getPedidoAll(int codcli, String emissao, String situacao) {
        DALVenda dal = new DALVenda();
        return dal.getPedidoAll(codcli, emissao, situacao);
    }

    public boolean alterarSituacao(int codvenda, String situacao) {
        DALVenda dal = new DALVenda();
        return dal.alterarSituacao(codvenda, situacao);
    }

    public boolean alterarSituacaoItems(List<Venda> itemsV) {
        DALVenda dal = new DALVenda();
        return dal.alterarSituacaoItems(itemsV);
    }

    public List<Venda> getPedidoSituCli(int codcli, String situacao) {
        DALVenda dal = new DALVenda();
        return dal.getPedidoSituCli(codcli, situacao);
    }

    public BooleanProperty choosedProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
