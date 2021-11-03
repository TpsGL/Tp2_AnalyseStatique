package views;

import bootstrap.jdt.ASTCreator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import responsesForQuestions.total.ClassesApp;
import responsesForQuestions.total.CodeLinesApp;
import responsesForQuestions.total.MethodsApp;
import responsesForQuestions.total.PackagesApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TotaliyDiagramme {

    ASTCreator astCreator; ArrayList<File> javaFiles;

    public TotaliyDiagramme(ASTCreator astCreator, ArrayList<File> javaFiles) {
        this.astCreator = astCreator; this.javaFiles = javaFiles;
    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private DefaultPieDataset createDataSetNombreTotal() throws IOException {
        final DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Classes = 4", ClassesApp.getInstance().classesNumber(astCreator,javaFiles));
        pieDataset.setValue("Lignes de code = 680", CodeLinesApp.getInstance().codeLinesNumberApp(astCreator, javaFiles));
        pieDataset.setValue("Méthodes = 8", MethodsApp.getInstance().methodNumber(astCreator,javaFiles));
        pieDataset.setValue("Packages = 4", PackagesApp.getInstance().packageNumberApp(astCreator,javaFiles));
        return pieDataset;
    }

     public ChartPanel createCombertDiagramme() throws IOException {

        DefaultPieDataset dataSetNombreTotal = createDataSetNombreTotal();

        final JFreeChart pieChartForNombreTotal = ChartFactory.createPieChart3D(
                "Nombre Total de (Classes, Lignes de Code, Méthodes et Package) d'Application",
                dataSetNombreTotal,
                true,
                false,
                false);

        final ChartPanel cPanelForNombreTotal = new ChartPanel(pieChartForNombreTotal);

         cPanelForNombreTotal.setBackground(null);
         cPanelForNombreTotal.setFillZoomRectangle(true);
         cPanelForNombreTotal.setMouseWheelEnabled(true);
         cPanelForNombreTotal.setDismissDelay(Integer.MAX_VALUE);
         cPanelForNombreTotal.setPreferredSize(new Dimension(500, 500));

        return cPanelForNombreTotal;
    }

}
