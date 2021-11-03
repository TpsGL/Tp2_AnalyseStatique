package views;

import bootstrap.jdt.ASTCreator;
import responsesForQuestions.dixPercen.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InformationApp {

    ASTCreator astCreator; ArrayList<File> javaFiles;

    public InformationApp(ASTCreator astCreator, ArrayList<File> javaFiles) {
        this.astCreator = astCreator; this.javaFiles = javaFiles;
    }


    public JPanel createInformations() throws IOException {
        JPanel panel = new JPanel();
        int row = 50;
        panel.setLayout(new GridLayout(row, 1));


        panel.add(new JLabel("Les 10% des classes qui possèdent le plus grand nombre de méthodes = " +
                MostMethodsPerClassApp.getInstance().classesThatHaveMostMethodsNumber(astCreator, javaFiles))
        );

        panel.add(new JLabel("Les 10% des classes qui possèdent le plus grand nombre d'attributs = " +
                MostAttributesPerClassApp.getInstance().classesThatHaveMostAttributesNumber(astCreator, javaFiles) ));

        panel.add(
                new JLabel("Les classes qui font partie en meme temps des deux catégories précédente = " +
                        MostAttributesMethodsPerClassApp.getInstance().classesThatHaveMostAttributesMethodsNumber(astCreator, javaFiles))
        );

        panel.add(new JLabel("Les classes qui possèdent plus de X méthodes (la valeur de X est donnée) = " +
                XMethodsPerClassApp.getInstance().classesThatHaveXMethodsNumber(astCreator, javaFiles, 5))
        );

        panel.add(new JLabel("Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe) = " +
                MostMethodsCodeLinesPerClass.getInstance().methodsThatHaveMostCodeLinesNumber(astCreator, javaFiles)));

        panel.add(new JLabel("Le nombre maximal de paramètre par rapport à toutes les méthodes de l'application = "
                +  MaxParametersApp.getInstance().maxParametersNumber(astCreator, javaFiles)));
        return panel;

    }

}
