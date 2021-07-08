package org.sonar.samples.java.checks.spring;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.samples.java.utils.PackageUtils;

import java.util.Arrays;
import java.util.Optional;

@Rule(key = "FeignClientPackagePathRule")
public class FeignClientPackagePathRule extends BaseTreeVisitor implements JavaFileScanner {
  private JavaFileScannerContext context;
  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }



  @Override
  public void visitCompilationUnit(CompilationUnitTree tree) {
    Optional<Tree> first = tree.types().stream().filter(tree1 -> tree1.is(Tree.Kind.INTERFACE)).findFirst();
    if (!first.isPresent()) {
      return;
    }
    ClassTree classTree = (ClassTree)first.get();
    Symbol.TypeSymbol symbol = classTree.symbol();
    boolean isFeignContext = symbol.metadata().isAnnotatedWith("org.springframework.cloud.openfeign.FeignClient");

    if (isFeignContext){
      PackageDeclarationTree packageDeclaration = tree.packageDeclaration();
      if (packageDeclaration != null) {
        String packageName = PackageUtils.packageName(packageDeclaration, ".");
        if (Arrays.stream(packageName.split("\\.")).noneMatch(s -> s.equals("client"))) {
          context.reportIssue(this, packageDeclaration.packageName(), "client path invalid");
        }
      }
    }

    super.visitCompilationUnit(tree);
  }


}
