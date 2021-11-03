package bootstrap.main;

import bootstrap.parsers.ProjectReader;
import bootstrap.parsers.jdt.EclipseJDTASTParser;
import graphs.CouplingGraphTransformer;
import graphs.Graph;
import graphs.StaticCallGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserLauncher {

    //public static final String projectPath = "C:\\Users\\gm_be\\IdeaProjects\\untitled1";

    public static final String projectPath= "C:\\Users\\gm_be\\IdeaProjects\\Visitor-Pattern-Implementation-Java-master";

    //public static final String projectPath="C:\\Users\\gm_be\\IdeaProjects\\untitled1";

    public static final String projectSrcPath=projectPath + "\\src\\";

    public static final String jrePath="C:\\Program Files\\Java\\jdk1.8.0_291";

    public static void main(String[] args) throws IOException {

        ProjectReader projectReader = new ProjectReader();
        final File folder = new File(projectSrcPath);
        ArrayList<File> javaFiles  = projectReader.listJavaFilesForFolder(folder);

        EclipseJDTASTParser parser = new EclipseJDTASTParser(projectPath, jrePath);

        Graph graph = StaticCallGraph.getInstance().createCallGraph(parser, javaFiles);

        CouplingGraphTransformer couplingGraphTransformer = new CouplingGraphTransformer(graph);

        couplingGraphTransformer.calculateMetrics();

    }
}
