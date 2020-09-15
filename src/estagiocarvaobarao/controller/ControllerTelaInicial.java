/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagiocarvaobarao.controller;

import estagiocarvaobarao.entidade.EmpresaParametros;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;

/**
 *
 * @author Paulo
 */
public class ControllerTelaInicial {

    public void telabemvindo(Label lbHome, String nome, ImageView logoCarvao,AnchorPane logoinit) {
        EmpresaParametros carvao = new EmpresaParametros();
        List<EmpresaParametros> empresa = new ArrayList();
        empresa = carvao.get("");
        File arquivo = new File("/estagiocarvaobarao/ui/icons/blank.jpg");
        File arquivoaux = new File("/estagiocarvaobarao/ui/icons/blank.jpg");
        FileInputStream arq;

        if (empresa.get(0) != null) {
            lbHome.setText(lbHome.getText() + " " + empresa.get(0).getFantasia() + "!" + " Olá, " + nome);
            InputStream is = empresa.get(0).getFoto(empresa.get(0));
            Image imgaux = null;
            if (is != null) {
                try {
                    BufferedImage bimage = ImageIO.read(is);
                    logoCarvao.setImage(SwingFXUtils.toFXImage(bimage, null));
                    try {
                        setImageGarcon(null, null, arquivoaux, logoCarvao, null, null,logoinit);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ControllerEmpresaParametros.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    arquivoaux = new File("/estagiocarvaobarao/ui/icons/blank.jpg");
                    arquivoaux.createNewFile();
                    ImageIO.write(bimage, "jpg", arquivoaux);

                } catch (IOException ex) {
                    Logger.getLogger(ControllerEmpresaParametros.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                logoCarvao.setImage(imgaux);
                arquivoaux = new File("/estagiocarvaobarao/ui/icons/blank.jpg");
            }

        }

    }

    public void setImageGarcon(File f, File arquivo, File arquivoaux, ImageView logo, Image imagem, FileInputStream arq,AnchorPane logoinit) throws FileNotFoundException {
        if (f != null) {
            arq = new FileInputStream(arquivo);
            logo.setPreserveRatio(true);
            imagem = new Image(arquivo.toURI().toString());
            logo.setImage(imagem);

        }
       logo.setFitWidth(1100);
       logo.setFitHeight(600);
       

    }

}
