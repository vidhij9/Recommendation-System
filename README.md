# Track & Trace Full-Stack Demo

This project delivers an end-to-end shipment tracking experience on top of Spring Boot.
It combines a RESTful backend with a lightweight, single-page front-end so you can
create shipments, append tracking events, and visualize delivery timelines in real-time.

## Getting Started

### Prerequisites
- Java 8 or newer
- Maven 3.6+

### Run locally
```bash
./mvnw spring-boot:run
```
The application will start on [http://localhost:8080](http://localhost:8080).
Navigate to that URL to open the tracking dashboard.

### API Overview
- `POST /api/shipments` – Create a shipment
- `GET /api/shipments` – List all shipments
- `GET /api/shipments/{trackingNumber}` – Fetch shipment details with tracking events
- `POST /api/shipments/{trackingNumber}/events` – Record a tracking event

### Front-End
The UI is served statically from `src/main/resources/static` and interacts with the API
using `fetch`. It supports creating shipments, refreshing the list, selecting a shipment
to review its timeline, and appending new tracking events.

## Deployment
1. Build an executable JAR:
   ```bash
   ./mvnw clean package
   ```
   The artifact is produced at `target/discoveryContent-0.0.1-SNAPSHOT.jar`.

2. Run the packaged app (suitable for traditional server or container runtime):
   ```bash
   java -jar target/discoveryContent-0.0.1-SNAPSHOT.jar
   ```

3. (Optional) Containerize for cloud platforms:
   - Create a Dockerfile that uses `adoptopenjdk:11-jre-hotspot` (or similar) as base.
   - Copy the JAR produced in step 1 into the container and expose port `8080`.
   - Deploy the image to your container platform of choice (Kubernetes, ECS, Cloud Run, etc.).

Because this environment cannot access external networks, the project ships with an
in-memory data store. For production, plug in a persistent database by replacing
`ShipmentService` with a repository-backed implementation.

## Testing
Run unit tests with:
```bash
./mvnw test
```

> **Note:** Maven may attempt to download dependencies on the first run. If you are in a
> restricted network, configure a local Maven mirror or pre-populate the dependency cache.
