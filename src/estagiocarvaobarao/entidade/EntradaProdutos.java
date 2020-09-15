package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALEntrada_Produtos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class EntradaProdutos {

    private int codigo;
    private Fornecedor fornecedor;
    private Funcionario Funcionario;
    private CondPagto condPagto;
    private Date emissao;
    private Double total;
    private String fiado;
    private int qtde_parcelas;
    private int diasentreparc;

    public EntradaProdutos() {
    }

    public EntradaProdutos(int codigo) {
        this.codigo = codigo;
    }

    public EntradaProdutos(int codigo, Fornecedor fornecedor, Funcionario Funcionario, Date emissao, Double total) {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.Funcionario = Funcionario;
        this.emissao = emissao;
        this.total = total;
    }

    public EntradaProdutos(int codigo, Fornecedor fornecedor, Date emissao, Double total) {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.emissao = emissao;
        this.total = total;
    }

    public EntradaProdutos(int codigo, Fornecedor fornecedor, Funcionario Funcionario) {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.Funcionario = Funcionario;
    }

    public EntradaProdutos(int codigo, Date emissao, Double total) {
        this.codigo = codigo;

        this.emissao = emissao;
        this.total = total;
    }

    public EntradaProdutos(int codigo, Fornecedor fornecedor, Funcionario funcionario, CondPagto condpagto, Date emissao, Double total, int qtde_parcelas, int diasentreparc) {
        this.codigo = codigo;
        this.fornecedor = fornecedor;
        this.Funcionario = funcionario;
        this.condPagto = condpagto;

        this.emissao = emissao;
        this.total = total;
        this.qtde_parcelas = qtde_parcelas;
        this.diasentreparc = diasentreparc;
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

    public void setFiado(String fiado) {
        this.fiado = fiado;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    public boolean gravar(EntradaProdutos entrada_produtos, Fornecedor fornecedor, Funcionario funcionario, List<Produto_Entrada> Produtos, List<ContasPagar> Receber,
            CondPagto condpagto, Double total, LocalDate value, boolean fiado, int qtde, int diasentre, String emissor) {
        DALEntrada_Produtos dal = new DALEntrada_Produtos();
        return dal.gravar(entrada_produtos, fornecedor, funcionario, Produtos, Receber, condpagto, total, value, fiado, qtde, diasentre, emissor);
    }

    public boolean apagar(int codigo,List<Produto_Entrada> Produtos) {
        DALEntrada_Produtos dal = new DALEntrada_Produtos();
        return dal.apagar(codigo,Produtos);
    }

    public boolean verificarParcelaPaga(int codigo) {
        DALEntrada_Produtos dal = new DALEntrada_Produtos();
        return dal.verificarParcelaPaga(codigo);
    }

    public boolean alterar(int cod,Fornecedor fornecedor, Funcionario funcionario, List<Produto_Entrada> Produtos, List<ContasPagar> ReceberE, CondPagto condpagto, Double total, LocalDate value, boolean fiado, int qtdeparc, int diasentre, String emissor) {
        DALEntrada_Produtos dal = new DALEntrada_Produtos();
        return dal.alterar(cod,fornecedor, funcionario, Produtos, ReceberE, condpagto, total, value, fiado, qtdeparc, diasentre, emissor);
    }

}
