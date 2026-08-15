import { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import ForceGraph2D from 'react-force-graph-2d';
import { API_BASE_URL } from '../services/api';

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
  networkDensity: number;
  nodeTypes: NodeType[];
  relationshipTypes: RelationshipType[];
}


interface GraphNode {
  id: string | number;
  label: string;
  relSize?: number;
  [key: string]: any;
}

interface GraphLink {
  source: string | number;
  target: string | number;
  [key: string]: any;
}

const NetworkExplorer = () => {
  const graphContainerRef = useRef<HTMLDivElement>(null);
  const [graphWidth, setGraphWidth] = useState(800);

  const [stats, setStats] = useState<Stats>({
    totalNodes: 0,
    totalRelationships: 0,
    networkDensity: 0,
    nodeTypes: [],
    relationshipTypes: [],
  });
  const [graphData, setGraphData] = useState<{ nodes: GraphNode[]; links: GraphLink[] }>({ nodes: [], links: [] });
  const [selectedPersonId, setSelectedPersonId] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [samplePeople, setSamplePeople] = useState<any[]>([]);

  // Helper to get Tailwind color class for node label (used in lists)
  const getNodeColorClass = (label: string) => {
    switch (label) {
      case 'Person': return 'bg-blue-500';
      case 'Skill': return 'bg-green-500';
      case 'Project': return 'bg-purple-500';
      case 'Company': return 'bg-red-500';
      case 'Domain': return 'bg-yellow-500';
      case 'Technology': return 'bg-indigo-500';
      default: return 'bg-gray-500';
    }
  };

  // ForceGraph node color mapping (must be preserved exactly)
  const nodeColor = (node: GraphNode) => {
    switch (node.label) {
      case 'Person': return '#3b82f6'; // blue-500
      case 'Skill': return '#10b981'; // green-500
      case 'Project': return '#a855f7'; // purple-500
      case 'Company': return '#ef4444'; // red-500
      case 'Domain': return '#eab308'; // yellow-500
      case 'Technology': return '#6366f1'; // indigo-500
      default: return '#6b7280'; // gray-500
    }
  };

  // Fetch network statistics from admin endpoint
  const fetchNetworkStats = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/admin/stats`);
      if (!response.ok) {
        throw new Error(`Failed to fetch stats: ${response.status}`);
      }
      const data: Stats = await response.json();
      setStats(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred while fetching network statistics');
      console.error('Error fetching network stats:', err);
    } finally {
      setLoading(false);
    }
  };

  // Fetch a sample of people for clickable selection
  const fetchSamplePeople = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/people?limit=6`);
      if (!response.ok) {
        throw new Error('Failed to fetch sample people');
      }
      const data = await response.json();
      setSamplePeople(data);
    } catch (err) {
      console.error('Error fetching sample people:', err);
    }
  };

  // Fetch network for a specific person
  const fetchPersonNetwork = async (personId: string) => {
    try {
      setLoading(true);
      setError(null);
      const response = await fetch(`${API_BASE_URL}/api/people/${personId}/network`);
      if (!response.ok) {
        throw new Error(`Failed to fetch person network: ${response.status}`);
      }
      const data = await response.json();

      const normalizedGraphData = {
        nodes: data.nodes ?? [],
        links: (data.relationships ?? []).map((relationship: any) => ({
          ...relationship,
          source: relationship.startNodeId,
          target: relationship.endNodeId,
          type: relationship.type
        }))
      };

      setGraphData(normalizedGraphData);
      setSelectedPersonId(personId);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred while fetching person network');
      console.error('Error fetching person network:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const container = graphContainerRef.current;
    if (!container) return;

    const updateWidth = () => {
      setGraphWidth(container.clientWidth);
    };

    updateWidth();

    const observer = new ResizeObserver(updateWidth);
    observer.observe(container);

    return () => observer.disconnect();
  }, [selectedPersonId]);

  useEffect(() => {
    fetchNetworkStats();
    fetchSamplePeople();
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

          {/* Network Density */}
          <div className="text-center space-y-2">
            <p className="text-sm font-medium text-gray-500">Approx. Network Density</p>
            <p className="text-3xl font-bold text-gray-900">
              {(stats.networkDensity * 100).toFixed(2)}%
            </p>
            <p className="text-xs text-gray-500">
              Uses an undirected connectivity approximation for a simple overview.
            </p>
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

      {/* Person Selection */}
      {selectedPersonId === null && samplePeople.length > 0 && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Explore Sample Networks</h2>
          <p className="text-gray-600 mb-4">
            Click on a person below to view their network in the graph.
          </p>
          <div className="flex flex-wrap gap-4">
            {samplePeople.map((person) => (
              <div
                key={person.id}
                onClick={() => fetchPersonNetwork(person.id)}
                className="cursor-pointer bg-blue-50 hover:bg-blue-100 p-3 rounded border border-blue-200 transition-colors"
              >
                <div className="font-medium">{person.name}</div>
                <div className="text-sm text-gray-500">{person.title}</div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Interactive Graph Section */}
      {selectedPersonId !== null && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">
            Network for {samplePeople.find(p => p.id === selectedPersonId)?.name || 'Selected Person'}
          </h2>
          <div ref={graphContainerRef} className="h-[420px] w-full max-w-4xl mx-auto rounded-lg border border-gray-100">
            <ForceGraph2D
              width={graphWidth}
              height={420}
              graphData={graphData}
              nodeId="id"
              nodeRelSize={4}
              nodeColor={nodeColor}
              linkSource="source"
              linkTarget="target"
              linkWidth={1.5}
              linkColor="#999"
              onNodeClick={(node) => {
                // Optionally handle node click (e.g., show details)
                console.log('Node clicked:', node);
              }}
            />
          </div>
        </div>
      )}

      {/* Legend */}
      {selectedPersonId !== null && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Legend</h2>
          <div className="space-y-3 text-sm">
            <div className="flex items-center">
              <div className="w-3 h-3 rounded-full bg-blue-500 mr-2"></div>
              <span>Person</span>
            </div>
            <div className="flex items-center">
              <div className="w-3 h-3 rounded-full bg-green-500 mr-2"></div>
              <span>Skill</span>
            </div>
            <div className="flex items-center">
              <div className="w-3 h-3 rounded-full bg-purple-500 mr-2"></div>
              <span>Project</span>
            </div>
            <div className="flex items-center">
              <div className="w-3 h-3 rounded-full bg-red-500 mr-2"></div>
              <span>Company</span>
            </div>
            <div className="flex items-center">
              <div className="w-3 h-3 rounded-full bg-yellow-500 mr-2"></div>
              <span>Domain</span>
            </div>
            <div className="flex items-center">
              <div className="w-3 h-3 rounded-full bg-indigo-500 mr-2"></div>
              <span>Technology</span>
            </div>
          </div>
        </div>
      )}

      {/* Nodes List */}
      {selectedPersonId !== null && graphData.nodes.length > 0 && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Nodes ({graphData.nodes.length})</h2>
          <div className="space-y-2 max-h-[300px] overflow-y-auto">
            {graphData.nodes.map((node) => (
              <div key={node.id} className="flex justify-between items-center p-2 bg-gray-50 rounded">
                <div className="flex items-center">
                  <div className={`w-3 h-3 rounded-full ${getNodeColorClass(node.label)} mr-3`}></div>
                  <span className="font-medium">{node.id}</span>
                </div>
                <span className="text-sm text-gray-500">{node.label}</span>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Relationships List */}
      {selectedPersonId !== null && graphData.links.length > 0 && (
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Relationships ({graphData.links.length})</h2>
          <div className="space-y-2 max-h-[300px] overflow-y-auto">
            {graphData.links.map((link, index) => (
              <div key={index} className="flex justify-between items-center p-2 bg-gray-50 rounded">
                <span className="font-medium">
                  {link.source} → {link.target}
                </span>
                <span className="text-sm text-gray-500">{link.type ?? 'related'}</span>
              </div>
            ))}
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