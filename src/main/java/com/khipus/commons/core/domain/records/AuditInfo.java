package com.khipus.commons.core.domain.records;

import java.time.OffsetDateTime;

/// Representa la información de auditoría de un modelo de dominio para
/// entidades que extiendan de
/// [AuditableModel][com.khipus.commons.core.domain.models.AuditableModel].
///
/// Es un registro **inmutable**: cada cambio de estado (modificación o
/// eliminación) devuelve una nueva instancia en lugar de alterar la actual.
///
/// ## Ciclo de vida
///
/// | Operación                  | `createdAt` | `updatedAt` | `deletedAt` | Usuario que cambia |
/// |----------------------------|-------------|-------------|-------------|--------------------|
/// | [#created(String)]         | ahora       | ahora       | `null`      | `createdBy` y `updatedBy` |
/// | [#modified()]              | se conserva | ahora       | se conserva | ninguno            |
/// | [#modified(String)]        | se conserva | ahora       | se conserva | `updatedBy`        |
/// | [#deleted(String)]         | se conserva | ahora       | ahora       | `deletedBy`        |
///
/// ## Ejemplo de uso
///
/// ```java
/// AuditInfo audit = AuditInfo.created("admin");
/// audit = audit.modified("operador");
/// audit = audit.deleted("admin");
///
/// boolean eliminado = audit.deletedAt() != null; // true
/// ```
///
/// @param createdAt la fecha y hora de creación del modelo de dominio; si es
///                  `null`, se usa la fecha y hora actual
/// @param updatedAt la fecha y hora de la última actualización del modelo de
///                  dominio; si es `null`, se usa la fecha y hora actual
/// @param deletedAt la fecha y hora de eliminación del modelo de dominio, o
///                  `null` si no ha sido eliminado
/// @param createdBy el usuario que creó el modelo de dominio
/// @param updatedBy el usuario que actualizó por última vez el modelo de
///                  dominio
/// @param deletedBy el usuario que eliminó el modelo de dominio, o `null` si
///                  no ha sido eliminado
public record AuditInfo(OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime deletedAt, String createdBy,
        String updatedBy, String deletedBy) {

    /// Constructor compacto que completa las fechas obligatorias.
    ///
    /// Si `createdAt` o `updatedAt` son `null`, se inicializan con la **misma**
    /// fecha y hora actual ([OffsetDateTime#now()]), de modo que un registro
    /// recién creado tiene ambas fechas idénticas. `deletedAt` y los campos de
    /// usuario se asignan tal como se reciben.
    public AuditInfo {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = createdAt == null ? now : createdAt;
        updatedAt = updatedAt == null ? now : updatedAt;
    }

    /// Crea una nueva instancia de [AuditInfo] para un modelo de dominio recién
    /// creado.
    ///
    /// `createdAt` y `updatedAt` se inicializan con la fecha y hora actual, y el
    /// usuario indicado se registra tanto como creador como último editor. Los
    /// campos de eliminación quedan en `null`.
    ///
    /// @param createdBy el usuario que creó el modelo de dominio
    /// @return una nueva instancia de [AuditInfo] con la información de
    ///         auditoría de creación
    public static AuditInfo created(String createdBy) {
        return new AuditInfo(null, null, null, createdBy, createdBy, null);
    }

    /// Crea una nueva instancia de [AuditInfo] que marca el modelo de dominio
    /// como modificado, conservando el último usuario editor.
    ///
    /// Equivale a invocar [#modified(String)] con el valor actual de
    /// [#updatedBy()]. Solo se actualiza `updatedAt` con la fecha y hora
    /// actual.
    ///
    /// @return una nueva instancia de [AuditInfo] con `updatedAt` actualizado
    /// @see #modified(String)
    public AuditInfo modified() {
        return modified(updatedBy);
    }

    /// Crea una nueva instancia de [AuditInfo] que marca el modelo de dominio
    /// como modificado por el usuario indicado.
    ///
    /// Se actualizan `updatedAt` con la fecha y hora actual y `updatedBy` con
    /// el usuario recibido; el resto de los campos se conservan.
    ///
    /// @param updatedBy el usuario que actualizó el modelo de dominio
    /// @return una nueva instancia de [AuditInfo] con la información de
    ///         auditoría de modificación
    public AuditInfo modified(String updatedBy) {
        return new AuditInfo(createdAt, OffsetDateTime.now(), deletedAt, createdBy, updatedBy, deletedBy);
    }

    /// Crea una nueva instancia de [AuditInfo] que marca el modelo de dominio
    /// como eliminado (eliminación lógica).
    ///
    /// `updatedAt` y `deletedAt` se fijan en la **misma** fecha y hora actual y
    /// `deletedBy` toma el usuario recibido. `updatedBy` **no** cambia: conserva
    /// el último usuario que modificó el modelo antes de eliminarlo.
    ///
    /// @param deletedBy el usuario que eliminó el modelo de dominio
    /// @return una nueva instancia de [AuditInfo] con la información de
    ///         auditoría de eliminación
    public AuditInfo deleted(String deletedBy) {
        OffsetDateTime now = OffsetDateTime.now();
        return new AuditInfo(createdAt, now, now, createdBy, updatedBy, deletedBy);
    }

}
