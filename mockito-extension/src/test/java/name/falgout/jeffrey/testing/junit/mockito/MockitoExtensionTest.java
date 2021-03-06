package name.falgout.jeffrey.testing.junit.mockito;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import name.falgout.jeffrey.testing.junit.testing.ExpectFailure;
import name.falgout.jeffrey.testing.junit.testing.ExpectFailure.Cause;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;

@ExtendWith(MockitoExtension.class)
class MockitoExtensionTest {
  @Mock Function<String, String> mock;
  @Spy ArrayList<String> spy;
  @Captor ArgumentCaptor<String> captor;

  @Test
  void mocksAreInitialized() {
    assertThat(mock).isNotNull();
  }

  @Test
  void spiesAreInitialized() {
    assertThat(spy).isNotNull();
  }

  @Test
  void captorsAreInitialized() {
    assertThat(captor).isNotNull();
  }

  @Test
  void canCreateMocksForTests(@Mock Supplier<String> supplier) {
    assertThat(supplier).isNotNull();
  }

  @Test
  void canCreateCaptors(ArgumentCaptor<String> captor) {
    assertThat(captor).isNotNull();

    mock.apply("foo");

    verify(mock).apply(captor.capture());
    assertThat(captor.getAllValues()).containsExactly("foo");
  }

  @Test
  void mocksAreDifferent(@Mock List<String> list1, @Mock List<String> list2) {
    assertThat(list1).isNotEqualTo(list2);
  }

  @SuppressWarnings("unused")
  @Nested
  class NegativeExamples {
    @ExpectFailure(
        @Cause(type = ParameterResolutionException.class, message = "Too many factories")
    )
    @Test
    void tooManyFactories(@Mock ArgumentCaptor<String> whoShouldWin) {}

    @ExpectFailure(
        @Cause(type = ParameterResolutionException.class, message = "No ParameterResolver")
    )
    @Test
    void notAnnotated(Supplier<String> notAnnotatedWithMock) {}
  }
}
