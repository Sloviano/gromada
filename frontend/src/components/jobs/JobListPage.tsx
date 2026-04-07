import { useEffect, useState } from 'react';
import { jobApi } from '../../services/api';
import { JobPosting } from '../../types';

export default function JobListPage() {
  const [jobs, setJobs] = useState<JobPosting[]>([]);
  const [search, setSearch] = useState('');

  useEffect(() => {
    jobApi.getOpen().then((res) => setJobs(res.data));
  }, []);

  const handleSearch = () => {
    if (search.trim()) {
      jobApi.search(search).then((res) => setJobs(res.data));
    } else {
      jobApi.getOpen().then((res) => setJobs(res.data));
    }
  };

  return (
    <div className="job-list-page">
      <h2>Вакансії</h2>
      <div className="search-bar">
        <input placeholder="Пошук вакансій..." value={search} onChange={(e) => setSearch(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()} />
        <button onClick={handleSearch}>Шукати</button>
      </div>
      <div className="job-grid">
        {jobs.map((job) => (
          <div key={job.id} className="job-card">
            <h3>{job.title}</h3>
            <p className="company">{job.company}</p>
            <p>{job.description}</p>
            <span className="location">{job.location}</span>
            {job.salaryMin && job.salaryMax && (
              <span className="salary">{job.salaryMin}–{job.salaryMax} грн</span>
            )}
            <span className="contact">{job.contactInfo}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
