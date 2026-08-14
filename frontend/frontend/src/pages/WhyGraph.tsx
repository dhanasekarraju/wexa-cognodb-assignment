import { Link } from 'react-router-dom';

const WhyGraph = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-gray-900">Why Graph Databases for Talent Management?</h1>
        <p className="text-gray-600 mt-2">
          Understanding the advantages of graph technology in the context of skills, experience, and relationships.
        </p>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">The Power of Connected Data</h2>
        <p className="text-gray-700 mb-6">
          Traditional relational databases struggle to efficiently query complex relationships. Graph databases excel at
          traversing connections, making them ideal for talent management where relationships between people, skills,
          projects, and companies are paramount.
        </p>

        <div className="grid gap-6 md:grid-cols-2">
          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-900">Natural Representation</h3>
            <p className="text-gray-700">
              Graphs naturally represent entities (people, skills, projects) as nodes and their relationships as edges,
              closely matching how we think about professional networks.
            </p>
          </div>

          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-900">Efficient Traversals</h3>
            <p className="text-gray-700">
              Finding experts with specific skill combinations, discovering career paths, or identifying
              indirect connections becomes efficient with graph traversals, unlike complex SQL joins.
            </p>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Use Cases in TalentGraph</h2>
        <div className="space-y-6">
          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6-2a9 9 0 11-18 0 6 6 0 00-7.5 2.05M12 15a3 3 0 110-6 3 3 0 010 6z" />
              </svg>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Skill-Based Talent Discovery</h3>
              <p className="text-gray-700 mt-1">
                Find all people with specific skill combinations, accounting for proficiency levels and
                years of experience through graph traversals.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3-.895 3-2-1.343-2-3-2m7 10a2 2 0 11-4 0 2 2 0 00-4 0 2 2 0 114 0h-1a2 2 0 100-4 2 2 0 010 4h-1a2 2 0 100-4 2 2 0 114 0h-1a2 2 0 100-4 2 2 0 010 4h-1a2 2 0 100-4 2 2 0 114 0h-1a2 2 0 110 4 0" />
              </svg>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Career Path Recommendations</h3>
              <p className="text-gray-700 mt-1">
                Suggest logical career progressions by analyzing common trajectories in the professional network.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Project-Staffing Optimization</h3>
              <p className="text-gray-700 mt-1">
                Identify the best available talent for projects based on skills, experience, and
                past performance indicators stored in the graph.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
              </svg>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Organizational Network Analysis</h3>
              <p className="text-gray-700 mt-1">
                Analyze informal communication channels, identify key influencers, and understand
                how information flows through the organization.
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Technical Advantages</h2>
        <div className="space-y-4">
          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Schema Flexibility</h3>
              <p className="text-gray-700 mt-1">
                Easily adapt to new types of entities and relationships without rigid schema migrations.
              </p>
            </div>
          </div>

          <div className="flex items-start space-x-4">
            <div className="flex-shrink-0">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m2 0h0" />
              </svg>
            </div>
            <div>
              <h3 className="text-lg font-semibold text-gray-900">Performance at Scale</h3>
              <p className="text-gray-700 mt-1">
                Relationship queries remain fast even as the database grows, unlike joins in relational databases.
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