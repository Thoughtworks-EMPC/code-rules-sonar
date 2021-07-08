package org.sonar.samples.java.checks.spring;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.samples.java.utils.PackageUtils;

import java.util.*;

@Rule(key = "FeignClientMustHaveNameRule")
public class FeignClientMustHaveNameRule extends BaseTreeVisitor implements JavaFileScanner {

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
    SymbolMetadata metadata = symbol.metadata();
    boolean isFeignContext = metadata.isAnnotatedWith("org.springframework.cloud.openfeign.FeignClient");

    if (isFeignContext){
      List<SymbolMetadata.AnnotationValue> annotationValues = metadata.valuesForAnnotation("org.springframework.cloud.openfeign.FeignClient");
      Optional<SymbolMetadata.AnnotationValue> name = annotationValues.stream().filter(annotationValue -> "name".equals(annotationValue.name())).findFirst();
      if (!name.isPresent()) {
        context.reportIssue(this, classTree.simpleName(), "FeignClient must specific name");
      }
    }

    super.visitCompilationUnit(tree);
  }


}
