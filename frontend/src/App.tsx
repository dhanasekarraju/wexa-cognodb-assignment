import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import { useTheme } from './hooks/useTheme';

// Import pages (will create these)
import Overview from './pages/Overview';
import Projects from './pages/Projects';
import ProjectDetail from './pages/ProjectDetail';
import TalentExplorer from './pages/TalentExplorer';
import PersonDetail from './pages/PersonDetail';
import NetworkExplorer from './pages/NetworkExplorer';
import WhyGraph from './pages/WhyGraph';

function App() {
  const { theme, toggleTheme } = useTheme();

  return (
    <BrowserRouter>
      <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
        <nav className="bg-white dark:bg-gray-800 shadow-md">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex h-16 items-center justify-between">
              <div className="flex items-center space-x-4">
                <a href="/" className="text-xl font-bold text-indigo-600 dark:text-indigo-400">
                  TalentGraph
                </a>
              </div>
              <div className="flex items-center space-x-4">
                {/* Theme toggle button */}
                <button
                  onClick={toggleTheme}
                  aria-label={theme === 'dark' ? 'Switch to light mode' : 'Switch to dark mode'}
                  className="p-2 rounded-md hover:bg-gray-100 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                >
                  {theme === 'dark' ? (
                    // Sun icon for dark mode (to switch to light)
                    <svg className="h-5 w-5 text-yellow-400" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
                    </svg>
                  ) : (
                    // Moon icon for light mode (to switch to dark)
                    <svg className="h-5 w-5 text-gray-600" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                    </svg>
                  )}
                </button>
                {/* Nav links (hidden on small screens) */}
                <div className="hidden md:flex md:items-center md:space-x-6">
                  <a href="/" className="text-gray-500 hover:text-gray-900 transition-colors dark:hover:text-gray-200">
                    Overview
                  </a>
                  <a href="/projects" className="text-gray-500 hover:text-gray-900 transition-colors dark:hover:text-gray-200">
                    Projects
                  </a>
                  <a href="/talent" className="text-gray-500 hover:text-gray-900 transition-colors dark:hover:text-gray-200">
                    Talent Explorer
                  </a>
                  <a href="/network" className="text-gray-500 hover:text-gray-900 transition-colors dark:hover:text-gray-200">
                    Network Explorer
                  </a>
                  <a href="/why-graph" className="text-gray-500 hover:text-gray-900 transition-colors dark:hover:text-gray-200">
                    Why Graph?
                  </a>
                </div>
              </div>
            </div>
          </div>
        </nav>

        <main className="py-8">
          <Routes>
            <Route path="/" element={<Overview />} />
            <Route path="/projects" element={<Projects />} />
            <Route path="/projects/:id" element={<ProjectDetail />} />
            <Route path="/talent" element={<TalentExplorer />} />
            <Route path="/person/:id" element={<PersonDetail />} />
            <Route path="/network" element={<NetworkExplorer />} />
            <Route path="/why-graph" element={<WhyGraph />} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;