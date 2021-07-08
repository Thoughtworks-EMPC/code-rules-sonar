package org.sonar.samples.java.checks.spring;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.samples.java.utils.PackageUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;

@Rule(key = "SpringControllerPackagePathRule")
public class SpringControllerPackagePathRule extends BaseTreeVisitor implements JavaFileScanner {
  private JavaFileScannerContext context;
  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }


  @Override
  public void visitCompilationUnit(CompilationUnitTree tree) {
    Optional<Tree> first = tree.types().stream().filter(tree1 -> tree1.is(Tree.Kind.CLASS)).findFirst();
    if (!first.isPresent()) {
      return;
    }
    ClassTree tree1 = (ClassTree)first.get();
    Symbol.TypeSymbol symbol = tree1.symbol();
    boolean isControllerContext = symbol.metadata().isAnnotatedWith("org.springframework.stereotype.Controller");
    boolean isRestControllerContext = symbol.metadata().isAnnotatedWith("org.springframework.web.bind.annotation.RestController");

    if (isControllerContext||isRestControllerContext){
      PackageDeclarationTree packageDeclaration = tree.packageDeclaration();
      if (packageDeclaration != null) {
        String packageName = PackageUtils.packageName(packageDeclaration, ".");
        if (Arrays.stream(packageName.split("\\.")).noneMatch(s -> s.equals("controller"))) {
          context.reportIssue(this, packageDeclaration.packageName(), "controller path invalid");
        }
      }
    }

    super.visitCompilationUnit(tree);
  }


}
