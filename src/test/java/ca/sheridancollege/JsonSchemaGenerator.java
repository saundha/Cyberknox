package ca.sheridancollege;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

import ca.sheridancollege.beans.UserAccount;

public class JsonSchemaGenerator {

	@SuppressWarnings("deprecation")
	@Test
	public void test() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonSchema schema = mapper.generateJsonSchema(UserAccount.class);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));

		BufferedWriter bw = new BufferedWriter(new FileWriter("src/test/" + "JsonSchema" + ".json"));

		bw.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
		bw.close();
	}

}
