/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Menu;
import model.MenuDAO;

/**
 *
 * @author clemer.ribeiro
 */
public class GerenciarMenu extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String mensagem = "";

        String acao = request.getParameter("acao");
        String idmenu = request.getParameter("idmenu");

        Menu m = new Menu();

        try {

            MenuDAO mDAO = new MenuDAO();
            if (acao.equals("alterar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    m = mDAO.getCarregaPorID(Integer.parseInt(idmenu));
                    if (m.getIdmenu() > 0) {
                        RequestDispatcher disp = getServletContext().getRequestDispatcher("/editar_menu.jsp");
                        request.setAttribute("menu", m);
                        disp.forward(request, response);

                    } else {

                        mensagem = "Menu não encontrado";
                    }
                } else {
                    mensagem = "Acesso Negado";
                }
            }

            if (acao.equals("deletar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    m.setIdmenu(Integer.parseInt(idmenu));
                    if (mDAO.deletar(m)) {

                        mensagem = "Exluido com sucesso";
                    } else {
                        mensagem = "Erro ao executar";
                    }
                } else {
                    mensagem = "Acesso Negado";
                }
            }

        } catch (Exception e) {
            out.print(e);
            mensagem = "Erro ao executar";

        }
        out.println("<script type='text/javascript'>");
        out.println("alert('" + mensagem + "')");
        out.println("location.href='listar_menu.jsp';");
        out.println("</script>");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String idmenu = request.getParameter("idmenu");
        String nome = request.getParameter("nome");
        String link = request.getParameter("link");
        String icone = request.getParameter("icone");
        String exibir = request.getParameter("exibir");

        String mensagem = "";

        Menu m = new Menu();
        try {

            MenuDAO mDAO = new MenuDAO();

            if (!idmenu.isEmpty()) {

                m.setIdmenu(Integer.parseInt(idmenu));
            }
            if (nome.equals("") || link.equals("") || exibir.equals("")) {

                mensagem = "Campos obrigatorios deverâo ser preenchidos";

            } else {

                m.setNome(nome);
                m.setLink(link);
                m.setIcone(icone);
                m.setExibir(Integer.parseInt(exibir));
                if (mDAO.gravar(m)) {

                    mensagem = "Gravado com sucesso";
                } else {

                    mensagem = "Erro ao gravar o menu";
                }
            }

        } catch (Exception e) {
            out.print(e);
            mensagem = "Erro ao executar";

        }
        out.println("<script type='text/javascript'>");
        out.println("alert('" + mensagem + "')");
        out.println("location.href='listar_menu.jsp';");
        out.println("</script>");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
