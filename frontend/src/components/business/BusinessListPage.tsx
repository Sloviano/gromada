import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { fetchBusinesses, searchBusinesses, fetchCategories } from '../../state/businessSlice';
import { businessApi } from '../../services/api';
import { AppDispatch, RootState } from '../../state/store';

export default function BusinessListPage() {
  const dispatch = useDispatch<AppDispatch>();
  const { businesses, categories, loading } = useSelector((state: RootState) => state.business);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    dispatch(fetchBusinesses());
    dispatch(fetchCategories());
  }, [dispatch]);

  const handleSearch = () => {
    if (searchTerm.trim()) {
      dispatch(searchBusinesses(searchTerm));
    } else {
      dispatch(fetchBusinesses());
    }
  };

  const handleLike = async (id: number) => {
    await businessApi.like(id);
    dispatch(fetchBusinesses());
  };

  return (
    <div className="business-list-page">
      <h2>Місцеві бізнеси</h2>

      <div className="search-bar">
        <input
          type="text"
          placeholder="Пошук бізнесу..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
        />
        <button onClick={handleSearch}>Шукати</button>
      </div>

      <div className="category-filter">
        <button onClick={() => dispatch(fetchBusinesses())}>Всі</button>
        {categories.map((cat) => (
          <button key={cat} onClick={() => dispatch(searchBusinesses(cat))}>{cat}</button>
        ))}
      </div>

      {loading && <p>Завантаження...</p>}

      <div className="business-grid">
        {businesses.map((b) => (
          <div key={b.id} className="business-card">
            <Link to={`/businesses/${b.id}`}>
              <h3>{b.name} {b.verified && '✓'}</h3>
            </Link>
            <p>{b.description}</p>
            <span className="category">{b.category}</span>
            <span className="location">{b.location}</span>
            <div className="rating">Rating: {b.rating.toFixed(1)}</div>
            <div className="actions">
              <button onClick={() => handleLike(b.id)}>👍 {b.likes}</button>
              <span>👎 {b.dislikes}</span>
              <span>{b.productCount} товарів</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
