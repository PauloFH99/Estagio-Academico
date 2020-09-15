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
import estagiocarvaobarao.dal.DALConsulta;
import estagiocarvaobarao.entidade.CondPagto;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.Despesas;
import estagiocarvaobarao.entidade.EntradaProdutos;
import estagiocarvaobarao.entidade.Funcionario;
import static estagiocarvaobarao.ui.TelaLancarDespesasController.ReceberLD;
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
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class ControllerLancarDespesas {

    boolean fiado = false;
    CondPagto condpagto = new CondPagto();
    Mensagens msg = new Mensagens();

    public void carregarNivel(JFXComboBox<CondPagto> cbFormaPagto, String tabela) {
        CondPagto condpagto = new CondPagto();
        List<CondPagto> ListaCond = condpagto.getCondDes();//Lista de cond
        cbFormaPagto.setItems(FXCollections.observableArrayList(ListaCond));
        cbFormaPagto.getSelectionModel().getSelectedItem();
    }

    public List<ContasPagar> gerarParcelas(CondPagto condpagto, Double total, DatePicker dpemissao, DatePicker dpvencimento, JFXTextField txdiasentreparc, int qtde, int codiddes, int cod) throws ParseException {
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
            datapago = null;
            valorpago = 0.0;
        } else {
            hoje.setTime(vencimento);
            resultVencimento = hoje.getTime();
        }
        if (condpagto.getDescricao().toLowerCase().equals("fixa")) {
            hoje.setTime(vencimento);
            resultVencimento = hoje.getTime();
            valor = total;
            valorpago = 0.0;
            datapago = null;
            for (int i = 0; i < qtde; i++) {
                if (i > 0) {
                    hoje.add(Calendar.MONTH, 1);
                    resultVencimento = hoje.getTime();
                }
                parcela = new ContasPagar(cod, condpagto,
                        new EntradaProdutos(),
                        String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                        valor,
                        valorpago,
                        java.sql.Date.valueOf(emissao),
                        datapago,
                        resultVencimento, 0, valor, codiddes);

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
                            resultVencimento, total, valor_pen, codiddes);
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
                    parcela = new ContasPagar(cod, condpagto,
                            new EntradaProdutos(),
                            String.valueOf(i + 1) + "/" + String.valueOf(qtde),
                            valor,
                            valorpago,
                            java.sql.Date.valueOf(emissao),
                            datapago,
                            resultVencimento, 0, valor, codiddes);
                }
                rec.add(parcela);
            }
        }
        return rec;
    }

    public void confirmarPag(String tipoFormaPagto, JFXComboBox<CondPagto> cbFormaPagto, Label lbqtdeparc, Label lbdata,
            JFXTextField txqtdeparc, Label lbforma, FontAwesomeIcon ftformpagt, JFXTextField txdiasentreparc,
            DatePicker dpdatavencimento, int cod, double total, DatePicker dpemissao, TableView<ContasPagar> tabela, ContasPagar contasapagar) {
        int qtde = 0, codiddes = 0;
        tipoFormaPagto = cbFormaPagto.getSelectionModel().getSelectedItem().getDescricao();

        lbdata.setVisible(true);
        lbdata.setText(dpdatavencimento.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lbforma.setVisible(true);
        lbforma.setText(tipoFormaPagto);
        ftformpagt.setVisible(true);
        if (tipoFormaPagto.equals("à vista")) {
            ftformpagt.setIconName("MONEY");
        } else if (tipoFormaPagto.equals("a prazo")) {
            ftformpagt.setIconName("CALENDAR");
        } else if (tipoFormaPagto.equals("débito")) {
            ftformpagt.setIconName("CREDIT_CARD");
        } else if (tipoFormaPagto.equals("cheque") || tipoFormaPagto.equals("fiado") || tipoFormaPagto.equals("fixa")) {
            ftformpagt.setIconName("CALENDAR");
        } else if (tipoFormaPagto.equals("crédito")) {
            ftformpagt.setIconName("CREDIT_CARD");
        }

        if (tipoFormaPagto.equals("à vista") || tipoFormaPagto.equals("débito")) { //<-- Avista ou Débito, gera uma parcela já baixada
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'v');

            qtde = 1;
        } else if (tipoFormaPagto.equals("a prazo") || tipoFormaPagto.equals("crédito")) { //<-- Aprazo ou Crédito precisa buscar as condpagto
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');

            qtde = Integer.parseInt(txqtdeparc.getText());
        } else if (tipoFormaPagto.equals("cheque")) { //<-- Cheque
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');
            qtde = 1;
        }
        if (tipoFormaPagto.equals("fixa")) { //<-- fixa
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(),
                    tipoFormaPagto, 'p');
            qtde = Integer.parseInt(txqtdeparc.getText());
        }
        if (tipoFormaPagto.equals("fiado")) { //  for fiado 
            condpagto = new CondPagto(cbFormaPagto.getSelectionModel().getSelectedItem().getCodigo(), tipoFormaPagto, 'p');
            qtde = 1;
            fiado = true;
        }
        if (contasapagar != null) {
            codiddes = contasapagar.getCod_iddes();

        }
        try {
            ReceberLD = gerarParcelas(condpagto, total, dpemissao, dpdatavencimento, txdiasentreparc, qtde, codiddes, cod);
            tabela.setVisible(true);
            tabela.setItems(null);
            tabela.setItems(FXCollections.observableArrayList(ReceberLD));
        } catch (ParseException ex) {
            Logger.getLogger(ControllerLancarDespesas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean finalizar(int cod, int coddes, Funcionario funcionario, List<ContasPagar> ReceberLD, TableView<ContasPagar> tabela,
            DatePicker dpdatavencimento, DatePicker dpemissao, String emissor_cheque, int qtde_parcelas) {
        if (cod == 0) {
            ContasPagar contp = new ContasPagar(cod, funcionario, new Despesas(coddes), condpagto, java.sql.Date.valueOf(dpemissao.getValue()),
                    java.sql.Date.valueOf(dpdatavencimento.getValue()), ReceberLD.get(0).getValor_total());

            if (contp.salvar(contp, ReceberLD, funcionario, emissor_cheque, qtde_parcelas)) {
                return true;
            }
        } else {
            ContasPagar contp = new ContasPagar();
            if (contp.alterar(ReceberLD, new Despesas(coddes), funcionario, emissor_cheque, qtde_parcelas)) {
                return true;
            }
        }
        return false;
    }

    public boolean excluir(ContasPagar contaspagar) {
        if (contaspagar != null) {
            if (contaspagar.apagar(contaspagar, ReceberLD)) {
                return true;
            }
        }
        return false;
    }

    public void pesquisarTipoDespesas(Despesas des, JFXTextField txcoddes, JFXTextField txtipodes, int cod) {
        Despesas despesas = new Despesas();
        if (des != null) {
            despesas = despesas.get(des.getCodigo());
        } else {
            despesas = despesas.get(cod);
        }

        if (despesas != null) {
            txcoddes.setText(String.valueOf(despesas.getCodigo()));
            txtipodes.setText(despesas.getDescricao());
        } else {
            txcoddes.setText("0");
            txtipodes.setText("Valor digitado não encontrado...");
        }
    }

    public boolean calculaTotalDes(List<ContasPagar> ReceberLD, double totalDes) {
        Double total = 0.0;
        for (ContasPagar receber : ReceberLD) {
            total += receber.getValor();
        }
        if (total < totalDes || total > totalDes) {
            return false;
        }
        return true;
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

    public double calcValor(ContasPagar contasPagar, String tipopagamento) {
        double total = 0.0;
        List<ContasPagar> aux = new ArrayList();
        aux = contasPagar.getConta(contasPagar.getCod_iddes());
        for (ContasPagar contasPagar1 : aux) {
            if (tipopagamento.equals("fixa")) {
                total = contasPagar1.getValor();
            } else {
                total += contasPagar1.getValor();
            }
        }
        return total;
    }

    public boolean carregarDespesa(ContasPagar contaspagar, DatePicker dpemissao, JFXComboBox<CondPagto> cbFormaPagto, JFXTextField txqtdeparc,
            JFXTextField txdiasentreparc, List<ContasPagar> ReceberLD, JFXButton btnexcluir, JFXButton btnfinalizar,
            JFXButton btncancelar, JFXButton btnnovo, TableView<ContasPagar> tabela, Double total, JFXTextField txcod, JFXTextField txcoddes,
            JFXTextField txtipodes, JFXTextField txvalor, DatePicker dpdatavencimento) {
        DALConsulta dal = new DALConsulta();
        if (contaspagar.verificaPagamento(contaspagar)) {
            btnfinalizar.setDisable(true);
            btnexcluir.setDisable(true);
            msg.Error("Prometheus Informa", "Essa despesa possui parcelas pagas!");
            return false;
        } else {
            int ano = contaspagar.getEmissaoDate().getYear() + 1900;
            int mes = contaspagar.getEmissaoDate().getMonth() + 1;
            int dia = contaspagar.getEmissaoDate().getDate();
            dpemissao.setValue(LocalDate.of(ano, mes, dia));
            dpemissao.setShowWeekNumbers(true);

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
            CondPagto cp = new CondPagto();
            cp = cp.get(contaspagar.getCondicaopagamento().getCodigo());
            dpemissao.setConverter(converter);
            txcod.setText(String.valueOf(contaspagar.getCodigo()));
            txcoddes.setText(String.valueOf(contaspagar.getDespesas().getCodigo()));
            txtipodes.setText(contaspagar.getDespesas().getDescricao());
            ContasPagar aux = new ContasPagar();
            List<ContasPagar> auxVe = new ArrayList();
            auxVe = contaspagar.getConta(contaspagar.getCod_iddes());
            int anoV = auxVe.get(0).getVencimentoDate().getYear() + 1900;
            int mesV = auxVe.get(0).getVencimentoDate().getMonth() + 1;
            int diaV = auxVe.get(0).getVencimentoDate().getDate();
            dpdatavencimento.setValue(LocalDate.of(ano, mes, dia));
            dpdatavencimento.setShowWeekNumbers(true);
            String tipopagamento = "";
            tipopagamento = cp.getDescricao();
            if (tipopagamento.toLowerCase().equals("a prazo") || tipopagamento.toLowerCase().equals("crédito")) {
                txqtdeparc.setText(String.valueOf(contaspagar.getQtde_parcela()));
                txdiasentreparc.setText(String.valueOf(contaspagar.getDias_entre()));
            }
            if (tipopagamento.toLowerCase().equals("cheque")) {
                txqtdeparc.setText(contaspagar.getEmissor_cheque());
                //txqtdeparc.setText(aux.getNum_boleto());
            }
            if (tipopagamento.toLowerCase().equals("fixa")) {
                txqtdeparc.setText("");
                String qtde_parc = "";
                qtde_parc = String.valueOf(contaspagar.getQtde_parcela());
                txqtdeparc.setText(qtde_parc);
            }
            double valor = 0;
            valor = calcValor(contaspagar, tipopagamento);
            if (decimal(String.valueOf(valor)) && String.valueOf(valor).length() >= 3 && String.valueOf(valor).length() <= 5 || valor >= 100.0) {
                txvalor.setText(String.valueOf(valor) + "0");
            } else {
                txvalor.setText(String.valueOf(valor));
            }

            cbFormaPagto.getSelectionModel().select(0);
            cbFormaPagto.getSelectionModel().select(cp);
            ReceberLD.clear();
            ReceberLD.addAll(dal.getDespesas(contaspagar.getCod_iddes()));
            atualizarReceber(tabela, ReceberLD);
            btnnovo.setDisable(true);
            btnfinalizar.setDisable(false);
            btnexcluir.setDisable(false);
            return true;
        }
    }

    private void atualizarReceber(TableView<ContasPagar> tabela, List<ContasPagar> ReceberLD) {
        try {
            ObservableList<ContasPagar> contr;
            contr = FXCollections.observableArrayList(ReceberLD);
            tabela.setItems(contr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
