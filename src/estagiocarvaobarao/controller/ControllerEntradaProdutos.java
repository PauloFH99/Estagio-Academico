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
import estagiocarvaobarao.entidade.Categoria;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Fornecedor;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.entidade.Produto_Entrada;
import static estagiocarvaobarao.ui.TelaEntradaProdutosController.Produtos;
import static estagiocarvaobarao.ui.TelaEntradaProdutosController.ReceberE;

import static estagiocarvaobarao.ui.TelaEntradaProdutosController.contaspagar;
import static estagiocarvaobarao.ui.TelaEntradaProdutosController.entradaproduto;
import static estagiocarvaobarao.ui.TelaEntradaProdutosController.tipoFormaPagto;

import estagiocarvaobarao.utils.Mensagens;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import static javafx.scene.input.KeyCode.C;
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class ControllerEntradaProdutos {

    boolean fiado = false;
    CondPagto condpagto = new CondPagto();
    Mensagens msg = new Mensagens();

    public void pesquisaFornecedor(Fornecedor forn, JFXTextField txcodfor, JFXTextField txfornome) {
        Fornecedor fornecedor = new Fornecedor();
        if (forn != null) {
            fornecedor = fornecedor.get(forn.getCodigo());
        } else {
            fornecedor = fornecedor.get(Integer.parseInt(txcodfor.getText()));
        }
        if (fornecedor != null) {
            txcodfor.setText(String.valueOf(fornecedor.getCodigo()));
            txfornome.setText(fornecedor.getNomefantasia());
        } else {
            txcodfor.setText("0");
            txfornome.setText("Valor digitado não encontrado...");
        }
    }

    public void pesquisaProduto(Armazem prod, JFXTextField txcodprod, JFXTextField txproddesc, JFXTextField txquantidade, JFXTextField txpreco, JFXTextField txcodfor) {
        Produto produto = new Produto();
        int codfor = 0;
        try {
            codfor = Integer.parseInt(txcodfor.getText());
        } catch (Exception e) {
            codfor = 0;
        }
        if (prod == null) {
            prod = new Armazem();
            prod = prod.get(Integer.parseInt(txcodprod.getText()));
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

    public void addProduto(JFXTextField txcodprod, JFXTextField txproddesc, JFXTextField txquantidade, JFXTextField txpreco, List<Produto_Entrada> Produtos, TableView<Produto_Entrada> tabela, Label lbtotal, Double total) {
        boolean achou = false;
        int indexProd = 0, qtdeAltera = 0, quantidade = 0, bruto = 0;
        Categoria aux = new Categoria();
        Produto p = new Produto();
        NumberFormat nf = new DecimalFormat("#,###.00");
        for (Produto_Entrada Produto : Produtos) {
            if (Produto.getCodprod() == Integer.parseInt(txcodprod.getText())) {
                indexProd = Produtos.indexOf(Produto);
                qtdeAltera = Produto.getQtde() + Integer.parseInt(txquantidade.getText());
                achou = !achou;
            }
        }
        if (achou) {
            p = p.get(Produtos.get(indexProd).getCodprod());
            aux = aux.get(p.getCategoria().getCodigo());
            try {
                total = qtdeAltera * nf.parse(txpreco.getText()).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(ControllerEntradaProdutos.class.getName()).log(Level.SEVERE, null, ex);
            }
            Produtos.get(indexProd).setQtde(qtdeAltera);
            Produtos.get(indexProd).setTotal(total);

        } else {
            p = p.get(Integer.parseInt(txcodprod.getText()));
            aux = aux.get(p.getCategoria().getCodigo());
            if (!achou) {
                try {
                    total = Integer.parseInt(txquantidade.getText()) * nf.parse(txpreco.getText()).doubleValue();
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerEntradaProdutos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            try {
                Produtos.add(new Produto_Entrada(new Produto(Integer.parseInt(txcodprod.getText())),
                        txproddesc.getText(),
                        Integer.parseInt(txcodprod.getText()),
                        Integer.parseInt(txquantidade.getText()),
                        nf.parse(txpreco.getText()).doubleValue(),
                        total));
            } catch (ParseException ex) {
                Logger.getLogger(ControllerEntradaProdutos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        atualizarTabela(tabela, Produtos);
        calculaTotal(total, lbtotal, Produtos);
    }

    public void atualizarTabela(TableView<Produto_Entrada> tabela, List<Produto_Entrada> Produtos) {
        ObservableList<Produto_Entrada> prod_v = null;
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }

    public void calculaTotal(Double total, Label lbtotal, List<Produto_Entrada> Produtos, Produto_Entrada prode) {
        Categoria aux = new Categoria();
        Produto p = new Produto();
        total = 0.0;
        for (Produto_Entrada Produto : Produtos) {
            p = p.get(Produto.getCodprod());
            aux = aux.get(p.getCategoria().getCodigo());
            total += Produto.getQtde() * Produto.getUnitario();
        }
        lbtotal.setText(total.toString());

    }

    public double calculaTotalP(Double total, Label lbtotal, List<Produto_Entrada> Produtos, Produto_Entrada prode) {
        Categoria aux = new Categoria();
        Produto p = new Produto();
        total = 0.0;
        for (Produto_Entrada Produto : Produtos) {
            if (Produto.getCodprod() == prode.getCodprod()) {
                p = p.get(Produto.getCodprod());
                aux = aux.get(p.getCategoria().getCodigo());
                total += Produto.getQtde() * Produto.getUnitario();
            }
        }
        return total;
    }

    public void calculaTotal(Double total, Label lbtotal, List<Produto_Entrada> Produtos) {
        Categoria aux = new Categoria();
        Produto p = new Produto();
        total = 0.0;
        for (Produto_Entrada Produto : Produtos) {
            p = p.get(Produto.getCodprod());
            aux = aux.get(p.getCategoria().getCodigo());
            total += Produto.getQtde() * Produto.getUnitario();
        }

        lbtotal.setText(total.toString());
    }

    public List<ContasPagar> gerarParcelas(CondPagto condpagto, Double total, DatePicker dpemissao, DatePicker dpvencimento, JFXTextField txdiasentreparc, int qtde, int cod) throws ParseException {
        List<ContasPagar> rec = new ArrayList();
        ContasPagar parcela;
        Double valor = 0.0, valorpago = 0.0, parcelaResto = 0.0;
        LocalDate emissao = dpemissao.getValue();

        Calendar hoje = Calendar.getInstance();
//        if (dtprazo != null) {
//            hoje.set(dtprazo.getYear(), dtprazo.getMonthValue() - 1, dtprazo.getDayOfMonth());
//        }

        Date vencimento = java.sql.Date.valueOf(dpvencimento.getValue());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date resultVencimento = formato.parse(formato.format(vencimento));

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
        } else if (condpagto.getDescricao().toLowerCase().equals("cheque")) {
            resultVencimento = formato.parse(formato.format(vencimento));
            valor_pen = valor;
        } else {
            hoje.setTime(vencimento);
//            if (!txdiasentreparc.getText().isEmpty()) {
//                hoje.add(hoje.DAY_OF_MONTH, Integer.parseInt(txdiasentreparc.getText()));
//            }
            resultVencimento = hoje.getTime();
        }
        if (condpagto.getDescricao().toLowerCase().equals("cheque")) {
            for (int i = 0; i < qtde; i++) {
                valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
                if (qtde > 1) {
                    valor_pen = valor;
                }
                parcela = new ContasPagar(cod, condpagto,
                        new EntradaProdutos(),
                        String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                        valor,
                        valorpago,
                        java.sql.Date.valueOf(emissao),
                        datapago,
                        resultVencimento, total, valor_pen);
                rec.add(parcela);
            }
        } else {
            for (int i = 0; i < qtde; i++) {

                if (i == 0) { // primeira parcela é diferente
                    valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
                    if (qtde > 1) {
                        valor_pen = valor;
                    }

                    parcela = new ContasPagar(cod, condpagto,
                            new EntradaProdutos(),
                            String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                            valor,
                            valorpago,
                            java.sql.Date.valueOf(emissao),
                            datapago,
                            resultVencimento, total, valor_pen);
                } else {
                    hoje.add(hoje.DAY_OF_MONTH, Integer.parseInt(txdiasentreparc.getText()));
                    resultVencimento = hoje.getTime();
                    valor = Double.parseDouble(String.format("%.2f", valor).replaceAll(",", "."));
//                if (i == qtde - 1 && (valor * qtde) < total) { ver depois
//                    parcelaResto = total - valor * qtde;
//                    parcelaResto = Double.parseDouble(nf.format(parcelaResto).replaceAll(",", "."));
//                    valor += parcelaResto;
//                    valor = Double.parseDouble(nf.format(valor).replace(",", "."));
//                } else if (i == qtde - 1 && (valor * qtde) > total) {
//                    parcelaResto = valor * qtde - total;
//                    parcelaResto = nf.parse (tfValor.getText()).doubleValue();
//                    valor -= parcelaResto;
//                    valor = nf.parse (tfValor.getText()).doubleValue();
//                }
                    parcela = new ContasPagar(cod, condpagto,
                            new EntradaProdutos(),
                            String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                            valor,
                            valorpago,
                            java.sql.Date.valueOf(emissao),
                            datapago,
                            resultVencimento, 0, valor);
                }
                rec.add(parcela);
            }
        }

        return rec;
    }

    public void confirmarPag(String tipoFormaPagto, JFXComboBox<CondPagto> cbFormaPagto, Label lbqtdeparc,
            Label lbqtdeentre, Label lbdata, JFXTextField txqtdeparc, Label lbforma, FontAwesomeIcon ftformpagt,
            JFXTextField txdiasentreparc, DatePicker dpdatavencimento, int cod, double total,
            DatePicker dpemissao, TableView<ContasPagar> tabelaReceber, JFXTextField txemissorcheque) {
        int qtde = 0;
        tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();

        if (tipoFormaPagto.equals("à vista") || tipoFormaPagto.equals("débito")) { //<-- Avista ou Débito, gera uma parcela já baixada
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'v');

            qtde = 1;
        } else if (tipoFormaPagto.equals("a prazo") || tipoFormaPagto.equals("crédito")) { //<-- Aprazo ou Crédito precisa buscar as condpagto
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');

            qtde = Integer.parseInt(txqtdeparc.getText());
        } else if (tipoFormaPagto.equals("cheque")) { //<-- Cheque
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');
            qtde = Integer.parseInt(txqtdeparc.getText());
        }

        if (tipoFormaPagto.equals("fiado")) { // <-- caso for fiado não gera financeiro
            ReceberE = null;
            fiado = true;
        } else {
            try {

                ReceberE = gerarParcelas(condpagto, total, dpemissao, dpdatavencimento, txdiasentreparc, qtde, cod);
                tabelaReceber.setVisible(true);
                tabelaReceber.setItems(null);
                tabelaReceber.setItems(FXCollections.observableArrayList(ReceberE));
            } catch (ParseException ex) {

            }
        }

    }

    public boolean finalizar(Double total, JFXTextField txcod, Label lbtotal, String tipoFormaPagto, JFXComboBox<CondPagto> cbFormaPagto, JFXTextField txqtdeparc, ContasPagar contaspagar, JFXTextField txcodfor, List<Produto_Entrada> Produtos, DatePicker dpemissao, JFXTextField txdiasentreparc, TableView<ContasPagar> tabelaReceber, TableView<Produto_Entrada> tabela, Funcionario funcionario, JFXTextField txemissorcheque) {
        int cod = 0, erro = 0, qtdeparc = 0, diasentre = 0, cod_cond;
        EntradaProdutos saida = new EntradaProdutos();

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
        tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();

        try {
            cod_cond = cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo();
        } catch (Exception e) {
            cod_cond = 0;
        }

        if (cod == 0) {
            if (msg.Confirmation("", "Confirmar compra?")) {

                if (txemissorcheque.getText().isEmpty()) {
                    txemissorcheque.setText("");
                }

                // Comora nova
                if (cod == 0) {

                    if (saida.gravar(new EntradaProdutos(0),
                            new Fornecedor(Integer.parseInt(txcodfor.getText())),
                            funcionario,
                            Produtos,
                            ReceberE,
                            new CondPagto(cod_cond),
                            total,
                            dpemissao.getValue(),
                            fiado,
                            qtdeparc,
                            diasentre,
                            txemissorcheque.getText())) {
                        return true;

                    } else {
                        return false;

                    }
                }
            }

        } else {
            if (msg.Confirmation("", "Confirmar alteração na compra?")) {
                if (txemissorcheque.getText().isEmpty()) {
                    txemissorcheque.setText("");
                }
                if (saida.alterar(cod, new Fornecedor(Integer.parseInt(txcodfor.getText())),
                        funcionario,
                        Produtos,
                        ReceberE,
                        new CondPagto(cod_cond),
                        total,
                        dpemissao.getValue(),
                        fiado,
                        qtdeparc,
                        diasentre,
                        txemissorcheque.getText())) {
                    return true;

                }
            }
        }
        return false;
    }

    public void atualizarTabela(TableView<Produto_Entrada> tabela) {
        ObservableList<Produto_Entrada> prod_v;
        prod_v = FXCollections.observableArrayList(Produtos);
        tabela.setItems(prod_v);
    }

    public void calculaTotal(Label lbtotal) {
        Double total = 0.0;
        for (Produto_Entrada Produto : Produtos) {
            total += Produto.getTotal();
        }

        lbtotal.setText(total.toString());

    }

    public void atualizarReceber(TableView<ContasPagar> tabelaReceber) {
        try {
            tabelaReceber.setVisible(true);
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(ReceberE);
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

    public void carregarCompra(EntradaProdutos entradaproduto, DatePicker dpemissao, JFXTextField txcod, JFXTextField txcodfor,
            JFXTextField txfornome, JFXTextField txcodfor0, JFXTextField txfornome0, List<Produto_Entrada> Produtos,
            JFXComboBox<CondPagto> cbFormaPagto, JFXTextField txqtdeparc, JFXTextField txdiasentreparc, List<ContasPagar> ReceberE,
            JFXButton btnexcluir, JFXButton btnfinalizar, JFXButton btncancelar, JFXButton btnnovo,
            TableView<Produto_Entrada> tabela, Label lbtotal, TableView<ContasPagar> tabelaReceber, JFXTextField txemissorcheque) {
        DALConsulta dal = new DALConsulta();
        if (entradaproduto.verificarParcelaPaga(entradaproduto.getCodigo())) {
            btnfinalizar.setDisable(true);
            btnexcluir.setDisable(true);
            msg.Error("Prometheus Informa", "Essa compra possui parcelas pagas!");
        } else {

            int ano = entradaproduto.getEmissaoDate().getYear() + 1900;
            int mes = entradaproduto.getEmissaoDate().getMonth() + 1;
            int dia = entradaproduto.getEmissaoDate().getDate();
            dpemissao.setValue(LocalDate.of(ano, mes, dia));
            dpemissao.setShowWeekNumbers(true);
            txcod.setText(String.valueOf(entradaproduto.getCodigo()));
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
            Fornecedor f = new Fornecedor();
            f = f.get(entradaproduto.getFornecedor().getCodigo());
            txcodfor.setText(String.valueOf(f.getCodigo()));
            txfornome.setText(f.getNomefantasia());
            Produtos.clear();
            Produtos.addAll(dal.getProdutosCompra(entradaproduto.getCodigo()));
            CondPagto cp = new CondPagto();
            cp = cp.get(entradaproduto.getCondPagto().getCodigo());
            cbFormaPagto.getSelectionModel().select(0);
            cbFormaPagto.getSelectionModel().select(cp);
            ContasPagar aux = new ContasPagar();
            String tipopagamento = "";
            tipopagamento = cp.getDescricao();
            if (tipopagamento.toLowerCase().equals("cheque")) {
                aux = aux.getCompra(entradaproduto.getCodigo());
                txemissorcheque.setText(aux.getEmissor_cheque());
                txqtdeparc.setText(String.valueOf(entradaproduto.getQtde_parcelas()));
            } else if (tipopagamento.toLowerCase().equals("a prazo") || tipopagamento.toLowerCase().equals("crédito")) {
                txqtdeparc.setText(String.valueOf(entradaproduto.getQtde_parcelas()));
                txdiasentreparc.setText(String.valueOf(entradaproduto.getDiasentreparc()));
            }

            atualizarTabela(tabela);
            calculaTotal(lbtotal);

            ReceberE.clear();
            ReceberE.addAll(dal.getPagar(entradaproduto.getCodigo()));
            atualizarReceber(tabelaReceber);
            btnnovo.setDisable(true);
            btnfinalizar.setDisable(false);
            btnexcluir.setDisable(false);
        }
    }

    public boolean calculaTotalVenda(List<ContasPagar> ReceberE, double totalCompra) {
        Double total = 0.0;
        for (ContasPagar receber : ReceberE) {
            total += receber.getValor();
        }
        if (total < totalCompra || total > totalCompra) {
            return false;
        }
        return true;
    }

    public void excluirProd(int cod, int codP) {
        Produto_Entrada pe = new Produto_Entrada();
        pe.apagar(cod, codP);
    }

}
