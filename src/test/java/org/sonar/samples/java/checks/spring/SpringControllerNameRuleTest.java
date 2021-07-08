package org.sonar.samples.java.checks.spring;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.samples.java.utils.FilesUtils;

public class SpringControllerNameRuleTest {
  @Test
  void check() {
    CheckVerifier.newVerifier()
      .onFile("src/test/files/springrequestmapping/SpringControllerNameRule.java")
      .withCheck(new SpringControllerNameRule())
      .withClassPath(FilesUtils.getClassPath("target/test-jars"))
      .verifyNoIssues();
  }
}
