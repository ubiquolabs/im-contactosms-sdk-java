package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.shortlinks.ShortlinkJsonObject;
import com.interactuamovil.apps.contactosms.api.sdk.Shortlinks;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import com.interactuamovil.apps.contactosms.api.utils.JavaVersionDetector;
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
        test(new String[0]);
    }

    @Override
    public void test(String... args) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Shortlinks shortlinksApi = new Shortlinks(getApiKey(), getApiSecretKey(), getApiUri());

        System.out.println("=== Testing Shortlinks API ===\n");

        ShortlinkCommands commands = parseArgs(args);

        switch (commands.command()) {
            case "create":
                testCreateShortlink(shortlinksApi, commands.longUrl(), commands.name(), commands.status(), commands.alias());
                break;
            case "list":
                testListShortlinks(shortlinksApi, commands.limit(), commands.offset());
                break;
            case "date":
                testListByDateRange(shortlinksApi, commands.startDate(), commands.endDate(), commands.limit(), commands.offset());
                break;
            case "id":
                require(commands.id(), "Shortlink ID is required");
                testGetShortlinkById(shortlinksApi, commands.id());
                break;
            case "update":
                require(commands.id(), "Shortlink ID is required");
                require(commands.status(), "Status is required");
                testUpdateStatus(shortlinksApi, commands.id(), commands.status());
                break;
            case "status":
                testStatusValidation(shortlinksApi);
                break;
            default:
                runDefaultFlow(shortlinksApi);
                break;
        }
    }

    private void runDefaultFlow(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        testCreateShortlink(shortlinksApi, null, null, null, null);
        testListShortlinks(shortlinksApi, 10, null);
        testGetShortlinkById(shortlinksApi, null);
        testUpdateStatus(shortlinksApi, null, "INACTIVE");
        testListByDateRange(shortlinksApi, null, null, 20, null);
    }

    private void testCreateShortlink(Shortlinks shortlinksApi, String longUrl, String name, String status, String alias)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("1. Testing Create Shortlink");
        System.out.println("---------------------------");

        String longUrlValue = longUrl != null ? longUrl : getRandomLongUrl();
        String nameValue = name != null ? name : "Test Shortlink " + System.currentTimeMillis();
        String aliasValue = alias != null ? alias : getRandomAlias();
        String statusValue = status != null ? status : "ACTIVE";
        
        ApiResponse<ShortlinkJsonObject> response = shortlinksApi.create(longUrlValue, nameValue, aliasValue, statusValue);
        
        if (response.isOk() && response.getResponse() != null) {
            ShortlinkJsonObject shortlink = response.getResponse();
            System.out.println("Success! Created shortlink:");
            System.out.println("  ID: " + (shortlink.getUrlId() != null ? shortlink.getUrlId() : shortlink.getId()));
            System.out.println("  Name: " + shortlink.getName());
            System.out.println("  Alias: " + shortlink.getAlias());
            System.out.println("  Short URL: " + shortlink.getShortUrl());
            System.out.println("  Long URL: " + shortlink.getLongUrl());
            System.out.println("  Status: " + shortlink.getStatus());
        } else {
            System.err.println("Error creating shortlink: " + (response.getErrorDescription() != null ? response.getErrorDescription() : "Unknown error"));
            if (response.getErrorCode() != null) {
                System.err.println("Error code: " + response.getErrorCode());
            }
            if (response.getHttpCode() > 0) {
                System.err.println("HTTP Code: " + response.getHttpCode());
            }
        }
        System.out.println();
    }

    private void testListShortlinks(Shortlinks shortlinksApi, Integer limit, Integer offset) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("2. Testing List Shortlinks");
        System.out.println("--------------------------");

        ApiResponse<List<ShortlinkJsonObject>> response = shortlinksApi.getList(null, null, limit, offset, null);
        
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

    private void testGetShortlinkById(Shortlinks shortlinksApi, String idOverride) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("3. Testing Get Shortlink by ID");
        System.out.println("-------------------------------");

        if (idOverride != null && !isBlank(idOverride)) {
            ApiResponse<ShortlinkJsonObject> response = shortlinksApi.getById(idOverride);
            if (response.isOk()) {
                printShortlink(response.getResponse());
            } else {
                System.err.println("Error getting shortlink: " + response.getErrorDescription());
            }
            System.out.println();
            return;
        }

        ApiResponse<List<ShortlinkJsonObject>> listResponse = shortlinksApi.getList(null, null, 1, null, null);
        
        if (listResponse.isOk() && listResponse.getResponse() != null && !listResponse.getResponse().isEmpty()) {
            ShortlinkJsonObject firstShortlink = listResponse.getResponse().get(0);
            String id = firstShortlink.getUrlId() != null ? firstShortlink.getUrlId() : firstShortlink.getId();
            
            if (id != null) {
                fetchAndPrintById(shortlinksApi, id);
            } else {
                System.out.println("No shortlink ID found to test");
            }
        } else {
            System.out.println("No shortlinks found to test getById");
        }
        System.out.println();
    }

    private void testUpdateStatus(Shortlinks shortlinksApi, String idOverride, String statusOverride) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("4. Testing Update Status");
        System.out.println("-------------------------");

        if (idOverride != null) {
            ApiResponse<ShortlinkJsonObject> response = shortlinksApi.updateStatus(idOverride, statusOverride);
            if (response.isOk()) {
                System.out.println("Success! Updated shortlink status.");
            } else {
                System.err.println("Error updating status: " + response.getErrorDescription());
            }
            System.out.println();
            return;
        }

        ApiResponse<List<ShortlinkJsonObject>> listResponse = shortlinksApi.getList(null, null, 1, null, null);
        
        if (listResponse.isOk() && listResponse.getResponse() != null && !listResponse.getResponse().isEmpty()) {
            ShortlinkJsonObject firstShortlink = listResponse.getResponse().get(0);
            String id = firstShortlink.getUrlId() != null ? firstShortlink.getUrlId() : firstShortlink.getId();
            
            if (id != null) {
                if (!"ACTIVE".equals(firstShortlink.getStatus())) {
                    System.out.println("Shortlink is already INACTIVE. Reactivation is not supported.");
                    return;
                }

                ApiResponse<ShortlinkJsonObject> response = shortlinksApi.updateStatus(id, "INACTIVE");

                if (response.isOk()) {
                    System.out.println("Success! Deactivated shortlink.");
                    fetchAndPrintById(shortlinksApi, id);
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

    private void testListByDateRange(Shortlinks shortlinksApi, String startDate, String endDate, Integer limit, Integer offset) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        System.out.println("5. Testing List by Date Range");
        System.out.println("------------------------------");

        String startDateStr = startDate;
        String endDateStr = endDate;

        if (startDateStr == null || endDateStr == null) {
            Date defaultEnd = new Date();
            Date defaultStart = new Date(defaultEnd.getTime() - (30L * 24 * 60 * 60 * 1000));
            startDateStr = startDateStr != null ? startDateStr : DATE_FORMAT.format(defaultStart);
            endDateStr = endDateStr != null ? endDateStr : DATE_FORMAT.format(defaultEnd);
        }

        ApiResponse<List<ShortlinkJsonObject>> response = shortlinksApi.getList(startDateStr, endDateStr, limit, offset, null);
        
        if (response.isOk()) {
            List<ShortlinkJsonObject> shortlinks = response.getResponse();
            System.out.println("Success! Found " + shortlinks.size() + " shortlinks in date range");
            System.out.println("  Date range: " + startDateStr + " to " + endDateStr);
        } else {
            System.err.println("Error listing shortlinks by date: " + response.getErrorDescription());
        }
        System.out.println();
    }

    private void testStatusValidation(Shortlinks shortlinksApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String[] invalid = {"PENDING", "DRAFT", "DELETED", "active", "inactive", "ACTIVE ", " INACTIVE"};
        for (String status : invalid) {
            System.out.println("Testing invalid status: " + status);
            try {
                shortlinksApi.create(getRandomLongUrl(), "Status Validation " + status, getRandomAlias(), status);
                System.out.println("  Unexpected success");
            } catch (IllegalArgumentException e) {
                System.out.println("  Expected error: " + e.getMessage());
            }
        }

        System.out.println("Testing valid statuses");
        shortlinksApi.create(getRandomLongUrl(), "Status Validation ACTIVE", null, "ACTIVE");
        shortlinksApi.create(getRandomLongUrl(), "Status Validation INACTIVE", null, "INACTIVE");
    }

    private void printShortlink(ShortlinkJsonObject shortlink) {
        System.out.println("Success! Found shortlink:");
        System.out.println("  ID: " + (shortlink.getUrlId() != null ? shortlink.getUrlId() : shortlink.getId()));
        System.out.println("  Name: " + shortlink.getName());
        System.out.println("  Alias: " + shortlink.getAlias());
        System.out.println("  Short URL: " + shortlink.getShortUrl());
        System.out.println("  Status: " + shortlink.getStatus());
        System.out.println("  Visits: " + shortlink.getVisits());
        System.out.println("  Unique Visits: " + shortlink.getUniqueVisits());
        System.out.println("  Preview Visits: " + shortlink.getPreviewVisits());
    }
    private void fetchAndPrintById(Shortlinks shortlinksApi, String id) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        ApiResponse<ShortlinkJsonObject> response = shortlinksApi.getById(id);
        if (response.isOk()) {
            printShortlink(response.getResponse());
        } else {
            System.err.println("Error getting shortlink: " + response.getErrorDescription());
        }
    }

    private ShortlinkCommands parseArgs(String... args) {
        if (args == null || args.length == 0) {
            return ShortlinkCommands.defaultCommand();
        }
        if ("shortlinks".equalsIgnoreCase(args[0])) {
            if (args.length == 1) {
                return ShortlinkCommands.defaultCommand();
            }
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);
            return ShortlinkCommands.from(subArgs);
        }
        return ShortlinkCommands.from(args);
    }

    private static final class ShortlinkCommands {
        private final String command;
        private final String id;
        private final String status;
        private final String alias;
        private final String longUrl;
        private final String name;
        private final String startDate;
        private final String endDate;
        private final Integer limit;
        private final Integer offset;
        
        public ShortlinkCommands(String command, String id, String status, String alias, 
                               String longUrl, String name, String startDate, String endDate, 
                               Integer limit, Integer offset) {
            this.command = command;
            this.id = id;
            this.status = status;
            this.alias = alias;
            this.longUrl = longUrl;
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.limit = limit;
            this.offset = offset;
        }
        
        public String command() { return command; }
        public String id() { return id; }
        public String status() { return status; }
        public String alias() { return alias; }
        public String longUrl() { return longUrl; }
        public String name() { return name; }
        public String startDate() { return startDate; }
        public String endDate() { return endDate; }
        public Integer limit() { return limit; }
        public Integer offset() { return offset; }

        static ShortlinkCommands defaultCommand() {
            return new ShortlinkCommands("default", null, null, null, null, null, null, null, 10, null);
        }

        static ShortlinkCommands from(String[] args) {
            String command = args.length == 0 ? "default" : args[0].toLowerCase();
            switch (command) {
                case "create":
                    return new ShortlinkCommands(
                        command,
                        null,
                        args.length > 3 ? args[3] : null,
                        args.length > 4 ? args[4] : null,
                        args.length > 1 ? args[1] : null,
                        args.length > 2 ? args[2] : null,
                        null,
                        null,
                        null,
                        null
                    );
                case "list":
                    return new ShortlinkCommands(
                        command,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        args.length > 1 ? parseInteger(args[1], 10) : 10,
                        args.length > 2 ? parseInteger(args[2], null) : null
                    );
                case "date":
                    return new ShortlinkCommands(
                        command,
                        null,
                        null,
                        null,
                        null,
                        null,
                        args.length > 1 ? args[1] : null,
                        args.length > 2 ? args[2] : null,
                        args.length > 3 ? parseInteger(args[3], 10) : 10,
                        args.length > 4 ? parseInteger(args[4], -6) : -6
                    );
                case "id":
                    return new ShortlinkCommands(
                        command,
                        args.length > 1 ? args[1] : null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    );
                case "update":
                    return new ShortlinkCommands(
                        command,
                        args.length > 1 ? args[1] : null,
                        args.length > 2 ? args[2].toUpperCase() : "INACTIVE",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    );
                case "status":
                    return new ShortlinkCommands(command, null, null, null, null, null, null, null, null, null);
                default:
                    return defaultCommand();
            }
        }

        private static Integer parseInteger(String value, Integer defaultValue) {
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }

    private static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (JavaVersionDetector.isJava11OrHigher()) {
            try {
                java.lang.reflect.Method isBlankMethod = String.class.getMethod("isBlank");
                return (Boolean) isBlankMethod.invoke(str);
            } catch (Exception e) {
                return str.trim().isEmpty();
            }
        } else {
            return str.trim().isEmpty();
        }
    }
    
    private void require(String value, String message) {
        if (value == null || isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    private String getRandomLongUrl() {
        String[] urls = {
            "https://example.com/faq/what-is-an-sms",
            "https://example.org/docs/product-messaging-platform",
            "https://learn.example.net/tutorials/sms-marketing-guide",
            "https://demo.example.com/campaigns/sms-autoresponder",
            "https://blog.example.io/articles/shortlinks-best-practices"
        };
        return urls[RANDOM.nextInt(urls.length)];
    }

    private String getRandomAlias() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }
        return sb.toString();
    }
}

