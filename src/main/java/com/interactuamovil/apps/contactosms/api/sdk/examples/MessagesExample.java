package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.client.rest.messages.MessageJson;
import com.interactuamovil.apps.contactosms.api.enums.MessageDirection;
import com.interactuamovil.apps.contactosms.api.sdk.Messages;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Modern Messages Example using Java 21 features
 */
@SuppressWarnings("ChainedMethodCall")
class MessagesExample extends BaseExample {

    private String testMessage = null;
    private String testContactMsisdn = null;
    private String testGroupSmsShortName = null;

    protected MessagesExample(String apiKey, String apiSecretKey, String apiUri, Configuration config) {
        super(apiKey, apiSecretKey, apiUri, config);
    }

    @Override
    public void configure() {
        testMessage = getConfig().getString("test_message");
        testContactMsisdn = getConfig().getString("test_contact_msisdn");
        testGroupSmsShortName = getConfig().getString("test_group_sms_short_name");

        if (null == testMessage) {
            throw new AssertionError("Add test_message configuration.");
        }

        if (null == testContactMsisdn) {
            throw new AssertionError("Add test_contact_msisdn configuration.");
        }

        if (null == testGroupSmsShortName) {
            throw new AssertionError("Add test_group_sms_short_name configuration.");
        }
    }

    @Override   
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Messages messagesApi = new Messages(getApiKey(), getApiSecretKey(), getApiUri());

        testGetMessages(messagesApi);
        testSendToContact(messagesApi);
        testSendToGroup(messagesApi);
    }

    private void testGetMessages(Messages messagesApi) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse("2015-01-01 00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse("2017-01-01 00:00", formatter);
        
        int start = 0;
        int limit = 10;
        String msisdn = null;

        try {
            // Use modern MessageQuery with delivery status
            Messages.MessageQuery query = new Messages.MessageQuery(
                startDate, endDate, start, limit, msisdn, MessageDirection.MT, true
            );
            
            ApiResponse<List<MessageJson>> response = messagesApi.getList(query);

            if (!response.isOk()) {
                throw new AssertionError("Error getting messages: " + response.getErrorDescription());
            } else {
                for (MessageJson m : response.getResponse()) {
                    System.out.printf("msg: [%s] [%d] [%s] [%s] [%s] [%s] [%s] [%s]%n",
                            m.getMessageDirection().name(),
                            m.getMessageTypeId(),
                            m.getClientMessageId(),
                            m.getShortCode(),
                            m.getMsisdn(),
                            m.getMessage(),
                            m.getTags() != null ? StringUtils.join(m.getTags(), ",") : "",
                            formatter.format(m.getCreatedOn().toInstant()
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .toLocalDateTime())
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testSendToContact(Messages messagesApi) {
        // Use modern SendMessageRequest
        String uniqueMessage = String.format("%s - %s", testMessage, UUID.randomUUID().toString().substring(0, 8));
        Messages.SendMessageRequest request = Messages.SendMessageRequest.toContact(
                uniqueMessage, "50252010101"
        );

        ApiResponse<MessageJson> response = messagesApi.sendToContact(request);

        if (!response.isOk()) {
            throw new AssertionError("Error sending message to new contact: " + response.getErrorDescription());
        }
    }

    private void testSendToGroup(Messages messagesApi) {
        // Use modern SendMessageRequest
        String uniqueMessage = String.format("%s - %s", testMessage, UUID.randomUUID().toString().substring(0, 8));
        Messages.SendMessageRequest request = Messages.SendMessageRequest.toGroups(
                uniqueMessage, new String[]{"comida", "dennis"}
        );

        ApiResponse<MessageJson> response = messagesApi.sendToGroups(request);

        if (!response.isOk()) {
            throw new AssertionError("Error sending message to group: " + response.getErrorDescription());
        }
    }
}
