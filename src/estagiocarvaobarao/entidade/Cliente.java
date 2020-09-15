package estagiocarvaobarao.entidade;

import estagiocarvaobarao.dal.DALCliente;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Cliente {

    private int codigo;
    private String nome;
    private String cpf;
    private String endereco;
    private String numero;
    private String cep;
    private Cidade cidade;
    private String bairro;
    private Date data;
    private String ativo;
    private String email;
    private String telefone;
    private Double saldo_devedor;
    private Double limite_fiado;

    public Cliente(int codigo, String nome, String cpf, String endereco, String numero, String cep, Cidade cidade, String bairro, Date data, String ativo, String email, String telefone, Double saldo_devedor, Double limite_fiado) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.data = data;
        this.ativo = ativo;
        this.email = email;
        this.telefone = telefone;
        this.saldo_devedor = saldo_devedor;
        this.limite_fiado = limite_fiado;
    }

    public Cliente(int codigo, String nome, double saldo_devedor) {
        this.codigo = codigo;
        this.nome = nome;
        this.saldo_devedor = saldo_devedor;
    }

    public Cliente(Double saldo_devedor, Double limite_fiado) {
        this.saldo_devedor = saldo_devedor;
        this.limite_fiado = limite_fiado;
    }
    

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente() {
    }

    public Cliente(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Cliente(String nome, String cpf, String endereco, String numero, String cep, Cidade cidade, String bairro, Date data, String ativo, String email, String telefone, Double saldo_devedor, Double limite_fiado) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.data = data;
        this.ativo = ativo;
        this.email = email;
        this.telefone = telefone;
        this.saldo_devedor = saldo_devedor;
        this.limite_fiado = limite_fiado;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo_devedor() {
        return saldo_devedor;
    }

    public void setSaldo_devedor(double saldo_devedor) {
        this.saldo_devedor = saldo_devedor;
    }

    public double getLimite_fiado() {
        return limite_fiado;
    }

    public void setLimite_fiado(double limite_fiado) {
        this.limite_fiado = limite_fiado;
    }

    @Override
    public String toString() {
        return nome;
    }

    public boolean salvar(Cliente c) {
        DALCliente cli = new DALCliente();
        return cli.salvar(c);
    }

    public boolean alterar(Cliente c) {
        DALCliente cli = new DALCliente();
        return cli.alterar(c);
    }
    
    public boolean alterarSaldo(Cliente c,Double valor,String tipo) {
        DALCliente cli = new DALCliente();
        return cli.alterarSaldo(c,valor,tipo);
    }

    public boolean apagar(int cod) {
        DALCliente cli = new DALCliente();
        return cli.apagar(cod);
    }

    public List<Cliente> get(String filtro) {
        DALCliente cli = new DALCliente();
        return cli.get(filtro);
    }

    public Cliente get(int filtro) {
        DALCliente cli = new DALCliente();
        return cli.get(filtro);
    }

    public boolean verificaSaldo(int codcli, double total) {
        DALCliente cli = new DALCliente();
        return  cli.verificaSaldo(codcli,total);
    }

    
}
