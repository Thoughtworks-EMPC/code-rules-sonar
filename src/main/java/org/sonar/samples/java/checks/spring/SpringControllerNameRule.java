package org.sonar.samples.java.checks.spring;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "SpringControllerNameRule")
public class SpringControllerNameRule extends BaseTreeVisitor implements JavaFileScanner {
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitClass(ClassTree tree) {
    Symbol.TypeSymbol symbol = tree.symbol();
    boolean isControllerContext = symbol.metadata().isAnnotatedWith("org.springframework.stereotype.Controller");
    boolean isRestControllerContext = symbol.metadata().isAnnotatedWith("org.springframework.web.bind.annotation.RestController");

    if (isControllerContext ||isRestControllerContext) {
      String controllerName = tree.simpleName().name();
      if (!controllerName.endsWith("Controller")) {
        context.reportIssue(this, tree, String.format("%s name invalid", controllerName));
      }

    }
    super.visitClass(tree);
  }





}
