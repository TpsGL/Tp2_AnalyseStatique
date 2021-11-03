package responsesForQuestions.total;


import bootstrap.jdt.ASTCreator;
import calculators.PackageCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PackagesApp {

    private static PackagesApp instance;

    private PackagesApp() { }

    public static PackagesApp getInstance() {
        if (instance == null) { instance = new PackagesApp(); }
        return instance;
    }


    public int packageNumberApp(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        Set<String> namePackagesDuplicated = new HashSet<>();
        Set<String> nameSubPackages = new HashSet<>();

        PackageCounter packageCounter = new  PackageCounter();
        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            namePackagesDuplicated.add(cu.getPackage().getName().getFullyQualifiedName());
        }

        for (String name: namePackagesDuplicated) {
            nameSubPackages.addAll(Arrays.asList(name.split("\\.")));
        }

        return nameSubPackages.size();
    }

}
