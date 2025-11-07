# Java SMS API SDK v5.0.0

A modern, feature-rich Java SDK for interacting with the SMS API service. Built with Java 21 and modern patterns, this SDK provides easy-to-use methods for managing contacts, sending messages, and handling tags with enhanced functionality and improved error handling.

## Features

- **Complete Contact Management**: Create, read, update, delete contacts with custom fields
- **Advanced Message Handling**: Send to individuals, groups, tags, and bulk messaging
- **Tag Management**: Create, manage, and organize contacts with tags
- **Shortlink Management**: Create, list, update shortlinks with statistics
- **UTF-8 Full Support**: Perfect handling of special characters (¡¿áéíóú, emojis, symbols) compatible with JavaScript and Python SDKs
- **Modern Java Patterns**: Records, Optional, CompletableFuture, and Java Time API
- **Robust Error Handling**: Comprehensive validation and error reporting
- **Async Support**: Non-blocking operations with CompletableFuture
- **Type Safety**: Strong typing with generics and modern Java features
- **Easy Integration**: Simple setup and intuitive API design
- **Cross-SDK Compatibility**: Consistent behavior with JavaScript and Python SDKs

## Rate Limits

The API has rate limits to ensure fair usage:

- **Shortlinks**: Maximum of 10 shortlinks created per minute per account (default)
- When you exceed the limit, you'll receive a 403 error with code `42900`
- **For inquiries or requests to increase the limit**: Please contact Technical Support directly through their support channels

Example error response:
```json
{
  "code": 42900,
  "error": "Ha excedido el límite de solicitudes. Intente nuevamente más tarde"
}
```

## Requirements

- Java 21 or higher
- Maven 3.6+ or Gradle 7.0+

## Installation

### Maven

Add the JAR to your project dependencies:

```xml
<dependency>
    <groupId>com.interactuamovil.apps.contactosms</groupId>
    <artifactId>im-contactosms-sdk-java</artifactId>
    <version>5.0.0</version>
</dependency>
```

### Manual Installation

1. Download the JAR file: `im-contactosms-sdk-java-5.0.0.jar`
2. Add it to your project's classpath
3. Include required dependencies (see Dependencies section)

## Configuration

Create a configuration file or use environment variables for your API credentials:

```properties
# examples.properties
API_KEY=your_api_key_here
API_SECRET=your_api_secret_here
API_URI=https://your-api-url.com/api/v4/
```

## Quick Start

### Basic Usage

```java
import com.interactuamovil.apps.contactosms.api.sdk.Messages;
import com.interactuamovil.apps.contactosms.api.sdk.Contacts;
import com.interactuamovil.apps.contactosms.api.sdk.Accounts;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;

// Initialize the API clients
Messages messages = new Messages(API_KEY, API_SECRET, API_URI);
Contacts contacts = new Contacts(API_KEY, API_SECRET, API_URI);
Accounts accounts = new Accounts(API_KEY, API_SECRET, API_URI);

// Test connection
ApiResponse<AccountJsonObject> account = accounts.getAccount();
if (account.isOk()) {
    System.out.println("Connected! Account: " + account.getResponse().getAccountName());
}
```

### Send a Message with UTF-8 Support

```java
// Send messages with perfect UTF-8 character support
var request = Messages.SendMessageRequest.toContact(
    "¡Hola desde Java SDK! ¿Se ven correctamente los caracteres especiales?", 
    "50212345678"
);

ApiResponse<MessageJson> response = messages.sendToContact(request);

if (response.isOk()) {
    System.out.println("Message sent successfully!");
    System.out.println("Message ID: " + response.getResponse().getId());
} else {
    System.err.println("Failed to send message: " + response.getErrorDescription());
}
```

### UTF-8 Character Examples

The SDK now perfectly handles all UTF-8 characters, just like JavaScript and Python:

```java
// Spanish characters
"¡Hola! ¿Cómo estás? Adiós"

// Accented letters
"Café, niño, corazón, piñata"

// Symbols and currency
"Precio: €50, £40, ¥100, $30"

// Emojis and Unicode
"¡Excelente!"

// Mathematical symbols
"Infinito: ∞, Plus/Minus: ±, Square root: √"
```

### Create a Contact

```java
ContactJsonObject contact = new ContactJsonObject();
contact.setMsisdn("50212345678");
contact.setFirstName("John");
contact.setLastName("Doe");
contact.setCustomField1("VIP Customer");

ApiResponse<ContactJsonObject> response = contacts.createContact(contact);

if (response.isOk()) {
    System.out.println("Contact created: " + response.getResponse().getMsisdn());
}
```

##  API Reference

### Messages

#### Modern Query Pattern (Recommended)

```java
// Create a query with modern Java Time API
var query = Messages.MessageQuery.of(
    LocalDateTime.now().minusDays(7),
    LocalDateTime.now()
)
.withPagination(0, 50)
.withMsisdn("50212345678")
.withDirection(MessageDirection.MT)
.withDeliveryStatus(true);

// Get messages with delivery status
ApiResponse<List<MessageJson>> messages = messages.getList(query);

if (messages.isOk()) {
    messages.getResponse().forEach(msg -> 
        System.out.println("Message: " + msg.getMessage() + 
                          " Status: " + msg.getDeliveryStatus())
    );
}
```

#### Send to Contact

```java
// Modern pattern
var request = Messages.SendMessageRequest.toContact(
    "Hello from Java SDK!", 
    "50212345678"
);
ApiResponse<MessageJson> response = messages.sendToContact(request);

// Legacy pattern
ApiResponse<MessageJson> response = messages.sendToContact(
    "50212345678", 
    "Hello from Java SDK!", 
    "unique-message-id"
);
```

#### Send to Groups/Tags

```java
// Send to multiple tags
var request = Messages.SendMessageRequest.toGroups(
    "Special offer for VIP customers!", 
    new String[]{"vip", "premium"}
);
ApiResponse<MessageJson> response = messages.sendToGroups(request);
```

#### Async Operations

```java
// Async message sending
CompletableFuture<ApiResponse<MessageJson>> future = 
    messages.sendToContactAsync(request);

future.thenAccept(response -> {
    if (response.isOk()) {
        System.out.println("Message sent asynchronously!");
    }
});

// Wait for completion
ApiResponse<MessageJson> response = future.get();
```

### Contacts

#### List Contacts

```java
ApiResponse<List<ContactJsonObject>> contacts = contacts.getList(
    0, 50, "SUSCRIBED", "search term"
);

if (contacts.isOk()) {
    contacts.getResponse().forEach(contact -> 
        System.out.println("Contact: " + contact.getFirstName() + 
                          " " + contact.getLastName())
    );
}
```

#### Create Contact

```java
ContactJsonObject contact = new ContactJsonObject();
contact.setMsisdn("50212345678");
contact.setFirstName("John");
contact.setLastName("Doe");
contact.setCustomField1("VIP Customer");
contact.setCustomField2("Premium Plan");

ApiResponse<ContactJsonObject> response = contacts.createContact(contact);
```

#### Update Contact

```java
ContactJsonObject updates = new ContactJsonObject();
updates.setFirstName("Jane");
updates.setLastName("Smith");

ApiResponse<ContactJsonObject> response = contacts.updateContact(
    "50212345678", updates
);
```

#### Delete Contact

```java
ApiResponse<ContactJsonObject> response = contacts.deleteContact("50212345678");
```

#### Add/Remove Tags

```java
// Add tag to contact
ApiResponse<ContactJsonObject> response = contacts.addTagToContact(
    "50212345678", "vip"
);

// Remove tag from contact
ApiResponse<ContactJsonObject> response = contacts.removeTagFromContact(
    "50212345678", "vip"
);
```

### Tags

#### List Tags

```java
ApiResponse<List<TagJsonObject>> tags = tags.getList(0, 50, 0);

if (tags.isOk()) {
    tags.getResponse().forEach(tag -> 
        System.out.println("Tag: " + tag.getName() + 
                          " (" + tag.getShortName() + ")")
    );
}
```

#### Create Tag

```java
TagJsonObject tag = new TagJsonObject();
tag.setName("VIP Customers");
tag.setShortName("vip");
tag.setDescription("High-value customers");

ApiResponse<TagJsonObject> response = tags.createTag(tag);
```

#### Get Tag Contacts

```java
ApiResponse<List<ContactJsonObject>> contacts = tags.getTagContacts(
    "vip", 0, 50, "SUSCRIBED"
);
```

#### Update Tag

```java
TagJsonObject updates = new TagJsonObject();
updates.setName("Updated VIP Customers");
updates.setDescription("Updated description");

ApiResponse<TagJsonObject> response = tags.updateTag("vip", updates);
```

#### Delete Tag

```java
ApiResponse<TagJsonObject> response = tags.deleteTag("vip");
```

### Shortlinks

#### List Shortlinks

```java
import com.interactuamovil.apps.contactosms.api.sdk.Shortlinks;

Shortlinks shortlinks = new Shortlinks(API_KEY, API_SECRET, API_URI);

// List all shortlinks
ApiResponse<List<ShortlinkJsonObject>> response = shortlinks.getList();

// List with filters
ApiResponse<List<ShortlinkJsonObject>> response = shortlinks.getList(
    "2024-01-01",  // start_date
    "2024-12-31",  // end_date
    10,            // limit
    0,             // offset
    null           // id
);

if (response.isOk()) {
    response.getResponse().forEach(shortlink -> 
        System.out.println("Shortlink: " + shortlink.getShortUrl())
    );
}
```

#### Get Shortlink by ID

```java
ApiResponse<ShortlinkJsonObject> response = shortlinks.getById("abc123");

if (response.isOk()) {
    ShortlinkJsonObject shortlink = response.getResponse();
    System.out.println("Short URL: " + shortlink.getShortUrl());
    System.out.println("Long URL: " + shortlink.getLongUrl());
    System.out.println("Visits: " + shortlink.getVisits());
}
```

#### Create Shortlink

```java
// Create with all parameters
ApiResponse<ShortlinkJsonObject> response = shortlinks.create(
    "https://www.example.com/very-long-url",
    "My Shortlink Name",
    "ACTIVE"
);

// Create with just URL (defaults to ACTIVE)
ApiResponse<ShortlinkJsonObject> response = shortlinks.create(
    "https://www.example.com/very-long-url"
);

if (response.isOk()) {
    ShortlinkJsonObject shortlink = response.getResponse();
    System.out.println("Created: " + shortlink.getShortUrl());
}
```

#### Update Shortlink Status

```java
ApiResponse<ShortlinkJsonObject> response = shortlinks.updateStatus(
    "abc123",      // shortlink ID
    "INACTIVE"     // new status
);

if (response.isOk()) {
    System.out.println("Status updated successfully");
}
```

## API Response Examples

### Create Shortlink - Success
```json
{
  "url_id": "123ABC",
  "short_url": "https://shorturl-pais.com/123ABC",
  "long_url": "https://www.example.com/very-long-url",
  "name": "Example Shortlink",
  "status": "ACTIVE",
  "account_uid": "abcde12345678kklm"
}
```

### List Shortlinks - Success
```json
{
  "success": true,
  "message": "Shortlinks retrieved successfully",
  "data": [
    {
      "_id": "123ABC",
      "account_uid": "abcde12345678kklm",
      "name": "Example Shortlink",
      "status": "ACTIVE",
      "base_url": "https://shorturl-pais.com/",
      "short_url": "https://shorturl-pais.com/123ABC",
      "long_url": "https://www.example.com/long-url-here",
      "visits": 0,
      "unique_visits": 0,
      "preview_visits": 0,
      "created_by": "SHORTLINK_API",
      "reference_type": "SHORT_LINK",
      "expiration": false,
      "expiration_date": null,
      "created_on": 1735689600000
    }
  ],
  "account_id": 12345
}
```

### Get Shortlink by ID - Success
```json
{
  "success": true,
  "message": "Shortlink found",
  "account_id": 12345,
  "url_id": "123ABC",
  "short_url": "https://shorturl-pais.com/123ABC",
  "long_url": "https://www.example.com/long-url-with-parameters",
  "name": "Example Shortlink",
  "status": "ACTIVE",
  "visits": 0,
  "unique_visits": 0,
  "preview_visits": 0,
  "created_by": "SHORTLINK_API",
  "created_on": 1735689600000
}
```

### Get Shortlink by ID - Not Found
```json
{
  "success": false,
  "message": "Shortlink not found"
}
```

### Rate Limit Exceeded
When you create too many shortlinks in a short time window (default: 10 per minute per account):
```json
{
  "code": 42900,
  "error": "Ha excedido el límite de solicitudes. Intente nuevamente más tarde"
}
```

## Testing and Examples

### Quick Test (Recommended for UTF-8 Testing)

Test the SDK with real message sending, including UTF-8 character support:

```bash
# Quick test with UTF-8 character examples
mvn exec:java -Dexec.mainClass="QuickTest" -q

# This will test:
# - Account connection
# - Contact retrieval  
# - Message sending with special characters: ¡¿áéíóú
# - Modern Java features
```

### Run Example Classes

Run the included examples to see the SDK in action:

```bash
# Run all examples
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.AllExamplesRunner"

# Run specific examples
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.MessagesExample"
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.ContactsExample"
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.AccountsExample"
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.ShortlinksExample"
```

### Test UTF-8 Encoding Compatibility

Verify that UTF-8 characters work exactly like JavaScript and Python SDKs:

```bash
# Test UTF-8 encoding (comprehensive test)
mvn exec:java -Dexec.mainClass="EncodingTest" -q

# Test modern messaging features
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.ModernMessagesExample"
```

The `QuickTest` class is perfect for verifying that your API credentials work and that UTF-8 characters are sent and received correctly, just like in the JavaScript and Python SDKs.

## Dependencies

The SDK requires the following dependencies:

```xml
<dependencies>
    <!-- Jackson for JSON processing -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.17.0</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.0</version>
    </dependency>
    
    <!-- Apache HttpClient -->
    <dependency>
        <groupId>org.apache.httpcomponents.client5</groupId>
        <artifactId>httpclient5</artifactId>
        <version>5.3.1</version>
    </dependency>
    
    <!-- Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.12</version>
    </dependency>
</dependencies>
```

## UTF-8 and Cross-SDK Compatibility

This Java SDK now provides **perfect UTF-8 character handling**, matching the behavior of other SDKs:

### What Works Perfectly
- **Spanish characters**: `¡¿` (inverted punctuation)
- **Accented letters**: `áéíóú ñÑ`
- **Currency symbols**: `€¢£¥`
- **Mathematical symbols**: `∞±√`
- **All Unicode characters**

### Technical Implementation
- **JSON Serialization**: Uses Jackson with `ESCAPE_NON_ASCII=false` (equivalent to Python's `ensure_ascii=False`)
- **HTTP Encoding**: All requests use `StandardCharsets.UTF_8` explicitly
- **Headers**: Proper `Content-Type: application/json; charset=UTF-8`
- **Signature Generation**: UTF-8 encoding for HMAC-SHA1 canonical strings

### Cross-SDK Compatibility
The Java SDK now generates identical:
- **HTTP signatures** as JavaScript and Python SDKs
- **JSON payloads** without character escaping
- **API requests** with consistent UTF-8 encoding

## Modern Java Features

This SDK leverages modern Java features:

- **Records**: Immutable data classes for queries and requests
- **Optional**: Null-safe operations
- **CompletableFuture**: Async operations
- **Java Time API**: Modern date/time handling
- **Pattern Matching**: Enhanced switch expressions
- **Text Blocks**: Multi-line strings
- **Var**: Type inference

## Error Handling

The SDK provides comprehensive error handling:

```java
ApiResponse<MessageJson> response = messages.sendToContact(request);

if (response.isOk()) {
    // Success
    MessageJson message = response.getResponse();
    System.out.println("Message sent: " + message.getId());
} else {
    // Error handling
    System.err.println("Error Code: " + response.getErrorCode());
    System.err.println("Error Description: " + response.getErrorDescription());
    System.err.println("HTTP Code: " + response.getHttpCode());
}
```

## Logging

The SDK uses SLF4J for logging. Configure your logging framework:

```xml
<!-- Logback configuration -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This SDK is licensed under the MIT License.

## Support

For support and questions:
- **Quick Start**: Run `mvn exec:java -Dexec.mainClass="QuickTest" -q` to test your setup
- **UTF-8 Issues**: Verify character handling with the examples in this README
- **Examples**: Check the comprehensive examples in the `examples` package
- **Test Cases**: Review the test cases for usage patterns
- **JavaScript/Python Compatibility**: All three SDKs now handle UTF-8 identically
- Contact the development team for additional support

### Troubleshooting UTF-8

If you experience character encoding issues:

1. **Test First**: Run `QuickTest` to verify your setup
2. **Check Logs**: Enable debug logging to see HTTP request/response details
3. **Verify Credentials**: Ensure your API credentials are correctly configured
4. **Compare with JavaScript/Python**: The behavior should be identical across all SDKs

---

Built for modern Java development