package bootstrap.main;

import bootstrap.parsers.jdt.EclipseJDTASTParser;
import bootstrap.parsers.spoon.SpoonASTParser;
import metier.graphs.transformer.CouplingGraphTransformer;
import metier.graphs.normal.Graph;
import metier.strategy.jdt.EclipseJDTStaticCallGraphStrategy;
import metier.strategy.spoon.SpoonStaticCallGraphStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class ParserLauncher {

    public static final String QUIT = "X";

    //public static final String projectPath= "C:\\Users\\gm_be\\IdeaProjects\\Visitor-Pattern-Implementation-Java-master";

    public static final String projectPath = "C:\\Users\\gm_be\\IdeaProjects\\chain_of_responsibility";

    public static final String jrePath="C:\\Program Files\\Java\\jdk1.8.0_291";

    public static void main(String[] args) throws IOException {

        //////////////////////////////////////////////////////////
        ParserLauncher main = new ParserLauncher();
        BufferedReader inputReader;
        try {
            inputReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput = "";

            do {
                main.menu();
                userInput = inputReader.readLine();
                Graph graph = main.processUserInput(userInput);

                CouplingGraphTransformer couplingGraphTransformer = new CouplingGraphTransformer(graph);

                couplingGraphTransformer.calculateMetrics();


                Thread.sleep(3000);

            } while(!userInput.equals(QUIT));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Graph processUserInput(String userInput) {

        Graph graph = null;


        try {
            switch(userInput) {
                case "1":
                    EclipseJDTASTParser parser = new EclipseJDTASTParser(projectPath, jrePath);
                    graph = EclipseJDTStaticCallGraphStrategy.getInstance(parser).createCallGraph();
                    return graph;

                case "2":
                    SpoonASTParser spoonASTParser = new SpoonASTParser( projectPath, jrePath);
                    graph = SpoonStaticCallGraphStrategy.getInstance(spoonASTParser).createCallGraph();
                    return graph;

                case QUIT:
                    System.out.println("Bye...");
                    return null;

                default:
                    System.err.println("Sorry, wrong input. Please try again.");
                    return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    private void menu() {
        StringBuilder builder = new StringBuilder();
        builder.append("Static call graph :");
        builder.append("\n1. Eclipse JDT.");
        builder.append("\n2. Spoon.");
        builder.append("\n"+QUIT+". To quit.");
        System.out.println(builder);
    }


}
