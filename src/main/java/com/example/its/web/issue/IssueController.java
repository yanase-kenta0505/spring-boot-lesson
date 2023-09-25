package com.example.its.web.issue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.its.domain.issue.IssueEntity;
import com.example.its.domain.issue.IssueService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

  private final IssueService issueService;

  // GET
  @GetMapping
  public String showList(Model model) {
    model.addAttribute("issueList", issueService.findAll());
    return "issues/list";
  }

  @GetMapping("/creationForm")
  public String showCreationForm(@ModelAttribute IssueForm form) {
    return "issues/creationForm";
  }

  @PostMapping
  public String create(@Validated IssueForm form, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return showCreationForm(form);
    }

    issueService.create(form.getSummary(), form.getDescription());
    return "redirect:/issues";
  }

  @GetMapping("/{issueId}")
  public String showDetail(@PathVariable("issueId") long issueId, Model model) {
    var dummyEntity = new IssueEntity(1, "概要", "説明");
    model.addAttribute("issue", dummyEntity);
    return "issues/detail";
  }
}
