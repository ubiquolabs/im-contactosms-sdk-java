# Java SMS API SDK v5.0.0

A modern, feature-rich Java SDK for interacting with the SMS API service. Built with Java 21 and modern patterns, this SDK provides easy-to-use methods for managing contacts, sending messages, and handling tags with enhanced functionality and improved error handling.

## 🚀 Features

- **Complete Contact Management**: Create, read, update, delete contacts with custom fields
- **Advanced Message Handling**: Send to individuals, groups, tags, and bulk messaging
- **Tag Management**: Create, manage, and organize contacts with tags
- **Modern Java Patterns**: Records, Optional, CompletableFuture, and Java Time API
- **Robust Error Handling**: Comprehensive validation and error reporting
- **Async Support**: Non-blocking operations with CompletableFuture
- **Type Safety**: Strong typing with generics and modern Java features
- **Easy Integration**: Simple setup and intuitive API design

## 📋 Requirements

- Java 21 or higher
- Maven 3.6+ or Gradle 7.0+

## ️ Installation

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

## ⚙️ Configuration

Create a configuration file or use environment variables for your API credentials:

```properties
# examples.properties
API_KEY=your_api_key_here
API_SECRET=your_api_secret_here
API_URI=https://your-api-url.com/api/v4/
```

## 🚀 Quick Start

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

### Send a Message

```java
// Send to a specific contact using modern pattern
var request = Messages.SendMessageRequest.toContact(
    "Hello from Java SDK v5.0! 🚀", 
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

## 🧪 Examples

Run the included examples to see the SDK in action:

```bash
# Run all examples
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.AllExamplesRunner"

# Run specific examples
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.MessagesExample"
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.ContactsExample"
mvn exec:java -Dexec.mainClass="com.interactuamovil.apps.contactosms.api.sdk.examples.AccountsExample"
```

## 🔧 Dependencies

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

## 🚀 Modern Java Features

This SDK leverages modern Java features:

- **Records**: Immutable data classes for queries and requests
- **Optional**: Null-safe operations
- **CompletableFuture**: Async operations
- **Java Time API**: Modern date/time handling
- **Pattern Matching**: Enhanced switch expressions
- **Text Blocks**: Multi-line strings
- **Var**: Type inference

## 🔍 Error Handling

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

## 📝 Logging

The SDK uses SLF4J for logging. Configure your logging framework:

```xml
<!-- Logback configuration -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This SDK is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Check the examples in the `examples` package
- Review the test cases for usage patterns
- Contact the development team

---

**Built with ❤️ for modern Java development**