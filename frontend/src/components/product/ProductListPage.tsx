import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { productApi } from '../../services/api';
import { ProductResponse } from '../../types';

export default function ProductListPage() {
  const { id } = useParams<{ id: string }>();
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [search, setSearch] = useState('');

  useEffect(() => {
    if (id) {
      productApi.getByBusiness(Number(id)).then((res) => setProducts(res.data));
    }
  }, [id]);

  const handleSearch = () => {
    if (search.trim()) {
      productApi.search(search).then((res) => setProducts(res.data));
    }
  };

  return (
    <div className="product-list-page">
      <h2>Товари</h2>
      <div className="search-bar">
        <input placeholder="Пошук товару..." value={search} onChange={(e) => setSearch(e.target.value)} />
        <button onClick={handleSearch}>Шукати</button>
      </div>
      <div className="product-grid">
        {products.map((p) => (
          <div key={p.id} className="product-card">
            <h3>{p.name}</h3>
            <p>{p.description}</p>
            <div className="price">
              {p.discountPrice ? (
                <><del>{p.price} грн</del> <strong>{p.discountPrice} грн</strong></>
              ) : (
                <strong>{p.price} грн</strong>
              )}
            </div>
            <span>В наявності: {p.stockQuantity} {p.unit}</span>
            <span>Бізнес: {p.businessName}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
