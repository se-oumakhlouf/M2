package fr.uge.jee.servlet.rectangle;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebServlet("/rectangle")
public class RectangleServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/templates/rectangle-form.html");
    String html = readFromInputStream(inputStream);

    PrintWriter writer = response.getWriter();
    writer.println("<!DOCTYPE html> " + html + " </html>");
    writer.flush();
  }

  public static String readFromInputStream(InputStream inputStream) throws IOException {
    var lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
    return lines.collect(Collectors.joining("\n"));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    var reader = request.getReader();
    var args = reader.readLine().split("&");
    var height = Integer.parseInt(args[0].split("=")[1]);
    var width = Integer.parseInt(args[1].split("=")[1]);
    String body = height * width + "";

    PrintWriter writer = response.getWriter();
    writer.println("<!DOCTYPE html>" + body + "</html>");
    writer.flush();
  }
}