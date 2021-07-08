package org.sonar.samples.java.checks.spring;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.samples.java.utils.FilesUtils;

public class SpringMethodNotAllowRequestMappingRuleTest {
  @Test
  void check() {
    CheckVerifier.newVerifier()
      .onFile("src/test/files/springrequestmapping/SpringMethodNotAllowRequestMappingRule.java")
      .withCheck(new SpringMethodNotAllowRequestMappingRule())
      .withClassPath(FilesUtils.getClassPath("target/test-jars"))
      .verifyIssues();
  }
}
