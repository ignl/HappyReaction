package org.happyreaction.model.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ignas
 *
 */
public class SearchConfigEditor extends PropertyEditorSupport {

    private final Logger log = Logger.getLogger(SearchConfigEditor.class.getName());

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();

        SearchConfig value = null;

        try {
            JsonNode root = mapper.readTree(text);
            int firstRow = root.path("firstRow").asInt();
            int numberOfRows = root.path("numberOfRows").asInt();
            Map<String, Object> filters = mapper.convertValue(root.path("filters"), Map.class);
            JsonNode fetchFieldsNode = root.path("fetchFields");
            List<String> fetchFields = fetchFieldsNode.isMissingNode() ? null : mapper.readValue(fetchFieldsNode.traverse(), new TypeReference<ArrayList<String>>(){});
            JsonNode sortNode = root.path("sortField");
            String sortField = sortNode.isMissingNode() ? null : sortNode.asText();
            JsonNode orderingNode = root.path("ordering");
            Sort.Direction ordering = orderingNode.isMissingNode() ? Sort.Direction.DESC : Sort.Direction.fromString(root.path("ordering").asText());
            value = new SearchConfig(firstRow, numberOfRows, filters, fetchFields, sortField, ordering);
        } catch (Exception e) {
            log.error("Incorrect SearchConfig parameter!");
        }

        setValue(value);
    }
}