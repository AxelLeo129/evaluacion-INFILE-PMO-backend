package com.example.filters;

import com.example.utilities.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class AuthFilter extends GenericFilter {

    /**
     * Filtra las solicitudes HTTP para verificar la autenticación mediante un token JWT.
     * <p>
     * Este método:
     * <ul>
     *     <li>Valida que el encabezado <code>Authorization</code> esté presente y tenga un formato correcto.</li>
     *     <li>Valida la firma y los datos del token JWT.</li>
     *     <li>Extrae el atributo <code>userId</code> del token y lo agrega a la solicitud.</li>
     *     <li>Devuelve un error HTTP <code>401 Unauthorized</code> si el token es inválido, ha expirado,
     *     o si el encabezado <code>Authorization</code> no está presente.</li>
     * </ul>
     * </p>
     *
     * @param servletRequest  La solicitud entrante.
     * @param servletResponse La respuesta saliente.
     * @param filterChain     La cadena de filtros para procesar la solicitud.
     * @throws IOException      Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error en el procesamiento del filtro.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // Configuración CORS
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200"); // Cambia según tu origen permitido
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Manejo de solicitudes preflight (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Verificación de token en encabezado Authorization
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(Keys.hmacShaKeyFor(Constants.API_SECRET_KEY.getBytes()))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
                    httpServletRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "invalid/expired token");
                    return;
                }
            } else {
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization token must be Bearer [token]");
                return;
            }
        } else {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization must be provided");
            return;
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
