package com.khipus.commons.core.domain.models;

import java.io.Serializable;

/// Clase base para los modelos de dominio que tienen un identificador único
/// pero **no** registran auditoría ni estado.
///
/// Es una de las dos subclases permitidas de [BaseModel] (la otra es
/// [AuditableModel]) y se declara `non-sealed`: cualquier modelo de dominio de
/// los servicios puede extenderla libremente. Es adecuada para datos de
/// referencia o catálogos en los que no interesa saber quién ni cuándo los
/// cambió.
///
/// ## Ejemplo de uso
///
/// ```java
/// public class Currency extends NonAuditableModel<String> {
///
///     public Currency(String code) {
///         super(code);
///     }
/// }
/// ```
///
/// @param <ID> el tipo del identificador único del modelo; debe ser
///             [Serializable]
/// @see BaseModel
/// @see AuditableModel
public abstract non-sealed class NonAuditableModel<ID extends Serializable> extends BaseModel<ID> {

    /// Inicializa el modelo de dominio no auditable con su identificador único.
    ///
    /// @param id el identificador único del modelo, o `null` si aún no se ha
    ///           asignado
    protected NonAuditableModel(ID id) {
        super(id);
    }

}
