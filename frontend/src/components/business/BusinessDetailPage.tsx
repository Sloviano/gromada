import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchBusinessById } from '../../state/businessSlice';
import { productApi, reviewApi } from '../../services/api';
import { AppDispatch, RootState } from '../../state/store';
import { ProductResponse } from '../../types';

export default function BusinessDetailPage() {
  const { id } = useParams<{ id: string }>();
  const dispatch = useDispatch<AppDispatch>();
  const { selectedBusiness } = useSelector((state: RootState) => state.business);
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [avgRating, setAvgRating] = useState<number | null>(null);

  useEffect(() => {
    if (id) {
      dispatch(fetchBusinessById(Number(id)));
      productApi.getAvailable(Number(id)).then((res) => setProducts(res.data));
      reviewApi.getAverageRating(Number(id)).then((res) => setAvgRating(res.data));
    }
  }, [id, dispatch]);

  if (!selectedBusiness) return <p>Завантаження...</p>;

  return (
    <div className="business-detail-page">
      <h2>{selectedBusiness.name}</h2>
      <p>{selectedBusiness.description}</p>
      <div className="info">
        <span>Категорія: {selectedBusiness.category}</span>
        <span>Телефон: {selectedBusiness.phone}</span>
        <span>Адреса: {selectedBusiness.location}</span>
        <span>Рейтинг: {avgRating?.toFixed(1) ?? selectedBusiness.rating.toFixed(1)}</span>
        <span>Графік: {selectedBusiness.operatingHours}</span>
      </div>

      <section>
        <h3>Товари ({products.length})</h3>
        <div className="product-grid">
          {products.map((p) => (
            <div key={p.id} className="product-card">
              <h4>{p.name}</h4>
              <p>{p.description}</p>
              <div className="price">
                {p.discountPrice ? (
                  <><del>{p.price} грн</del> <strong>{p.discountPrice} грн</strong></>
                ) : (
                  <strong>{p.price} грн</strong>
                )}
                {p.unit && <span> / {p.unit}</span>}
              </div>
              <span>В наявності: {p.stockQuantity}</span>
            </div>
          ))}
        </div>
      </section>

      <div className="actions">
        <Link to={`/chat?businessId=${selectedBusiness.id}`}>Написати повідомлення</Link>
      </div>
    </div>
  );
}
