import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { API_BASE_URL } from '../services/api';

const Overview = () => {
  const [stats, setStats] = useState({
    people: 0,
    projects: 0,
    skills: 0,
    companies: 0,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Fetch statistics from the API
    const fetchStats = async () => {
      try {
        // Fetch people count
        const peopleResponse = await fetch(`${API_BASE_URL}/api/people`);
        if (!peopleResponse.ok) {
          throw new Error(`Failed to fetch people: ${peopleResponse.status}`);
        }
        const peopleData = await peopleResponse.json();
        const peopleCount = peopleData.length;

        // Fetch projects count
        const projectsResponse = await fetch(`${API_BASE_URL}/api/projects`);
        if (!projectsResponse.ok) {
          throw new Error(`Failed to fetch projects: ${projectsResponse.status}`);
        }
        const projectsData = await projectsResponse.json();
        const projectsCount = projectsData.length;

        // Fetch skills count
        const skillsResponse = await fetch(`${API_BASE_URL}/api/skills`);
        if (!skillsResponse.ok) {
          throw new Error(`Failed to fetch skills: ${skillsResponse.status}`);
        }
        const skillsData = await skillsResponse.json();
        const skillsCount = skillsData.length;

        // Fetch companies count
        const companiesResponse = await fetch(`${API_BASE_URL}/api/companies`);
        if (!companiesResponse.ok) {
          throw new Error(`Failed to fetch companies: ${companiesResponse.status}`);
        }
        const companiesData = await companiesResponse.json();
        const companiesCount = companiesData.length;

        setStats({
          people: peopleCount,
          projects: projectsCount,
          skills: skillsCount,
          companies: companiesCount,
        });
        setError(null);
      } catch (error) {
        console.error('Error fetching stats:', error);
        setError(error instanceof Error ? error.message : 'An error occurred while fetching statistics');
      } finally {
        setLoading(false);
      }
    };

    fetchStats();
  }, []);

  if (loading) {
    return (
      <div className="min-h-[60vh] flex flex-col items-center justify-center">
        <div className="animate-spin rounded-full border-4 border-t-indigo-600 w-12 h-12"></div>
        <p className="mt-4 text-gray-500">Loading statistics...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-red-50 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
          <p className="font-bold">Error Loading Statistics:</p>
          <p className="mt-1">{error}</p>
          <p className="mt-3">
            The TalentGraph application could not load statistics from the database.
            This may be due to a connection issue or the backend service being unavailable.
          </p>
          <div className="mt-4">
            <Link to="/" className="bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded">
              Try Again
            </Link>
          </div>
        </div>
      </div>
    );
  }

  // Only show zero stats if there's no error (meaning we successfully fetched data)
  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          TalentGraph Overview
        </h1>
        <p className="text-gray-600">
          A graph-based talent management system demonstrating the power of relationships data.
        </p>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">
        {/* People Stat */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex items-center">
            <div className="p-3 bg-indigo-100 rounded-full">
              <svg className="h-5 w-5 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 005.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.188-.352M16 11.19a4.001 4.001 0 00-6.138-1.08A4 4 0 1014.582 10.318a11.952 11.952 0 015.418-2.33m-1.414.356a1 1 0 00-1.415 0M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Total People</p>
              <p className="text-2xl font-bold text-gray-900">{stats.people}</p>
            </div>
          </div>
        </div>

        {/* Projects Stat */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex items-center">
            <div className="p-3 bg-blue-100 rounded-full">
              <svg className="h-5 w-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 7h18M3 7v9a2 2 0 002 2h10l2-2-2-2-2-2V7zm-1-4h10a2 2 0 012 2v2a2 2 0 01-2 2H4a2 2 0 01-2 2v-2a2 2 0 012-2zm0 0l2-2m-2 2l2 2M3 9h14" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Total Projects</p>
              <p className="text-2xl font-bold text-gray-900">{stats.projects}</p>
            </div>
          </div>
        </div>

        {/* Skills Stat */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex items-center">
            <div className="p-3 bg-green-100 rounded-full">
              <svg className="h-5 w-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.07A12.082 12.082 0 003 9c0 2.751.94 5.19 2.428 6.88a11.943 11.943 0 01-2.07 8.12A11.97 11.97 0 006.188 22h11.623c2-.114 3.829-.87 5.056-2.11a12 12 0 006.215-7.72c-.874-2.43-.223-4.78 1.02-6.89 1.25-2.14 3.03-3.57 5.07-4z" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Total Skills</p>
              <p className="text-2xl font-bold text-gray-900">{stats.skills}</p>
            </div>
          </div>
        </div>

        {/* Companies Stat */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex items-center">
            <div className="p-3 bg-purple-100 rounded-full">
              <svg className="h-5 w-5 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3-.895 3-2-1.343-2-3-2z" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-500">Total Companies</p>
              <p className="text-2xl font-bold text-gray-900">{stats.companies}</p>
            </div>
          </div>
        </div>
      </div>

      <div className="mt-12">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">
          Explore TalentGraph
        </h2>
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          <Link to="/projects" className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Projects</h3>
            <p className="text-gray-600">
              Browse all projects and see talent recommendations based on skills and experience.
            </p>
          </Link>
          <Link to="/talent" className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Talent Explorer</h3>
            <p className="text-gray-600">
              Search and filter people by skills, experience, and other attributes.
            </p>
          </Link>
          <Link to="/network" className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Network Explorer</h3>
            <p className="text-gray-600">
              Visualize the interconnected graph of people, skills, projects, and companies.
            </p>
          </Link>
          <Link to="/why-graph" className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
            <h3 className="text-lg font-semibold text-gray-900 mb-2">Why Graph?</h3>
            <p className="text-gray-600">
              Learn about the advantages of graph databases for talent management.
            </p>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Overview;