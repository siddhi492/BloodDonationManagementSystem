package com.blooddonation.controller;

import com.blooddonation.model.BloodRequest;
import com.blooddonation.model.Donor;
import com.blooddonation.repository.BloodRequestRepository;
import com.blooddonation.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicController {

    @Autowired
    private DonorRepository donorRepo;

    @Autowired
    private BloodRequestRepository requestRepo;

    @GetMapping("/public/donors")
    public String viewDonors(Model model) {
        List<Donor> donors = donorRepo.findAll();
        model.addAttribute("donors", donors);
        return "public/donors";
    }

    @GetMapping("/public/requests")
    public String viewRequests(Model model) {
        List<BloodRequest> requests = requestRepo.findAll();
        model.addAttribute("requests", requests);
        return "public/requests";
    }
}
