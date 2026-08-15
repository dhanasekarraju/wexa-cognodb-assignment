import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';

import Overview from './pages/Overview';
import Projects from './pages/Projects';
import ProjectDetail from './pages/ProjectDetail';
import TalentExplorer from './pages/TalentExplorer';
import PersonDetail from './pages/PersonDetail';
import NetworkExplorer from './pages/NetworkExplorer';
import WhyGraph from './pages/WhyGraph';

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-gray-50">
        <nav className="bg-white shadow-md">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex h-16 items-center justify-between">
              <div className="flex items-center space-x-4">
                <a href="/" className="text-xl font-bold text-indigo-600">
                  TalentGraph
                </a>
              </div>

              <div className="hidden md:flex md:items-center md:space-x-6">
                <a href="/" className="text-gray-500 hover:text-gray-900 transition-colors">
                  Overview
                </a>
                <a href="/projects" className="text-gray-500 hover:text-gray-900 transition-colors">
                  Projects
                </a>
                <a href="/talent" className="text-gray-500 hover:text-gray-900 transition-colors">
                  Talent Explorer
                </a>
                <a href="/network" className="text-gray-500 hover:text-gray-900 transition-colors">
                  Network Explorer
                </a>
                <a href="/why-graph" className="text-gray-500 hover:text-gray-900 transition-colors">
                  Why Graph?
                </a>
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
            <Route path="/person/:id/similar" element={<PersonDetail />} />
            <Route path="/person/:id/network" element={<PersonDetail />} />

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
