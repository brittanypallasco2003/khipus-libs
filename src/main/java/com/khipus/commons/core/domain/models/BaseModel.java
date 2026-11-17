package com.khipus.commons.core.domain.models;

import java.io.Serializable;

import lombok.Getter;

/// Clase base para todos los modelos de dominio que tienen un identificador
/// único.
///
/// Es una clase **sellada** (`sealed`) con exactamente dos ramas, de modo que
/// todo modelo de dominio de Khipus debe elegir una:
///
/// | Subclase              | Uso                                                        |
/// |-----------------------|------------------------------------------------------------|
/// | [AuditableModel]      | Modelos que registran auditoría ([AuditInfo][com.khipus.commons.core.domain.records.AuditInfo]) y estado |
/// | [NonAuditableModel]   | Modelos que solo necesitan un identificador                |
///
/// Ambas subclases son `non-sealed`, así que los servicios pueden extenderlas
/// libremente. Como la jerarquía es cerrada, un `switch` con patrones sobre
/// [BaseModel] que cubra las dos subclases es exhaustivo y no necesita
/// `default`:
///
/// ```java
/// String tipo = switch (modelo) {
///     case AuditableModel<?> a -> "auditable";
///     case NonAuditableModel<?> n -> "no auditable";
/// };
/// ```
///
/// Para añadir otra rama a la jerarquía hay que incluirla en la cláusula
/// `permits`.
///
/// El identificador es inmutable: se asigna en el constructor y no puede
/// cambiarse después. Se lee con `getId()`, que genera Lombok (`@Getter`).
///
/// @param <ID> el tipo del identificador único del modelo; debe ser
///             [Serializable] (por ejemplo, [Long], [String] o
///             [java.util.UUID])
/// @see AuditableModel
/// @see NonAuditableModel
@Getter
public abstract sealed class BaseModel<ID extends Serializable> permits AuditableModel, NonAuditableModel {

    /// El identificador único del modelo de dominio.
    private final ID id;

    /// Inicializa el modelo de dominio con su identificador único.
    ///
    /// Solo pueden invocarlo las subclases permitidas. El valor no se valida:
    /// `id` puede ser `null`, por ejemplo en un modelo que todavía no se ha
    /// persistido.
    ///
    /// @param id el identificador único del modelo, o `null` si aún no se ha
    ///           asignado
    protected BaseModel(ID id) {
        this.id = id;
    }
}
