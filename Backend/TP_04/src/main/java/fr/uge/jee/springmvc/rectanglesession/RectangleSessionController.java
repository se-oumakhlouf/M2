package fr.uge.jee.springmvc.rectanglesession;

import fr.uge.jee.springmvc.rectangle.Rectangle;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rs")
public class RectangleSessionController {

  @GetMapping()
  public String rectangle (Model model) {
    model.addAttribute("rectangle", new Rectangle(0, 0));
    return "rectangle-form";
  }

  @PostMapping()
  public String rectangleSubmit (@ModelAttribute("rectangle") @Valid Rectangle rectangle, BindingResult bindingResult
      , HttpSession session) {
    if (bindingResult.hasErrors()) {
      return "rectangle-form";
    }
    List<Rectangle> rectangles = (List<Rectangle>) session.getAttribute("rectangles");
    if (rectangles == null) {
      rectangles = List.of(rectangle);
    }
    session.setAttribute("rectangles", rectangles.add(rectangle));
    System.out.println(session.getAttribute("rectangles"));
    return "rectangle-result";
  }
}
