package lazy.dev.dynamo.repository;

import lazy.dev.dynamo.domain.User;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface UserRepository extends DynamoDBCrudRepository<User, String> {

}
