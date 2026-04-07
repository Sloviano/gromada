import { useEffect, useState } from 'react';
import { classifiedApi } from '../../services/api';
import { ClassifiedAd } from '../../types';

export default function ClassifiedListPage() {
  const [ads, setAds] = useState<ClassifiedAd[]>([]);
  const [search, setSearch] = useState('');

  useEffect(() => {
    classifiedApi.getAll().then((res) => setAds(res.data));
  }, []);

  const handleSearch = () => {
    if (search.trim()) {
      classifiedApi.search(search).then((res) => setAds(res.data));
    } else {
      classifiedApi.getAll().then((res) => setAds(res.data));
    }
  };

  return (
    <div className="classified-list-page">
      <h2>Оголошення послуг</h2>
      <div className="search-bar">
        <input placeholder="Пошук послуг..." value={search} onChange={(e) => setSearch(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()} />
        <button onClick={handleSearch}>Шукати</button>
      </div>
      <div className="classified-grid">
        {ads.map((ad) => (
          <div key={ad.id} className="classified-card">
            <h3>{ad.title}</h3>
            <p>{ad.description}</p>
            {ad.price && <span className="price">{ad.price} грн</span>}
            <span className="contact">{ad.contactInfo}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
