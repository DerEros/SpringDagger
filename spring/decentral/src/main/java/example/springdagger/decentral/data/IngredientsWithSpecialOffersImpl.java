package example.springdagger.decentral.data;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IngredientsWithSpecialOffersImpl implements IngredientsWithSpecialOffers {
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, String>> queryIngredientsAndSpecialOffers() {
        String query = "SELECT * FROM ingredients AS i JOIN special_offers AS s ON s.ingredients_id=i.id ORDER BY i.id, s.id ASC";
        List<String> columnNames = Arrays.asList("ID", "ingredient_type", "name", "price", "description");
        List<Map<String, String>> rows = new ArrayList<>();

        jdbcTemplate.query(query, (result) -> {
            Map<String, String> row = columnNames.stream().collect(Collectors.toMap(Function.identity(), getString(result)));
            rows.add(row);
        });

        return rows;
    }

    private Function<String, String> getString(ResultSet rs) {
        return (columnName) -> {
            try {
                return rs.getString(columnName);
            } catch (SQLException e) {
                throw new RuntimeException("Getting column " + columnName
                        + " from result set should not throw exception", e);
            }
        };
    }
}
