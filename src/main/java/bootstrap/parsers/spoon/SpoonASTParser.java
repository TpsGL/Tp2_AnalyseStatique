package bootstrap.parsers.spoon;

import bootstrap.parsers.parser.Parser;
import spoon.Launcher;
import spoon.reflect.CtModel;

public class SpoonASTParser extends Parser<Launcher> {

    /* CONSTRUCTOR */
    public SpoonASTParser(String projectPath, String jrePath) {
        super(projectPath, jrePath);
    }


    private void setParser() {

        parser = new Launcher();

        parser.addInputResource(projectPath);

        parser.getEnvironment().setComplianceLevel(7);

        parser.getEnvironment().setCommentEnabled(true); // represent the comments from the source code in the AST

        parser.getEnvironment().setAutoImports(true);

    }


    public CtModel createFAMIXModel() {

        setParser();

        CtModel model = parser.buildModel();

        return model;
    }


}
