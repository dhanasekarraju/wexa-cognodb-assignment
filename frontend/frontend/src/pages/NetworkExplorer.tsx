import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

interface NodeType {
  type: string;
  count: number;
}

interface RelationshipType {
  type: string;
  count: number;
}

interface Stats {
  totalNodes: number;
  totalRelationships: number;
  nodeTypes: NodeType[];
  relationshipTypes: RelationshipType[];
}

interface SampleData {
  people: any[];
  projects: any[];
  skills: any[];
  companies: any[];
  domains: any[];
}

const NetworkExplorer = () => {
  const [stats, setStats] = useState<Stats>({
    totalNodes: 0,
    totalRelationships: 0,
    nodeTypes: [],
    relationshipTypes: [],
  });
  const [sampleData, setSampleData] = useState<SampleData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchNetworkStats = async () => {
      try {
        // We don't have a direct endpoint for network stats, so we'll fetch all entities and compute
        // In a real implementation, we would have a dedicated endpoint for this

        // Fetch all people
        const peopleResponse = await fetch('/api/people');
        const peopleData = await peopleResponse.json();

        // Fetch all projects
        const projectsResponse = await fetch('/api/projects');
        const projectsData = await projectsResponse.json();

        // Fetch all skills
        const skillsResponse = await fetch('/api/skills');
        const skillsData = await skillsResponse.json();

        // Fetch all companies
        const companiesResponse = await fetch('/api/companies');
        const companiesData = await companiesResponse.json();

        // Fetch all domains
        const domainsResponse = await fetch('/api/domains');
        const domainsData = await domainsResponse.json();

        // Calculate total nodes
        const totalNodes =
          peopleData.length +
          projectsData.length +
          skillsData.length +
          companiesData.length +
          domainsData.length;

        // For relationships, we don't have a direct way to count without traversing
        // We'll estimate or show placeholder data
        setStats({
          totalNodes: totalNodes,
          totalRelationships: 0, // Placeholder
          nodeTypes: [
            { type: 'Person', count: peopleData.length },
            { type: 'Project', count: projectsData.length },
            { type: 'Skill', count: skillsData.length },
            { type: 'Company', count: companiesData.length },
            { type: 'Domain', count: domainsData.length },
          ],
          relationshipTypes: [ // Placeholder
            { type: 'HAS_SKILL', count: 0 },
            { type: 'WORKED_ON', count: 0 },
            { type: 'WORKS_AT', count: 0 },
            { type: 'IN_DOMAIN', count: 0 },
            { type: 'RELATED_TO', count: 0 },
          ],
        });

        // Get a sample of the data for display
        setSampleData({
          people: peopleData.slice(0, 5),
          projects: projectsData.slice(0, 5),
          skills: skillsData.slice(0, 5),
          companies: companiesData.slice(0, 5),
          domains: domainsData.slice(0, 5),
        });
      } catch (err) {
        setError(err instanceof Error ? err.message : 'An error occurred');
        console.error('Error fetching network data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchNetworkStats();
  }, []);

  if (loading) {
    return (
      <div className="min-h-[60vh] flex flex-col items-center justify-center">
        <div className="animate-spin rounded-full border-4 border-t-indigo-600 w-12 h-12"></div>
        <p className="mt-4 text-gray-500">Loading network data...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-red-50 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
          <p className="font-bold">Error:</p>
          <p className="mt-1">{error}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Network Explorer</h1>
        <p className="text-gray-600 mt-2">
          Explore the interconnected graph of people, skills, projects, companies, and domains.
        </p>
      </div>

      {/* Network Statistics */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Network Statistics</h2>
        <div className="grid gap-6 md:grid-cols-3">
          {/* Total Nodes */}
          <div className="text-center">
            <p className="text-sm font-medium text-gray-500">Total Nodes</p>
            <p className="text-3xl font-bold text-gray-900">{stats.totalNodes}</p>
          </div>

          {/* Total Relationships */}
          <div className="text-center">
            <p className="text-sm font-medium text-gray-500">Total Relationships</p>
            <p className="text-3xl font-bold text-gray-900">{stats.totalRelationships}</p>
          </div>

          {/* Density (placeholder) */}
          <div className="text-center">
            <p className="text-sm font-medium text-gray-500">Network Density</p>
            <p className="text-3xl font-bold text-gray-900">N/A</p>
          </div>
        </div>
      </div>

      {/* Node Type Breakdown */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Node Type Breakdown</h2>
        <div className="space-y-4">
          {stats.nodeTypes.map((type, index) => (
            <div key={index} className="flex justify-between items-center p-3 bg-gray-50 rounded">
              <span className="font-medium">{type.type}</span>
              <span className="text-gray-900">{type.count}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Relationship Type Breakdown */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Relationship Type Breakdown</h2>
        <div className="space-y-4">
          {stats.relationshipTypes.map((type, index) => (
            <div key={index} className="flex justify-between items-center p-3 bg-gray-50 rounded">
              <span className="font-medium">{type.type}</span>
              <span className="text-gray-900">{type.count}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Sample Data */}
      {sampleData && (
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Sample Data</h2>
          <div className="space-y-6">
            {/* People Sample */}
            <div>
              <h3 className="text-lg font-semibold text-gray-900 mb-3">People (Sample)</h3>
              {sampleData.people.length > 0 ? (
                <ul className="list-disc list-inset space-y-2">
                  {sampleData.people.map((person: any, index: number) => (
                    <li key={index}>
                      {person.name} ({person.title})
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="text-gray-500">No people data</p>
              )}
            </div>

            {/* Projects Sample */}
            <div>
              <h3 className="text-lg font-semibold text-gray-900 mb-3">Projects (Sample)</h3>
              {sampleData.projects.length > 0 ? (
                <ul className="list-disc list-inset space-y-2">
                  {sampleData.projects.map((project: any, index: number) => (
                    <li key={index}>
                      {project.name} ({project.status})
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="text-gray-500">No projects data</p>
              )}
            </div>

            {/* Skills Sample */}
            <div>
              <h3 className="text-lg font-semibold text-gray-900 mb-3">Skills (Sample)</h3>
              {sampleData.skills.length > 0 ? (
                <ul className="list-disc list-inset space-y-2">
                  {sampleData.skills.map((skill: any, index: number) => (
                    <li key={index}>
                      {skill.name} ({skill.category})
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="text-gray-500">No skills data</p>
              )}
            </div>
          </div>
        </div>
      )}

      {/* Call to Action */}
      <div className="mt-12 text-center">
        <Link to="/why-graph" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-3 px-8 rounded">
          Learn Why Graph Databases Excel at Talent Management
        </Link>
      </div>
    </div>
  );
};

export default NetworkExplorer;