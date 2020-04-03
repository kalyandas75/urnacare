MIGRATION_LABEL = "to-be-changed"

makeInit:
	mvn liquibase:generateChangeLog

makeMigration:
	mvn liquibase:diff -DdiffChangeLogFile=src/main/resources/db/changelog/changes/${MIGRATION_LABEL}.yml
	@echo   - include: >> src/main/resources/db/changelog/db.changelog-master.yml
	@echo       file: classpath*:db/changelog/changes/$(MIGRATION_LABEL).yml >> src/main/resources/db/changelog/db.changelog-master.yml
