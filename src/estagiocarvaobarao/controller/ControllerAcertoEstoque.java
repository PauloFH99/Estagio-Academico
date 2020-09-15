/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.dal.DALArmazem;
import estagiocarvaobarao.entidade.AcertoEstoque;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Produto;
import static estagiocarvaobarao.ui.TelaAcertoEstoqueController.prod;
import estagiocarvaobarao.utils.Mensagens;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 *
 * @author Paulo
 */
public class ControllerAcertoEstoque {

    Mensagens msg = new Mensagens();

    public boolean gravar(int cod, Date emissao, String tipo, int codprod, String obser, int qtde, Funcionario funcionario, String estoque) {
        AcertoEstoque ae = new AcertoEstoque(cod, emissao, tipo, new Produto(codprod), funcionario, obser, qtde, estoque);
        DALArmazem dal = new DALArmazem();
        if (cod == 0) {
            if (ae.salvar(ae)) {
                if (estoque.equals("a")) {

                    if (tipo.equals("e")) {
                        if (dal.alterar(codprod, qtde, "e")) {
                            msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                            return true;
                        }
                    } else {
                        if (dal.alterar(codprod, qtde, "s")) {
                            msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                            return true;
                        }
                    }
                } else {
                    //estoque casa
                }

            }
        } else {
            AcertoEstoque aux_qtde = new AcertoEstoque();
            aux_qtde = aux_qtde.get(cod);
            if (ae.alterar(ae)) {
                if (estoque.equals("a")) {
                    if (tipo.equals("e")) {
                        if (dal.alterar(codprod, aux_qtde.getQuantidade(), "s")) {
                            if (dal.alterar(codprod, qtde, "e")) {
                                msg.Confirmation("Gravação concluida", "Alterado com Sucesso");
                                return true;
                            }
                        }
                    } else {
                        if (dal.alterar(codprod, aux_qtde.getQuantidade(), "e")) {
                            if (dal.alterar(codprod, qtde, "s")) {
                                msg.Confirmation("Gravação concluida", "Gravado com Sucesso");
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    public void estadoInicial(Pane pnconteudo, Pane pnbotoes, JFXButton btnfinalizar,JFXButton btnpesquisar, JFXButton btncancelar, JFXButton btnnovo, DatePicker dpemissao, Label lbqtde) {
        pnconteudo.setDisable(true);
        pnbotoes.setDisable(false);
        btnfinalizar.setDisable(true);
       
        btnpesquisar.setDisable(true);
        btncancelar.setDisable(false);
        btnnovo.setDisable(false);
        dpemissao.setValue(null);
        lbqtde.setText("");
        ObservableList<Node> componentes = pnconteudo.getChildren();//”limpa” os componentes
        for (Node n : componentes) {
            if (n instanceof TextInputControl)//textfield, textarea e htmleditor
            {
                ((TextInputControl) n).setText("");
            }
        }
    }

    public void estadoEdicao(Pane pnconteudo, JFXButton btnfinalizar, JFXButton btncancelar, JFXButton btnpesquisar,
           JFXButton btnnovo, DatePicker dpemissao, JFXTextField txcod,
            JFXTextField txcodprod, JFXTextField txqtde, JFXTextField txobser, Produto prod) {
        Mensagens msg = new Mensagens();
        pnconteudo.setDisable(false);
        btnfinalizar.setDisable(false);
        btncancelar.setDisable(false);
        btnpesquisar.setDisable(false);
       
        btnnovo.setDisable(false);
        dpemissao.requestFocus();

    }

    public void pesquisarAcerto(AcertoEstoque ae, DatePicker dpemissao, JFXTextField txcod,
            JFXTextField txqtde, JFXTextField txqtdefinal, JFXTextField txcodusu, JFXTextField txusunome,
            JFXTextField txcodprod, JFXTextField txprod, JFXTextField txobser, JFXRadioButton rbsaida,
            JFXRadioButton rbentrada, JFXRadioButton rbarmazem, JFXRadioButton rbgeral) {
        AcertoEstoque acertoestoque = new AcertoEstoque();

        if (ae != null) {
            acertoestoque = acertoestoque.get(ae.getCodigo());

            int ano = acertoestoque.getEmissaoDate().getYear() + 1900;
            int mes = acertoestoque.getEmissaoDate().getMonth() + 1;
            int dia = acertoestoque.getEmissaoDate().getDate();
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
            dpemissao.setConverter(converter);
            txcod.setText(String.valueOf(acertoestoque.getCodigo()));
            String valor = "";
            txqtde.setText(String.valueOf(acertoestoque.getQuantidade()));
            txcodprod.setText(String.valueOf(acertoestoque.getProduto().getCodigo()));
            txprod.setText(acertoestoque.getProduto().getDescricao());
            txobser.setText(acertoestoque.getObservacoes());
            if (acertoestoque.getTipo().toLowerCase().equals("Saída") || acertoestoque.getTipo().equals("s")) {
                rbsaida.setSelected(true);
                rbentrada.setSelected(false);
            } else {
                rbentrada.setSelected(true);
                rbsaida.setSelected(false);
            }
            if (acertoestoque.getEstoque().equals("a")) {
                rbarmazem.setSelected(true);
            } 

        } else {
            txcod.setText("0");
            txprod.setText("Valor digitado não encontrado...");
        }
    }

    public void pesquisarProduto(Produto prod, JFXTextField txcodprod, JFXTextField txprod, JFXTextField txqtde) {
        Produto produto = new Produto();
        if (prod != null) {
            produto = produto.get(prod.getCodigo());
        } else {
            produto = produto.get(Integer.parseInt(txcodprod.getText()));
        }

        if (produto != null) {
            txcodprod.setText(String.valueOf(produto.getCodigo()));
            txprod.setText(produto.getDescricao());

        } else {
            txcodprod.setText("0");
            txprod.setText("Valor digitado não encontrado...");
        }
    }

    public void cancelar(Pane pnconteudo, Pane pnbotoes, JFXButton btnfinalizar, JFXButton btnpesquisar, JFXButton btncancelar, JFXButton btnnovo, DatePicker dpemissao, Label lbqtde) {
        if (!pnconteudo.isDisabled())//encontra em estado de edição
        {
            estadoInicial(pnconteudo, pnbotoes, btnfinalizar, btnpesquisar, btncancelar, btnnovo,
                    dpemissao, lbqtde);
        } else {
            btnnovo.getScene().getWindow().hide();//fecha a janela
        }
    }

    public void pesquisarProduto(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void CalcQtdeProd(JFXTextField txcodprod, JFXTextField txqtde, JFXRadioButton rbarmazem, JFXRadioButton rbentrada, JFXTextField txqtdefinal) {
        Armazem a = new Armazem();
        int codp = Integer.parseInt(txcodprod.getText()), total = 0;
        if (rbarmazem.isSelected()) {
            a = a.get(codp);
            if (rbentrada.isSelected()) {
                total = a.getQuantidade() + Integer.parseInt(txqtde.getText());
                txqtdefinal.setText("" + total);
            } else {
                if (Integer.parseInt(txqtde.getText()) > a.getQuantidade()) {
                    msg.Error("Prometheus Informa", "Quantidade insuficiente no estoque!");
                } else {
                    total = a.getQuantidade() - Integer.parseInt(txqtde.getText());
                    txqtdefinal.setText("" + total);
                }
            }
        }
    }

}
