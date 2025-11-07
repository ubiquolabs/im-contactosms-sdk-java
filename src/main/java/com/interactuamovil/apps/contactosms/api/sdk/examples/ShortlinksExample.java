package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.shortlinks.ShortlinkJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.Shortlinks;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ShortlinksExample extends BaseExample {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Random RANDOM = new Random();

    private String testLongUrl = null;

    public ShortlinksExample(String apiKey, String apiSecretKey, String apiUri, Configuration config) {
        super(apiKey, apiSecretKey, apiUri, config);
    }

    @Override
    public void configure() {
        testLongUrl = getConfig().getString("test_long_url", "https://www.example.com/test-shortlink");
    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Shortlinks shortlinksApi = new Shortlinks(getApiKey(), getApiSecretKey(), getApiUri());

        System.out.println("=== Testing Shortlinks API ===\n");

        testCreateShortlink(shortlinksApi);
        testListShortlinks(shortlinksApi);
        testGetShortlinkById(shortlinksApi);
        testUpdateStatus(shortlinksApi);
        testListByDateRange(shortlinksApi);
    }

    private void testCreateShortlink(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("1. Testing Create Shortlink");
        System.out.println("---------------------------");

        String longUrl = getRandomLongUrl();
        String name = "Test Shortlink " + System.currentTimeMillis();
        
        ApiResponse<ShortlinkJsonObject> response = shortlinksApi.create(longUrl, name, "ACTIVE");
        
        if (response.isOk()) {
            ShortlinkJsonObject shortlink = response.getResponse();
            System.out.println("Success! Created shortlink:");
            System.out.println("  ID: " + (shortlink.getUrlId() != null ? shortlink.getUrlId() : shortlink.getId()));
            System.out.println("  Name: " + shortlink.getName());
            System.out.println("  Short URL: " + shortlink.getShortUrl());
            System.out.println("  Long URL: " + shortlink.getLongUrl());
            System.out.println("  Status: " + shortlink.getStatus());
        } else {
            System.err.println("Error creating shortlink: " + response.getErrorDescription());
            System.err.println("Error code: " + response.getErrorCode());
        }
        System.out.println();
    }

    private void testListShortlinks(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("2. Testing List Shortlinks");
        System.out.println("--------------------------");

        ApiResponse<List<ShortlinkJsonObject>> response = shortlinksApi.getList(null, null, 10, null, null);
        
        if (response.isOk()) {
            List<ShortlinkJsonObject> shortlinks = response.getResponse();
            System.out.println("Success! Found " + shortlinks.size() + " shortlinks");
            
            for (int i = 0; i < Math.min(3, shortlinks.size()); i++) {
                ShortlinkJsonObject sl = shortlinks.get(i);
                System.out.println("  " + (i + 1) + ". " + (sl.getUrlId() != null ? sl.getUrlId() : sl.getId()) + 
                                 " - " + sl.getShortUrl() + " (" + sl.getStatus() + ")");
            }
        } else {
            System.err.println("Error listing shortlinks: " + response.getErrorDescription());
        }
        System.out.println();
    }

    private void testGetShortlinkById(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("3. Testing Get Shortlink by ID");
        System.out.println("-------------------------------");

        ApiResponse<List<ShortlinkJsonObject>> listResponse = shortlinksApi.getList(null, null, 1, null, null);
        
        if (listResponse.isOk() && listResponse.getResponse() != null && !listResponse.getResponse().isEmpty()) {
            ShortlinkJsonObject firstShortlink = listResponse.getResponse().get(0);
            String id = firstShortlink.getUrlId() != null ? firstShortlink.getUrlId() : firstShortlink.getId();
            
            if (id != null) {
                ApiResponse<ShortlinkJsonObject> response = shortlinksApi.getById(id);
                
                if (response.isOk()) {
                    ShortlinkJsonObject shortlink = response.getResponse();
                    System.out.println("Success! Found shortlink:");
                    System.out.println("  ID: " + (shortlink.getUrlId() != null ? shortlink.getUrlId() : shortlink.getId()));
                    System.out.println("  Name: " + shortlink.getName());
                    System.out.println("  Short URL: " + shortlink.getShortUrl());
                    System.out.println("  Status: " + shortlink.getStatus());
                    System.out.println("  Visits: " + shortlink.getVisits());
                    System.out.println("  Unique Visits: " + shortlink.getUniqueVisits());
                } else {
                    System.err.println("Error getting shortlink: " + response.getErrorDescription());
                }
            } else {
                System.out.println("No shortlink ID found to test");
            }
        } else {
            System.out.println("No shortlinks found to test getById");
        }
        System.out.println();
    }

    private void testUpdateStatus(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("4. Testing Update Status");
        System.out.println("-------------------------");

        ApiResponse<List<ShortlinkJsonObject>> listResponse = shortlinksApi.getList(null, null, 1, null, null);
        
        if (listResponse.isOk() && listResponse.getResponse() != null && !listResponse.getResponse().isEmpty()) {
            ShortlinkJsonObject firstShortlink = listResponse.getResponse().get(0);
            String id = firstShortlink.getUrlId() != null ? firstShortlink.getUrlId() : firstShortlink.getId();
            
            if (id != null) {
                String newStatus = "ACTIVE".equals(firstShortlink.getStatus()) ? "INACTIVE" : "ACTIVE";
                
                ApiResponse<ShortlinkJsonObject> response = shortlinksApi.updateStatus(id, newStatus);
                
                if (response.isOk()) {
                    System.out.println("Success! Updated shortlink status to: " + newStatus);
                } else {
                    System.err.println("Error updating status: " + response.getErrorDescription());
                }
            } else {
                System.out.println("No shortlink ID found to test");
            }
        } else {
            System.out.println("No shortlinks found to test updateStatus");
        }
        System.out.println();
    }

    private void testListByDateRange(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("5. Testing List by Date Range");
        System.out.println("------------------------------");

        Date endDate = new Date();
        Date startDate = new Date(endDate.getTime() - (30L * 24 * 60 * 60 * 1000)); // 30 days ago
        
        String startDateStr = DATE_FORMAT.format(startDate);
        String endDateStr = DATE_FORMAT.format(endDate);

        ApiResponse<List<ShortlinkJsonObject>> response = shortlinksApi.getList(startDateStr, endDateStr, 20, null, null);
        
        if (response.isOk()) {
            List<ShortlinkJsonObject> shortlinks = response.getResponse();
            System.out.println("Success! Found " + shortlinks.size() + " shortlinks in date range");
            System.out.println("  Date range: " + startDateStr + " to " + endDateStr);
        } else {
            System.err.println("Error listing shortlinks by date: " + response.getErrorDescription());
        }
        System.out.println();
    }

    private String getRandomLongUrl() {
        String[] urls = {
            "https://www.example.com/mi-pagina-muy-larga-con-parametros",
            "https://www.google.com/search?q=test+shortlink+api",
            "https://www.youtube.com/watch?v=test",
            "https://www.amazon.com/producto-super-largo",
            "https://www.github.com/usuario/repositorio-muy-largo"
        };
        return urls[RANDOM.nextInt(urls.length)];
    }
}

