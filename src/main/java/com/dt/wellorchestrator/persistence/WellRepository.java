package com.dt.wellorchestrator.persistence;

import static com.datastax.driver.core.DataType.text;
import static com.datastax.driver.core.DataType.timestamp;
import static com.datastax.driver.core.DataType.uuid;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.dt.wellorchestrator.persistence.entity.Well;

@Repository
public class WellRepository {

	private static final String TABLE = "well";

	private Mapper<Well> mapper;

	private Session session;

	public WellRepository(MappingManager mappingManager) {
		createTable(mappingManager.getSession());
		this.mapper = mappingManager.mapper(Well.class);
		this.session = mappingManager.getSession();
	}

	private void createTable(Session session) {
		session.execute(
				SchemaBuilder.createTable(TABLE)
						.ifNotExists()
						.addPartitionKey("well_id", uuid())
						.addColumn("name", text())
						.addColumn("well_info", text())
						.addColumn("creation_date_time", timestamp()));
	}

	public List<Well> findAll() {
		final ResultSet result = session.execute(select().all().from(TABLE));
		return mapper.map(result).all();
	}

	public Well findById(UUID id) {
		return mapper.get(id);
	}

	public Well save(Well well) {
		mapper.save(well);
		return well;
	}

	public void delete(UUID id) {
		mapper.delete(id);
	}

}
