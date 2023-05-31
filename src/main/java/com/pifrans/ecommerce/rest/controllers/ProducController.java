package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.domains.entities.Product;
import com.pifrans.ecommerce.services.products.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProducController extends GenericControllerImpl<Product, ProducController> {


    @Autowired
    public ProducController(ProductService productService, HttpServletRequest request) {
        super(productService, Product.class, ProducController.class, request);
    }
}
