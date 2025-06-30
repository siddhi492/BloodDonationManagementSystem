package com.blooddonation.controller;

import com.blooddonation.model.Donor;
import com.blooddonation.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorRepository donorRepo;

    private Donor currentDonor;

    @GetMapping("/login")
    public String loginPage() {
        return "donor/donorLogin";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Donor donor = donorRepo.findByUsername(username);
        if (donor != null && donor.getPassword().equals(password)) {
            currentDonor = donor;
            model.addAttribute("donor", donor);
            return "donor/donorProfile";
        } else {
            model.addAttribute("error", "Invalid credentials.");
            return "donor/donorLogin";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (donorRepo.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists.");
            return "donor/donorLogin";
        }
        Donor donor = new Donor();
        donor.setUsername(username);
        donor.setPassword(password);
        donorRepo.save(donor);
        model.addAttribute("msg", "Registration successful. Please log in.");
        return "donor/donorLogin";
    }

    @PostMapping("/form")
    public String saveDonor(@ModelAttribute Donor donor, Model model) {
        donor.setId(currentDonor.getId());
        donor.setUsername(currentDonor.getUsername());
        donor.setPassword(currentDonor.getPassword());
        donorRepo.save(donor);
        model.addAttribute("donor", donor);
        model.addAttribute("msg", "Profile updated successfully.");
        return "donor/donorProfile";
    }

    @PostMapping("/update")
    public String updateDonor(@ModelAttribute Donor donor, Model model) {
        return saveDonor(donor, model);
    }

    @GetMapping("/delete")
    public String deleteDonor(Model model) {
        if (currentDonor != null) {
            donorRepo.deleteById(currentDonor.getId());
            currentDonor = null;
        }
        model.addAttribute("msg", "Donor profile deleted.");
        return "redirect:/donor/login";
    }
}
