package org.sonar.samples.java.checks.spring;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;
@Rule(key = "SpringMethodNotAllowRequestMappingRule")
public class SpringMethodNotAllowRequestMappingRule extends BaseTreeVisitor implements JavaFileScanner {
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }
  @Override
  public void visitMethod(MethodTree tree) {
    Symbol.MethodSymbol methodSymbol = tree.symbol();
    SymbolMetadata metadata = methodSymbol.metadata();
    if ( metadata.isAnnotatedWith("org.springframework.web.bind.annotation.RequestMapping")) {
      context.reportIssue(this, tree,"method not allow request mapping");

    }
    super.visitMethod(tree);
  }
}
