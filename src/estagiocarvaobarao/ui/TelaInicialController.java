/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.ui;

import com.jfoenix.controls.JFXButton;
import estagiocarvaobarao.controller.ControllerTelaInicial;

import estagiocarvaobarao.entidade.Funcionario;
import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Paulo
 */
public class TelaInicialController implements Initializable {

    public static Funcionario func;

    public static Funcionario getFunc() {
        return func;
    }

    public static void setFunc(Funcionario func) {
        TelaInicialController.func = func;
        TelaProducaoController.func = func;
        TelaVendaController.func = func;
        TelaEntradaProdutosController.funcionario = func;
        TelaAcertoEstoqueController.funcionario = func;
        TelaLancarDespesasController.funcionario = func;
        TelaPedidosCaminhaoController.func=func;
    }
    @FXML
    private HBox menubar;
    public boolean flag = true;

    @FXML
    private BorderPane tela;
    @FXML
    private JFXButton btnsair;
    @FXML
    private Label lbHome = new Label();
    @FXML
    private ScrollPane painelcentral;
    @FXML
    private VBox sidebar;
    @FXML
    private HBox lancD;
    private Node lado;
    private Node home;
    @FXML
    private Menu menuCad;
    @FXML
    private Menu menuCompra;
    @FXML
    private Menu menuEstoque;
    @FXML
    private Menu menuFluxoCaixa;
    @FXML
    private Menu menuVenda;
    @FXML
    private Menu MenuRelatorio;
    ControllerTelaInicial ctl = new ControllerTelaInicial();
    @FXML
    private ImageView logoCarvao = new ImageView();
    @FXML
    private Menu menuProducao;
    @FXML
    private Menu menuVenda1;
    @FXML
    private Menu menuProducao1;
    @FXML
    private AnchorPane logoinit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        if (func != null) {
            verificarAcesso(func);
            ctl.telabemvindo(lbHome, func.getNome(), logoCarvao,logoinit);

        }
        lado = tela.getLeft();
        home = painelcentral.getContent();
        tela.setLeft(null);

    }

    public void AtualizaTela() {
        if (func != null) {
            ctl.telabemvindo(lbHome, func.getNome(), logoCarvao,logoinit);
        }
    }

    public void center(Bounds viewPortBounds, Parent centeredNode) {
        double width = viewPortBounds.getWidth();
        double height = viewPortBounds.getHeight();
        double pwidth = ((Region) centeredNode).getPrefWidth();
        double pheight = ((Region) centeredNode).getPrefHeight();

        if (width > pwidth) {
            centeredNode.setTranslateX((width - pwidth) / 2);
        } else {
            centeredNode.setTranslateX(0);
        }
        if (height > pheight) {
            centeredNode.setTranslateY((height - pheight) / 2);
        } else {
            centeredNode.setTranslateY(0);
        }

    }

    public void verificarAcesso(Funcionario func) {
        //Usuário Operador
        if (func.getNivel().getCodigo() == 2) {
            menuCad.getItems().get(4).setVisible(false); // Cadastro Funcionario
            menuCad.getItems().get(5).setVisible(false); // Cadastro Fornecedor
            menuFluxoCaixa.setVisible(false);  // Fluxo Caixa
            MenuRelatorio.setVisible(false); // Relatórios
        }
    }

    public void chamaTela(String telaParam) {
        try {
            Parent telaP = FXMLLoader.load(getClass().getResource("TelaProdCad.fxml"));
            painelcentral.setContent(null);
            painelcentral.setContent(telaP);
            this.center(painelcentral.getViewportBounds(), telaP);
            painelcentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
                this.center(newValue, telaP);
            });
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadProduto(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaProdCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Produto");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void abrir_menulateral(ActionEvent event) throws IOException {
        if (flag == true) {
            // Parent sidebar = FXMLLoader.load(getClass().getResource("MenuLateral.fxml"));
            tela.setLeft(lado);
            tela.getLeft().setVisible(flag);
            flag = false;
        } else {
            tela.setLeft(null);
            painelcentral.setContent(null);
            painelcentral.setContent(home);
            flag = true;
        }
    }

    @FXML
    private void clkCadFornecedor(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaFornecedorCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Fornecedores");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadFuncionario(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaFuncionarioCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Funcionário");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadNivelFuncionarios(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaNivelFuncionarioCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Nível de Funcionário");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadCategorias(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaCategoriaCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Categorias");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadDespesas(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaDespesasCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Tipos de Despesas");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadCliente(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaClienteCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Clientes");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadEmpresa(ActionEvent event) {
        try {
            TelaEmpresaParametrosCadController.setFunc(func);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaEmpresaParametrosCad.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Cadastro de Empresa");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void clkAcertoEstoque(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaAcertoEstoque.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Acerto de Estoque");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkLançarDespesas(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaLancarDespesas.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Lançar Despesas");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkContasaPagar(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaContasPagar.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Contas a Pagar");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void SairT(ActionEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja sair?");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            btnsair.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("TelaAutenticacao.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Autenticação de Usuário");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.show();
        }
    }

    @FXML
    private void clkVenda(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaVenda.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Venda");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCompra(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaEntradaProdutos.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Compra");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCompensarCheque(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaCompensarCheque.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Compensar Cheque");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void lancarDesp(MouseEvent event) {
        chamaTela("TelaCategoriaCad.fxml");
    }

    @FXML
    private void Sair(MouseEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Deseja sair?");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            btnsair.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("TelaAutenticacao.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Autenticação de Usuário");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.show();
        }
    }

    @FXML
    private void home(MouseEvent event) {
        painelcentral.setContent(null);
        painelcentral.setContent(home);
    }

    @FXML
    private void clkProducao(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaProducao.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Produção");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkReceberFiado(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaReceberFiado.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Receber Fiado");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void relProdutos(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("RelProduto.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Relatório Produtos");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void relClientes(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("RelCliente.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Relatório Clientes");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void relVendas(ActionEvent event) {

    }

    @FXML
    private void relCompras(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("RelCompra.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Relatório Compras");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void relCheques(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("RelCheques.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Relatório Cheques");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkEstoqueaux(ActionEvent event) {
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Estoque.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Estoque");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadCustoFornecedor(ActionEvent event) {
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaCustosporFornecedor.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Custos");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkCadCustoCliente(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaCustosporCliente.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Custos");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkPedidos(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaPedidosArmazem.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Pedidos");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void clkPedidosCaminhao(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("TelaPedidosCaminhao.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Entrega Caminhão");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/estagiocarvaobarao/ui/icons/icon.png")));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
