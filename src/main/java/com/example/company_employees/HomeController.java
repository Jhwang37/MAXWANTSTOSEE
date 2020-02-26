package com.example.company_employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    CompanyRepository companyRepo;
    @Autowired
    EmployeeRepository employeeRepo;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("companies", companyRepo.findAll());
        model.addAttribute("employees", employeeRepo.findAll());
        return "index";
    }

    @GetMapping("/addcompany")
    public String listForm(Model model){
        model.addAttribute("company", new Company());
        return "companyform";
    }

    @PostMapping("/processcompany")
    public String processForm(@Valid Company company, BindingResult result){
        if(result.hasErrors()){
            return "companyform";
        }
        companyRepo.save(company);
        return "redirect:/";
    }

    @GetMapping("/addemployee")
    public String addEmployee(Model model){
        model.addAttribute("companies", companyRepo.findAll());
        model.addAttribute("employee", new Employee());
        return "employeeform";
    }

    @PostMapping("/processemployee")
    public String processEmployee(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            return "employeeform";
        }
        employeeRepo.save(employee);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepo.findById(id).get());
        return "employeeinfo";
    }
    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepo.findById(id).get());
        return "employeeform";
    }
    @RequestMapping("/delete/{id}")
    public String delEmployee(@PathVariable("id") long id){
        employeeRepo.deleteById(id);
        return "redirect:/";
    }
}
