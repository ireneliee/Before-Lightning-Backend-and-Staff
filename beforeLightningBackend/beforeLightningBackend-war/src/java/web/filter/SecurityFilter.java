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
        if (path.equals("/homePage.xhtml") || path.equals("/settingsPage.xhtml")|| path.equals("/calendarPage.xhtml") || path.endsWith(".png")) {
            return true;
        }
        //access everything
        if (accessRight.equals(accessRight.ADMIN)) {
            return true;

            //access Orders, Support and Customer related stuff
        } else if (accessRight.equals(accessRight.OPERATION)) {
            if ( //                    path.startsWith("/supportOperations")
                    //                    || path.startsWith("/customerOperations")
                    //                    || path.startsWith("/orderOperations"))) {
                    path.equals("/pages/orderOperations/orderHomePage.xhtml")
                    || path.equals("/pages/orderOperations/orderActiveDelivery.xhtml")
                    || path.equals("/pages/orderOperations/orderComplete.xhtml")
                    || path.equals("/pages/orderOperations/orderCompleteDelivery.xhtml")
                    || path.equals("/pages/orderOperations/orderReady.xhtml")
                    || path.equals("/pages/orderOperations/orderRefund.xhtml")
                    || path.equals("/pages/supportOperations/supportHomePage.xhtml")
                    || path.equals("/pages/customerOperations/customerHomePageOLD.xhtml")) {
                return true;
            } else {
                return false;
            }
            //access Product Accessory related stuff
        } else if (accessRight.equals(accessRight.PRODUCT)) {
            if ( //                    path.equals(path.startsWith("/pages/productOperations")
                    //                    || path.startsWith("/pages/accessoryOperations"))) {
                    path.equals("/pages/productOperations/productHomePage.xhtml")
                    || path.equals("/pages/productOperations/manageAllProductsPage.xhtml")
                    || path.equals("/pages/productOperations/manageAllPartsPage.xhtml")
                    || path.equals("/pages/productOperations/manageAllPartChoicesPage.xhtml")
                    || path.equals("/pages/accessoryOperations/accessoryHomePage.xhtml")
                    || path.equals("/pages/accessoryOperations/manageAllAccessoryPage.xhtml")
                    || path.equals("/pages/accessoryOperations/manageAllAccessoryItemPage.xhtml")) {
                return true;
            } else {
                return false;
            }

            //access Forum, Website and Promotion stuff
        } else if (accessRight.equals(accessRight.SALES)) {
            if ( //                    path.equals(path.startsWith("/pages/promotionOperations")
                    //                    || path.startsWith("/pages/websiteOperations"))) {
                    path.equals("/pages/promotionOperations/promotionHomePage.xhtml")
                    || path.equals("/pages/websiteOperations/forumHomePageOLD.xhtml")
                    || path.equals("/pages/websiteOperations/websiteHomePage.xhtml")
                    || path.equals("/pages/websiteOperations/websiteAllAccessoryPage.xhtml")
                    || path.equals("/pages/websiteOperations/websiteAllAccessoryItemsPage.xhtml")
                    || path.equals("/pages/websiteOperations/websiteAllProductsPage.xhtml")
                    || path.equals("/pages/websiteOperations/websiteAllPartsPage.xhtml")
                    || path.equals("/pages/websiteOperations/websiteAllPartChoicesPage.xhtml")) {
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
//        System.out.println(path);
        if (path.equals("/index.xhtml")
                || path.equals("/accessRightError.xhtml")
                || path.equals("/settingsPage.xhtml")
                || path.equals("/calendarPage.xhtml")
                || path.startsWith("/uploadedFiles")
                || path.startsWith("/javax.faces.resource")) {

            return true;
        } else {
            return false;
        }
    }
}
