package com.khipus.commons;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Configuración solo para tests: khipus-commons es una librería y no tiene aplicación propia,
 * pero {@code @SpringBootTest} necesita una {@code @SpringBootConfiguration} para arrancar el contexto.
 */
@SpringBootApplication
class KhipusCommonsTestApplication {
}
