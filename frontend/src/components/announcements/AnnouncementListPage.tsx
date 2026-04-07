import { useEffect, useState } from 'react';
import { announcementApi } from '../../services/api';
import { Announcement, AnnouncementType } from '../../types';

export default function AnnouncementListPage() {
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);

  useEffect(() => {
    announcementApi.getAll().then((res) => setAnnouncements(res.data));
  }, []);

  return (
    <div className="announcement-list-page">
      <h2>Новини та оголошення громади</h2>
      <div className="announcement-list">
        {announcements.map((a) => (
          <div key={a.id} className={`announcement-card type-${a.type.toLowerCase()}`}>
            {a.pinned && <span className="pinned-badge">Закріплено</span>}
            <span className="type-badge">{a.type}</span>
            <h3>{a.title}</h3>
            <p>{a.content}</p>
            <small>{new Date(a.createdAt).toLocaleDateString('uk-UA')}</small>
          </div>
        ))}
      </div>
    </div>
  );
}
