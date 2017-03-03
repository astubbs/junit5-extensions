package name.falgout.jeffrey.testing.junit5;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GuiceExtension.class)
@IncludeModule(TestModule.class)
public abstract class BaseTypeTest {
  @Inject String baseClassFieldInjection;

  @Test
  void baseClassIsNotInjected(byte baseClassParameterInjection) {
    // XXX: Why is baseClassFieldInjection being set to "" instead of "abc" or null?
    assertAll(() -> assertThat(baseClassFieldInjection).isEqualTo(TestModule.STRING),
        () -> assertThat(baseClassParameterInjection).isEqualTo(TestModule2.BYTE));
  }
}
