package com.example.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

/**
 * Clase de utilidad para manejar operaciones relacionadas con JWT (JSON Web Tokens).
 * <p>
 * Esta clase proporciona métodos para validar y extraer información de tokens JWT firmados
 * utilizando la clave secreta definida en la aplicación.
 * </p>
 */
public class JwtUtil {

    /**
     * Obtiene los claims (reclamaciones) de un token JWT.
     * <p>
     * Este método valida el token proporcionado y extrae los claims contenidos en él,
     * como el ID del usuario, su correo electrónico, roles u otra información definida
     * durante la generación del token.
     * </p>
     *
     * @param token El token JWT que se desea validar y procesar.
     * @return Un objeto {@link Claims} que contiene la información almacenada en el token.
     * @throws io.jsonwebtoken.JwtException Si el token es inválido, ha expirado o no es confiable.
     * @throws IllegalArgumentException Si el token proporcionado es nulo o vacío.
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().
                setSigningKey(Keys.hmacShaKeyFor(Constants.API_SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}

