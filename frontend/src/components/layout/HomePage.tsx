import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { announcementApi, sellOutEventApi } from '../../services/api';
import { Announcement, SellOutEvent } from '../../types';

export default function HomePage() {
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);
  const [events, setEvents] = useState<SellOutEvent[]>([]);

  useEffect(() => {
    announcementApi.getPinned().then((res) => setAnnouncements(res.data));
    sellOutEventApi.getActive().then((res) => setEvents(res.data));
  }, []);

  return (
    <div className="home-page">
      <h1>Почаївська Громада</h1>
      <p>Платформа для місцевого бізнесу та громади</p>

      <section>
        <h2>Важливі оголошення</h2>
        {announcements.map((a) => (
          <div key={a.id} className="announcement-card">
            <h3>{a.title}</h3>
            <p>{a.content}</p>
          </div>
        ))}
      </section>

      <section>
        <h2>Активні розпродажі</h2>
        {events.map((e) => (
          <div key={e.id} className="event-card">
            <h3>{e.title} — {e.discountPercent}% знижка</h3>
            <p>{e.description}</p>
          </div>
        ))}
      </section>

      <section className="quick-links">
        <Link to="/businesses">Переглянути бізнеси</Link>
        <Link to="/classifieds">Послуги</Link>
        <Link to="/jobs">Вакансії</Link>
      </section>
    </div>
  );
}
