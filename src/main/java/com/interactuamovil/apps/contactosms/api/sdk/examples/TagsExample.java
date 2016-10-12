package com.interactuamovil.apps.contactosms.api.sdk.examples;

import com.interactuamovil.apps.contactosms.api.sdk.Tags;
import com.interactuamovil.apps.contactosms.api.sdk.responses.TagResponse;
import com.interactuamovil.apps.contactosms.api.utils.ApiResponse;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

class TagsExample extends BaseExample {

    private String testTagName = null;

    public TagsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

        testTagName = getConfig().getString("test_group_name");

        if (null == testTagName) {
            throw new AssertionError(
                "Please add tag configurations: test_tag_name"
            );
        }

    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Tags tagsApi = new Tags(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Get list of tags
        testTagsList(tagsApi);

    }

    private void testTagsList(Tags tagsApi) {

        ApiResponse<List<TagResponse>> groups = tagsApi.getList();

        Boolean found = Boolean.FALSE;

        for (TagResponse group : groups.getResponse()) {
            if (group.getName().equalsIgnoreCase(testTagName)) {
                found = Boolean.TRUE;
            }
        }

        if (!found) {
            throw new AssertionError("Group not found on list.");
        }

    }

}
