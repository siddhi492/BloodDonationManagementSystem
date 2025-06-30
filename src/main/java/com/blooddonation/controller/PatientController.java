package com.blooddonation.controller;

import com.blooddonation.model.BloodRequest;
import com.blooddonation.model.Patient;
import com.blooddonation.repository.BloodRequestRepository;
import com.blooddonation.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private BloodRequestRepository requestRepo;

    private Patient currentPatient;

    @GetMapping("/login")
    public String loginPage() {
        return "patient/patientLogin";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Patient patient = patientRepo.findByUsername(username);
        if (patient != null && patient.getPassword().equals(password)) {
            currentPatient = patient;
            BloodRequest request = requestRepo.findByUsername(username);
            model.addAttribute("request", request != null ? request : new BloodRequest());
            return "patient/patientProfile";
        } else {
            model.addAttribute("error", "Invalid credentials.");
            return "patient/patientLogin";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (patientRepo.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists.");
            return "patient/patientLogin";
        }
        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setPassword(password);
        patientRepo.save(patient);
        model.addAttribute("msg", "Registration successful. Please log in.");
        return "patient/patientLogin";
    }

    @PostMapping("/form")
    public String saveRequest(@ModelAttribute BloodRequest request, Model model) {
        request.setUsername(currentPatient.getUsername());
        requestRepo.save(request);
        model.addAttribute("request", request);
        model.addAttribute("msg", "Request submitted successfully.");
        return "patient/patientProfile";
    }

    @PostMapping("/update")
    public String updateRequest(@ModelAttribute BloodRequest request, Model model) {
        return saveRequest(request, model);
    }

    @GetMapping("/delete")
    public String deleteRequest(Model model) {
        BloodRequest req = requestRepo.findByUsername(currentPatient.getUsername());
        if (req != null) {
            requestRepo.delete(req);
            model.addAttribute("msg", "Request deleted.");
        }
        return "redirect:/patient/login";
    }
}
