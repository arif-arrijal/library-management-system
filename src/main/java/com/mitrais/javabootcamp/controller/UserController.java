package com.mitrais.javabootcamp.controller;

import com.mitrais.javabootcamp.model.Role;
import com.mitrais.javabootcamp.model.User;
import com.mitrais.javabootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @ModelAttribute("roleList")
    public List<Role> getRoleList() {
        return userService.findAllRole();
    }

    @GetMapping("/list")
    public ModelAndView getListPage(@AuthenticationPrincipal UsernamePasswordAuthenticationToken loginUser) {
        ModelAndView mv = new ModelAndView("user/list");
        String username = (String) loginUser.getPrincipal();
        List<User> userList = userService.findAll(username);
        mv.addObject("userList", userList);
        return mv;
    }

    @GetMapping("")
    public String getFormPage(User user) {
        return "user/form";
    }

    @GetMapping("/{id}")
    public ModelAndView getUpdateForm(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("user/form");
        mv.addObject("user", userService.findOne(id));
        return mv;
    }

    @GetMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        userService.delete(userId);
        redirectAttributes.addFlashAttribute("successMsg", "Successfully delete user data");
        return "redirect:/users/list";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user/form";

        userService.saveOrUpdate(user);
        return "redirect:/users/list";
    }
}
