package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/hellosession")
public class HelloServlet extends HttpServlet {

  private final Map<String, Integer> sessions = new ConcurrentHashMap<>();
  private final Object lock = new Object();
  private int count;

  @Override
  public void doGet (HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    var countSnaptshot = 0;
    String token = "";
    synchronized (lock) {
      countSnaptshot = ++count;

      String cookie = request.getHeader("Cookie");
      if (cookie == null) {
        token = UUID.randomUUID().toString();
        sessions.putIfAbsent(token, 0);
        response.addHeader("Set-Cookie", "token=" + token);
        System.out.println(token);
      } else {
        System.out.println(cookie);
        token = cookie.split("=")[1].split(";")[0];
        System.out.println(token);
        if (!sessions.containsKey(token)) {
          token = UUID.randomUUID().toString();
          sessions.putIfAbsent(token, 0);
          response.addHeader("Set-Cookie", "token=" + token);
        } else {
          sessions.compute(token, (tokenKey, counter) -> counter + 1);
        }
      }
    }

    response.setContentType("text/html ; charset=utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter writer = response.getWriter();
    writer.println("<!DOCTYPE html><html><h1>Bonjour pour la " + sessions.getOrDefault(token, 10) + "eme fois" +
                   "</h1></html>");
    writer.flush();
  }
}