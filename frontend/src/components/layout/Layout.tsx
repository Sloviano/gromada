import { Outlet, Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../state/store';
import { logout } from '../../state/authSlice';

export default function Layout() {
  const { user, isAuthenticated } = useSelector((state: RootState) => state.auth);
  const { unreadCount } = useSelector((state: RootState) => state.chat);
  const dispatch = useDispatch<AppDispatch>();

  return (
    <div className="app">
      <nav className="navbar">
        <Link to="/" className="nav-brand">Громада</Link>
        <div className="nav-links">
          <Link to="/businesses">Бізнеси</Link>
          <Link to="/classifieds">Оголошення</Link>
          <Link to="/jobs">Робота</Link>
          <Link to="/announcements">Новини</Link>
          {isAuthenticated && (
            <>
              <Link to="/chat">
                Чат {unreadCount > 0 && <span className="badge">{unreadCount}</span>}
              </Link>
              <Link to="/orders">Замовлення</Link>
              <Link to="/dashboard">Кабінет</Link>
              <button onClick={() => dispatch(logout())}>Вийти ({user?.fullName})</button>
            </>
          )}
          {!isAuthenticated && (
            <>
              <Link to="/login">Увійти</Link>
              <Link to="/register">Реєстрація</Link>
            </>
          )}
        </div>
      </nav>
      <main className="content">
        <Outlet />
      </main>
    </div>
  );
}
