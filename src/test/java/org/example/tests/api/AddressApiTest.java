package org.example.tests.api;

import io.restassured.response.Response;
import org.example.api.AddressApi;
import org.example.base.AuthBaseTest;
import org.example.models.ShippingAddress;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class AddressApiTest extends AuthBaseTest {

    @Test(description = "API: Verify User can get Address list ")
    public void testGetAddressListSuccess() {
        // Get list address
        Response getListAddress = AddressApi.getShippingAddressList(token);
        Assert.assertEquals(getListAddress.getStatusCode(), 200, "Status code mismatch!");
        Assert.assertTrue(getListAddress.jsonPath().getBoolean("success"), "Get list address failed!");
    }

    @Test(description = "API: Verify User can add new shipping address via API")
    public void testAddShippingAddressSuccess() {
//        UserAccount user = new UserAccount();
//        AuthApi.register(user);
//        AuthApi.login(user.getEmail(), user.getPassword());

        // Add new shipping address
        ShippingAddress shippingAddress = new ShippingAddress();
        LogUtils.info("Adding new shipping address: " + shippingAddress.getAddress());
        Response addressResponse = AddressApi.addShippingAddress(token, shippingAddress);

        // Verify add a new Shipping address successfully
        Assert.assertEquals(addressResponse.getStatusCode(), 200, "Status code mismatch!");
        Assert.assertTrue(addressResponse.jsonPath().getBoolean("result"), "Add shipping address failed!");
        Assert.assertTrue(addressResponse.jsonPath().getString("message").contains("Shipping information has been " +
                "added successfully"), "Add shipping address failed!");

        // Get list address
        Response getListAddress = AddressApi.getShippingAddressList(token);
        Assert.assertEquals(getListAddress.getStatusCode(), 200, "Status code mismatch!");
        Assert.assertTrue(getListAddress.jsonPath().getBoolean("success"), "Get list address failed!");

        // Verify list address contains new shipping address and get its ID
        List<Map<String, Object>> listData = getListAddress.jsonPath().getList("data");
        int createdAddressId = 0;
        boolean found = false;
        
        for (Map<String, Object> address : listData) {
            if (address.get("address").equals(shippingAddress.getAddress())) {
                createdAddressId = (Integer) address.get("id");
                found = true;
                break;
            }
        }
        
        Assert.assertTrue(found, "New shipping address not found in list!");
        LogUtils.info("Test Passed: Address added and verified new shipping address found in list!");

        // Teardown
        AddressApi.deleteShippingAddress(token, createdAddressId);
    }

    @Test(description = "API: Verify User can delete shipping address via API (CRUD: Add -> Get -> Delete Address)")
    public void testDeleteShippingAddressSuccess() {
        // Add an address to delete
        ShippingAddress shippingAddress = new ShippingAddress();
        LogUtils.info("Adding new shipping address: " + shippingAddress.getAddress());
        Response addressResponse = AddressApi.addShippingAddress(token, shippingAddress);
        Assert.assertEquals(addressResponse.getStatusCode(), 200, "Add shipping address Status code mismatch!");

        // Get address ID
        Response getListAddressResponse = AddressApi.getShippingAddressList(token);
        Assert.assertEquals(getListAddressResponse.getStatusCode(), 200, "Get list address Status code mismatch!");

        // Verify list address contains new shipping address
        List<Map<String, Object>> listData = getListAddressResponse.jsonPath().getList("data");
        Assert.assertTrue(listData.stream().anyMatch(address -> address.get("address").equals(shippingAddress.getAddress())),
                "New shipping address not found in list!");

        int addressIdToDelete = 0;
        boolean found = false;

        for (Map<String, Object> item : listData) {
            if (item.get("address").equals(shippingAddress.getAddress())) {
                addressIdToDelete = (Integer) item.get("id");
                found = true;
                break;
            }
        }

        Assert.assertTrue(found, "Shipping address not found in list!");
        LogUtils.info("Found Address ID to delete: " + addressIdToDelete);

        // Delete a shipping address
        Response deleteResponse = AddressApi.deleteShippingAddress(token, addressIdToDelete);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Delete shipping address Status code mismatch!");
        Assert.assertTrue(deleteResponse.jsonPath().getString("message").contains("deleted"), "Delete shipping " +
                "address failed!");

        // Verify deleted successfully
        Response finalGetListAddressResponse = AddressApi.getShippingAddressList(token);
        Assert.assertEquals(finalGetListAddressResponse.getStatusCode(), 200, "Get list address Status code mismatch!");

        // Verify list address not contains deleted shipping address
        List<Map<String, Object>> finalListData = finalGetListAddressResponse.jsonPath().getList("data");
        Assert.assertFalse(finalListData.stream().anyMatch(address -> address.get("address").equals(shippingAddress.getAddress())),
                "Deleted shipping address" + addressIdToDelete + " found in list!");

        LogUtils.info("Test Passed: Address deleted and verified deleted shipping address not found in list!");
    }

    @Test(description = "API: Verify User can delete all shipping address")
    public void testDeleteAllShippingAddressSuccess() {
        LogUtils.info("Getting all addresses to delete...");
        Response getListAddressResponse = AddressApi.getShippingAddressList(token);
        Assert.assertEquals(getListAddressResponse.getStatusCode(), 200, "Get list address Status code mismatch!");

        // Get list address ID
        List<Map<String, Object>> addressList = getListAddressResponse.jsonPath().getList("data");
        LogUtils.info("List address size: " + addressList.size());

        if (addressList.isEmpty()) {
            LogUtils.info("List is already empty. Nothing to delete");
            return;
        }

        // Delete all
        for (Map<String, Object> item : addressList) {
            int addressIdToDelete = (Integer) item.get("id");
            String addressName = (String) item.get("address");
            LogUtils.info("Deleting address ID: " + addressIdToDelete + " - " + addressName);

            Response deleteResponse = AddressApi.deleteShippingAddress(token, addressIdToDelete);
            Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Delete shipping address Status code mismatch!");
            Assert.assertTrue(deleteResponse.jsonPath().getString("message").contains("deleted"), "Delete shipping " +
                    "address failed!");
        }

        // Verify address list is empty
        Response finalGetListAddress = AddressApi.getShippingAddressList(token);
        Assert.assertEquals(finalGetListAddress.getStatusCode(), 200, "Get list address Status code mismatch!");

        List<Map<String, Object>> finalListData = finalGetListAddress.jsonPath().getList("data");
        Assert.assertTrue(finalListData.isEmpty(), "Failed: Address list is not empty after deletion!");
        LogUtils.info("Clean up successful! All addresses deleted.");
    }

    @Test(description = "Verify User can update a shipping address (Flow: Create -> Get ID -> Update -> Verify)")
    public void testUpdateShippingAddressSuccess() {
        // Create a new address
        ShippingAddress newAddress = new ShippingAddress();
        LogUtils.info("Adding new shipping address: " + newAddress.getAddress());
        Response addressResponse = AddressApi.addShippingAddress(token, newAddress);
        Assert.assertEquals(addressResponse.getStatusCode(), 200, "Add shipping address Status code mismatch!");

        // Get ID address created
        Response getRes = AddressApi.getShippingAddressList(token);
        List<Map<String, Object>> listData = getRes.jsonPath().getList("data");

        int addressId = 0;
        for (Map<String, Object> item : listData) {
            if (item.get("address").toString().equals(newAddress.getAddress())) {
                addressId = (Integer) item.get("id");
                break;
            }
        }
        Assert.assertTrue(addressId > 0, "Failed: Address ID not found!");

        // Update address
        LogUtils.info("Updating Address ID " + addressId);
        String updateName = "Updated " + newAddress.getAddress();
        newAddress.setId(addressId);
        newAddress.setAddress(updateName);

        Response updateResponse = AddressApi.updateShippingAddress(token, newAddress);
        Assert.assertEquals(updateResponse.getStatusCode(), 200, "Update shipping address Status code mismatch!");
        Assert.assertTrue(updateResponse.jsonPath().getString("message").contains("updated"), "Update shipping " +
                "address failed!");

        // Verify data changed after the update
        Response getUpdatedAddressResponse = AddressApi.getShippingAddressList(token);
        List<Map<String, Object>> updatedListData = getUpdatedAddressResponse.jsonPath().getList("data");

        boolean isUpdated = false;
        for (Map<String, Object> item : updatedListData) {
            if ((Integer) item.get("id") == addressId) {
                // Check new address name equals new update data
                Assert.assertEquals(item.get("address").toString(), updateName, "Address name mismatch!");
                isUpdated = true;
                break;
            }
        }

        Assert.assertTrue(isUpdated, "FAILED: Address ID " + addressId + " was not updated correctly in the list.");
        LogUtils.info("Test Passed: Address updated successfully!");
        // Teardown
        AddressApi.deleteShippingAddress(token, addressId);
    }
}
