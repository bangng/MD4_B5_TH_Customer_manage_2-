package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rikkei.academy.model.Customer;
import rikkei.academy.service.ICustomerService;

@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/")
    public String viewIndex(Model model){
        model.addAttribute("showList",customerService.findAll());
        return "index";
    }
    @GetMapping("/create")
    public String showFormCreate(Model model){
        model.addAttribute("formCreate",new Customer());
        return "create";
    }
    @PostMapping("/action/create")
    public String createCus(@ModelAttribute ("formCreate")Customer customer,Model model){
        customerService.save(customer);
        model.addAttribute("message","add success!!!");
        return "create";
    }
    @GetMapping("/detail/{id}")
    public String detailCus (@PathVariable ("id") Long id,Model model ){
        Customer customer = customerService.findById(id);
        model.addAttribute("custm",customer);
        return "detail";
    }
    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable ("id") Long id,Model model){
        Customer customerEdit = customerService.findById(id);
        model.addAttribute("editForm",customerEdit);
        return "edit";
    }
    @PostMapping("/action/edit")
    public String editCus(@ModelAttribute ("editForm")Customer customer,Model model){
        customerService.save(customer);
        model.addAttribute("message","edit success!!!");
        return "edit";
    }
    @GetMapping("/delete/{id}")
    public String formDelete(@PathVariable ("id") Long id,Model model){
        Customer customer = customerService.findById(id);
        model.addAttribute("deleteForm",customer);
        return "delete";
    }
    @PostMapping("/action/delete")
    public String delete(@ModelAttribute ("deleteForm")Customer customer ,Model model){
        customerService.deleteById(customer.getId());
        model.addAttribute("message","delete success!!!");
        return "delete";
    }

}
