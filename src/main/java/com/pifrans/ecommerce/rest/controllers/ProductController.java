package com.pifrans.ecommerce.rest.controllers;

import com.pifrans.ecommerce.domains.dtos.ProductDto;
import com.pifrans.ecommerce.domains.entities.Product;
import com.pifrans.ecommerce.services.products.ProductService;
import com.pifrans.ecommerce.utilities.ModelMapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends GenericControllerImpl<Product, ProductController> {


    @Autowired
    public ProductController(ProductService productService, HttpServletRequest request, ModelMapperUtil modelMapperUtil) {
        super(productService, Product.class, ProductController.class, request, modelMapperUtil);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto.Save> findById(@PathVariable Long id) {
        return super.findById(ProductDto.Save.class, id);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto.Save>> findAll() {
        return super.findAll(ProductDto.Save.class);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductDto.Save>> findByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linesPerPage, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "ASC") String direction) {
        return super.findByPage(ProductDto.Save.class, page, linesPerPage, orderBy, direction);
    }

    @PostMapping
    public ResponseEntity<ProductDto.Save> save(@Validated @RequestBody Product body) {
        return super.save(ProductDto.Save.class, body);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<ProductDto.Save>> saveAll(@Validated @RequestBody List<Product> body) {
        return super.saveAll(ProductDto.Save.class, body);
    }

    @PutMapping
    public ResponseEntity<ProductDto.Save> update(@Validated @RequestBody Product body) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return super.update(ProductDto.Save.class, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto.Save> deleteById(@PathVariable Long id) {
        return super.deleteById(ProductDto.Save.class, id);
    }
}
