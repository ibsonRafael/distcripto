package br.com.acme.sample.security.cript;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import({ClusterFormation.class, EncriptionIncomeFilter.class})
public @interface ArsenalEnableFullEncript {
}
