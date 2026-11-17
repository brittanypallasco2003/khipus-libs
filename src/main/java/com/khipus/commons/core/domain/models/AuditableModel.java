package com.khipus.commons.core.domain.models;

import java.io.Serializable;

import com.khipus.commons.core.domain.enums.StatusEnum;
import com.khipus.commons.core.domain.records.AuditInfo;

import lombok.Getter;

/// Clase base para todos los modelos de dominio que tienen un identificador
/// único y son auditables.
///
/// Es una de las dos subclases permitidas de [BaseModel] (la otra es
/// [NonAuditableModel]) y se declara `non-sealed`: cualquier modelo de dominio
/// de los servicios puede extenderla libremente.
///
/// Además del identificador, cada modelo auditable guarda:
///
/// - su información de auditoría, es decir, quién y cuándo lo creó, modificó
///   o eliminó ([AuditInfo]), accesible con `getAuditInfo()`;
/// - su estado ([StatusEnum]), accesible con `getStatus()`.
///
/// Los métodos de acceso los genera Lombok (`@Getter`).
///
/// ## Ejemplo de uso
///
/// ```java
/// public class Invoice extends AuditableModel<UUID> {
///
///     public Invoice(UUID id, AuditInfo auditInfo, StatusEnum status) {
///         super(id, auditInfo, status);
///     }
/// }
///
/// // Alta de un modelo nuevo: estado ACTIVE por defecto.
/// Invoice factura = new Invoice(UUID.randomUUID(), AuditInfo.created("admin"), null);
/// ```
///
/// @param <ID> el tipo del identificador único del modelo; debe ser
///             [Serializable]
/// @see BaseModel
/// @see NonAuditableModel
@Getter
public abstract non-sealed class AuditableModel<ID extends Serializable> extends BaseModel<ID> {

/// La información de auditoría del modelo de dominio.
///
/// Se asigna en el constructor y no cambia durante la vida del objeto.
private final AuditInfo auditInfo;
/// El estado actual del modelo de dominio.
///
/// Se asigna en el constructor; la clase no ofrece por ahora métodos para
/// cambiarlo.
private StatusEnum status;


    /// Inicializa el modelo de dominio auditable con su identificador, su
    /// información de auditoría y su estado.
    ///
    /// Valores por defecto cuando un argumento es `null`:
    ///
    /// - `auditInfo`: se usa `AuditInfo.created(null)`. `createdAt` y
    ///   `updatedAt` toman la fecha y hora **actual** y `createdBy` queda en
    ///   `null`. Al reconstruir un modelo ya existente (por ejemplo, desde la
    ///   base de datos) conviene pasar siempre su [AuditInfo] original para no
    ///   perder las fechas reales.
    /// - `status`: se usa [StatusEnum#ACTIVE].
    ///
    /// @param id        el identificador único del modelo, o `null` si aún no
    ///                  se ha asignado
    /// @param auditInfo la información de auditoría del modelo, o `null` para
    ///                  crear una nueva con la fecha y hora actual
    /// @param status    el estado del modelo, o `null` para usar
    ///                  [StatusEnum#ACTIVE]
    protected AuditableModel(ID id, AuditInfo auditInfo, StatusEnum status) {
        super(id);
        this.auditInfo = auditInfo != null ? auditInfo : AuditInfo.created(null);
        this.status = status != null ? status : StatusEnum.ACTIVE;
    }

}
