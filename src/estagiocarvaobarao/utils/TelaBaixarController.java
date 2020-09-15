/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import estagiocarvaobarao.entidade.Cliente;
import estagiocarvaobarao.entidade.ContasPagar;
import estagiocarvaobarao.entidade.ContasReceber;
import estagiocarvaobarao.ui.TelaContasPagarController;
import estagiocarvaobarao.ui.TelaReceberFiadoController;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaBaixarController implements Initializable {

    @FXML
    private DatePicker dppago;
    @FXML
    private JFXTextField txvalor;
    @FXML
    private JFXButton btnOk;
    public static ContasReceber contasreceberclic;

    public static ContasReceber getContasreceberclic() {
        return contasreceberclic;
    }

    public static void setContasreceberclic(ContasReceber contasreceberclic) {
        TelaBaixarController.contasreceberclic = contasreceberclic;
    }

    public static ContasPagar contaspagarclic;

    public static ContasPagar getContaspagarclic() {
        return contaspagarclic;
    }

    public static void setContaspagarclic(ContasPagar contaspagarclic) {
        TelaBaixarController.contaspagarclic = contaspagarclic;
    }
    @FXML
    private Label lbvalor;
    @FXML
    private Label lbdata;
    Mensagens msg = new Mensagens();
    public static int tipo;

    public static int getTipo() {
        return tipo;
    }

    public static void setTipo(int tipo) {
        TelaBaixarController.tipo = tipo;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MaskFieldUtil.monetaryField(txvalor);
        dppago.setValue(LocalDate.now());

    }

    public void validar(JFXTextField campo, String texto) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        campo.resetValidation();
        campo.getValidators().add(validator);
        validator.setMessage(texto);
        campo.validate();
    }

    public String gerarparcela() {
        List<ContasPagar> listparcelas = new ArrayList<ContasPagar>();
        listparcelas = contaspagarclic.getConta(contaspagarclic.getCod_iddes());
        ContasPagar parcelaC = listparcelas.get(listparcelas.size() - 1);
        String parcelaNum = "";
        String parcela = "";
        int aux_parcela;
        int i = 0, j = 0;
        for (i = 0; i < parcelaC.getParcela().length(); i++) {
            if (parcelaC.getParcela().charAt(i) == '/') {
                j = i;
                i = parcelaC.getParcela().length();
            }
        }

        for (i = j + 1; i < parcelaC.getParcela().length(); i++) {
            parcelaNum += parcelaC.getParcela().charAt(i);
        }
        aux_parcela = Integer.parseInt(parcelaNum) + 1;
        for (ContasPagar listparcela : listparcelas) {
            listparcela.setParcela(listparcela.getParcela().charAt(0) + "/" + String.valueOf(aux_parcela));
//            listparcela.alterar(listparcela);
        }
        parcela = String.valueOf(aux_parcela) + "/" + String.valueOf(aux_parcela);

        return parcela;
    }

    public List<ContasPagar> gerarResto(ContasPagar cp, Double valor, Double valor_pen, String parcela) {
        List<ContasPagar> listc = new ArrayList<ContasPagar>();
        listc.add(cp);
        listc.get(0).setValor_pago(0.0);
        listc.get(0).setValor_pendente(0.0);
        listc.get(0).setValor(valor_pen);
        listc.get(0).setParcela(parcela);
        listc.get(0).setValor_total(0.0);
        listc.get(0).setCod_iddes(cp.getCod_iddes());
        return listc;
    }

    @FXML
    private void evtBtnOk(ActionEvent event) throws ParseException {

        LocalDate pago = dppago.getValue();
        NumberFormat nf = new DecimalFormat("#,###.0000");
        double valor_pen = 0.0;
        String parcela = "";
        Double valor = 0.0;
        int erro = 0, erroV = 0, cod = 0, codcli = 0;
        if (!txvalor.getText().isEmpty()) {
            valor = nf.parse(txvalor.getText()).doubleValue();
        }
        if (contaspagarclic != null) {
            contaspagarclic = contaspagarclic.get(contaspagarclic.getCodigo());
            valor_pen = contaspagarclic.getValor() - valor;
            try {
                cod = contaspagarclic.getCodigo();
            } catch (Exception e) {
                cod = 0;
            }

        }
        if (contasreceberclic != null) {
            contasreceberclic = contasreceberclic.get(contasreceberclic.getCodigo());
            valor_pen = contasreceberclic.getValor_pendente() - valor;
            try {
                cod = contasreceberclic.getCodigo();
            } catch (Exception e) {
                cod = 0;
            }
        }

        if (txvalor.getText().trim().isEmpty()) {
            validar(txvalor, "Campo não pode estar vazio!");
            erro = 1;

        } else if (tipo == 1) {

            if (valor > contaspagarclic.getValor()) {
                msg.campoNumerico(txvalor, lbvalor, "Valor maior do que o da conta.");
                txvalor.requestFocus();
                erro = 1;
                erroV = 1;
                //exit(1);
            } else if (erroV == 0) {
                msg.desativarLabel(lbvalor);
            }
            if (contaspagarclic.getValor_pendente() == 0 && contaspagarclic.getData_pago() != null && contaspagarclic.getValor_pago() == contaspagarclic.getValor()) {
                msg.Error("", "A conta selecionada já esta paga.");
                erroV = 1;
                erro = 1;
                //exit(1);
            } else if (erroV == 0) {
                msg.desativarLabel(lbvalor);
            }
            if (dppago.getValue() == null) {
                msg.campoNumerico(new JFXTextField(), lbdata, "A data do pagamento não foi selecionada.");

                erro = 1;
                //exit(1);
            } else {
                msg.desativarLabel(lbdata);
            }
        } else if (tipo == 2) {

        } else if (tipo == 3) {
            try {
                codcli = contasreceberclic.getCliente().getCodigo();
            } catch (Exception e) {
                codcli = 0;
            }

        }
        if (erro == 0) {
            msg.desativarLabel(lbvalor);
            msg.desativarLabel(lbdata);
            if (valor_pen < 0) {
                valor_pen = 0;
            }

            if (baixar(cod, valor, valor_pen, parcela, pago, tipo)) {
                Stage self = (Stage) btnOk.getScene().getWindow();
                Cliente c = new Cliente(); 
                if (tipo == 3) {
                    c = c.get(codcli);
                    c.alterarSaldo(c,valor,"-");
                }
                self.close();
            } else {
                msg.Error("Prometheus Informa", "Erro ao baixar!");
            }
        }
    }

    public void baixarTotal(ContasPagar cp, int cod, LocalDate pago, double valor, double valor_pen) {
        cp = new ContasPagar(cod, contaspagarclic.getDespesas(), contaspagarclic.getFornecedor(),
                contaspagarclic.getParcela(), contaspagarclic.getValor(), valor, contaspagarclic.getEmissaoDate(),
                java.sql.Date.valueOf(pago), contaspagarclic.getVencimentoDate(), contaspagarclic.getFuncionario(),
                contaspagarclic.getCondicaopagamento(), contaspagarclic.getCod_iddes(), valor_pen, contaspagarclic.getEntradaprodutos(),
                contaspagarclic.getValor_total());
        if (cp.getValor_pendente() > 0) {
            cp.setValor_pendente(cp.getValor_pendente() - valor);
        } else {
            cp.setValor_pendente(cp.getValor() - valor);
        }
        if (cp.getValor_pago() > 0) {
            cp.setValor_pago(cp.getValor_pago() + valor);
        } else {
            cp.setValor_pago(valor);
        }
//        if (cp.alterar(cp)) {
//            msg.Affirmation("Gravação concluida", "Quitação total com Sucesso!");
//        } else {
//            msg.Affirmation("Erro na gravação", "Problemas na quitação total!");
//        }
    }

    public void baixarParcial(ContasPagar cp, int cod, Double valor) {
        String parcela = "";
        parcela = gerarparcela();
        NumberFormat nf = new DecimalFormat("#,###.0000");
        cp = cp.get(cod);

        valor = Double.parseDouble(nf.format(valor).replaceAll(",", "."));

        if (cp.getValor_pendente() > 0) {
            cp.setValor_pendente(cp.getValor_pendente() - valor);
        } else {
            cp.setValor_pendente(cp.getValor() - valor);
        }
        if (cp.getValor_pago() > 0) {
            cp.setValor_pago(cp.getValor_pago() + valor);
        } else {
            cp.setValor_pago(valor);
        }
        cp.setData_pago(java.sql.Date.valueOf(dppago.getValue()));

//        if (cp.alterar(cp)) {
//            msg.Affirmation("Gravação concluida", "Quitação parcial com Sucesso!");
//        } else {
//            msg.Affirmation("Erro na gravação", "Problemas na quitação parcial!");
//        }
        List<ContasPagar> listc = new ArrayList<ContasPagar>();
        listc = gerarResto(cp, cp.getValor(), cp.getValor_pendente(), parcela);

//        if (cp.salvar(cp, listc)) {
//            msg.Affirmation("Quitação concluida", "Nova parcela gerada com sucesso!");
//        } else {
//            msg.Affirmation("Erro na quitação", "Problemas na quitação!");
//        }
    }

    public boolean baixar(int cod, Double valor, Double valor_pen, String parcela, LocalDate pago, int tipo) {
        ContasPagar cp = new ContasPagar();
        NumberFormat nf = new DecimalFormat("#,###.0000");
        if (tipo == 1) {
        } else if (tipo == 2) {
        } else if (tipo == 3) {
            contasreceberclic.setData_pago(java.sql.Date.valueOf(pago));
            contasreceberclic.setValor_pendente(valor_pen);
            contasreceberclic.setValor_pago(contasreceberclic.getValor_pago() + valor);
            if (contasreceberclic.alterar(contasreceberclic)) {
                TelaReceberFiadoController.setValorBaixado(TelaReceberFiadoController.getValorBaixado() + valor);
                msg.Confirmation("Prometheus Informa", "Baixado com sucesso!");
                return true;
            }
        }
//        if (contaspagarclic.getData_pago() == null && contaspagarclic.getValor_pago() == 0) {
//            if (valor_pen == 0) {
//                baixarTotal(cp, cod, pago, valor, valor_pen);
//            } else {
//                baixarParcial(cp, cod, valor);
//            }
//        } else {
//
//            if (contaspagarclic.getValor_pendente() == valor) {
//                baixarTotal(cp, cod, pago, valor, valor_pen);
//            } else {
//                baixarParcial(cp, cod, valor);
//            }
//        }
//        TelaContasPagarController.setValorbaixado(valor);
        return false;
    }

    private void exit(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
