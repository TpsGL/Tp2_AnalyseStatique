package views;

import bootstrap.jdt.ASTCreator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import responsesForQuestions.moyen.AttributesPerClassApp;
import responsesForQuestions.moyen.CodeLinesPerMethodApp;
import responsesForQuestions.moyen.MethodsPerClassApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MoyenDiagramme {

    ASTCreator astCreator; ArrayList<File> javaFiles;

    public MoyenDiagramme(ASTCreator astCreator, ArrayList<File> javaFiles) {
        this.astCreator = astCreator; this.javaFiles = javaFiles;
    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private DefaultPieDataset createDataSetForNombreMoyen() throws IOException {
        final DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Méthodes par classe = 2.0", MethodsPerClassApp.getInstance().averageNumberOfMethodsPerClass(astCreator,javaFiles));
        pieDataset.setValue("Lignes de code par méthode = 8.5", CodeLinesPerMethodApp.getInstance().averageNumberOfCodeLinesPerMethod(astCreator, javaFiles));
        pieDataset.setValue("Attributs par classe = 4.75", AttributesPerClassApp.getInstance().averageNumberOfAttributesPerClass(astCreator,javaFiles));
        return pieDataset;
    }


    public ChartPanel createCombertDiagramme() throws IOException {

        DefaultPieDataset  dataSetMoyenTotal = createDataSetForNombreMoyen();

        final JFreeChart pieChartForNombreMoyen = ChartFactory.createPieChart3D(
                "Nombre moyen de méthodes et d'attributs par classe et lignes de code par méthodes",
                dataSetMoyenTotal,
                true,
                false,
                false);

        final ChartPanel cPanelForNombreMoyen = new ChartPanel(pieChartForNombreMoyen);

        cPanelForNombreMoyen.setBackground(null);
        cPanelForNombreMoyen.setFillZoomRectangle(true);
        cPanelForNombreMoyen.setMouseWheelEnabled(true);
        cPanelForNombreMoyen.setDismissDelay(Integer.MAX_VALUE);
        cPanelForNombreMoyen.setPreferredSize(new Dimension(500, 500));

        return cPanelForNombreMoyen;
    }
}
