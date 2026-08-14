package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.*;
import com.wexa.talentgraph.repository.*;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.Map;

import java.util.Arrays;
import java.util.List;

/**
 * Service for seeding the graph database with initial data.
 * Uses MERGE operations to ensure idempotency.
 */
@Service
public class SeedDataService {

    private final PersonRepository personRepository;
    private final SkillRepository skillRepository;
    private final TechnologyRepository technologyRepository;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final DomainRepository domainRepository;

    public SeedDataService(PersonRepository personRepository,
                           SkillRepository skillRepository,
                           TechnologyRepository technologyRepository,
                           ProjectRepository projectRepository,
                           CompanyRepository companyRepository,
                           DomainRepository domainRepository) {
        this.personRepository = personRepository;
        this.skillRepository = skillRepository;
        this.technologyRepository = technologyRepository;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.domainRepository = domainRepository;
    }

    /**
     * Seed the database with initial data.
     * This method is idempotent and uses MERGE operations.
     */
    public void seedData() {
        seedDomains();
        seedCompanies();
        seedTechnologies();
        seedSkills();
        seedProjects();
        seedPeople();
        seedRelationships();
        seedRequiredSkills();
    }

    private void seedDomains() {
        List<DomainDto> domains = Arrays.asList(
                new DomainDto(1L, "FinTech", "Financial Technology"),
                new DomainDto(2L, "Telecom", "Telecommunications"),
                new DomainDto(3L, "Healthcare", "Healthcare and Medical"),
                new DomainDto(4L, "E-commerce", "Electronic Commerce"),
                new DomainDto(5L, "Logistics", "Logistics and Supply Chain"),
                new DomainDto(6L, "SaaS", "Software as a Service")
        );

        for (DomainDto domain : domains) {
            domainRepository.save(domain);
        }
    }

    private void seedCompanies() {
        List<CompanyDto> companies = Arrays.asList(
                new CompanyDto(1L, "JPMorgan Chase", "FinTech"),
                new CompanyDto(2L, "Verizon", "Telecom"),
                new CompanyDto(3L, "Pfizer", "Healthcare"),
                new CompanyDto(4L, "Amazon", "E-commerce"),
                new CompanyDto(5L, "DHL", "Logistics"),
                new CompanyDto(6L, "Salesforce", "SaaS"),
                new CompanyDto(7L, "Google", "SaaS"),
                new CompanyDto(8L, "Microsoft", "SaaS")
        );

        for (CompanyDto company : companies) {
            companyRepository.save(company);
        }
    }

    private void seedTechnologies() {
        List<TechnologyDto> technologies = Arrays.asList(
                new TechnologyDto(1L, "Java", "Programming Language"),
                new TechnologyDto(2L, "Spring Boot", "Framework"),
                new TechnologyDto(3L, "React", "Frontend Library"),
                new TechnologyDto(4L, "TypeScript", "Programming Language"),
                new TechnologyDto(5L, "Kafka", "Message Queue"),
                new TechnologyDto(6L, "PostgreSQL", "Database"),
                new TechnologyDto(7L, "Redis", "Cache"),
                new TechnologyDto(8L, "Docker", "Containerization"),
                new TechnologyDto(9L, "Kubernetes", "Orchestration"),
                new TechnologyDto(10L, "AWS", "Cloud Platform"),
                new TechnologyDto(11L, "Python", "Programming Language")
        );

        for (TechnologyDto technology : technologies) {
            technologyRepository.save(technology);
        }
    }

    private void seedSkills() {
        List<SkillDto> skills = Arrays.asList(
                new SkillDto(1L, "Java Development", "Programming"),
                new SkillDto(2L, "Spring Boot Development", "Framework"),
                new SkillDto(3L, "React Development", "Frontend"),
                new SkillDto(4L, "TypeScript Development", "Programming"),
                new SkillDto(5L, "Kafka Administration", "Messaging"),
                new SkillDto(6L, "PostgreSQL Administration", "Database"),
                new SkillDto(7L, "Redis Administration", "Cache"),
                new SkillDto(8L, "Docker Administration", "Containerization"),
                new SkillDto(9L, "Kubernetes Administration", "Orchestration"),
                new SkillDto(10L, "AWS Architecture", "Cloud"),
                new SkillDto(11L, "Python Development", "Programming"),
                new SkillDto(12L, "Project Management", "Management"),
                new SkillDto(13L, "Data Analysis", "Analytics"),
                new SkillDto(14L, "Machine Learning", "AI"),
                new SkillDto(15L, "UI/UX Design", "Design")
        );

        for (SkillDto skill : skills) {
            skillRepository.save(skill);
        }
    }

    private void seedProjects() {
        List<ProjectDto> projects = Arrays.asList(
                new ProjectDto(1L, "Digital Banking Platform", "A modern banking application", "COMPLETED"),
                new ProjectDto(2L, "5G Network Optimization", "Optimizing telecom network for 5G", "IN_PROGRESS"),
                new ProjectDto(3L, "Healthcare Patient Portal", "Portal for patients to access medical records", "COMPLETED"),
                new ProjectDto(4L, "E-commerce Recommendation Engine", "AI-powered product recommendations", "IN_PROGRESS"),
                new ProjectDto(5L, "Supply Chain Visibility Platform", "Real-time logistics tracking", "PLANNED"),
                new ProjectDto(6L, "SaaS Customer Analytics", "Analytics platform for SaaS customers", "COMPLETED"),
                new ProjectDto(7L, "AI Fraud Detection System", "Detecting fraudulent transactions in real-time", "IN_PROGRESS"),
                new ProjectDto(8L, "Cloud Migration Project", "Migrating legacy systems to AWS", "COMPLETED")
        );

        for (ProjectDto project : projects) {
            projectRepository.save(project);
        }
    }

    private void seedPeople() {
        List<PersonDto> people = Arrays.asList(
                new PersonDto(1L, "John Smith", "Senior Java Developer", 10, "New York", "Experienced Java developer with expertise in Spring Boot and microservices."),
                new PersonDto(2L, "Lisa Johnson", "Data Scientist", 5, "San Francisco", "Data scientist specializing in machine learning and predictive analytics."),
                new PersonDto(3L, "Michael Chen", "DevOps Engineer", 7, "Seattle", "DevOps engineer with deep knowledge of Docker, Kubernetes, and AWS."),
                new PersonDto(4L, "Sarah Williams", "Frontend Developer", 4, "Remote", "Frontend developer skilled in React, TypeScript, and UI/UX design."),
                new PersonDto(5L, "David Brown", "Project Manager", 9, "Chicago", "Certified project manager with experience in agile methodologies."),
                new PersonDto(6L, "Emily Davis", "Solutions Architect", 8, "Austin", "Solutions architect specializing in cloud migrations and enterprise systems."),
                new PersonDto(7L, "Robert Wilson", "Database Administrator", 6, "Boston", "DBA with expertise in PostgreSQL, Redis, and database optimization."),
                new PersonDto(8L, "Jessica Taylor", "Network Engineer", 5, "Dallas", "Network engineer specializing in 5G, telecom protocols, and network optimization."),
                new PersonDto(9L, "Christopher Lee", "Full Stack Developer", 6, "San Jose", "Full stack developer with experience in Java, Spring Boot, React, and AWS."),
                new PersonDto(10L, "Amanda Martinez", "UX Researcher", 4, "Remote", "UX researcher focused on user-centered design and usability testing."),
                new PersonDto(11L, "Daniel Moore", "Technical Lead", 12, "New York", "Technical leader with full-stack expertise and team management skills."),
                new PersonDto(12L, "Laura Garcia", "QA Engineer", 3, "Remote", "QA engineer specializing in test automation and quality assurance processes.")
        );

        for (PersonDto person : people) {
            personRepository.save(person);
        }
    }

    private void seedRelationships() {
        // Get the driver from one of the repositories
        Driver driver = personRepository.getDriver();

        try (Session session = driver.session()) {
            // Create some HAS_SKILL relationships - made idempotent by separating MERGE and SET
            String hasSkillMergeQuery = """
                MATCH (p:Person {id: $personId})
                MATCH (s:Skill {id: $skillId})
                MERGE (p)-[r:HAS_SKILL]->(s)
                RETURN r
                """;

            String hasSkillSetQuery = """
                MATCH (p:Person {id: $personId})
                MATCH (s:Skill {id: $skillId})
                MERGE (p)-[r:HAS_SKILL]->(s)
                SET r.proficiency = $proficiency, r.years = $years
                RETURN r
                """;

            // Person 1 (John Smith) has Java Development (skill 1) and Spring Boot Development (skill 2)
            session.run(hasSkillMergeQuery, Map.of("personId", 1L, "skillId", 1L));
            session.run(hasSkillSetQuery, Map.of("personId", 1L, "skillId", 1L, "proficiency", "EXPERT", "years", 10));
            session.run(hasSkillMergeQuery, Map.of("personId", 1L, "skillId", 2L));
            session.run(hasSkillSetQuery, Map.of("personId", 1L, "skillId", 2L, "proficiency", "ADVANCED", "years", 8));

            // Person 2 (Lisa Johnson) has Data Analysis (skill 13) and Machine Learning (skill 14)
            session.run(hasSkillMergeQuery, Map.of("personId", 2L, "skillId", 13L));
            session.run(hasSkillSetQuery, Map.of("personId", 2L, "skillId", 13L, "proficiency", "ADVANCED", "years", 4));
            session.run(hasSkillMergeQuery, Map.of("personId", 2L, "skillId", 14L));
            session.run(hasSkillSetQuery, Map.of("personId", 2L, "skillId", 14L, "proficiency", "INTERMEDIATE", "years", 3));

            // Person 3 (Michael Chen) has Docker Administration (skill 8) and Kubernetes Administration (skill 9)
            session.run(hasSkillMergeQuery, Map.of("personId", 3L, "skillId", 8L));
            session.run(hasSkillSetQuery, Map.of("personId", 3L, "skillId", 8L, "proficiency", "EXPERT", "years", 6));
            session.run(hasSkillMergeQuery, Map.of("personId", 3L, "skillId", 9L));
            session.run(hasSkillSetQuery, Map.of("personId", 3L, "skillId", 9L, "proficiency", "ADVANCED", "years", 5));

            // Person 4 (Sarah Williams) has React Development (skill 3) and TypeScript Development (skill 4)
            session.run(hasSkillMergeQuery, Map.of("personId", 4L, "skillId", 3L));
            session.run(hasSkillSetQuery, Map.of("personId", 4L, "skillId", 3L, "proficiency", "ADVANCED", "years", 4));
            session.run(hasSkillMergeQuery, Map.of("personId", 4L, "skillId", 4L));
            session.run(hasSkillSetQuery, Map.of("personId", 4L, "skillId", 4L, "proficiency", "ADVANCED", "years", 3));

            // Person 5 (David Brown) has Project Management (skill 12)
            session.run(hasSkillMergeQuery, Map.of("personId", 5L, "skillId", 12L));
            session.run(hasSkillSetQuery, Map.of("personId", 5L, "skillId", 12L, "proficiency", "EXPERT", "years", 9));

            // Person 6 (Emily Davis) has AWS Architecture (skill 10)
            session.run(hasSkillMergeQuery, Map.of("personId", 6L, "skillId", 10L));
            session.run(hasSkillSetQuery, Map.of("personId", 6L, "skillId", 10L, "proficiency", "EXPERT", "years", 7));

            // Person 7 (Robert Wilson) has PostgreSQL Administration (skill 6) and Redis Administration (skill 7)
            session.run(hasSkillMergeQuery, Map.of("personId", 7L, "skillId", 6L));
            session.run(hasSkillSetQuery, Map.of("personId", 7L, "skillId", 6L, "proficiency", "EXPERT", "years", 5));
            session.run(hasSkillMergeQuery, Map.of("personId", 7L, "skillId", 7L));
            session.run(hasSkillSetQuery, Map.of("personId", 7L, "skillId", 7L, "proficiency", "ADVANCED", "years", 4));

            // Person 8 (Jessica Taylor) - no skills for now

            // Person 9 (Christopher Lee) has Java Development (skill 1), Spring Boot Development (skill 2), React Development (skill 3), AWS Architecture (skill 10)
            session.run(hasSkillMergeQuery, Map.of("personId", 9L, "skillId", 1L));
            session.run(hasSkillSetQuery, Map.of("personId", 9L, "skillId", 1L, "proficiency", "ADVANCED", "years", 5));
            session.run(hasSkillMergeQuery, Map.of("personId", 9L, "skillId", 2L));
            session.run(hasSkillSetQuery, Map.of("personId", 9L, "skillId", 2L, "proficiency", "ADVANCED", "years", 4));
            session.run(hasSkillMergeQuery, Map.of("personId", 9L, "skillId", 3L));
            session.run(hasSkillSetQuery, Map.of("personId", 9L, "skillId", 3L, "proficiency", "INTERMEDIATE", "years", 3));
            session.run(hasSkillMergeQuery, Map.of("personId", 9L, "skillId", 10L));
            session.run(hasSkillSetQuery, Map.of("personId", 9L, "skillId", 10L, "proficiency", "ADVANCED", "years", 4));

            // Person 10 (Amanda Martinez) has UI/UX Design (skill 15)
            session.run(hasSkillMergeQuery, Map.of("personId", 10L, "skillId", 15L));
            session.run(hasSkillSetQuery, Map.of("personId", 10L, "skillId", 15L, "proficiency", "ADVANCED", "years", 4));

            // Person 11 (Daniel Moore) has Java Development (skill 1), Spring Boot Development (skill 2), Project Management (skill 12)
            session.run(hasSkillMergeQuery, Map.of("personId", 11L, "skillId", 1L));
            session.run(hasSkillSetQuery, Map.of("personId", 11L, "skillId", 1L, "proficiency", "EXPERT", "years", 12));
            session.run(hasSkillMergeQuery, Map.of("personId", 11L, "skillId", 2L));
            session.run(hasSkillSetQuery, Map.of("personId", 11L, "skillId", 2L, "proficiency", "EXPERT", "years", 10));
            session.run(hasSkillMergeQuery, Map.of("personId", 11L, "skillId", 12L));
            session.run(hasSkillSetQuery, Map.of("personId", 11L, "skillId", 12L, "proficiency", "EXPERT", "years", 10));

            // Person 12 (Laura Garcia) - no skills for now

            // Create some WORKED_ON relationships - made idempotent by separating MERGE and SET
            String workedOnMergeQuery = """
                MATCH (p:Person {id: $personId})
                MATCH (pr:Project {id: $projectId})
                MERGE (p)-[r:WORKED_ON]->(pr)
                RETURN r
                """;

            String workedOnSetQuery = """
                MATCH (p:Person {id: $personId})
                MATCH (pr:Project {id: $projectId})
                MERGE (p)-[r:WORKED_ON]->(pr)
                SET r.role = $role
                RETURN r
                """;

            // Person 1 worked on Project 1 (Digital Banking Platform) as Lead Developer
            session.run(workedOnMergeQuery, Map.of("personId", 1L, "projectId", 1L));
            session.run(workedOnSetQuery, Map.of("personId", 1L, "projectId", 1L, "role", "Lead Developer"));
            // Person 1 also worked on Project 6 (SaaS Customer Analytics) as Consultant
            session.run(workedOnMergeQuery, Map.of("personId", 1L, "projectId", 6L));
            session.run(workedOnSetQuery, Map.of("personId", 1L, "projectId", 6L, "role", "Consultant"));

            // Person 2 worked on Project 4 (E-commerce Recommendation Engine) as Data Scientist
            session.run(workedOnMergeQuery, Map.of("personId", 2L, "projectId", 4L));
            session.run(workedOnSetQuery, Map.of("personId", 2L, "projectId", 4L, "role", "Data Scientist"));

            // Person 3 worked on Project 2 (5G Network Optimization) as DevOps Lead
            session.run(workedOnMergeQuery, Map.of("personId", 3L, "projectId", 2L));
            session.run(workedOnSetQuery, Map.of("personId", 3L, "projectId", 2L, "role", "DevOps Lead"));
            // Person 3 also worked on Project 8 (Cloud Migration Project) as DevOps Engineer
            session.run(workedOnMergeQuery, Map.of("personId", 3L, "projectId", 8L));
            session.run(workedOnSetQuery, Map.of("personId", 3L, "projectId", 8L, "role", "DevOps Engineer"));

            // Person 4 worked on Project 4 (E-commerce Recommendation Engine) as Frontend Lead
            session.run(workedOnMergeQuery, Map.of("personId", 4L, "projectId", 4L));
            session.run(workedOnSetQuery, Map.of("personId", 4L, "projectId", 4L, "role", "Frontend Lead"));
            // Person 4 also worked on Project 6 (SaaS Customer Analytics) as UI/UX Consultant
            session.run(workedOnMergeQuery, Map.of("personId", 4L, "projectId", 6L));
            session.run(workedOnSetQuery, Map.of("personId", 4L, "projectId", 6L, "role", "UI/UX Consultant"));

            // Person 5 worked on Project 1 (Digital Banking Platform) as Project Manager
            session.run(workedOnMergeQuery, Map.of("personId", 5L, "projectId", 1L));
            session.run(workedOnSetQuery, Map.of("personId", 5L, "projectId", 1L, "role", "Project Manager"));
            // Person 5 also worked on Project 7 (AI Fraud Detection System) as Project Manager
            session.run(workedOnMergeQuery, Map.of("personId", 5L, "projectId", 7L));
            session.run(workedOnSetQuery, Map.of("personId", 5L, "projectId", 7L, "role", "Project Manager"));

            // Person 6 worked on Project 8 (Cloud Migration Project) as Lead Architect
            session.run(workedOnMergeQuery, Map.of("personId", 6L, "projectId", 8L));
            session.run(workedOnSetQuery, Map.of("personId", 6L, "projectId", 8L, "role", "Lead Architect"));

            // Person 7 worked on Project 1 (Digital Banking Platform) as DBA
            session.run(workedOnMergeQuery, Map.of("personId", 7L, "projectId", 1L));
            session.run(workedOnSetQuery, Map.of("personId", 7L, "projectId", 1L, "role", "Database Administrator"));
            // Person 7 also worked on Project 4 (E-commerce Recommendation Engine) as DBA
            session.run(workedOnMergeQuery, Map.of("personId", 7L, "projectId", 4L));
            session.run(workedOnSetQuery, Map.of("personId", 7L, "projectId", 4L, "role", "Database Administrator"));

            // Person 9 worked on Project 1 (Digital Banking Platform) as Full Stack Developer
            session.run(workedOnMergeQuery, Map.of("personId", 9L, "projectId", 1L));
            session.run(workedOnSetQuery, Map.of("personId", 9L, "projectId", 1L, "role", "Full Stack Developer"));
            // Person 9 also worked on Project 6 (SaaS Customer Analytics) as Full Stack Developer
            session.run(workedOnMergeQuery, Map.of("personId", 9L, "projectId", 6L));
            session.run(workedOnSetQuery, Map.of("personId", 9L, "projectId", 6L, "role", "Full Stack Developer"));
            // Person 9 also worked on Project 8 (Cloud Migration Project) as Full Stack Developer
            session.run(workedOnMergeQuery, Map.of("personId", 9L, "projectId", 8L));
            session.run(workedOnSetQuery, Map.of("personId", 9L, "projectId", 8L, "role", "Full Stack Developer"));

            // Person 11 worked on Project 1 (Digital Banking Platform) as Technical Lead
            session.run(workedOnMergeQuery, Map.of("personId", 11L, "projectId", 1L));
            session.run(workedOnSetQuery, Map.of("personId", 11L, "projectId", 1L, "role", "Technical Lead"));
            // Person 11 also worked on Project 7 (AI Fraud Detection System) as Technical Lead
            session.run(workedOnMergeQuery, Map.of("personId", 11L, "projectId", 7L));
            session.run(workedOnSetQuery, Map.of("personId", 11L, "projectId", 7L, "role", "Technical Lead"));

            // Create some WORKS_AT relationships - made idempotent by separating MERGE and SET
            String worksAtMergeQuery = """
                MATCH (p:Person {id: $personId})
                MATCH (c:Company {id: $companyId})
                MERGE (p)-[r:WORKS_AT]->(c)
                RETURN r
                """;

            String worksAtSetQuery = """
                MATCH (p:Person {id: $personId})
                MATCH (c:Company {id: $companyId})
                MERGE (p)-[r:WORKS_AT]->(c)
                SET r.position = $position
                RETURN r
                """;

            // Person 1 works at Company 1 (JPMorgan Chase) as Senior Java Developer
            session.run(worksAtMergeQuery, Map.of("personId", 1L, "companyId", 1L));
            session.run(worksAtSetQuery, Map.of("personId", 1L, "companyId", 1L, "position", "Senior Java Developer"));

            // Person 2 works at Company 5 (DHL) as Data Scientist
            session.run(worksAtMergeQuery, Map.of("personId", 2L, "companyId", 5L));
            session.run(worksAtSetQuery, Map.of("personId", 2L, "companyId", 5L, "position", "Data Scientist"));

            // Person 3 works at Company 8 (Microsoft) as DevOps Engineer
            session.run(worksAtMergeQuery, Map.of("personId", 3L, "companyId", 8L));
            session.run(worksAtSetQuery, Map.of("personId", 3L, "companyId", 8L, "position", "DevOps Engineer"));

            // Person 4 works at Company 6 (Salesforce) as Frontend Developer
            session.run(worksAtMergeQuery, Map.of("personId", 4L, "companyId", 6L));
            session.run(worksAtSetQuery, Map.of("personId", 4L, "companyId", 6L, "position", "Frontend Developer"));

            // Person 5 works at Company 1 (JPMorgan Chase) as Project Manager
            session.run(worksAtMergeQuery, Map.of("personId", 5L, "companyId", 1L));
            session.run(worksAtSetQuery, Map.of("personId", 5L, "companyId", 1L, "position", "Project Manager"));

            // Person 6 works at Company 7 (Google) as Solutions Architect
            session.run(worksAtMergeQuery, Map.of("personId", 6L, "companyId", 7L));
            session.run(worksAtSetQuery, Map.of("personId", 6L, "companyId", 7L, "position", "Solutions Architect"));

            // Person 7 works at Company 3 (Pfizer) as Database Administrator
            session.run(worksAtMergeQuery, Map.of("personId", 7L, "companyId", 3L));
            session.run(worksAtSetQuery, Map.of("personId", 7L, "companyId", 3L, "position", "Database Administrator"));

            // Person 8 works at Company 2 (Verizon) as Network Engineer
            session.run(worksAtMergeQuery, Map.of("personId", 8L, "companyId", 2L));
            session.run(worksAtSetQuery, Map.of("personId", 8L, "companyId", 2L, "position", "Network Engineer"));

            // Person 9 works at Company 4 (Amazon) as Full Stack Developer
            session.run(worksAtMergeQuery, Map.of("personId", 9L, "companyId", 4L));
            session.run(worksAtSetQuery, Map.of("personId", 9L, "companyId", 4L, "position", "Full Stack Developer"));

            // Person 10 works at Company 6 (Salesforce) as UX Researcher
            session.run(worksAtMergeQuery, Map.of("personId", 10L, "companyId", 6L));
            session.run(worksAtSetQuery, Map.of("personId", 10L, "companyId", 6L, "position", "UX Researcher"));

            // Person 11 works at Company 1 (JPMorgan Chase) as Technical Lead
            session.run(worksAtMergeQuery, Map.of("personId", 11L, "companyId", 1L));
            session.run(worksAtSetQuery, Map.of("personId", 11L, "companyId", 1L, "position", "Technical Lead"));

            // Person 12 works at Company 6 (Salesforce) as QA Engineer
            session.run(worksAtMergeQuery, Map.of("personId", 12L, "companyId", 6L));
            session.run(worksAtSetQuery, Map.of("personId", 12L, "companyId", 6L, "position", "QA Engineer"));

            // Create some IN_DOMAIN relationships (Project to Domain) - already idempotent (no properties)
            String inDomainQuery = """
                MATCH (pr:Project {id: $projectId})
                MATCH (d:Domain {id: $domainId})
                MERGE (pr)-[r:IN_DOMAIN]->(d)
                RETURN r
                """;

            // Project 1 (Digital Banking Platform) is in FinTech (domain 1)
            session.run(inDomainQuery, Map.of("projectId", 1L, "domainId", 1L));

            // Project 2 (5G Network Optimization) is in Telecom (domain 2)
            session.run(inDomainQuery, Map.of("projectId", 2L, "domainId", 2L));

            // Project 3 (Healthcare Patient Portal) is in Healthcare (domain 3)
            session.run(inDomainQuery, Map.of("projectId", 3L, "domainId", 3L));

            // Project 4 (E-commerce Recommendation Engine) is in E-commerce (domain 4)
            session.run(inDomainQuery, Map.of("projectId", 4L, "domainId", 4L));

            // Project 5 (Supply Chain Visibility Platform) is in Logistics (domain 5)
            session.run(inDomainQuery, Map.of("projectId", 5L, "domainId", 5L));

            // Project 6 (SaaS Customer Analytics) is in SaaS (domain 6)
            session.run(inDomainQuery, Map.of("projectId", 6L, "domainId", 6L));

            // Project 7 (AI Fraud Detection System) is in FinTech (domain 1)
            session.run(inDomainQuery, Map.of("projectId", 7L, "domainId", 1L));

            // Project 8 (Cloud Migration Project) is in SaaS (domain 6)
            session.run(inDomainQuery, Map.of("projectId", 8L, "domainId", 6L));

            // Create some RELATED_TO relationships (Skill to Technology) - already idempotent (no properties)
            String relatedToQuery = """
                MATCH (s:Skill {id: $skillId})
                MATCH (t:Technology {id: $techId})
                MERGE (s)-[r:RELATED_TO]->(t)
                RETURN r
                """;

            // Java Development skill is related to Java technology
            session.run(relatedToQuery, Map.of("skillId", 1L, "techId", 1L));

            // Spring Boot Development skill is related to Spring Boot technology
            session.run(relatedToQuery, Map.of("skillId", 2L, "techId", 2L));

            // React Development skill is related to React technology
            session.run(relatedToQuery, Map.of("skillId", 3L, "techId", 3L));

            // TypeScript Development skill is related to TypeScript technology
            session.run(relatedToQuery, Map.of("skillId", 4L, "techId", 4L));

            // Kafka Administration skill is related to Kafka technology
            session.run(relatedToQuery, Map.of("skillId", 5L, "techId", 5L));

            // PostgreSQL Administration skill is related to PostgreSQL technology
            session.run(relatedToQuery, Map.of("skillId", 6L, "techId", 6L));

            // Redis Administration skill is related to Redis technology
            session.run(relatedToQuery, Map.of("skillId", 7L, "techId", 7L));

            // Docker Administration skill is related to Docker technology
            session.run(relatedToQuery, Map.of("skillId", 8L, "techId", 8L));

            // Kubernetes Administration skill is related to Kubernetes technology
            session.run(relatedToQuery, Map.of("skillId", 9L, "techId", 9L));

            // AWS Architecture skill is related to AWS technology
            session.run(relatedToQuery, Map.of("skillId", 10L, "techId", 10L));

            // Python Development skill is related to Python technology
            session.run(relatedToQuery, Map.of("skillId", 11L, "techId", 11L));

            // Project Management skill is not related to any specific technology (optional)
            // Data Analysis skill is related to Python technology (as it's often used with Python)
            session.run(relatedToQuery, Map.of("skillId", 13L, "techId", 11L));

            // Machine Learning skill is related to Python technology
            session.run(relatedToQuery, Map.of("skillId", 14L, "techId", 11L));

            // UI/UX Design skill is not related to any specific technology (optional)
        } catch (Exception e) {
            // Log the error but don't fail the seed
            System.err.println("Error seeding relationships: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seedRequiredSkills() {
        Driver driver = personRepository.getDriver();
        try (Session session = driver.session()) {
            String query = """
                MATCH (p:Project {id: $projectId})
                MATCH (s:Skill {id: $skillId})
                MERGE (p)-[r:REQUIRES_SKILL]->(s)
                RETURN r
                """;

            // Project 1: Digital Banking Platform -> Java Development, Spring Boot Development
            session.run(query, Map.of("projectId", 1L, "skillId", 1L));
            session.run(query, Map.of("projectId", 1L, "skillId", 2L));

            // Project 2: 5G Network Optimization -> Kafka Administration
            session.run(query, Map.of("projectId", 2L, "skillId", 5L));

            // Project 3: Healthcare Patient Portal -> Python Development, Data Analysis
            session.run(query, Map.of("projectId", 3L, "skillId", 11L));
            session.run(query, Map.of("projectId", 3L, "skillId", 13L));

            // Project 4: E-commerce Recommendation Engine -> AWS Architecture, Machine Learning, React Development
            session.run(query, Map.of("projectId", 4L, "skillId", 10L));
            session.run(query, Map.of("projectId", 4L, "skillId", 14L));
            session.run(query, Map.of("projectId", 4L, "skillId", 3L));

            // Project 5: Supply Chain Visibility Platform -> PostgreSQL Administration, Redis Administration
            session.run(query, Map.of("projectId", 5L, "skillId", 6L));
            session.run(query, Map.of("projectId", 5L, "skillId", 7L));

            // Project 6: SaaS Customer Analytics -> TypeScript Development, React Development, AWS Architecture
            session.run(query, Map.of("projectId", 6L, "skillId", 4L));
            session.run(query, Map.of("projectId", 6L, "skillId", 3L));
            session.run(query, Map.of("projectId", 6L, "skillId", 10L));

            // Project 7: AI Fraud Detection System -> Python Development, Machine Learning, AWS Architecture
            session.run(query, Map.of("projectId", 7L, "skillId", 11L));
            session.run(query, Map.of("projectId", 7L, "skillId", 14L));
            session.run(query, Map.of("projectId", 7L, "skillId", 10L));

            // Project 8: Cloud Migration Project -> Docker Administration, Kubernetes Administration, AWS Architecture
            session.run(query, Map.of("projectId", 8L, "skillId", 8L));
            session.run(query, Map.of("projectId", 8L, "skillId", 9L));
            session.run(query, Map.of("projectId", 8L, "skillId", 10L));
        } catch (Exception e) {
            System.err.println("Error seeding required skills: " + e.getMessage());
            e.printStackTrace();
        }
    }
}