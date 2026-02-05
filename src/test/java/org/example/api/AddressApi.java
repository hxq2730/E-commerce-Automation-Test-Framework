package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.FrameworkConstants;
import org.example.models.ShippingAddress;
import org.example.utils.LogUtils;

public class AddressApi {
    /**
     * Get List shipping address
     * @param token - access token
     * @return Response
     */
    public static Response getShippingAddressList(String token){
        LogUtils.info("API: Getting List shipping address");
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/user/shipping/address");

        LogUtils.info("Get List shipping address Status code: " + response.getStatusCode());
        //response.prettyPrint();
        return response;
    }

    /**
     * Add new shipping address
     * @param token - access-token
     * @param shippingAddress new shipping address
     * @return response
     */
    public static Response addShippingAddress(String token, ShippingAddress shippingAddress) {
        LogUtils.info("API: Adding new Shipping Address...");
        Response response =  RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(shippingAddress)
                .when()
                .post("/user/shipping/create");

        LogUtils.info("Add new shipping address Status code: " + response.getStatusCode());
        //response.prettyPrint();
        LogUtils.info("Successfully add new shipping address ");
        return response;
    }

    /**
     * Delete shipping address by ID
     * URL: /user/shipping/delete/{id}
     * @param token - access token
     * @param addressID - address id
     * @return Response
     */
    public static Response deleteShippingAddress(String token, int addressID) {
        LogUtils.info("Deleting Shipping address id " + addressID);
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/user/shipping/delete/" + addressID);

        LogUtils.info("Delete shipping address id " + addressID + " Status code: " + response.getStatusCode());
        return response;
    }

    public static Response updateShippingAddress(String token, ShippingAddress addressData) {
        LogUtils.info("API: Updating Shipping Address ID: " + addressData.getId());
        Response response =  RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(addressData)
                .when()
                .post("/user/shipping/update");

        LogUtils.info("Update shipping address id " + addressData.getId() + " Status code: " + response.getStatusCode());
        return response;
    }
}
