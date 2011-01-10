package net.balusc.example.upload;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.balusc.http.multipart.MultipartRequest;

@WebServlet(urlPatterns = { "/upload" })
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String text = request.getParameter("text");
        File file = ((MultipartRequest)request).getFile("file");
        String[] check = request.getParameterValues("check");

        // Now do your thing with the obtained input.
        System.out.println("Text: " + text);
        System.out.println("File: " + file);
        System.out.println("Check: " + Arrays.toString(check));

        request.getRequestDispatcher("/WEB-INF/upload.jsp").forward(request, response);
    }

}