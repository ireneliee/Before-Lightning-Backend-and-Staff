/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.filter;

import entity.EmployeeEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.enumeration.EmployeeAccessRightEnum;

/**
 *
 * @author irene
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private static final boolean debug = true;

    FilterConfig filterConfig;

    private static final String CONTEXT_ROOT = "/beforeLightningBackend-war";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");

        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin == true) {
                EmployeeEntity currentEmployeeEntity = (EmployeeEntity) httpSession.getAttribute("currentEmployeeEntity");

                if (checkAccessRight(requestServletPath, currentEmployeeEntity.getEmployeeAccessRight())) {
                    chain.doFilter(request, response);
                } else {
                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
                }
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/accessRightError.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private Boolean checkAccessRight(String path, EmployeeAccessRightEnum accessRight) {

        if (accessRight.equals(accessRight.ADMIN)) {
            return true;
        } else if (accessRight.equals(accessRight.OPERATION)) {
            if (path.equals("/promotionOperation.xhtml")
                    || path.equals("/websiteOperation.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else if (accessRight.equals(accessRight.PRODUCT)) {
            if (path.equals("/productOperation.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else if (accessRight.equals(accessRight.SALES)) {
            if (path.equals("/orderOperation.xhtml")
                    || path.equals("/supportOperation.xhtml")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public void destroy() {

    }

    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.equals("/homePage.xhtml")
                || path.equals("/accessRightError.xhtml")
                || path.startsWith("/javax.faces.resource")) {
            return true;
        } else {
            return false;
        }
    }
}
