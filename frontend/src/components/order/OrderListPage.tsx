import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../state/store';
import { orderApi } from '../../services/api';
import { OrderResponse } from '../../types';

export default function OrderListPage() {
  const { user } = useSelector((state: RootState) => state.auth);
  const [orders, setOrders] = useState<OrderResponse[]>([]);

  useEffect(() => {
    if (user?.id) {
      orderApi.getByCustomer(user.id).then((res) => setOrders(res.data));
    }
  }, [user]);

  const handleCancel = async (orderId: number) => {
    await orderApi.cancel(orderId);
    if (user?.id) {
      orderApi.getByCustomer(user.id).then((res) => setOrders(res.data));
    }
  };

  return (
    <div className="order-list-page">
      <h2>Мої замовлення</h2>
      {orders.length === 0 && <p>У вас поки немає замовлень.</p>}
      {orders.map((o) => (
        <div key={o.id} className="order-card">
          <h3>Замовлення #{o.id} — {o.businessName}</h3>
          <span className={`status status-${o.status.toLowerCase()}`}>{o.status}</span>
          <ul>
            {o.items.map((item) => (
              <li key={item.id}>{item.productName} x{item.quantity} = {item.subtotal} грн</li>
            ))}
          </ul>
          <p>Всього: {o.totalAmount} грн</p>
          <p>Адреса: {o.deliveryAddress}</p>
          {o.status === 'PENDING' && (
            <button onClick={() => handleCancel(o.id)}>Скасувати</button>
          )}
        </div>
      ))}
    </div>
  );
}
