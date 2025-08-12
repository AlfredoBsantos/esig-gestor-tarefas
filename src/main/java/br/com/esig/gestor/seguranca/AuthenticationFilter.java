package br.com.esig.gestor.seguranca;

import br.com.esig.gestor.modelo.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*.xhtml")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/login.xhtml";
        // Adicionamos a URI de cadastro aqui
        String registerURI = req.getContextPath() + "/cadastro-usuario.xhtml";

        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        boolean isLoginRequest = req.getRequestURI().equals(loginURI);
        // Adicionamos a verificação da página de cadastro
        boolean isRegisterRequest = req.getRequestURI().equals(registerURI);
        boolean isResourceRequest = req.getRequestURI().startsWith(req.getContextPath() + "/javax.faces.resource/");

        // Adicionamos isRegisterRequest à condição
        if (usuarioLogado != null || isLoginRequest || isRegisterRequest || isResourceRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURI);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}