import { Link } from 'react-router-dom';

const WhyGraph = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-gray-900">Why Graph Databases for TalentGraph?</h1>
        <p className="text-gray-600 mt-2">
          Understanding how TalentGraph leverages graph technology for talent discovery and recommendations.
        </p>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Connected Data in TalentGraph</h2>
        <p className="text-gray-700 mb-6">
          TalentGraph models professional networks as interconnected graphs where people, skills, projects,
          companies, domains, and technologies are nodes connected by meaningful relationships. This structure
          enables powerful traversals that would be complex and slow in traditional databases.
        </p>

        <div className="grid gap-6 md:grid-cols-2">
          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-900">Natural Representation</h3>
            <p className="text-gray-700">
              Graphs naturally represent entities (people, skills, projects, companies, domains, technologies) as nodes
              and their relationships as edges, closely matching how we think about professional ecosystems.
            </p>
          </div>

          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-900">Efficient Multi-Hop Traversals</h3>
            <p className="text-gray-700">
              Finding connected data through multiple relationship hops is efficient with graph databases.
              TalentGraph uses this for similarity matching, project recommendations, and skill discovery.
            </p>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Concrete Traversals Used in TalentGraph</h2>
        <div className="space-y-6">
          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-blue-100 text-blue-600 rounded">
                1
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Skill-Based Talent Discovery</h3>
              <p className="text-gray-700 mt-1">
                Traversal: Person -(HAS_SKILL)&rarr; Skill
                <br className="hidden md:block" />
                Example: Find all people with "Java" skill by traversing from Skill nodes to connected Person nodes.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-blue-100 text-blue-600 rounded">
                2
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Skill-to-Technology Mapping</h3>
              <p className="text-gray-700 mt-1">
                Traversal: Skill -(RELATED_TO)&rarr; Technology
                <br className="hidden md:block" />
                Example: Find what technologies are related to a skill like "Python" (e.g., Django, Flask, Pandas).
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-blue-100 text-blue-600 rounded">
                3
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Project Experience Chains</h3>
              <p className="text-gray-700 mt-1">
                Traversal: Person -(WORKED_ON)&rarr; Project -(IN_DOMAIN)&rarr; Domain
                <br className="hidden md:block" />
                Example: Find people with project experience in a specific domain like "Finance" or "Healthcare".
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-blue-100 text-blue-600 rounded">
                4
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Company Employment History</h3>
              <p className="text-gray-700 mt-1">
                Traversal: Person -(WORKS_AT)&rarr; Company
                <br className="hidden md:block" />
                Example: Find all people who work or have worked at a specific company.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-blue-100 text-blue-600 rounded">
                5
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Project Skill Requirements</h3>
              <p className="text-gray-700 mt-1">
                Traversal: Project -(REQUIRES_SKILL)&rarr; Skill
                <br className="hidden md:block" />
                Example: Find what skills are required for a specific project.
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">How Similarity and Recommendations Work</h2>
        <p className="text-gray-700 mb-4">
          TalentGraph's similarity and recommendation features combine multiple connected signals from the graph:
        </p>
        <div className="space-y-4">
          <div className="flex items-start space-x-3 text-gray-600">
            <div className="flex-shrink-0">
              <svg className="h-4 w-4 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path d="M12 2l3.09 6.26L22 9.27l-5 4.87L18.18 18.36l-1.32 7.92L12 15.36l-4.86 2.96l-4.87-2.96-1.32-7.92L5.18 18.36l-5-4.87z" />
              </svg>
            </div>
            <div>
              <strong>Skills Matching (Weighted 40%)</strong>: Shared skills and related technologies
              <br className="hidden md:block" />
              Traversals: Person → HAS_SKILL → Skill and Skill → RELATED_TO → Technology
            </div>
          </div>
          <div className="flex items-start space-x-3 text-gray-600">
            <div className="flex-shrink-0">
              <svg className="h-4 w-4 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path d="M12 2l3.09 6.26L22 9.27l-5 4.87L18.18 18.36l-1.32 7.92L12 15.36l-4.86 2.96l-4.87-2.96-1.32-7.92L5.18 18.36l-5-4.87z" />
              </svg>
            </div>
            <div>
              <strong>Domain Experience (Weighted 30%)</strong>: Project-domain connections
              <br className="hidden md:block" />
              Traversals: Person → WORKED_ON → Project → IN_DOMAIN → Domain
            </div>
          </div>
          <div className="flex items-start space-x-3 text-gray-600">
            <div className="flex-shrink-0">
              <svg className="h-4 w-4 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path d="M12 2l3.09 6.26L22 9.27l-5 4.87L18.18 18.36l-1.32 7.92L12 15.36l-4.86 2.96l-4.87-2.96-1.32-7.92L5.18 18.36l-5-4.87z" />
              </svg>
            </div>
            <div>
              <strong>Company Affiliation (Weighted 15%)</strong>: Employment history
              <br className="hidden md:block" />
              Traversal: Person → WORKS_AT → Company
            </div>
          </div>
          <div className="flex items-start space-x-3 text-gray-600">
            <div className="flex-shrink-0">
              <svg className="h-4 w-4 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path d="M12 2l3.09 6.26L22 9.27l-5 4.87L18.18 18.36l-1.32 7.92L12 15.36l-4.86 2.96l-4.87-2.96-1.32-7.92L5.18 18.36l-5-4.87z" />
              </svg>
            </div>
            <div>
              <strong>Project Requirements Match (Weighted 15%)</strong>: Skills required for projects
              <br className="hidden md:block" />
              Traversals: Project → REQUIRES_SKILL → Skill and Person → HAS_SKILL → Skill
            </div>
          </div>
        </div>

        <p className="text-gray-600 mt-4 text-sm">
          These weighted signals are combined to generate similarity scores for talent matching and recommendation scores
          for project-staffing optimization.
        </p>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Technical Advantages for TalentGraph</h2>
        <div className="space-y-4">
          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-indigo-100 text-indigo-600 rounded">
                A
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Schema Flexibility</h3>
              <p className="text-gray-700 mt-1">
                Easily adapt to new relationship types as TalentGraph evolves (e.g., adding mentorship,
                certification, or publication relationships) without rigid schema migrations.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-indigo-100 text-indigo-600 rounded">
                B
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Real-Time Relationship Queries</h3>
              <p className="text-gray-700 mt-1">
                Complex traversals like finding "people who have worked on projects in domains related to their skills"
                remain performant as the graph grows, unlike multi-table joins in relational databases.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <div className="h-5 w-5 flex items-center justify-center bg-indigo-100 text-indigo-600 rounded">
                C
              </div>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Context-Aware Recommendations</h3>
              <p className="text-gray-700 mt-1">
                Recommendations consider the full context of a person's network, not just isolated attributes,
                leading to more relevant talent and project matches.
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="mt-12 text-center">
        <Link to="/" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-3 px-8 rounded">
          Back to Overview
        </Link>
      </div>
    </div>
  );
};

export default WhyGraph;