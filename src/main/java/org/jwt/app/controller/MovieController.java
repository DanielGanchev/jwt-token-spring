package org.jwt.app.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
  @GetMapping
  public List<Movies> all() {
    return List.of(
        new Movies("The Godfather", "Francis Ford Coppola"),
        new Movies("The Shawshank Redemption", "Frank Darabont"),
        new Movies("The Dark Knight", "Christopher Nolan"),

        new Movies("The Godfather", "Francis Ford Coppola"),
        new Movies("The Shawshank Redemption", "Frank Darabont")


    );
  }

  public record Movies(String title, String director) {}
}