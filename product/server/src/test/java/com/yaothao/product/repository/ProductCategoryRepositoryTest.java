package com.yaothao.product.repository;

import com.yaothao.product.dataobject.ProductCategory;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void findByCategoryTypeIn() throws Exception{
        List<Integer> test = new ArrayList<>();
        test.add(11);
        test.add(22);
        List<ProductCategory> list = productCategoryRepository.findByCategoryTypeIn(test);
        Assert.assertTrue(list.size() > 0);
    }
}