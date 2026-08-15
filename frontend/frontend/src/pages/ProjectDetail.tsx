import { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';

const ProjectDetail = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [project, setProject] = useState<any>(null);
  const [recommendations, setRecommendations] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProjectData = async () => {
      try {
        // Fetch project details
        const projectResponse = await fetch(`/api/projects/${id}`);
        if (!projectResponse.ok) {
          if (projectResponse.status === 404) {
            navigate('/projects');
            return;
          }
          throw new Error('Failed to fetch project');
        }
        const projectData = await projectResponse.json();
        setProject(projectData);

        // Fetch talent recommendations for this project
        const recommendationsResponse = await fetch(`/api/projects/${id}/recommendations`);
        if (!recommendationsResponse.ok) {
          throw new Error('Failed to fetch recommendations');
        }
        const recommendationsData = await recommendationsResponse.json();
        setRecommendations(recommendationsData);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'An error occurred');
        console.error('Error fetching project data:', err);
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      fetchProjectData();
    }
  }, [id, navigate]);

  if (loading) {
    return (
      <div className="min-h-[60vh] flex flex-col items-center justify-center">
        <div className="animate-spin rounded-full border-4 border-t-indigo-600 w-12 h-12"></div>
        <p className="mt-4 text-gray-500">Loading project details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-red-50 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
          <p className="font-bold">Error Loading Project Data:</p>
          <p className="mt-1">{error}</p>
          <p className="mt-3">
            The TalentGraph application could not load project details from the database.
            This may be due to a connection issue or the requested project not existing.
          </p>
          <div className="mt-4">
            <Link to="/projects" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded">
              Back to Projects
            </Link>
          </div>
        </div>
      </div>
    );
  }

  if (!project) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-yellow-50 border-l-4 border-yellow-500 text-yellow-700 p-4 mb-6" role="alert">
          <p className="font-bold">Project not found.</p>
        </div>
        <div className="mt-6">
          <Link to="/projects" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded">
            Back to Projects
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-6">
        <Link to="/projects" className="text-indigo-600 hover:text-indigo-800 font-medium">
          ← Back to Projects
        </Link>
        <h1 className="text-3xl font-bold text-gray-900 mt-4">{project.name}</h1>
        <p className="text-gray-600 mt-2">{project.description}</p>
        <div className="flex mt-4">
          <span className="bg-indigo-100 text-indigo-800 text-xs font-medium px-2.5 py-0.5 rounded mr-3">
            Status: {project.status}
          </span>
        </div>
      </div>

      <div className="grid gap-6">
        {/* Project Details Card */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Project Details</h2>
          <div className="space-y-4">
            <div>
              <p className="text-sm font-medium text-gray-500">ID</p>
              <p className="text-gray-900 mt-1">{project.id}</p>
            </div>
            <div>
              <p className="text-sm font-medium text-gray-500">Created</p>
              <p className="text-gray-900 mt-1">N/A</p>
            </div>
          </div>
        </div>

        {/* Talent Recommendations Card */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">
            Talent Recommendations for this Project
          </h2>
          {recommendations.length === 0 ? (
            <p className="text-gray-500 text-center py-8">
              No talent recommendations found for this project.
            </p>
          ) : (
            <div className="space-y-4">
              {recommendations.map((rec: any, index: number) => (
                <div key={index} className="border-t pt-4 first:border-t-0">
                  <div className="flex items-start space-x-4">
                    <div className="flex-shrink-0 h-10 w-10 bg-gray-100 rounded-full flex items-center justify-center">
                      <svg className="h-5 w-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                      </svg>
                    </div>
                    <div>
                      <h3 className="text-lg font-semibold text-gray-900">{rec.person.name}</h3>
                      <p className="text-gray-600 mt-1">{rec.person.title}</p>
                      <div className="mt-2">
                        <span className="bg-indigo-100 text-indigo-800 text-xs font-medium px-2.5 py-0.5 rounded mr-2">
                          Score: {rec.score}
                        </span>
                        {rec.domainExperience && (
                          <span className="bg-green-100 text-green-800 text-xs font-medium px-2.5 py-0.5 rounded">
                            Domain Match
                          </span>
                        )}
                      </div>
                      <div className="mt-3">
                        <p className="text-sm font-medium text-gray-500">Why this match:</p>
                        <ul className="mt-1 list-disc list-inside text-sm text-gray-600 space-y-1">
                          {rec.reasons.map((reason: string, idx: number) => (
                                            <li key={idx}>
                                              • {reason}
                                            </li>
                                          ))}
                        </ul>
                      </div>
                      {rec.matchedSkills.length > 0 && (
                        <div className="mt-4">
                          <p className="text-sm font-medium text-gray-500">Matched Skills:</p>
                          <div className="mt-1 flex flex-wrap gap-2">
                            {rec.matchedSkills.map((skill: any, idx: number) => (
                                                      <span key={idx} className="bg-indigo-50 text-indigo-800 text-xs font-medium px-2 py-0.5 rounded">
                                                        {skill.skill} ({skill.proficiency})
                                                      </span>
                                                    ))}
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProjectDetail;