package com.example.DayOfTheWeek.controller;

import com.example.DayOfTheWeek.model.Day;
import com.example.DayOfTheWeek.repo.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class mainController {

    @Autowired
    DayRepository dayRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Day> days = dayRepository.findAll();
        model.addAttribute("title", "Main Page");
        model.addAttribute("days",days);
        return "home";
    }


    @GetMapping("/day/add")
    public String dayAdd(Model model) {
        return "dayAdd";
    }

    @PostMapping("/day/add")
    public String dayPostAdd(@RequestParam String day, @RequestParam String subjects, @RequestParam String activities, Model model) {
        Day d = new Day(day, subjects, activities);
        dayRepository.save(d);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!dayRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Day> post = dayRepository.findById(id);
        ArrayList<Day> res = new ArrayList<>();
        post.ifPresent(res :: add);
        model.addAttribute("post",res);
        return "day-edit";
    }

    @PostMapping("/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,@RequestParam String day,@RequestParam String subjects,@RequestParam String activities, Model model) {
        Day d = dayRepository.findById(id).orElseThrow();
        d.setDay(day);
        d.setSubjects(subjects);
        d.setActivities(activities);
        dayRepository.save(d);
        return "redirect:/";
    }

    @PostMapping("/day/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        Day d = dayRepository.findById(id).orElseThrow();
        dayRepository.delete(d);
        return "redirect:/";
    }
}
