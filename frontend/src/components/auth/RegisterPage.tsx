import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { register } from '../../state/authSlice';
import { AppDispatch, RootState } from '../../state/store';
import { UserRole } from '../../types';

export default function RegisterPage() {
  const [form, setForm] = useState({
    username: '', password: '', email: '', fullName: '', phone: '', role: UserRole.CITIZEN,
  });
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { loading, error } = useSelector((state: RootState) => state.auth);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const result = await dispatch(register(form));
    if (register.fulfilled.match(result)) {
      navigate('/businesses');
    }
  };

  const update = (field: string, value: string) => setForm({ ...form, [field]: value });

  return (
    <div className="auth-page">
      <h2>Реєстрація</h2>
      <form onSubmit={handleSubmit}>
        <input placeholder="Ім'я користувача" value={form.username} onChange={(e) => update('username', e.target.value)} required />
        <input type="password" placeholder="Пароль" value={form.password} onChange={(e) => update('password', e.target.value)} required />
        <input type="email" placeholder="Email" value={form.email} onChange={(e) => update('email', e.target.value)} required />
        <input placeholder="Повне ім'я" value={form.fullName} onChange={(e) => update('fullName', e.target.value)} required />
        <input placeholder="Телефон" value={form.phone} onChange={(e) => update('phone', e.target.value)} />
        <select value={form.role} onChange={(e) => update('role', e.target.value)}>
          <option value={UserRole.CITIZEN}>Громадянин</option>
          <option value={UserRole.BUSINESS_OWNER}>Власник бізнесу</option>
        </select>
        <button type="submit" disabled={loading}>{loading ? 'Реєстрація...' : 'Зареєструватися'}</button>
        {error && <p className="error">{error}</p>}
      </form>
    </div>
  );
}
