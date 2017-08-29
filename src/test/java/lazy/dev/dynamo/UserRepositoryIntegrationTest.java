package lazy.dev.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;

import lazy.dev.dynamo.config.DynamoConfig;
import lazy.dev.dynamo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = { PropertyPlaceholderAutoConfiguration.class, DynamoConfig.class }
)
public class UserRepositoryIntegrationTest {

	private static final String KEY_NAME = "id";
	private static final Long READ_CAPACITY_UNITS = 5L;
	private static final Long WRITE_CAPACITY_UNITS = 5L;

	private String TABLE_NAME = "users";

	@Autowired
    UserRepository repository;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Before
	public void init() throws Exception {

		ListTablesResult listTablesResult = amazonDynamoDB.listTables();

		listTablesResult.getTableNames().stream()
				.filter(tableName -> tableName.equals(TABLE_NAME))
				.forEach(tableName -> amazonDynamoDB.deleteTable(tableName));

		List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName(KEY_NAME).withAttributeType("S"));

		List<KeySchemaElement> keySchemaElements = new ArrayList<KeySchemaElement>();
		keySchemaElements.add(new KeySchemaElement().withAttributeName(KEY_NAME).withKeyType(KeyType.HASH));

		CreateTableRequest request = new CreateTableRequest()
				.withTableName(TABLE_NAME)
				.withKeySchema(keySchemaElements)
				.withAttributeDefinitions(attributeDefinitions)
				.withProvisionedThroughput(new ProvisionedThroughput()
						.withReadCapacityUnits(READ_CAPACITY_UNITS)
						.withWriteCapacityUnits(WRITE_CAPACITY_UNITS));

		amazonDynamoDB.createTable(request);

	}

	@Test
	public void test(){

	}

// TODO Example
//    @Test
//    public void sampleTestCase() {
//        User dave = new User("Dave", "Matthews");
//        repository.save(dave);
//
//        User carter = new User("Carter", "Beauford");
//        repository.save(carter);
//
//        List<User> result = repository.findByLastName("Matthews");
//        result.stream().forEach(System.out::println);
//
//        Assert.assertThat(result.size(), is(1));
//        Assert.assertThat(result, hasItem(dave));
//    }
}