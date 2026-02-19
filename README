# Console API

The programmable control plane for the Klustr.io platform.

------------------------------------------------------------------------

## Product Overview

`console-api` is the backend control plane powering the Klustr.io
Console --- the unified API platform that enables teams to build, ship,
run, and scale software with minimal DevOps overhead and full control
over identity, billing, and observability.

Klustr.io provides a vertically integrated infrastructure stack built on
open technologies. The Console API is the backend layer that ties these
systems together into a coherent, programmable platform.

It provides:

-   Identity and authentication services\
-   Fine-grained authorization and RBAC\
-   API lifecycle management\
-   Usage tracking and billing integration\
-   Event streaming and webhook orchestration\
-   Feature flag evaluation\
-   Metrics and observability endpoints

Full platform documentation:\
https://docs.klustr.io/

------------------------------------------------------------------------

## Mission

Mission:

To build a composable, container-native control plane that enables
infrastructure products to scale globally --- securely, observably, and
monetizably.

Console API ensures:

-   Every API call is authenticated\
-   Every resource action is authorized\
-   Every usage event is measurable\
-   Every service is observable\
-   Every deployment is reproducible

It is the backbone of the Klustr ecosystem.

------------------------------------------------------------------------

## Project Structure

    console-api/
    ├── auth-server/      Identity and policy orchestration
    ├── server/           Core Console REST API
    ├── sync-server/      Async workers and event processors
    ├── pom.xml           Maven multi-module root
    └── .gitlab/          CI/CD settings

Each module is independently deployable and produces its own Docker
image using Jib.

------------------------------------------------------------------------

## Architecture and Dependencies

Console API integrates with a cloud-native ecosystem.

### Identity and Authorization

-   Ory Hydra --- OAuth2 and OIDC provider\
-   Ory Keto --- Fine-grained, relationship-based access control\
-   JWT validation and token introspection

Flow:

1.  User authenticates via Hydra\
2.  Access token is issued\
3.  Console API validates token\
4.  Authorization checks are delegated to Keto\
5.  Request proceeds or is denied

------------------------------------------------------------------------

### API Gateway

-   Kong
    -   Traffic routing\
    -   Authentication plugins\
    -   Rate limiting\
    -   Upstream service proxying

Kong routes traffic to:

-   server\
-   auth-server\
-   sync-server

------------------------------------------------------------------------

### Event Streaming

-   Kafka
    -   Usage events\
    -   Background job triggers\
    -   Cross-service communication\
    -   Async workflows

`sync-server` consumes Kafka topics and processes background tasks.

------------------------------------------------------------------------

### Webhooks

-   Svix
    -   Reliable webhook delivery\
    -   Retries and signature verification\
    -   Event tracking

Console API publishes events which Svix delivers externally.

------------------------------------------------------------------------

### Billing and Monetization

-   Lago
    -   Usage-based billing\
    -   Subscription plans\
    -   Invoice generation\
    -   Metered events

Console API emits usage events to Kafka which Lago aggregates for
billing.

------------------------------------------------------------------------

### Feature Flags

-   GrowthBook
    -   Feature flags\
    -   Gradual rollouts\
    -   A/B testing\
    -   Environment targeting

------------------------------------------------------------------------

### Object Storage

-   MinIO
    -   S3-compatible object storage\
    -   Exports and artifacts\
    -   Uploaded assets

------------------------------------------------------------------------

### Observability

-   Prometheus
    -   Metrics scraping\
    -   Latency tracking\
    -   Error rate monitoring
-   Grafana
    -   Dashboards\
    -   Alerts\
    -   SLO tracking

Each service exposes Prometheus-compatible metrics endpoints.

------------------------------------------------------------------------

### Email

-   Mailtrap (development)
    -   SMTP testing\
    -   Transactional email preview

Production email providers can be swapped as needed.

------------------------------------------------------------------------

### CI/CD and Containers

-   Docker --- Local and production containers\
-   Jib --- Build images without requiring Docker daemon\
-   GitLab CI --- Pipelines, registry, artifact caching

Images are published to:

    registry.dev.klustr.io/klustr.io/console-api/<service>:<tag>

------------------------------------------------------------------------

## Local Development Setup

### Requirements

-   Java 17+
-   Maven 3.8+
-   Docker and Docker Compose
-   PostgreSQL

Verify installation:

    java -version
    mvn -version
    docker -v

------------------------------------------------------------------------

### Environment Configuration

Create `.env.local` in the project root:

    DB_HOST=localhost
    DB_PORT=5432
    DB_NAME=console
    DB_USER=console
    DB_PASSWORD=console

    HYDRA_PUBLIC_URL=http://localhost:4444
    HYDRA_ADMIN_URL=http://localhost:4445
    KETO_READ_URL=http://localhost:4466
    KETO_WRITE_URL=http://localhost:4467

    KAFKA_BOOTSTRAP_SERVERS=localhost:9092

    LAGO_API_URL=http://localhost:3000
    LAGO_API_KEY=dev-key

    SVIX_API_KEY=dev-key

    MINIO_ENDPOINT=http://localhost:9000
    MINIO_ACCESS_KEY=minio
    MINIO_SECRET_KEY=minio123

    GROWTHBOOK_API_HOST=http://localhost:3100

    SMTP_HOST=smtp.mailtrap.io
    SMTP_PORT=2525
    SMTP_USERNAME=your-user
    SMTP_PASSWORD=your-pass

    SPRING_PROFILES_ACTIVE=local

Do not commit this file.

------------------------------------------------------------------------

### Start Dependencies

    docker compose up -d

------------------------------------------------------------------------

### Build Project

    mvn clean install

------------------------------------------------------------------------

### Run Services

Core API:

    mvn -pl server spring-boot:run

Auth server:

    mvn -pl auth-server spring-boot:run

Async workers:

    mvn -pl sync-server spring-boot:run

------------------------------------------------------------------------

## Contributing

1.  Create a feature branch\
2.  Ensure `mvn clean install` passes\
3.  Validate Docker builds\
4.  Submit a merge request via GitLab

------------------------------------------------------------------------

## License

Internal project -- Klustr.io\
All rights reserved.


