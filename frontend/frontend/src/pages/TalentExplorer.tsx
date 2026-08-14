import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const TalentExplorer = () => {
  const [people, setPeople] = useState<any[]>([]);
  const [filteredPeople, setFilteredPeople] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedSkills, setSelectedSkills] = useState<string[]>([]);
  const [minExperience, setMinExperience] = useState<number>(0);
  const [allSkills, setAllSkills] = useState<string[]>([]);

  useEffect(() => {
    const fetchPeopleAndSkills = async () => {
      try {
        // Fetch all people
        const peopleResponse = await fetch('/api/people');
        if (!peopleResponse.ok) {
          throw new Error('Failed to fetch people');
        }
        const peopleData = await peopleResponse.json();
        setPeople(peopleData);
        setFilteredPeople(peopleData);

        // Fetch all skills to populate the filter dropdown
        const skillsResponse = await fetch('/api/skills');
        if (!skillsResponse.ok) {
          throw new Error('Failed to fetch skills');
        }
        const skillsData = await skillsResponse.json();
        const skillNames = skillsData.map((skill: any) => skill.name);
        setAllSkills(skillNames);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'An error occurred');
        console.error('Error fetching initial data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchPeopleAndSkills();
  }, []);

  // Filter people based on search criteria
  useEffect(() => {
    if (people.length === 0) return;

    const filtered = people.filter((person) => {
      // Text search in name, title, summary
      const matchesSearch =
        person.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        person.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        person.summary.toLowerCase().includes(searchTerm.toLowerCase());

      // Experience filter
      const matchesExperience = person.experienceYears >= minExperience;

      // Skills filter (if any skills selected)
      const matchesSkills =
        selectedSkills.length === 0 ||
        person.skills?.some((skill: any) =>
          selectedSkills.includes(skill.name)
        ) ||
        false; // We don't have skills in the person object yet, so this will be false for now

      return matchesSearch && matchesExperience && matchesSkills;
    });

    setFilteredPeople(filtered);
  }, [people, searchTerm, selectedSkills, minExperience]);

  if (loading) {
    return (
      <div className="min-h-[60vh] flex flex-col items-center justify-center">
        <div className="animate-spin rounded-full border-4 border-t-indigo-600 w-12 h-12"></div>
        <p className="mt-4 text-gray-500">Loading talent data...</p>
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
        <h1 className="text-2xl font-bold text-gray-900">Talent Explorer</h1>
        <p className="text-gray-600 mt-2">
          Search and filter people by skills, experience, and other attributes.
        </p>
      </div>

      {/* Search and Filter Controls */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-6">
        <div className="grid gap-4 md:grid-cols-3">
          {/* Search Text Input */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Search by name, title, or summary
            </label>
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Enter search term..."
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          {/* Skills Multi-select (simplified as checkboxes for now) */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Skills
            </label>
            <div className="space-y-1 max-h-[200px] overflow-y-auto">
              {allSkills.map((skill) => (
                <div key={skill} className="flex items-center">
                  <input
                    type="checkbox"
                    value={skill}
                    checked={selectedSkills.includes(skill)}
                    onChange={(e) => {
                      if (e.target.checked) {
                        setSelectedSkills([...selectedSkills, skill]);
                      } else {
                        setSelectedSkills(selectedSkills.filter((s) => s !== skill));
                      }
                    }}
                    className="h-4 w-4 text-indigo-600"
                  />
                  <span className="ml-2 text-sm">{skill}</span>
                </div>
              ))}
            </div>
          </div>

          {/* Minimum Experience */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Minimum Experience (years)
            </label>
            <input
              type="number"
              value={minExperience}
              onChange={(e) => setMinExperience(parseInt(e.target.value) || 0)}
              min="0"
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>
        </div>

        {/* Reset Filters Button */}
        <div className="flex items-end">
          <button
            onClick={() => {
              setSearchTerm('');
              setSelectedSkills([]);
              setMinExperience(0);
            }}
            className="text-sm text-indigo-600 hover:text-indigo-800"
          >
            Reset Filters
          </button>
        </div>
      </div>

      {/* Results Count */}
      <div className="mb-4">
        <p className="text-sm text-gray-600">
          Showing {filteredPeople.length} of {people.length} people
        </p>
      </div>

      {/* People List */}
      {filteredPeople.length === 0 ? (
        <div className="text-center py-12">
          <p className="text-gray-500">No people match the current filters.</p>
        </div>
      ) : (
        <div className="space-y-6">
          {filteredPeople.map((person) => (
            <div key={person.id} className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
              <div className="p-6">
                <div className="flex justify-between items-start">
                  <div>
                    <Link to={`/person/${person.id}`} className="text-lg font-semibold text-gray-900 hover:text-indigo-600">
                      {person.name}
                    </Link>
                    <p className="text-gray-600 mt-1">{person.title}</p>
                    <div className="flex items-center text-sm text-gray-500 mt-2">
                      <span className="mr-3">
                        <svg className="h-4 w-4 mr-1 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3" />
                        </svg>
                      </span>
                      {person.experienceYears} years experience
                    </div>
                    <p className="mt-2 line-clamp-2 text-gray-700">{person.summary}</p>
                  </div>
                  <div className="flex space-x-3">
                    <Link
                      to={`/person/${person.id}/similar`}
                      className="px-3 py-1.5 text-sm font-medium bg-indigo-100 text-indigo-800 rounded hover:bg-indigo-200"
                    >
                      Similar
                    </Link>
                    <Link
                      to={`/person/${person.id}/network`}
                      className="px-3 py-1.5 text-sm font-medium bg-gray-100 text-gray-800 rounded hover:bg-gray-200"
                    >
                      Network
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default TalentExplorer;