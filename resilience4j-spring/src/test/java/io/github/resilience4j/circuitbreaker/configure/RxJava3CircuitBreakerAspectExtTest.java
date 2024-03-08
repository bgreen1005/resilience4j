package io.github.resilience4j.circuitbreaker.configure;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * aspect unit test
 */
@RunWith(MockitoJUnitRunner.class)
public class RxJava3CircuitBreakerAspectExtTest {

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @InjectMocks
    RxJava3CircuitBreakerAspectExt rxJava3CircuitBreakerAspectExt;


    @Test
    public void testCheckTypes() {
        assertThat(rxJava3CircuitBreakerAspectExt.canHandleReturnType(Flowable.class)).isTrue();
        assertThat(rxJava3CircuitBreakerAspectExt.canHandleReturnType(Single.class)).isTrue();
    }

    @Test
    public void testRxTypes() throws Throwable {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("test");

        when(proceedingJoinPoint.proceed()).thenReturn(Single.just("Test"));
        assertThat(rxJava3CircuitBreakerAspectExt
            .handle(proceedingJoinPoint, circuitBreaker, "testMethod")).isNotNull();

        when(proceedingJoinPoint.proceed()).thenReturn(Flowable.just("Test"));
        assertThat(rxJava3CircuitBreakerAspectExt
            .handle(proceedingJoinPoint, circuitBreaker, "testMethod")).isNotNull();
    }
}