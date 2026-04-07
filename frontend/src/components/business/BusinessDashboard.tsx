import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../state/store';
import { businessApi, productApi, orderApi, adCampaignApi, sellOutEventApi } from '../../services/api';
import { BusinessResponse, OrderResponse } from '../../types';

export default function BusinessDashboard() {
  const { user } = useSelector((state: RootState) => state.auth);
  const [businesses, setBusinesses] = useState<BusinessResponse[]>([]);
  const [orders, setOrders] = useState<OrderResponse[]>([]);

  useEffect(() => {
    if (user?.id) {
      businessApi.getByOwner(user.id).then((res) => setBusinesses(res.data));
    }
  }, [user]);

  useEffect(() => {
    if (businesses.length > 0) {
      orderApi.getByBusiness(businesses[0].id).then((res) => setOrders(res.data));
    }
  }, [businesses]);

  if (!user) return <p>Увійдіть, щоб переглянути кабінет.</p>;

  return (
    <div className="dashboard">
      <h2>Кабінет: {user.fullName}</h2>

      <section>
        <h3>Мої бізнеси</h3>
        {businesses.map((b) => (
          <div key={b.id} className="dashboard-business">
            <h4>{b.name}</h4>
            <p>Рейтинг: {b.rating.toFixed(1)} | Товарів: {b.productCount}</p>
          </div>
        ))}
      </section>

      <section>
        <h3>Останні замовлення</h3>
        {orders.length === 0 && <p>Немає замовлень</p>}
        {orders.slice(0, 10).map((o) => (
          <div key={o.id} className="order-row">
            <span>#{o.id}</span>
            <span>{o.customerName}</span>
            <span>{o.status}</span>
            <span>{o.totalAmount} грн</span>
          </div>
        ))}
      </section>
    </div>
  );
}
