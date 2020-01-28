package com.gbm.sample.visualizer.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.gbm.sample.visualizer.entities.Operacion;
import com.gbm.sample.visualizer.services.OperacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController{

  @Autowired
  private OperacionService operacionService;

  @GetMapping(path = {"","/"})
  public String index(@RequestParam(defaultValue =  "1", name = "page") String page, @RequestParam(defaultValue = "5", name = "pageSize") String pageSize, @RequestParam(defaultValue = "false", name = "clear") String clear, Model model) {

    if (clear.equals("true")){
      operacionService.clear();
    }

    PageRequest pageable = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(pageSize));
    Page<Operacion> operacionPage = operacionService.findAllPageable(pageable);
    
    int totalPages = operacionPage.getTotalPages();
    if(totalPages > 0){
      List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }
    model.addAttribute("activeOperacionList", true);
    model.addAttribute("operacionList", operacionPage.getContent());

    return "data";
  }
  

}