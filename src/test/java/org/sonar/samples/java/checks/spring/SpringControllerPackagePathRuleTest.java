package org.sonar.samples.java.checks.spring;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.samples.java.utils.FilesUtils;

public class SpringControllerPackagePathRuleTest {
  @Test
  void check() {
    CheckVerifier.newVerifier()
      .onFile("src/test/files/springrequestmapping/SpringControllerPackagePathRule.java")
      .withCheck(new SpringControllerPackagePathRule())
      .withClassPath(FilesUtils.getClassPath("target/test-jars"))
      .verifyNoIssues();
  }
}
