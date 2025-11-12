package fr.uge.jee.springmvc.rectangle;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rectangle")
public class RectangleController {

  @GetMapping()
  public String rectangle (Model model) {
    model.addAttribute("rectangle", new Rectangle(0, 0));
    return "rectangle-form";
  }

  @PostMapping()
  public String rectangleSubmit(@ModelAttribute("rectangle") @Valid Rectangle rectangle, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "rectangle-form";
    }
    return "rectangle-result";
  }
}
