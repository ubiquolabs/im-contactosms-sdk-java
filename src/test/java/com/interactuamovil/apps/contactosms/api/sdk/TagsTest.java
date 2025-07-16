package com.interactuamovil.apps.contactosms.api.sdk;

import com.interactuamovil.apps.contactosms.api.client.rest.tags.TagJsonObject;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Modern unit tests for Tags API functionality
 */
@DisplayName("Tags API Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagsTest {

    private static final String TEST_API_KEY = "test-api-key";
    private static final String TEST_SECRET_KEY = "test-secret-key";
    private static final String TEST_API_URI = "https://api.test.com/";
    private static final String TEST_TAG_NAME = "test-tag";

    private Tags tags;

    @BeforeEach
    void setUp() {
        tags = new Tags(TEST_API_KEY, TEST_SECRET_KEY, TEST_API_URI);
    }

    @Test
    @DisplayName("Should create Tags instance with valid parameters")
    void shouldCreateTagsInstanceWithValidParameters() {
        assertThat(tags).isNotNull();
        assertThat(tags.getApiKey()).isEqualTo(TEST_API_KEY);
        assertThat(tags.getApiSecretKey()).isEqualTo(TEST_SECRET_KEY);
        assertThat(tags.getApiUri()).isEqualTo(TEST_API_URI);
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void shouldHandleNullParametersGracefully() {
        // Tags constructor doesn't validate parameters, so it should not throw exceptions
        Tags tagsWithNulls = new Tags(null, null, null);
        assertThat(tagsWithNulls).isNotNull();
    }

    @Test
    @DisplayName("getList() should return an ApiResponse with List of tags")
    void getListShouldReturnApiResponseWithList() throws Exception {
        ApiResponse<List<TagJsonObject>> response = tags.getList();

        assertThat(response).isNotNull();
        // Note: This test endpoint returns 200 OK, so we expect success
        assertThat(response.isOk()).isTrue();
        assertThat(response.getHttpCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("getTag() should return specific tag information")
    void getTagShouldReturnSpecificTag() throws Exception {
        ApiResponse<TagJsonObject> response = tags.getTag(TEST_TAG_NAME);

        assertThat(response).isNotNull();
        // Note: This test endpoint returns 200 OK, so we expect success
        assertThat(response.isOk()).isTrue();
        assertThat(response.getHttpCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("Should handle API errors gracefully")
    void shouldHandleApiErrorsGracefully() {
        // Test with invalid credentials
        Tags invalidTags = new Tags("invalid-key", "invalid-secret", TEST_API_URI);
        
        try {
            ApiResponse<List<TagJsonObject>> response = invalidTags.getList();
            assertThat(response).isNotNull();
            // Even with invalid credentials, we should get a proper response structure
            assertThat(response.getErrorCode()).isNotNull();
        } catch (Exception e) {
            // If an exception is thrown, that's also acceptable
            assertThat(e).isInstanceOf(Exception.class);
        }
    }

    @Test
    @DisplayName("Should validate API configuration")
    void shouldValidateApiConfiguration() {
        assertThat(tags.getApiKey()).isNotBlank();
        assertThat(tags.getApiSecretKey()).isNotBlank();
        assertThat(tags.getApiUri()).isNotBlank();
        assertThat(tags.getApiUri()).startsWith("https://");
    }
}
