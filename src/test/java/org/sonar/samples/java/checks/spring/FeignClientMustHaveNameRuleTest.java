package org.sonar.samples.java.checks.spring;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.samples.java.utils.FilesUtils;

public class FeignClientMustHaveNameRuleTest {
  @Test
  void check() {
    CheckVerifier.newVerifier()
      .onFile("src/test/files/springrequestmapping/FeignClientMustHaveNameRule.java")
      .withCheck(new FeignClientMustHaveNameRule())
      .withClassPath(FilesUtils.getClassPath("target/test-jars"))
      .verifyNoIssues();
  }
}
