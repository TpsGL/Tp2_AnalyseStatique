package bootstrap;

import responsesForQuestions.dixPercen.*;
import responsesForQuestions.graphAppel.CallGraph;
import responsesForQuestions.moyen.AttributesPerClassApp;
import responsesForQuestions.moyen.CodeLinesPerMethodApp;
import responsesForQuestions.total.MethodsApp;
import responsesForQuestions.moyen.MethodsPerClassApp;
import responsesForQuestions.total.*;
import views.ResultView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserLauncher {

    public static final String projectPath = "C:\\Users\\gm_be\\IdeaProjects\\untitled1";

    //public static final String projectPath="C:\\Users\\gm_be\\IdeaProjects\\Tp2_restructuration\\src\\main\\resources\\Calculator";

    public static final String projectSrcPath=projectPath + "\\src\\";

    public static final String jrePath="C:\\Program Files\\Java\\jdk1.8.0_291";

    public static void main(String[] args) throws IOException {

        ProjectReader projectReader = new ProjectReader();
        final File folder = new File(projectSrcPath);
        ArrayList<File> javaFiles  = projectReader.listJavaFilesForFolder(folder);

        ASTCreator astCreator = new ASTCreator(projectPath, jrePath);


        ResultView resultView = new ResultView();

        //resultView.launchView(astCreator, javaFiles);
        //ClassCounter classCounter = new ClassCounter();
        //AttributesCounter attributesCounter = new AttributesCounter();
        // Nombre total de packages de l’application
        //PackageCounter packageCounter = new PackageCounter();
        //MethodCounter methodCounter = new MethodCounter();
        /*for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            //System.out.println( attributesCounter.countItems(cu) );
            System.out.println(methodCounter.countItems(cu));
        }*/
        /*System.out.println( "Nombre totale de classes = " +
                ClassesApp.getInstance().classesNumber(astCreator,javaFiles)
        );

        System.out.println( "Nombre totale de méthodes = " +
                MethodsApp.getInstance().methodNumber(astCreator,javaFiles)
        );

        System.out.println( "Moyen de méthodes par classe = " +
                MethodsPerClassApp.getInstance().averageNumberOfMethodsPerClass(astCreator,javaFiles)
        );

        System.out.println( "Nombre totale d'attributs = " +
                AttributesApp.getInstance().attributesNumber(astCreator,javaFiles)
        );

        System.out.println( "Nombre moyen d'attributs par classe = " +
                AttributesPerClassApp.getInstance().averageNumberOfAttributesPerClass(astCreator,javaFiles)
        );

        System.out.println( "Nombre total de packages de l'application = " +
                PackagesApp.getInstance().packageNumberApp(astCreator,javaFiles)
        );

        System.out.println( "Nombre de lignes de code de  l'application = " +
                CodeLinesApp.getInstance().codeLinesNumberApp(astCreator, javaFiles)
        );

        System.out.println( "Nombre moyen de lignes de code par méthode = " +
                CodeLinesPerMethodApp.getInstance().averageNumberOfCodeLinesPerMethod(astCreator, javaFiles)
        );

        System.out.println( "Les 10% des classes qui possèdent le plus grand nombre de méthodes = " +
               MostMethodsPerClassApp.getInstance().classesThatHaveMostMethodsNumber(astCreator, javaFiles)
        );

        System.out.println( "Les 10% des classes qui possèdent le plus grand nombre d'attributs = " +
                MostAttributesPerClassApp.getInstance().classesThatHaveMostAttributesNumber(astCreator, javaFiles)
        );

        System.out.println( "Les classes qui font partie en meme temps des deux catégories précédente = " +
                MostAttributesMethodsPerClassApp.getInstance().classesThatHaveMostAttributesMethodsNumber(astCreator, javaFiles)
        );

        System.out.println( "Les classes qui possèdent plus de X méthodes (la valeur de X est donnée) = " +
                XMethodsPerClassApp.getInstance().classesThatHaveXMethodsNumber(astCreator, javaFiles, 5)
        );

        System.out.println( "Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe) = " +
                MostMethodsCodeLinesPerClass.getInstance().methodsThatHaveMostCodeLinesNumber(astCreator, javaFiles)
        );

        System.out.println( "Le nombre maximal de paramètre par rapport à toutes les méthodes de l'application = " +
                MaxParametersApp.getInstance().maxParametersNumber(astCreator, javaFiles)
        );*/


        CallGraph.getInstance("call_graph").createCallGraph(astCreator, javaFiles);



    }
}
