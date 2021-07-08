package org.sonar.samples.java.checks.spring;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

@Rule(key = "FeignClientNameRule")
public class FeignClientNameRule extends BaseTreeVisitor implements JavaFileScanner {
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }


  @Override
  public void visitClass(ClassTree tree) {
    Symbol.TypeSymbol symbol = tree.symbol();
    boolean isControllerContext = symbol.metadata().isAnnotatedWith("org.springframework.cloud.openfeign.FeignClient");

    if (isControllerContext ) {
      String controllerName = tree.simpleName().name();
      if (!controllerName.endsWith("Client")) {
        context.reportIssue(this, tree, String.format("%s name invalid", controllerName));
      }

    }
    super.visitClass(tree);
  }





}
