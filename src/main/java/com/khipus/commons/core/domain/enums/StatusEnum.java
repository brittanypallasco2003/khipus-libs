package com.khipus.commons.core.domain.enums;

/// Representa el estado de un modelo de dominio para entidades que extiendan
/// de [AuditableModel][com.khipus.commons.core.domain.models.AuditableModel].
///
/// Un modelo auditable nuevo empieza en [#ACTIVE] si no se indica otro estado.
///
/// ## Transiciones previstas
///
/// | Estado actual | Puede pasar a           |
/// |---------------|-------------------------|
/// | [#ACTIVE]     | [#INACTIVE], [#DELETED] |
/// | [#INACTIVE]   | [#ACTIVE], [#DELETED]   |
/// | [#DELETED]    | ninguno (estado final)  |
///
/// Esta tabla es una convención: por ahora ni el enum ni
/// [AuditableModel][com.khipus.commons.core.domain.models.AuditableModel]
/// impiden otras transiciones.
///
/// @see com.khipus.commons.core.domain.records.AuditInfo
public enum StatusEnum {

    /// Modelo de dominio en uso: aparece en las consultas normales.
    ACTIVE,
    /// Modelo de dominio desactivado temporalmente: queda fuera de las
    /// consultas normales, pero puede volver a [#ACTIVE].
    INACTIVE,
    /// Modelo de dominio eliminado de forma lógica: se conserva por auditoría
    /// (ver [AuditInfo#deleted(String)][com.khipus.commons.core.domain.records.AuditInfo#deleted(String)]),
    /// pero no puede reactivarse.
    DELETED

}
