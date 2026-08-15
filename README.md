# TalentGraph

A complete talent graph application built with Spring Boot and CognoDB (Neo4j) backend and React/Vite/TypeScript frontend that demonstrates genuine graph database modeling for talent management.


## Live Application

- **Frontend:** https://wexa-talentgraph.vercel.app
- **Backend:** https://wexa-cognodb-assignment-8g5d.onrender.com
- **Health Check:** https://wexa-cognodb-assignment-8g5d.onrender.com/health

> The Render backend may require a short cold start after inactivity.


## Overview

TalentGraph is a full-stack application that showcases the power of graph databases for talent management use cases. The application models people, skills, projects, companies, and domains as interconnected nodes in a graph, enabling efficient traversal and querying of complex relationships that would be difficult with traditional relational databases.

## Architecture

TalentGraph follows a three-tier architecture:

Frontend: Vercel (React + TypeScript + Vite)

↓ HTTPS / REST

Backend: Render (Spring Boot + Java)

↓ Bolt Protocol

Database: CognoDB (Graph Database)

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.2.0
- **Database**: CognoDB (accessed via Neo4j Java Driver 6.0.0 using Bolt protocol)
- **Architecture Pattern**: Controller-Service-Repository with DTOs
- **Build Tool**: Maven

### Frontend
- **Framework**: React 19 with TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS
- **Routing**: React Router DOM
- **State Management**: React hooks (useState, useEffect)
- **Proxy**: Configured to forward API requests to backend

## Features Implemented

### Backend Features
- Complete domain model with Person, Project, Skill, Technology, Domain, Company entities
- Relationship modeling including HAS_SKILL, WORKED_ON, WORKS_AT, IN_DOMAIN, RELATED_TO
- Full CRUD operations for all entities via REST API
- Specialized graph query endpoints:
  - `/api/people/{id}/similar` - Find people with similar skills and experience
  - `/api/people/{id}/network` - Get professional network connections
  - `/api/projects/{id}/recommendations` - Recommend talent for projects
  - `/api/people/{id}/recommendations` - Get talent recommendations for a person
- Repository implementations using direct Neo4j Java Driver with parameterized Cypher queries
- Service layer with business logic
- Health check endpoint with database connectivity verification
- Idempotent seed data using MERGE operations for consistent initialization
- Proper exception handling and validation
- Environment-variable based configuration for CognoDB connection

### Frontend Features
- Responsive design with Tailwind CSS
- Client-side routing with React Router
- Seven main pages:
  1. **Overview** - Statistics dashboard with links to all sections
  2. **Projects** - List all projects with navigation to details
  3. **Project Detail** - Project information, talent recommendations, and explanation
  4. **Talent Explorer** - Search and filter capabilities for people
  5. **Person Detail** - Overview, similar people, and professional network tabs
  6. **Network Explorer** - Interactive force-directed graph, network statistics, and relationship exploration
  7. **Why Graph** - Educational page about graph database advantages
- Interactive components with loading states and error handling
- Consumption of all backend API endpoints
- Environment-variable configuration for API base URL

## Technology Stack

### Backend
- **Java 21**
- **Spring Boot 3.2.0**
- **Neo4j Java Driver 6.0.0** (direct usage, no Spring Data Neo4j)
- **Maven** build system
- **CognoDB** graph database (accessed via Bolt protocol)

### Frontend
- **React 19** with TypeScript
- **Vite** build tool
- **Tailwind CSS** for styling
- **React Router DOM** for client-side navigation
- **Axios/Fetch API** for HTTP communication

## Setup Prerequisites

1. **Java 21** installed and configured
2. **Maven** 3.8+ installed
3. **Node.js** 18+ and **npm** 9+ installed
4. **CognoDB** instance running and accessible via Bolt protocol
   - Alternatively, a Neo4j instance can be used for development
5. Environment variables set for CognoDB connection:
   - `COGNODB_URI`: Bolt URI (e.g., `bolt://localhost:7687` or `bolt+s://your-instance-id.databases.cognodb.cloud`)
   - `COGNODB_USERNAME`: Database username
   - `COGNODB_PASSWORD`: Database password
6. Frontend environment variable:
   - `VITE_API_BASE_URL`: Base URL for API calls (defaults to `http://localhost:8080` during development)

## Configuration

### Backend Configuration
The application reads CognoDB connection details from environment variables:
- Copy `.env.example` to `.env` and fill in your actual credentials
- Or set the environment variables directly in your system

### Frontend Configuration
- The frontend uses Vite's environment variable prefixing (`VITE_`)
- Configure `VITE_API_BASE_URL` in a `.env` file in the frontend directory if needed
- By default, the frontend proxies API requests to the backend during development

## Running the Application

### Backend
1. Clone the repository
2. Set up environment variables (copy `.env.example` to `.env` and edit)
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. The application will start on port 8080 by default
6. Access the health endpoint at `http://localhost:8080/health`

### Frontend
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Or build for production:
   ```bash
   npm run build
   ```
5. Preview the production build:
   ```bash
   npm run preview
   ```

### Docker

The backend includes a multi-stage Dockerfile using Maven and Eclipse Temurin Java 21. It can be built and run as a container for deployment environments such as Render.

## API Endpoints

### Person Endpoints
- `GET /api/people` - Get all people
- `GET /api/people/{id}` - Get person by ID
- `POST /api/people` - Create a new person
- `PUT /api/people/{id}` - Update person by ID
- `DELETE /api/people/{id}` - Delete person by ID
- `GET /api/people/{id}/similar` - Get similar people
- `GET /api/people/{id}/network` - Get person's network
- `GET /api/people/{id}/recommendations` - Get talent recommendations

### Project Endpoints
- `GET /api/projects` - Get all projects
- `GET /api/projects/{id}` - Get project by ID
- `POST /api/projects` - Create a new project
- `PUT /api/projects/{id}` - Update project by ID
- `DELETE /api/projects/{id}` - Delete project by ID
- `GET /api/projects/{id}/recommendations` - Get talent recommendations for project

### Skill Endpoints
- `GET /api/skills` - Get all skills
- `GET /api/skills/{id}` - Get skill by ID
- `POST /api/skills` - Create a new skill
- `PUT /api/skills/{id}` - Update skill by ID
- `DELETE /api/skills/{id}` - Delete skill by ID

### Technology Endpoints
- `GET /api/technologies` - Get all technologies
- `GET /api/technologies/{id}` - Get technology by ID
- `POST /api/technologies` - Create a new technology
- `PUT /api/technologies/{id}` - Update technology by ID
- `DELETE /api/technologies/{id}` - Delete technology by ID

### Domain Endpoints
- `GET /api/domains` - Get all domains
- `GET /api/domains/{id}` - Get domain by ID
- `POST /api/domains` - Create a new domain
- `PUT /api/domains/{id}` - Update domain by ID
- `DELETE /api/domains/{id}` - Delete domain by ID

### Company Endpoints
- `GET /api/companies` - Get all companies
- `GET /api/companies/{id}` - Get company by ID
- `POST /api/companies` - Create a new company
- `PUT /api/companies/{id}` - Update company by ID
- `DELETE /api/companies/{id}` - Delete company by ID

## Sample Data

The application includes a SeedDataService that automatically populates the database with sample data on startup, including:
- Multiple domains (Software Development, Data Science, DevOps, etc.)
- Various companies (tech companies, startups, enterprises)
- Technologies (programming languages, frameworks, tools)
- Skills (technical and soft skills)
- Projects with different statuses and descriptions
- People with diverse backgrounds, titles, and experience
- Relationships connecting people to skills, projects, companies, and domains

The seed data uses MERGE operations to ensure idempotency - running the application multiple times will not create duplicate data.

## Current Functionality

- Backend starts successfully with CognoDB connection
- Health check endpoint available at `/health` with database status
- All CRUD operations functional for all entities
- Specialized graph query endpoints operational
- Frontend builds successfully and routes to all pages
- API calls from frontend to backend functional
- Seed data loads properly on application startup

## Key Technical Implementation Details

### Graph Modeling
- Nodes represent entities: Person, Project, Skill, Technology, Domain, Company
- Relationships represent connections between entities with semantic meaning
- Properties on nodes store entity attributes (name, description, etc.)
- Properties on relationships store relationship attributes (years, proficiency, etc.)
- All Cypher queries are parameterized to prevent injection

### Repository Pattern
- BaseRepository provides common Neo4j driver access
- Each entity has a repository with find, save, delete, and custom query methods
- Repositories use the Neo4j Java Driver directly with session.run() for Cypher execution
- Result mapping handles conversion from Neo4j values to Java types

### Service Layer
- Services encapsulate business logic
- Services delegate to repositories for data access
- Services handle DTO to entity mapping and vice versa
- Transaction management where appropriate

### DTO Pattern
- Data Transfer Objects separate internal models from API contracts
- Prevents overexposure of internal entities
- Allows for tailored responses for different clients
- Used consistently across all endpoints

### Frontend Implementation
- TypeScript for type safety
- Modular component organization
- Custom hooks for data fetching where appropriate
- Loading states and error handling for all API calls
- Responsive design that works on mobile and desktop
- Consistent UI styling with Tailwind CSS

## Notes

- All Cypher queries are parameterized to prevent injection
- No ORM or relational database is used - CognoDB is the sole data layer
- Database credentials are never hardcoded - always use environment variables
- The code follows Spring Boot and React conventions and is designed to be maintainable
- Relationship queries demonstrate graph traversal advantages over relational joins
- The application showcases how graph databases excel at talent management use cases
- Proper error handling and validation throughout the application
- Comprehensive logging for debugging and monitoring