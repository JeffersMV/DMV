package servlets;

import dao.DaoAbstract;
import dao.DaoException;
import dto.PhotoDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sql.AudioDAO;
import sql.PhotoDAO;
import sql.VideoDAO;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ShowServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml"); // Ищет бины в конфигурации
        AudioDAO audioDAO = (AudioDAO) context.getBean("audioDAO");
        PhotoDAO photoDAO = (PhotoDAO) context.getBean("photoDAO");
        VideoDAO videoDAO = (VideoDAO) context.getBean("videoDAO");
        try {
            System.out.println(audioDAO.getAll());
            System.out.println(photoDAO.getAll());
            System.out.println(videoDAO.getAll());
        } catch (DaoException e) {
            System.out.println("Ошибка Бинов");
        }

        if (Objects.equals(request.getParameter("action"), "audio")) {
            sendDTOListOnPage(request, response, (AudioDAO) request.getSession().getAttribute("audioDAO"));
        } else if (Objects.equals(request.getParameter("action"), "video")) {
            sendDTOListOnPage(request, response, (VideoDAO) request.getSession().getAttribute("videoDAO"));
        } else if (Objects.equals(request.getParameter("action"), "photo")) {
            sendDTOListOnPage(request, response, (PhotoDAO) request.getSession().getAttribute("photoDAO"));
        }else if (Objects.equals(request.getParameter("action"), "onePhoto")) {
            sendPhotoDTOOnPage(request, response);
        } else if (Objects.equals(request.getParameter("action"), "SEND")) {
            if (request.getParameter("name").isEmpty()) {
                request.getRequestDispatcher("/index.jsp?action=error&error=name_error").forward(request, response);
            } else if (request.getParameter("phone").isEmpty()) {
                request.getRequestDispatcher("/index.jsp?action=error&error=phone_error").forward(request, response);
            } else if (request.getParameter("e-mail").isEmpty()) {
                request.getRequestDispatcher("/index.jsp?action=error&error=e-mail_error").forward(request, response);
            } else {
                sentMail(request, response);
            }
        } else {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private void sendDTOListOnPage(HttpServletRequest request, HttpServletResponse response, DaoAbstract daoAbstract) throws ServletException, IOException {
        List dtoList = null;
        try {
            dtoList = daoAbstract.getAll();
        } catch (Exception e) {
            request.getRequestDispatcher("/index.jsp?action=error&error=connect_BD").forward(request, response);
        }
        request.setAttribute("dtoList", dtoList);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void sendPhotoDTOOnPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PhotoDTO photoDTO = null;
        Integer id = Integer.valueOf(request.getParameter("id"));
        try {
            photoDTO = ((PhotoDAO) request.getSession().getAttribute("photoDAO")).getEntityByK(id);
        } catch (Exception e) {
            request.getRequestDispatcher("/index.jsp?action=error&error=connect_BD").forward(request, response);
        }
        request.setAttribute("photoDTO", photoDTO);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void sentMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        try {
            SimpleEmail email = new SimpleEmail();
            email.setSSLOnConnect(true);
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setSubject("DMV " + phone + "|" + name);
            email.setAuthenticator(new DefaultAuthenticator("User Name", "Password"));
            email.addTo("DMV@gmail.com", "DMV.com");
            email.setFrom(request.getParameter("e-mail"), name);
            email.setMsg("Text + " + phone + "|" + name);
            email.send();
        } catch (EmailException e) {
            e.getStackTrace();
            request.getRequestDispatcher("/index.jsp?action=error&error=send_e-mail").forward(request, response);
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
