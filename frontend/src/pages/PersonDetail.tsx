import { useState, useEffect } from 'react';
import { useParams, Link, useNavigate, useLocation } from 'react-router-dom';
import { API_BASE_URL } from '../services/api';

const PersonDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const location = useLocation();
  const [person, setPerson] = useState<any>(null);
  const [similarPeople, setSimilarPeople] = useState<any[]>([]);
  const [networkData, setNetworkData] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<'overview' | 'similar' | 'network'>(() => {
    if (location.pathname.endsWith('/similar')) return 'similar';
    if (location.pathname.endsWith('/network')) return 'network';
    return 'overview';
  });

  useEffect(() => {
    if (location.pathname.endsWith('/similar')) {
      setActiveTab('similar');
    } else if (location.pathname.endsWith('/network')) {
      setActiveTab('network');
    } else {
      setActiveTab('overview');
    }
  }, [location.pathname]);

  useEffect(() => {
    const fetchPersonData = async () => {
      try {
        // Fetch person details
        const personResponse = await fetch(`${API_BASE_URL}/api/people/${id}`);
        if (!personResponse.ok) {
          if (personResponse.status === 404) {
            navigate('/talent');
            return;
          }
          throw new Error('Failed to fetch person');
        }
        const personData = await personResponse.json();
        setPerson(personData);

        // Fetch similar people
        const similarResponse = await fetch(`${API_BASE_URL}/api/people/${id}/similar`);
        if (!similarResponse.ok) {
          throw new Error('Failed to fetch similar people');
        }
        const similarData = await similarResponse.json();
        setSimilarPeople(similarData);

        // Fetch network data
        const networkResponse = await fetch(`${API_BASE_URL}/api/people/${id}/network`);
        if (!networkResponse.ok) {
          throw new Error('Failed to fetch network data');
        }
        const networkData = await networkResponse.json();
        setNetworkData(networkData);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'An error occurred');
        console.error('Error fetching person data:', err);
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      fetchPersonData();
    }
  }, [id, navigate]);

  if (loading) {
    return (
      <div className="min-h-[60vh] flex flex-col items-center justify-center">
        <div className="animate-spin rounded-full border-4 border-t-indigo-600 w-12 h-12"></div>
        <p className="mt-4 text-gray-500">Loading person details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-red-50 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
          <p className="font-bold">Error Loading Person Data:</p>
          <p className="mt-1">{error}</p>
          <p className="mt-3">
            The TalentGraph application could not load person details from the database.
            This may be due to a connection issue or the requested person not existing.
          </p>
          <div className="mt-4">
            <Link to="/talent" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded">
              Back to Talent Explorer
            </Link>
          </div>
        </div>
      </div>
    );
  }

  if (!person) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-yellow-50 border-l-4 border-yellow-500 text-yellow-700 p-4 mb-6" role="alert">
          <p className="font-bold">Person not found.</p>
        </div>
        <div className="mt-6">
          <Link to="/talent" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded">
            Back to Talent Explorer
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-6">
        <Link to="/talent" className="text-indigo-600 hover:text-indigo-800 font-medium">
          ← Back to Talent Explorer
        </Link>
        <h1 className="text-3xl font-bold text-gray-900 mt-4">{person.name}</h1>
        <p className="text-gray-600 mt-2">{person.title}</p>
        <div className="flex mt-4">
          <span className="bg-indigo-100 text-indigo-800 text-xs font-medium px-2.5 py-0.5 rounded mr-3">
            {person.experienceYears} years experience
          </span>
          <span className="bg-gray-100 text-gray-800 text-xs font-medium px-2.5 py-0.5 rounded">
            {person.location}
          </span>
        </div>
      </div>

      {/* Tabs */}
      <div className="mb-6">
        <div className="flex border-b border-gray-200">
          <button
            onClick={() => setActiveTab('overview')}
            className={`
              px-4 py-3 text-sm font-medium
              ${activeTab === 'overview'
                ? 'border-b-2 border-indigo-500 text-indigo-600'
                : 'text-gray-500 hover:text-gray-600'}
            `}
          >
            Overview
          </button>
          <button
            onClick={() => setActiveTab('similar')}
            className={`
              px-4 py-3 text-sm font-medium
              ${activeTab === 'similar'
                ? 'border-b-2 border-indigo-500 text-indigo-600'
                : 'text-gray-500 hover:text-gray-600'}
            `}
          >
            Similar People
          </button>
          <button
            onClick={() => setActiveTab('network')}
            className={`
              px-4 py-3 text-sm font-medium
              ${activeTab === 'network'
                ? 'border-b-2 border-indigo-500 text-indigo-600'
                : 'text-gray-500 hover:text-gray-600'}
            `}
          >
            Network
          </button>
        </div>
      </div>

      {/* Tab Content */}
      {activeTab === 'overview' && (
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">About</h2>
          <p className="text-gray-700 mb-4">{person.summary}</p>

          <div className="space-y-4">
            <div>
              <p className="text-sm font-medium text-gray-500">ID</p>
              <p className="text-gray-900 mt-1">{person.id}</p>
            </div>

            {/* We don't have skills, companies, etc. in the person object from the API yet */}
            {/* In a real implementation, we would fetch these relationships */}
            <div>
              <p className="text-sm font-medium text-gray-500">Skills</p>
              <p className="text-gray-900 mt-1">Data not available in current API</p>
            </div>

            <div>
              <p className="text-sm font-medium text-gray-500">Companies</p>
              <p className="text-gray-900 mt-1">Data not available in current API</p>
            </div>

            <div>
              <p className="text-sm font-medium text-gray-500">Projects</p>
              <p className="text-gray-900 mt-1">Data not available in current API</p>
            </div>
          </div>
        </div>
      )}

      {activeTab === 'similar' && (
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Similar People</h2>
          {similarPeople.length === 0 ? (
            <p className="text-gray-500 text-center py-8">
              No similar people found.
            </p>
          ) : (
            <div className="space-y-4">
              {similarPeople.map((similar: any, index: number) => (
                <div key={index} className="border-t pt-4 first:border-t-0">
                  <div className="flex items-start space-x-4">
                    <div className="flex-shrink-0 h-10 w-10 bg-gray-100 rounded-full flex items-center justify-center">
                      <svg className="h-5 w-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                      </svg>
                    </div>
                    <div>
                      <h3 className="text-lg font-semibold text-gray-900">{similar.person.name}</h3>
                      <p className="text-gray-600 mt-1">{similar.person.title}</p>
                      <div className="mt-2">
                        <span className="bg-indigo-100 text-indigo-800 text-xs font-medium px-2.5 py-0.5 rounded mr-2">
                          Similarity Score: {similar.similarityScore}
                        </span>
                      </div>
                      <div className="mt-3">
                        <p className="text-sm font-medium text-gray-500">Reasons:</p>
                        <ul className="mt-1 list-disc list-inside text-sm text-gray-600 space-y-1">
                          {similar.reasons.map((reason: string, idx: number) => (
                                            <li key={idx}>
                                              • {reason}
                                            </li>
                                          ))}
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {activeTab === 'network' && (
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Professional Network</h2>
          {networkData && networkData.nodes && networkData.nodes.length > 0 ? (
            <div>
              <p className="text-sm font-medium text-gray-500 mb-2">
                Network visualization would appear here in a full implementation.
                For now, we show the raw data.
              </p>
              <div className="bg-gray-50 rounded-lg p-4">
                <h3 className="text-lg font-semibold text-gray-900 mb-3">Network Data (JSON)</h3>
                <pre className="bg-white p-4 rounded overflow-auto text-xs">
                  {JSON.stringify(networkData, null, 2)}
                </pre>
              </div>
            </div>
          ) : (
            <p className="text-gray-500 text-center py-8">
              No network data available.
            </p>
          )}
        </div>
      )}
    </div>
  );
};

export default PersonDetail;