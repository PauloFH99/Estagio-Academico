/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import estagiocarvaobarao.dal.DALArmazem;
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Venda;
import estagiocarvaobarao.entidade.Venda;
import static estagiocarvaobarao.ui.TelaVendaController.Produtos;
import static estagiocarvaobarao.ui.TelaVendaController.Receber;
import static estagiocarvaobarao.ui.TelaVendaController.contasreceber;
import static estagiocarvaobarao.ui.TelaVendaController.tipoFormaPagto;
import static estagiocarvaobarao.ui.TelaVendaController.total_ant;
import static estagiocarvaobarao.ui.TelaVendaController.venda;
import estagiocarvaobarao.utils.Mensagens;
import static java.lang.System.exit;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class ControllerVenda {

    boolean fiado = false;
    CondPagto condpagto = new CondPagto();
    Mensagens msg = new Mensagens();

    public void carregarNivel(JFXComboBox<CondPagto> cbFormaPagto) {
        CondPagto condpagto = new CondPagto();
        List<CondPagto> ListaCond = condpagto.getVendas("");//Lista de cond
        cbFormaPagto.setItems(FXCollections.observableArrayList(ListaCond));
        cbFormaPagto.getSelectionModel().getSelectedItem();
    }

    public void carregarCliente(JFXComboBox<Cliente> cbCliente) {
        Cliente cli = new Cliente();
        List<Cliente> Listacli = cli.get("");//Lista de cli
        cbCliente.setItems(FXCollections.observableArrayList(Listacli));
        cbCliente.getSelectionModel().getSelectedItem();
    }

    public void carregarFornecedor(JFXComboBox<Fornecedor> cbFornecedor) {
        Fornecedor f = new Fornecedor();
        List<Fornecedor> Listafor = f.get("");//Lista de cli
        cbFornecedor.setItems(FXCollections.observableArrayList(Listafor));
        cbFornecedor.getSelectionModel().getSelectedItem();
    }

    public void pesquisarCliente(Cliente cli, int cod, JFXTextField txcodcli, JFXTextField txclinome) {
        Cliente cliente = new Cliente();
        if (cli != null) {
            cliente = cliente.get(cli.getCodigo());
        } else {
            cliente = cliente.get(cod);
        }
        if (cliente != null) {
            txcodcli.setText(String.valueOf(cliente.getCodigo()));
            txclinome.setText(cliente.getNome());
        } else {
            txcodcli.setText("0");
            txclinome.setText("Valor digitado não encontrado...");
        }
    }

    public List<ContasReceber> gerarParcelas(CondPagto condpagto, Double total, DatePicker dpemissao, DatePicker dpvencimento, JFXTextField txdiasentreparc, int qtde, int cod, String num_boleto) throws ParseException {
        List<ContasReceber> rec = new ArrayList();
        ContasReceber parcela;
        Double valor = 0.0, valorpago = 0.0, parcelaResto = 0.0;
        LocalDate emissao = dpemissao.getValue();

        Calendar hoje = Calendar.getInstance();
//        if (dtprazo != null) {
//            hoje.set(dtprazo.getYear(), dtprazo.getMonthValue() - 1, dtprazo.getDayOfMonth());
//        }

        Date vencimento = java.sql.Date.valueOf(dpvencimento.getValue());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date resultVencimento = formato.parse(formato.format(vencimento));
        String num_boletoaux = "";
        Date datapago = new Date();
        datapago = null;
        valor = total / qtde;
        valorpago = 0.0;
        Double valorfinal = 0.00;
        Double valor_pen = 0.00;
        NumberFormat nf = new DecimalFormat("#,###.00");

        if (condpagto.getAvistaaprazo() == 'v') {
            valorpago = valor;
            datapago = java.sql.Date.valueOf(emissao);
            resultVencimento = formato.parse(formato.format(vencimento));
        } else if (condpagto.getDescricao().toLowerCase().equals("cheque") || condpagto.getDescricao().toLowerCase().equals("boleto")) {
            resultVencimento = formato.parse(formato.format(vencimento));
            valor_pen = valor;
            num_boletoaux = num_boleto;
        } else {
            hoje.setTime(vencimento);
//            if (!txdiasentreparc.getText().isEmpty()) {
//                hoje.add(hoje.DAY_OF_MONTH, Integer.parseInt(txdiasentreparc.getText()));
//            }
            resultVencimento = hoje.getTime();
        }
        for (int i = 0; i < qtde; i++) {

            if (i == 0) { // primeira parcela é diferente
                valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
                if (qtde > 1) {
                    valor_pen = valor;
                }

                parcela = new ContasReceber(cod,
                        new Venda(),
                        String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                        valor,
                        valorpago,
                        java.sql.Date.valueOf(emissao),
                        datapago,
                        resultVencimento, total, valor_pen, num_boletoaux);
            } else {
                hoje.add(hoje.DAY_OF_MONTH, Integer.parseInt(txdiasentreparc.getText()));
                resultVencimento = hoje.getTime();
                valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
                if (i == qtde - 1 && (valor * qtde) < total) {
                    parcelaResto = total - valor * qtde;
                    parcelaResto = Double.parseDouble(nf.format(parcelaResto).replaceAll(",", "."));
                    valor += parcelaResto;
                    valor = Double.parseDouble(nf.format(valor).replace(",", "."));
                } else if (i == qtde - 1 && (valor * qtde) > total) {
                    parcelaResto = valor * qtde - total;
                    parcelaResto = Double.parseDouble(nf.format(parcelaResto).replaceAll(",", "."));
                    valor -= parcelaResto;
                    valor = Double.parseDouble(nf.format(valor).replaceAll(",", "."));
                }
                parcela = new ContasReceber(cod,
                        new Venda(),
                        String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                        valor,
                        valorpago,
                        java.sql.Date.valueOf(emissao),
                        datapago,
                        resultVencimento, 0, valor, num_boletoaux);
            }
            rec.add(parcela);
        }

        return rec;
    }

    public void atualizarTabela(TableView<Produto_Venda> tabela, List<Produto_Venda> Produtos) {
        ObservableList<Produto_Venda> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }

    public double calculaTotal(Double total, Label lbtotal, List<Produto_Venda> Produtos) {
        total = 0.0;
        for (Produto_Venda Produto : Produtos) {
            total += Produto.getQtde() * Produto.getUnitario();
        }

        lbtotal.setText(total.toString());
        total_ant = total;
        return total;
    }

    public boolean calculaTotalVenda(List<ContasReceber> Receber, double totalVenda) {
        Double total = 0.0;
        for (ContasReceber receber : Receber) {
            total += receber.getValor();
        }
        if (total < totalVenda || total > totalVenda) {
            return false;
        }
        return true;
    }

    public void addProduto(JFXTextField txcodprod, JFXTextField txproddesc, JFXTextField txquantidade,
            JFXTextField txpreco, List<Produto_Venda> Produtos, TableView<Produto_Venda> tabela, Label lbtotal, Double total) {
        NumberFormat nf = new DecimalFormat("#,###.00");
        boolean achou = false;
        int indexProd = 0, qtdeAltera = 0;
        for (Produto_Venda Produto : Produtos) {
            if (Produto.getCodprod() == Integer.parseInt(txcodprod.getText())) {
                indexProd = Produtos.indexOf(Produto);
                qtdeAltera = Produto.getQtde() + Integer.parseInt(txquantidade.getText());
                achou = !achou;
            }
        }
        if (achou) {
           
            try {
                total = qtdeAltera * nf.parse(txpreco.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(ControllerVenda.class.getName()).log(Level.SEVERE, null, ex);
            }
            Produtos.get(indexProd).setQtde(qtdeAltera);
            Produtos.get(indexProd).setTotal(total);

        } else {
            try {
                Produtos.add(new Produto_Venda(new Produto(Integer.parseInt(txcodprod.getText())),
                        txproddesc.getText(),
                        Integer.parseInt(txcodprod.getText()),
                        Integer.parseInt(txquantidade.getText()),
                        nf.parse(txpreco.getText()).doubleValue(),
                        Integer.parseInt(txquantidade.getText()) * nf.parse(txpreco.getText()).doubleValue()));
            } catch (ParseException ex) {
                Logger.getLogger(ControllerVenda.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        atualizarTabela(tabela, Produtos);
        calculaTotal(total, lbtotal, Produtos);
    }

    public boolean finalizar(Double total, JFXTextField txcod, Label lbtotal, String tipoFormaPagto,
            JFXComboBox<CondPagto> cbFormaPagto, JFXTextField txqtdeparc, ContasReceber contasreceber,
            JFXTextField txcodcli, List<Produto_Venda> Produtos, DatePicker dpemissao,
            JFXTextField txdiasentreparc, TableView<ContasReceber> tabelaReceber,
            TableView<Produto_Venda> tabela, Funcionario func, JFXTextField txemissorcheque, String estoque,
            Funcionario funcionario, String situacao) {
        int cod = 0, erro = 0, qtdeparc = 0, diasentre = 0, cod_cond;
        Mensagens msg = new Mensagens();
        Cliente cli = new Cliente();
        String emissorcheque = "";
        //Verificando se não foi informado cliente
        if (txcodcli.getText().isEmpty()) {
            txcodcli.setText("1");
        }
        cli = cli.get(Integer.parseInt(txcodcli.getText()));
        try {
            cod = Integer.parseInt(txcod.getText());
        } catch (Exception e) {
            cod = 0;
        }
        try {
            qtdeparc = Integer.parseInt(txqtdeparc.getText());
        } catch (Exception e) {
            qtdeparc = 0;
        }
        try {
            diasentre = Integer.parseInt(txdiasentreparc.getText());
        } catch (Exception e) {
            diasentre = 0;
        }
        try {
            cod_cond = cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo();
            condpagto.setCodigo(cod_cond);
        } catch (Exception e) {
            cod_cond = 0;
        }
        NumberFormat nf = new DecimalFormat("#,###.0000");
        tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();

        if (tipoFormaPagto.toLowerCase().equals("cheque") || tipoFormaPagto.toLowerCase().equals("boleto")) {
            emissorcheque = txemissorcheque.getText();
        }

        if (cod == 0) {
            if (msg.Confirmation("", "Confirmar venda?")) {

                // Venda nova
                if (cod == 0) {
                    Venda venda = new Venda();
                    if (venda.gravar(new Venda(0),
                            cli,
                            func,
                            Produtos,
                            Receber,
                            condpagto,
                            total,
                            dpemissao.getValue(),
                            fiado,
                            qtdeparc,
                            diasentre, emissorcheque, estoque, situacao)) {

                        //Atualizar saldo devedor
                        if (tipoFormaPagto.equals("fiado")) {
                            venda.atualizarSaldoDevedor(Integer.parseInt(txcodcli.getText()), total);
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            if (msg.Confirmation("", "Confirmar alteração na venda?")) {
                if (txemissorcheque.getText().isEmpty()) {
                    txemissorcheque.setText("");
                }
                if (venda.alterar(cod, new Cliente(Integer.parseInt(txcodcli.getText())),
                        funcionario,
                        Produtos,
                        Receber,
                        new CondPagto(cod_cond),
                        total,
                        dpemissao.getValue(),
                        fiado,
                        qtdeparc,
                        diasentre,
                        txemissorcheque.getText(), estoque, situacao)) {

                    //Atualizar saldo devedor
                    if (tipoFormaPagto.equals("fiado")) {
                        venda.atualizarSaldoDevedorAlteracao(Integer.parseInt(txcodcli.getText()), total);
                    }
                    return true;

                }
            }
        }
        return false;
    }

    public void confirmarPag(String tipoFormaPagto, JFXComboBox<CondPagto> cbFormaPagto, Label lbqtdeparc,
            Label lbqtdeentre, Label lbdata, JFXTextField txqtdeparc, Label lbforma,
            FontAwesomeIcon ftformpagt, JFXTextField txdiasentreparc, DatePicker dpdatavencimento,
            int cod, Double total, DatePicker dpemissao, TableView<ContasReceber> tabelaReceber, JFXTextField txemissorcheque) {
        int qtde = 0;
        tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();
        String num_boleto = "";
        lbdata.setVisible(true);
        lbdata.setText(dpdatavencimento.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lbforma.setVisible(true);
        lbforma.setText(tipoFormaPagto);
        ftformpagt.setVisible(true);
        lbqtdeparc.setVisible(true);
        lbqtdeparc.setText(txqtdeparc.getText());
        lbqtdeentre.setVisible(true);
        lbqtdeentre.setText(txdiasentreparc.getText());
        if (tipoFormaPagto.equals("à vista")) {
            ftformpagt.setIconName("MONEY");
        } else if (tipoFormaPagto.equals("a prazo")) {
            ftformpagt.setIconName("CALENDAR");
        } else if (tipoFormaPagto.equals("débito")) {
            ftformpagt.setIconName("CREDIT_CARD");
        } else if (tipoFormaPagto.equals("cheque") || tipoFormaPagto.equals("fiado")) {
            ftformpagt.setIconName("CALENDAR");
        } else if (tipoFormaPagto.equals("crédito")) {
            ftformpagt.setIconName("CREDIT_CARD");
        } else if (tipoFormaPagto.equals("boleto")) {
            lbqtdeparc.setVisible(false);
            lbqtdeentre.setVisible(false);
            ftformpagt.setIconName("FILE_ALT");
        }

        if (tipoFormaPagto.equals("à vista") || tipoFormaPagto.equals("débito")) { //<-- Avista ou Débito, gera uma parcela já baixada
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'v');

            qtde = 1;
        } else if (tipoFormaPagto.equals("a prazo") || tipoFormaPagto.equals("crédito")) { //<-- Aprazo ou Crédito precisa buscar as condpagto
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');

            qtde = Integer.parseInt(txqtdeparc.getText());
        } else if (tipoFormaPagto.equals("cheque") || tipoFormaPagto.equals("boleto")) { //<-- Cheque/Boleto
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');
            num_boleto = txqtdeparc.getText();
            qtde = 1;
        }

        if (tipoFormaPagto.equals("fiado")) { // <-- caso for fiado não gera financeiro
            Receber = null;
            fiado = true;
        } else {
            try {

                if (txqtdeparc.isVisible() && !txqtdeparc.getText().isEmpty() && !tipoFormaPagto.equals("boleto")) {
                    qtde = Integer.parseInt(txqtdeparc.getText());
                }

                Receber = gerarParcelas(condpagto, total, dpemissao, dpdatavencimento, txdiasentreparc, qtde, cod, num_boleto);
                tabelaReceber.setVisible(true);
                tabelaReceber.setItems(null);
                tabelaReceber.setItems(FXCollections.observableArrayList(Receber));
            } catch (ParseException ex) {

            }
        }
    }

    public void atualizarReceber(TableView<ContasReceber> tabelaReceber, List<ContasReceber> Receber) {
        try {
            tabelaReceber.setVisible(true);
            ObservableList<ContasReceber> contr;
            contr = FXCollections.observableArrayList(Receber);
            tabelaReceber.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean decimal(String valor) {
        int k = 0;
        int pos = valor.indexOf(".");
        for (int i = pos + 1; i < valor.length(); i++) {
            k++;
            if (k == 2) {
                if (Integer.parseInt(String.valueOf(valor.charAt(i))) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void carregarVenda(Venda venda, DatePicker dpemissao, JFXTextField txcodcli, JFXTextField txclinome,
            List<Produto_Venda> Produtos, JFXComboBox<CondPagto> cbFormaPagto, JFXTextField txqtdeparc,
            JFXTextField txdiasentreparc, List<ContasReceber> Receber, JFXButton btnexcluir,
            JFXButton btnfinalizar, JFXButton btncancelar, JFXButton btnnovo,
            TableView<Produto_Venda> tabela, TableView<ContasReceber> tabelaReceber, Double total, Label lbtotal,
            JFXTextField txemissorcheque, JFXTextField txcod, DatePicker dpdatavencimento, JFXComboBox<String> cbsitu) {
        DALConsulta dal = new DALConsulta();
        if (venda.verificarParcelaPaga(venda.getCodigo())) {
            btnfinalizar.setDisable(true);
            btnexcluir.setDisable(true);
            msg.Error("Prometheus Informa", "Essa venda possui parcelas pagas!");
        } else {
            int ano = venda.getEmissaoDate().getYear() + 1900;
            int mes = venda.getEmissaoDate().getMonth() + 1;
            int dia = venda.getEmissaoDate().getDate();
            dpemissao.setValue(LocalDate.of(ano, mes, dia));

            StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                DateTimeFormatter dateFormatter
                        = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }//To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    } //To change body of generated methods, choose Tools | Templates.
                }
            };
            dpemissao.setConverter(converter);

            Cliente c = new Cliente();
            c = c.get(venda.getCliente().getCodigo());
            txcodcli.setText(String.valueOf(c.getCodigo()));
            txclinome.setText(c.getNome());
            Funcionario f = new Funcionario();
            f = f.get(venda.getFuncionario().getCodigo());
            txcod.setText(String.valueOf(venda.getCodigo()));
            Produtos.clear();
            Produtos.addAll(dal.getProdutosVenda(venda.getCodigo()));
            ContasReceber aux = new ContasReceber();
            CondPagto cp = new CondPagto();
            cp = cp.get(venda.getCondPagto().getCodigo());
            String tipopagamento = "";
            tipopagamento = cp.getDescricao();

            if (tipopagamento.toLowerCase().equals("cheque") || tipopagamento.toLowerCase().equals("boleto")) {
                aux = aux.getVendaCond(venda.getCodigo());
                txemissorcheque.setText(aux.getEmissorcheque());
                txqtdeparc.setText(aux.getNum_boleto());
            } else if (tipopagamento.toLowerCase().equals("a prazo") || tipopagamento.toLowerCase().equals("crédito")) {
                txqtdeparc.setText(String.valueOf(venda.getQtde_parcelas()));
                txdiasentreparc.setText(String.valueOf(venda.getDiasentreparc()));
            }

            cbFormaPagto.getSelectionModel().select(0);
            cbFormaPagto.getSelectionModel().select(cp);

            cbsitu.getSelectionModel().select(venda.getSituacao());

            atualizarTabela(tabela, Produtos);
            calculaTotal(total, lbtotal, Produtos);
            if (Receber != null) {
                Receber.clear();
                Receber.addAll(dal.getReceber(venda.getCodigo()));
                atualizarReceber(tabelaReceber, Receber);
            }
            btnnovo.setDisable(true);
            btnfinalizar.setDisable(false);
            btnexcluir.setDisable(false);
        }
    }

    public boolean excluir(Venda venda, List<ContasReceber> Receber, TableView<ContasReceber> tabelaReceber) {
        Mensagens msg = new Mensagens();
        Venda v = new Venda();
        int cod = 0;
        boolean aux_confirma = false;
        if (venda == null) {
            msg.Error("", "Nenhum venda informada");
            exit(1);
        } else {
            if (msg.Confirmation("", "Confirmar exclusão?")) {
                if (!v.verificarParcelaPaga(venda.getCodigo())) {
                    Produto_Venda prodv = new Produto_Venda();
                    if (venda.getEstoque().equals("a")) {
                        List<Produto_Venda> aux = new ArrayList();
                        Produto_Venda produto_v = new Produto_Venda();
                        aux = produto_v.getporVenda(venda.getCodigo());
                        Armazem arm = new Armazem();
                        for (Produto_Venda produto_Venda : aux) {
                            aux_confirma = arm.alterar(produto_Venda.getCod_produto().getCodigo(), produto_Venda.getQtde(), "e");
                        }
                        if (aux_confirma == false) {
                            return false;
                        }
                    }
                    cod = venda.getCodigo();
                    if (v.apagar(venda.getCodigo())) {
                        ContasReceber contr = new ContasReceber();
                        if (contr.apagar(cod)) {
                            aux_confirma = true;
                        }
                        msg.Affirmation("", "Venda excluída com sucesso.");
                        if (Receber == null || Receber.isEmpty()) {
                            v.atualizarSaldoDevedor(venda.getCliente().getCodigo(), venda.getTotal() * -1);
                        } else {
                            Receber.clear();
                            atualizarReceber(tabelaReceber, Receber);
                        }
                    }

                } else {
                    msg.Error("", "Há parcelas pagas para esta venda.");
                    // exit(1);
                }
            }
        }
        return aux_confirma;
    }

    public boolean verificaEstoque(JFXTextField txcodprod, JFXTextField txquantidade, String estoque, List<Produto_Venda> Produtos) {
        DALArmazem dal = new DALArmazem();
        Mensagens msg = new Mensagens();
        int qtde = Integer.parseInt(txquantidade.getText());
        if (estoque.equals("a")) {
            if (dal.getProduto(Integer.parseInt(txcodprod.getText()))) {
                Armazem a = dal.get(Integer.parseInt(txcodprod.getText()));
                if (a.getQuantidade() == 0) {
                    msg.Error("Prometheus Informa", "Esse produto esta sem estoque!");
                    txquantidade.setText(String.valueOf(a.getQuantidade()));
                    txquantidade.requestFocus();
                    return false;
                } else if (qtde > a.getQuantidade()) {
                    msg.Error("Prometheus Informa", "O estoque do armazem não possui essa quantidade!");
                    txquantidade.setText(String.valueOf(a.getQuantidade()));
                    txquantidade.requestFocus();
                    return false;
                } else if (Produtos.size() > 0) {
                    for (Produto_Venda Produto : Produtos) {
                        if (Produto.getCodprod() == Integer.parseInt(txcodprod.getText())) {
                            if (qtde + Produto.getQtde() > a.getQuantidade()) {
                                msg.Error("Prometheus Informa", "O estoque do armazem não possui essa quantidade!");
                                txquantidade.setText(String.valueOf(a.getQuantidade()));
                                txquantidade.requestFocus();
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void pesquisarProduto(Armazem prod, int cod, JFXTextField txcodprod, JFXTextField txproddesc, JFXTextField txpreco, JFXTextField txquantidade, int cod_cli) {
        Produto produto = new Produto();

        int codcliente = 0;
        try {
            codcliente = cod_cli;
        } catch (Exception e) {
            codcliente = 0;
        }
        if (prod == null) {
            prod = new Armazem();
            prod = prod.get(cod);
        }
        if (prod != null) {
            produto = produto.get(prod.getProduto().getCodigo());
            txcodprod.setText(String.valueOf(prod.getCod_prod()));
            txproddesc.setText(produto.getDescricao());
            txquantidade.requestFocus();
            if (decimal(String.valueOf(produto.getPreco())) && String.valueOf(produto.getPreco()).length() >= 3 && String.valueOf(produto.getPreco()).length() <= 5 || produto.getPreco() >= 100.0) {
                txpreco.setText(String.valueOf(produto.getPreco()) + "0");
            } else {
                txpreco.setText(String.valueOf(produto.getPreco()));
            }

        } else {
            txcodprod.setText("0");
            txproddesc.setText("Valor digitado não encontrado...");
            txpreco.setText("0.00");
        }
    }

    public boolean verificaSaldo(int codcli, double total) {
        Cliente cli = new Cliente();
        return cli.verificaSaldo(codcli, total);
    }

    public void excluirProd(int cod, int codP) {
        Produto_Venda pv = new Produto_Venda();
        pv.apagar(cod, codP);
    }

    public boolean verificaEstoque(Integer qtde_nova, String estoque, int codP, int qtde_ant) {
        DALArmazem dal = new DALArmazem();
        Armazem a = new Armazem();
        Mensagens msg = new Mensagens();
        int dif_qtde = 0;
        if (qtde_nova > qtde_ant) {
            dif_qtde = qtde_nova - qtde_ant;
            if (estoque.equals("a")) {
                if (dal.getProduto(codP)) {
                    a = dal.get(codP);
                    if (a.getQuantidade() == 0) {
                        msg.Error("Prometheus Informa", "Esse produto esta sem estoque!");
                        return false;
                    } else if (dif_qtde > a.getQuantidade()) {
                        msg.Error("Prometheus Informa", "O estoque do armazem não possui essa quantidade!");
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
