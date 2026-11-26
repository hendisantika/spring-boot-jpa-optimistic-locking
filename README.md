# spring-boot-jpa-optimistic-locking

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-jpa-optimistic-locking/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-jpa-optimistic-locking/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.2.0-brightgreen?logo=springboot)

An example project to demonstrate **optimistic locking** with Java (JPA). The Spring Boot setup is just used to wrap
everything together with a few lines of code.

## Tech Stack

| Component       | Version / Tool                      |
|-----------------|-------------------------------------|
| Java            | 25 (Temurin in CI)                  |
| Spring Boot     | 4.2.0                               |
| Persistence     | Spring Data JPA (Jakarta Persistence, Hibernate) |
| Database        | H2 (in-memory)                      |
| Testing         | JUnit 5 (Jupiter), Spring Boot Test |
| Build           | Maven Wrapper (Maven 3.9.11)        |

## How Optimistic Locking Works Here

The `Movie` entity has a field annotated with `@Version`:

```java
@Version
private Integer version;
```

Every time a `Movie` is updated, Hibernate increments `version` and adds it to the `WHERE` clause of the `UPDATE`
statement. If another transaction has already changed the row, the version no longer matches, no row gets updated and
Spring throws an `ObjectOptimisticLockingFailureException`.

The test `SpringBootJpaOptimisticLockingApplicationTests#testConcurrencyWriting` simulates two users:

1. Both users load the same movie (`version = 0`) and map it to a `MovieDTO`.
2. Both users change the rating.
3. User one saves first → success, `version` becomes `1`.
4. User two saves with the stale `version = 0` → `ObjectOptimisticLockingFailureException` 💥

## Project Structure

```
src/main/java/com/hendisantika/springbootjpaoptimisticlocking
├── SpringBootJpaOptimisticLockingApplication.java
├── domain/Movie.java              # JPA entity with @Version
├── dto/MovieDTO.java              # transport object for the "view"
├── dto/Movies.java                # mapper Movie <-> MovieDTO
└── repository/MovieRepository.java
```

## Prerequisites

- JDK 25
- No Maven installation needed, the Maven Wrapper (`./mvnw`) is included.

## Build & Test

Run the unit & integration test:

```shell
./mvnw clean test
```

Build the application jar:

```shell
./mvnw clean package
```

## Continuous Integration & Dependency Updates

- **GitHub Actions** (`.github/workflows/maven.yml`) builds and tests the project on JDK 25 for every push and pull
  request to `master`, then submits the dependency graph to GitHub.
- **Dependabot** (`.github/dependabot.yml`) checks Maven dependencies and GitHub Actions weekly, grouping related
  updates (Spring Boot, Spring, database, test libraries, Maven plugins, GitHub Actions) into single pull requests.
