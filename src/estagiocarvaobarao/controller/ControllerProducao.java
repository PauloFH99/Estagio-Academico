/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import estagiocarvaobarao.entidade.Armazem;
import estagiocarvaobarao.entidade.Funcionario;
import estagiocarvaobarao.entidade.Producao;
import estagiocarvaobarao.entidade.Producao_Produtos;
import estagiocarvaobarao.entidade.Produto;
import estagiocarvaobarao.ui.TelaProducaoController;
import static estagiocarvaobarao.ui.TelaProducaoController.Produtos;
import static estagiocarvaobarao.ui.TelaProducaoController.total_kilos;
import static estagiocarvaobarao.ui.TelaProducaoController.total_pacotes;
import estagiocarvaobarao.utils.Mensagens;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Paulo
 */
public class ControllerProducao {

    Mensagens msg = new Mensagens();

    public void oxexitProduto(int cod_prod, KeyEvent event, JFXTextField txcodprod, JFXTextField txproddesc) {
        if (event.getCode() == KeyCode.TAB && cod_prod != 0) {
            pesquisarProduto(new Produto(cod_prod), txcodprod, txproddesc);
        }

    }

    public void pesquisarProduto(Produto produto, JFXTextField txcodprod, JFXTextField txproddesc) {

        produto = produto.get(produto.getCodigo());

        if (produto != null) {
            txcodprod.setText(String.valueOf(produto.getCodigo()));
            txproddesc.setText(produto.getDescricao());

        } else {
            txcodprod.setText("0");
            txproddesc.setText("Valor digitado não encontrado...");

        }
    }

    public void atualizarTabela(TableView<Producao_Produtos> tabela, List<Producao_Produtos> Produtos, Label lbtotkilos, Label lbtotpacotes, JFXTextField txtqtdekgmoinha, Label lbtotmoinha) {
        ObservableList<Producao_Produtos> prod_v = null;

        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        String totkilos = "";
        int totpac = 0;
        double tot = 0.0;
        if (Produtos != null) {
            prod_v = FXCollections.observableArrayList(Produtos);
            for (Producao_Produtos Produto : Produtos) {
                totpac += Produto.getQtde_pacotes();
                tot += Produto.getQtde_pacotes() * Produto.getProduto().getPeso();
            }
            total_kilos = tot;
            total_pacotes = totpac;
            lbtotkilos.setText("Total de kilos:  " + tot + "Kg");
            lbtotpacotes.setText("Quantidade de Pacote:" + String.valueOf(totpac));
            if (!txtqtdekgmoinha.getText().isEmpty()) {
                lbtotmoinha.setText("Quantidade em Kg de Moinha: " + txtqtdekgmoinha.getText());
            }
        }
        tabela.setItems(prod_v);
    }

    public int addProduto(String cod_prod, JFXTextField txtqtdekgmoinha, JFXTextField txtqtdepac,
            JFXTextField txtqtdepackg, JFXTextField txtqtdeperda, DatePicker emissao,
            TableView<Producao_Produtos> tabela, List<Producao_Produtos> Produtos, Label lbtotkilos, Label lbtotpacotes, Label lbtotmoinha) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date emissaoData;
        emissaoData = Date.from(emissao.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Produto aux = new Produto();
        aux = aux.get(Integer.parseInt(cod_prod));
        Double total = 0.0;
        total = Double.parseDouble(txtqtdepac.getText()) * aux.getPeso();
        txtqtdepackg.setText(String.valueOf(total) + "KG");
        boolean achou = false;
        int indexProd = 0, qtdeAltera = 0;
        for (Producao_Produtos Produto : Produtos) {
            if (Produto.getCodprod() == Integer.parseInt(cod_prod)) {
                indexProd = Produtos.indexOf(Produto);
                qtdeAltera = Produto.getQtde_pacotes() + Integer.parseInt(txtqtdepac.getText());
                achou = !achou;
            }
        }
        if (achou) {
            total = qtdeAltera * aux.getPeso();
            Produtos.get(indexProd).setQtde_pacotes(qtdeAltera);

        } else {
            Produtos.add(new Producao_Produtos(aux, Integer.parseInt(cod_prod), Integer.parseInt(txtqtdepac.getText()),
                    total));
        }

        atualizarTabela(tabela, Produtos, lbtotkilos, lbtotpacotes, txtqtdekgmoinha, lbtotmoinha);
        return 1;

    }

    public void oxexitTotal(int cod, KeyEvent event, JFXTextField txtqtdepac, JFXTextField txtqtdepackg) {
        if (event.getCode() == KeyCode.TAB && cod != 0) {
            Produto aux = new Produto();
            aux = aux.get(cod);
            Double total = 0.0;
            total = Double.parseDouble(txtqtdepac.getText()) * aux.getPeso();
            txtqtdepackg.setText(String.valueOf(total) + "KG");
        }
    }

    public void alteraEstoque(List<Producao_Produtos> Produtos, String tipo) {
        int tot_kilos = 0;
        Armazem arm = new Armazem();
        Armazem armAlca = new Armazem();
        Armazem armacendedor = new Armazem();
        Armazem armcarvaobruto = new Armazem();
        Armazem embalagem = new Armazem();
        armAlca = arm.get("alça de plastico");
        armacendedor = arm.get("acendedor");
        armcarvaobruto = arm.get("carvão bruto");
        for (Producao_Produtos Produto : Produtos) {
            if (Produto.getProduto().getCategoria().getDescricao().toLowerCase().equals("carvão saco de papel")
                    && arm.get(Produto.getProduto().getDescricao()) != null && armacendedor != null
                    && armAlca != null) {
                if (Produto.getProduto().getPeso() == 3.0) {
                    embalagem = embalagem.get("Embalagem de papel", "3kg");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armAlca.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armacendedor.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                } else if (Produto.getProduto().getPeso() == 5.0) {
                    embalagem = embalagem.get("Embalagem de papel", "5kg");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armAlca.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armacendedor.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                } else if (Produto.getProduto().getPeso() == 7.0) {
                    embalagem = embalagem.get("Embalagem de papel", "7kg");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armacendedor.getCod_prod(), Produto.getQtde_pacotes(), tipo);

                }
            } else if (Produto.getProduto().getCategoria().getDescricao().toLowerCase().equals("carvão saco de papel real")
                    && armAlca != null) {
                if (Produto.getProduto().getPeso() == 2.5) {
                    embalagem = embalagem.get("Embalagem de papel real", "2.5kg");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armAlca.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                }
            }
            tot_kilos += Produto.getQtde_pacotes() * Produto.getProduto().getPeso();
        }
        if (armcarvaobruto != null) {
            arm.alterar(armcarvaobruto.getCod_prod(), tot_kilos, tipo);
        }

    }

    public void alteraEstoqueProd(List<Producao_Produtos> Produtos, String tipo) {
        int tot_kilos = 0;
        Armazem arm = new Armazem();
        Armazem armAlca = new Armazem();
        Armazem armacendedor = new Armazem();
        Armazem armcarvaobruto = new Armazem();
        Armazem embalagem = new Armazem();
        armAlca = arm.get("alça de plastico");
        armacendedor = arm.get("acendedor");
        armcarvaobruto = arm.get("carvão bruto");
        for (Producao_Produtos Produto : Produtos) {
            Producao_Produtos qtde_ant = Produto.qtdeprodAnterior(Produto.getProduto().getCodigo(), Produto.getProducao().getCodigo());
            if (Produto.getProduto().getCategoria().getDescricao().toLowerCase().equals("carvão saco de papel")
                    && arm.get(Produto.getProduto().getDescricao()) != null && armacendedor != null
                    && armAlca != null) {
                if (Produto.getProduto().getPeso() == 3.0 || Produto.getProduto().getPeso() == 5.0) {
                    //retira valor anterior
                    embalagem = embalagem.get("Embalagem de papel", Produto.getProduto().getDescricao());
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), "s");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armAlca.getCod_prod(), qtde_ant.getQtde_pacotes(), "s");
                    arm.alterar(armacendedor.getCod_prod(), qtde_ant.getQtde_pacotes(), "s");

                    arm.alterar(armAlca.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armacendedor.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                } else if (Produto.getProduto().getPeso() == 7.0) {
                    //retira valor anterior
                    embalagem = embalagem.get("Embalagem de papel", "7kg");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), "s");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armacendedor.getCod_prod(), qtde_ant.getQtde_pacotes(), "s");

                    arm.alterar(armacendedor.getCod_prod(), Produto.getQtde_pacotes(), tipo);

                }
            } else if (Produto.getProduto().getCategoria().getDescricao().toLowerCase().equals("carvão saco de papel real")
                    && armAlca != null) {
                if (Produto.getProduto().getPeso() == 2.5) {
                    //retira valor anterior
                    embalagem = embalagem.get("Embalagem de papel real", "2.5kg");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), "s");
                    arm.alterar(embalagem.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                    arm.alterar(armAlca.getCod_prod(), qtde_ant.getQtde_pacotes(), "s");
                    arm.alterar(armAlca.getCod_prod(), Produto.getQtde_pacotes(), tipo);
                }
            }
            tot_kilos += Produto.getQtde_pacotes() * Produto.getProduto().getPeso();
        }
        if (armcarvaobruto != null) {
            //retira valor anterior
            arm.alterar(armcarvaobruto.getCod_prod(), tot_kilos, "s");

            arm.alterar(armcarvaobruto.getCod_prod(), tot_kilos, tipo);
        }

    }

    public boolean gravar(int cod, Funcionario func, DatePicker dpemissao, JFXTextField txcodprod,
            JFXTextField txtqtdekgmoinha, JFXTextField txtqtdepac, JFXTextField txtqtdepackg,
            JFXTextField txtqtdeperda, TableView<Producao_Produtos> tabela, List<Producao_Produtos> Produtos,
            JFXButton btnfinalizar, JFXButton btncancelar, JFXButton btnexcluir, JFXButton btnnovo, Pane conteudo,
            Label lbtotkilos, Label lbtotpacotes, List<TelaProducaoController.AuxValor> aux, Label lbtotmoinha) {
        int qtde_moinha, qtde_perda;
        try {
            qtde_moinha = Integer.parseInt(txtqtdekgmoinha.getText());
        } catch (Exception e) {
            qtde_moinha = 0;
        }
        try {
            qtde_perda = Integer.parseInt(txtqtdeperda.getText());
        } catch (Exception e) {
            qtde_perda = 0;
        }

        if (cod == 0) {

            Producao producao = new Producao(cod, func, java.sql.Date.valueOf(dpemissao.getValue()), total_kilos, qtde_moinha, qtde_perda);

            if (producao.salvar(producao, Produtos)) {

                msg.Affirmation("Gravação concluida", "Produção gravada com sucesso!");
                alteraEstoque(Produtos, "s");
                atualizarTabela(tabela, null, lbtotkilos, lbtotpacotes, txtqtdekgmoinha, lbtotmoinha);
                return true;
            } else {
                msg.Affirmation("Erro ao gravar!", "Problemas ao Gravar!");
            }

        } else {
            Producao producao = new Producao(cod, func, java.sql.Date.valueOf(dpemissao.getValue()), total_kilos, qtde_moinha, qtde_perda);
            if (producao.alterar(producao, Produtos, aux)) {
                msg.Affirmation("Gravação concluida", "Produção alterado com sucesso!");
                alteraEstoqueProd(Produtos, "e");
                atualizarTabela(tabela, null, lbtotkilos, lbtotpacotes, txtqtdekgmoinha, lbtotmoinha);
                return true;
            } else {
                msg.Affirmation("Erro ao gravar!", "Problemas ao alterar!");
            }
        }
        return false;
    }

    public boolean excluir(Producao prod, ActionEvent event) {
        if (prod != null) {
            if (msg.Confirmation("", "Confirmar exclusão?")) {

                boolean verif = false;
                Producao_Produtos novo = new Producao_Produtos();
                List<Producao_Produtos> aux = new ArrayList();

                aux = novo.getporProducao(prod.getCodigo());
                alteraEstoque(aux, "e");
                for (Producao_Produtos producao_Produtos : aux) {
                    verif = producao_Produtos.apagar(producao_Produtos);

                }
                if (verif) {
                    return prod.apagar(prod);
                }

            }

        }
        return false;
    }

    public List<Producao_Produtos> pesquisarProducao(Producao producao, TableView<Producao_Produtos> tabela,
            List<Producao_Produtos> Produtos, Label lbtotkilos, Label lbtotpacotes, Label lbtotmoinha, JFXTextField txtqtdekgmoinha) {
        if (producao != null) {
            Producao_Produtos aux = new Producao_Produtos();
            Produtos.clear();
            Produtos = aux.getporProducao(producao.getCodigo());
            atualizarTabela(tabela, Produtos, lbtotkilos, lbtotpacotes, txtqtdekgmoinha, lbtotmoinha);
            return Produtos;
        }
        return null;
    }

    public void atualizarTabela(TableView<Producao_Produtos> tabela, Producao producao, List<Producao_Produtos> Produtos, Label lbtotkilos, Label lbtotpacotes, JFXTextField txtqtdekgmoinha, Label lbtotmoinha) {
        ObservableList<Producao_Produtos> prod_v = null;
        Producao_Produtos aux = new Producao_Produtos();
        Produtos = aux.getporProducao(producao.getCodigo());
        if (tabela.getItems() != null) {
            tabela.getItems().clear();
        }
        String totkilos = "";
        int totpac = 0;
        double tot = 0.0;
        if (Produtos != null) {
            prod_v = FXCollections.observableArrayList(Produtos);
            for (Producao_Produtos Produto : Produtos) {
                totpac += Produto.getQtde_pacotes();
                tot += Produto.getQtde_pacotes() * Produto.getProduto().getPeso();
            }
            total_kilos = tot;
            total_pacotes = totpac;
            lbtotkilos.setText("Total de kilos:  " + tot + "Kg");
            lbtotpacotes.setText("Quantidade de Pacote:" + String.valueOf(totpac));
            if (!txtqtdekgmoinha.getText().isEmpty()) {
                lbtotmoinha.setText("Quantidade em Kg de Moinha: " + txtqtdekgmoinha.getText());
            }
        }
        tabela.setItems(prod_v);
    }

}
