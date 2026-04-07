import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { login } from '../../state/authSlice';
import { AppDispatch, RootState } from '../../state/store';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { loading, error } = useSelector((state: RootState) => state.auth);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const result = await dispatch(login({ username, password }));
    if (login.fulfilled.match(result)) {
      navigate('/businesses');
    }
  };

  return (
    <div className="auth-page">
      <h2>Увійти</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Ім'я користувача" value={username} onChange={(e) => setUsername(e.target.value)} required />
        <input type="password" placeholder="Пароль" value={password} onChange={(e) => setPassword(e.target.value)} required />
        <button type="submit" disabled={loading}>{loading ? 'Вхід...' : 'Увійти'}</button>
        {error && <p className="error">{error}</p>}
      </form>
    </div>
  );
}
