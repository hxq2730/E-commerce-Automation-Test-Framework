package org.example.tests.api;

import io.restassured.response.Response;
import org.example.api.ProductApi;
import org.example.models.Product;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductApiTest {
    @Test(description = "API: Verify get all products and pagination")
    public void testGetAllProduct() {
        Response response = ProductApi.getAllProduct();
        Assert.assertEquals(response.getStatusCode(), 200);

        List<Product> products = response.jsonPath().getList("data", Product.class);
        Assert.assertFalse(products.isEmpty(), "Product list should not be empty");

        // Verify first product has value
        Product firstProduct = products.get(0);
        Assert.assertNotNull(firstProduct.getName(), "Product Name should not be null");
        LogUtils.info("First Product: " + firstProduct.getName() + " , Price: " + firstProduct.getMain_price());

        // Verify Pagination
        Assert.assertNotNull(response.jsonPath().get("meta"), "Pagination metadata is missing");
        LogUtils.info("Test passed!");
    }

    @Test(description = "API: Verify Search product by keyword")
    public void testSearchProductByKeyword() {
        String keyword = "laptop";
        Map<String, Object> params = new HashMap<>();
        params.put("name", keyword);

        Response response = ProductApi.getProductList(params);
        Assert.assertEquals(response.getStatusCode(), 200);

        List<Product> products = response.jsonPath().getList("data", Product.class);
        Assert.assertFalse(products.isEmpty(), "Product list should not be empty");

        LogUtils.info("Found " + products.size() + " products for keyword '" + keyword + "'");
        if (products.isEmpty()) {
            LogUtils.warn("No products found for keyword '" + keyword + "'");
        } else {
            for (Product product: products) {
                boolean isMatch = product.getName().toLowerCase().contains(keyword.toLowerCase());
                Assert.assertNotNull(product.getName(), "Product Name should not be null");
                Assert.assertTrue(isMatch,
                        "Product name '" + product.getName() + "' should contain keyword '" + keyword + "'");
            }
        }
        LogUtils.info("Test passed");
    }

    @Test(description = "API: Verify Filter product by Price range")
    public void testFilterProductByPriceRange() {
        double maxPrice = 500.0;
        Map<String, Object> params = new HashMap<>();
        params.put("max", maxPrice);

        Response response = ProductApi.getProductList(params);
        Assert.assertEquals(response.getStatusCode(), 200);

        List<Product> products = response.jsonPath().getList("data", Product.class);
        Assert.assertFalse(products.isEmpty(), "Product list should not be empty");

        LogUtils.info("Found " + products.size() + " products under price: $" + maxPrice);
        for (Product product: products) {
            double actualPrice = product.getPriceAsDouble();
            Assert.assertTrue(actualPrice <= maxPrice,
                    "Failed: Product price " + product.getMain_price() + " should be <= $" + maxPrice);
        }
        LogUtils.info("Test passed");
    }

    @Test(description = "API: Verify Product detail consistency")
    public void testGetProductDetail() {
        // Get list product
        Response getListRes = ProductApi.getAllProduct();
        int productId = getListRes.jsonPath().getList("data", Product.class).get(0).getId();
        String productName = getListRes.jsonPath().getList("data", Product.class).get(0).getName();
        String productPrice = getListRes.jsonPath().getList("data", Product.class).get(0).getMain_price();

        // Get product detail
        Response getDetailRes = ProductApi.getProductDetail(productId);
        Assert.assertEquals(getDetailRes.getStatusCode(), 200);

        Product detailProduct = getDetailRes.jsonPath().getObject("data[0]", Product.class);
        Assert.assertEquals(detailProduct.getId(), productId, "Product ID mismatch");
        Assert.assertEquals(detailProduct.getName(), productName, "Product Name mismatch");
        Assert.assertEquals(detailProduct.getMain_price(), productPrice, "Product Price mismatch");

        LogUtils.info("Verified Product: " + detailProduct.getName() + " , Price: " + detailProduct.getMain_price());
    }



}
