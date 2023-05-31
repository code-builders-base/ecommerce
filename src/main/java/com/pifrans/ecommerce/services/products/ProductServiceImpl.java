package com.pifrans.ecommerce.services.products;

import com.pifrans.ecommerce.domains.entities.Product;
import com.pifrans.ecommerce.repositories.ProductRepository;
import com.pifrans.ecommerce.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends GenericServiceImpl<Product> implements ProductService {


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
    }
}
