package views;

import bootstrap.ASTCreator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;
import responsesForQuestions.total.ClassesApp;
import views.MoyenDiagramme;
import views.TotaliyDiagramme;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResultView {



    /**
     * Starting point for the demonstration application.
     *
     *
     */
    public void launchView(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        JFrame frame = new JFrame("TP2 Evolution et Restructuration");
        frame.setLayout( new FlowLayout() );

        ChartPanel chartPanelTotal = (new TotaliyDiagramme(astCreator, javaFiles)).createCombertDiagramme();
        frame.add( chartPanelTotal );
        ChartPanel chartPanelMoyen = (new MoyenDiagramme(astCreator, javaFiles)).createCombertDiagramme();
        frame.add( chartPanelMoyen );

        JPanel jPanel = (new InformationApp(astCreator, javaFiles)).createInformations();
        frame.add(jPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
