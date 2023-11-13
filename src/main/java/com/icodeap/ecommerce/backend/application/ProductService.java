package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
public class ProductService {
    private final IProductRepository iProductRepository;
    private final UploadFile uploadFile;

    public ProductService(IProductRepository iProductRepository, UploadFile uploadFile) {
        this.iProductRepository = iProductRepository;
        this.uploadFile = uploadFile;
    }

    public Product save(Product product, MultipartFile multipartFile) throws IOException {
        if(product.getId()!=0){//cuando es un producto modificado
            if(multipartFile==null){
                product.setUrlImage(product.getUrlImage());
            }else{
                String nameFile = product.getUrlImage().substring(29);
                log.info("este es el nombre de la imagen: {}", nameFile);
                if (!nameFile.equals("default.jpg")){
                    uploadFile.delete(nameFile);
                }
                product.setUrlImage(uploadFile.upload(multipartFile));
            }
        }else{
            product.setUrlImage(uploadFile.upload(multipartFile));
        }

        return this.iProductRepository.save(product);
    }

    public Iterable<Product> findAll(){
        return this.iProductRepository.findAll();
    }

    public Product findById(Integer id){
        return this.iProductRepository.findById(id);
    }
    public void deleteById(Integer id){
        Product product = findById(id);
        String nameFile = product.getUrlImage().substring(29);
        log.info("este es el nombre de la imagen: {}", nameFile);
        if (!nameFile.equals("default.jpg")){
            uploadFile.delete(nameFile);
        }
        this.iProductRepository.deleteById(id);
    }
}
